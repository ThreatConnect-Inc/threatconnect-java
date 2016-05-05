package com.threatconnect.sdk.parser.service.writer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.threatconnect.sdk.client.reader.BatchReaderAdapter;
import com.threatconnect.sdk.client.reader.ReaderAdapterFactory;
import com.threatconnect.sdk.client.writer.AbstractBatchWriterAdapter;
import com.threatconnect.sdk.client.writer.WriterAdapterFactory;
import com.threatconnect.sdk.conn.Connection;
import com.threatconnect.sdk.exception.FailedResponseException;
import com.threatconnect.sdk.parser.model.Indicator;
import com.threatconnect.sdk.parser.model.ItemType;
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

public class BatchIndicatorWriter extends Writer
{
	private static final Logger logger = LoggerFactory.getLogger(BatchIndicatorWriter.class);
	
	private final Collection<Indicator> source;
	
	public BatchIndicatorWriter(final Connection connection, final Collection<Indicator> source)
	{
		super(connection);
		this.source = source;
	}
	
	public SaveResults saveIndicators(final String ownerName) throws SaveItemFailedException, IOException
	{
		return writeIndicators(ownerName, AttributeWriteType.Replace, Action.Create);
	}
	
	public SaveResults saveIndicators(final String ownerName, final AttributeWriteType attributeWriteType)
		throws SaveItemFailedException, IOException
	{
		return writeIndicators(ownerName, attributeWriteType, Action.Create);
	}
	
	public SaveResults deleteIndicators(final String ownerName) throws SaveItemFailedException, IOException
	{
		return writeIndicators(ownerName, AttributeWriteType.Replace, Action.Delete);
	}
	
	private SaveResults writeIndicators(final String ownerName, final AttributeWriteType attributeWriteType,
		final Action action) throws SaveItemFailedException, IOException
	{
		try
		{
			// create a new bulk indicator converter
			BulkIndicatorConverter converter = new BulkIndicatorConverter();
			JsonElement json = converter.convertToJson(source);
			
			if (logger.isTraceEnabled())
			{
				logger.trace("Uploading Json Document:");
				logger.trace(new GsonBuilder().setPrettyPrinting().create().toJson(json));
			}
			
			// create the reader/writer adapter
			AbstractBatchWriterAdapter<com.threatconnect.sdk.server.entity.Indicator> batchWriterAdapter =
				createWriterAdapter();
			BatchReaderAdapter<com.threatconnect.sdk.server.entity.Indicator> batchReaderAdapter =
				createReaderAdapter();
				
			ApiEntitySingleResponse<Integer, ?> batchConfigResponse =
				batchWriterAdapter.create(new BatchConfig(false, attributeWriteType, action, ownerName));
				
			// check to see if the response was successful
			if (batchConfigResponse.isSuccess())
			{
				// retrieve the batch id and upload the file
				int batchID = batchConfigResponse.getItem();
				ApiEntitySingleResponse<?, ?> batchUploadResponse =
					batchWriterAdapter.uploadFile(batchID, jsonToInputStream(json));
					
				// check to see if the response was successful
				if (batchUploadResponse.isSuccess())
				{
					boolean processing = true;
					long delay = 1000L;
					
					// holds the response object
					ApiEntitySingleResponse<BatchStatus, BatchStatusResponseData> batchStatusResponse = null;
					
					// continue while the batch job is still processing
					while (processing)
					{
						try
						{
							logger.debug("Waiting {}ms for batch job: {}", delay, batchID);
							Thread.sleep(delay);
						}
						catch (InterruptedException e)
						{
							throw new IOException(e);
						}
						
						// check the status of the batch job
						batchStatusResponse = batchReaderAdapter.getStatus(batchID, ownerName);
						Status status = batchStatusResponse.getItem().getStatus();
						
						// this job is still considered processing as long as the status is not
						// completed
						processing = (status != Status.Completed);
						
						// increment the delay
						delay += delay;
					}
					
					// make sure the response is not null
					if (null != batchStatusResponse)
					{
						// check to see if there are errors
						if (batchStatusResponse.getItem().getErrorCount() > 0)
						{
							ByteArrayOutputStream baos = new ByteArrayOutputStream();
							batchReaderAdapter.downloadErrors(batchID, ownerName, baos);
							logger.warn(new String(baos.toByteArray()));
						}
						
						// create a new save result
						SaveResults saveResults = new SaveResults();
						saveResults.addFailedItems(ItemType.INDICATOR, batchStatusResponse.getItem().getErrorCount());
						return saveResults;
					}
					else
					{
						// this should never happen
						throw new IllegalStateException();
					}
				}
				else
				{
					throw new SaveItemFailedException(batchUploadResponse.getMessage());
				}
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
}
