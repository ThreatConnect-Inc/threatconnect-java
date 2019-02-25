package com.threatconnect.sdk.parser.service.writer;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.threatconnect.sdk.app.exception.TCMessageException;
import com.threatconnect.sdk.client.reader.BatchReaderAdapter;
import com.threatconnect.sdk.client.reader.ReaderAdapterFactory;
import com.threatconnect.sdk.client.writer.AbstractBatchWriterAdapter;
import com.threatconnect.sdk.client.writer.WriterAdapterFactory;
import com.threatconnect.sdk.conn.Connection;
import com.threatconnect.sdk.conn.exception.HttpResourceNotFoundException;
import com.threatconnect.sdk.exception.FailedResponseException;
import com.threatconnect.sdk.model.Group;
import com.threatconnect.sdk.model.Indicator;
import com.threatconnect.sdk.model.Item;
import com.threatconnect.sdk.model.ItemType;
import com.threatconnect.sdk.model.serialize.BatchItemSerializer;
import com.threatconnect.sdk.model.util.ItemUtil;
import com.threatconnect.sdk.parser.service.bulk.BulkIndicatorConverter;
import com.threatconnect.sdk.parser.service.save.SaveItemFailedException;
import com.threatconnect.sdk.parser.service.save.SaveResults;
import com.threatconnect.sdk.server.entity.BatchConfig;
import com.threatconnect.sdk.server.entity.BatchConfig.Action;
import com.threatconnect.sdk.server.entity.BatchConfig.AttributeWriteType;
import com.threatconnect.sdk.server.entity.BatchStatus;
import com.threatconnect.sdk.server.entity.BatchStatus.Status;
import com.threatconnect.sdk.server.response.entity.ApiEntitySingleResponse;
import com.threatconnect.sdk.server.response.entity.data.BatchStatusResponseData;
import com.threatconnect.sdk.util.UploadMethodType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BatchWriter extends AbstractBatchWriter
{
	public static final int DEFAULT_BATCH_LIMIT = 25000;
	
	public static final int POLL_TIMEOUT_MINUTES = 120;
	private static final long POLL_INITIAL_DELAY = 1000L;
	private static final long POLL_MAX_DELAY = 30000L;
	
	private static final Logger logger = LoggerFactory.getLogger(BatchWriter.class);
	
	private final Collection<? extends Item> source;
	
	// determines the max number of indicators per batch file. If there are more indicators than
	// this limit, multiple batch files are created
	private int indicatorLimitPerBatch;
	
	public BatchWriter(final Connection connection, final Collection<? extends Item> source)
	{
		super(connection);
		this.source = source;
		this.indicatorLimitPerBatch = DEFAULT_BATCH_LIMIT;
	}
	
	public SaveResults save(final String ownerName) throws SaveItemFailedException, IOException
	{
		return writeV2(ownerName, AttributeWriteType.Replace);
	}
	
	public SaveResults save(final String ownerName, final AttributeWriteType attributeWriteType)
		throws SaveItemFailedException, IOException
	{
		return writeV2(ownerName, attributeWriteType);
	}
	
	public SaveResults deleteIndicators(final String ownerName) throws SaveItemFailedException, IOException
	{
		//split all of the items based on groups and indicators
		Set<Group> groups = new HashSet<Group>();
		Set<Indicator> indicators = new HashSet<Indicator>();
		ItemUtil.separateGroupsAndIndicators(source, groups, indicators);
		
		// make sure that the indicator limit is a positive number and that the number of indicators
		// in the source exceeds the limit
		if (indicatorLimitPerBatch > 0 && source.size() > indicatorLimitPerBatch)
		{
			// the indicators need to be broken up into multiple batch files
			// partition the list of indicators
			final List<List<Indicator>> indicatorListPartitions =
				Lists.partition(new ArrayList<Indicator>(indicators), indicatorLimitPerBatch);
			
			logger.debug("Splitting batch files");
			
			// retrieve the total records
			final int total = indicatorListPartitions.size();
			
			// holds the list of future results
			final SaveResults saveResults = new SaveResults();
			final List<BatchUploadResponse> responses = new ArrayList<BatchUploadResponse>(total);
			
			// for each partition in the list
			for (int i = 0; i < total; i++)
			{
				final int index = i + 1;
				final List<Indicator> partition = indicatorListPartitions.get(i);
				
				// create a new bulk indicator converter
				logger.trace("Marshalling indicator list to JSON {}/{}", index, total);
				BulkIndicatorConverter converter = new BulkIndicatorConverter();
				JsonElement json = converter.convertToJson(partition);
				
				// upload the batch indicators and add it to the list
				BatchUploadResponse batchUploadResponse = uploadIndicators(json, ownerName,
					AttributeWriteType.Replace, Action.Delete, index, total, BatchConfig.Version.V1);
				responses.add(batchUploadResponse);
			}
			
			// for each of the upload responses
			for (int i = 0; i < responses.size(); i++)
			{
				final int index = i + 1;
				BatchUploadResponse batchUploadResponse = responses.get(i);
				
				// poll the batch until it is done and add merge these save results
				SaveResults results = pollBatch(batchUploadResponse, ownerName, index, responses.size());
				saveResults.addFailedItems(results);
			}
			
			return saveResults;
		}
		// there are fewer indicators than the limit so it can all be run in one batch
		else
		{
			// create a new bulk indicator converter
			logger.trace("Marshalling indicator list to JSON {}/{}", 1, 1);
			BulkIndicatorConverter converter = new BulkIndicatorConverter();
			JsonElement json = converter.convertToJson(indicators);
			
			BatchUploadResponse batchUploadResponse =
				uploadIndicators(json, ownerName, AttributeWriteType.Replace, Action.Delete, 1, 1,
					BatchConfig.Version.V1);
			return pollBatch(batchUploadResponse, ownerName, 1, 1);
		}
	}
	
	private SaveResults writeV2(final String ownerName, final AttributeWriteType attributeWriteType)
		throws SaveItemFailedException, IOException
	{
		// holds the list of future results
		final SaveResults saveResults = new SaveResults();
		
		// create a new bulk indicator converter
		logger.trace("Marshalling indicator list to JSON");
		BatchItemSerializer batchItemSerializer = new BatchItemSerializer(source);
		JsonElement json = batchItemSerializer.convertToJson();
		
		// upload the batch indicators and add it to the list
		BatchUploadResponse batchUploadResponse = uploadIndicators(json, ownerName,
			attributeWriteType, Action.Create, 1, 1, BatchConfig.Version.V2);
		
		// poll the batch until it is done and add merge these save results
		SaveResults results = pollBatch(batchUploadResponse, ownerName, 1, 1);
		saveResults.addFailedItems(results);
		
		return saveResults;
	}
	
	protected BatchUploadResponse uploadIndicators(final JsonElement json,
		final String ownerName, final AttributeWriteType attributeWriteType, final Action action, int batchIndex,
		int batchTotal, BatchConfig.Version version) throws SaveItemFailedException, IOException
	{
		if (logger.isTraceEnabled())
		{
			logger.trace("Uploading Json Document {}/{}:", batchIndex, batchTotal);
			logger.trace(new GsonBuilder().setPrettyPrinting().create().toJson(json));
		}
		
		return uploadIndicators(json, ownerName, attributeWriteType, action, version);
	}
	
	protected BatchUploadResponse uploadIndicators(final JsonElement json,
		final String ownerName, final AttributeWriteType attributeWriteType, final Action action,
		BatchConfig.Version version) throws SaveItemFailedException, IOException
	{
		try
		{
			// create the reader/writer adapter
			AbstractBatchWriterAdapter<com.threatconnect.sdk.server.entity.Indicator> batchWriterAdapter =
				createWriterAdapter();
			
			@SuppressWarnings("unchecked")
			ApiEntitySingleResponse<Long, ?> batchConfigResponse =
				batchWriterAdapter.create(new BatchConfig(false, attributeWriteType, action, ownerName, version));
			
			// check to see if the response was successful
			if (batchConfigResponse.isSuccess())
			{
				// retrieve the batch id and upload the file
				Long batchID = batchConfigResponse.getItem();
				ApiEntitySingleResponse<?, ?> batchUploadResponse =
					batchWriterAdapter.uploadFile(batchID, jsonToInputStream(json), UploadMethodType.POST);
				return new BatchUploadResponse(batchID, batchUploadResponse);
			}
			else
			{
				throw new SaveItemFailedException(batchConfigResponse.getMessage());
			}
		}
		catch (FailedResponseException e)
		{
			throw new SaveItemFailedException(e);
		}
	}
	
	protected InputStream jsonToInputStream(final JsonElement json)
	{
		byte[] bytes = new Gson().toJson(json).getBytes();
		return new ByteArrayInputStream(bytes);
	}
	
	/**
	 * A convenience method for creating a writer adapter for this class
	 *
	 * @return the writer adapter for this indicator
	 */
	protected AbstractBatchWriterAdapter<com.threatconnect.sdk.server.entity.Indicator> createWriterAdapter()
	{
		return WriterAdapterFactory.createBatchIndicatorWriter(connection);
	}
	
	protected BatchReaderAdapter<com.threatconnect.sdk.server.entity.Indicator> createReaderAdapter()
	{
		return ReaderAdapterFactory.createIndicatorBatchReader(connection);
	}
	
	public int getIndicatorLimitPerBatch()
	{
		return indicatorLimitPerBatch;
	}
	
	public void setIndicatorLimitPerBatch(int indicatorLimitPerBatch)
	{
		this.indicatorLimitPerBatch = indicatorLimitPerBatch;
	}
}
