package com.threatconnect.sdk.parser.service.writer;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.threatconnect.sdk.client.writer.AbstractBatchWriterAdapter;
import com.threatconnect.sdk.conn.Connection;
import com.threatconnect.sdk.exception.FailedResponseException;
import com.threatconnect.sdk.model.Item;
import com.threatconnect.sdk.parser.service.save.SaveItemFailedException;
import com.threatconnect.sdk.server.entity.BatchConfig;
import com.threatconnect.sdk.server.entity.BatchConfig.Action;
import com.threatconnect.sdk.server.entity.BatchConfig.AttributeWriteType;
import com.threatconnect.sdk.server.response.entity.ApiEntitySingleResponse;
import com.threatconnect.sdk.util.UploadMethodType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collection;

public class SynchonousBatchWriter extends BatchWriter
{
	private static final Logger logger = LoggerFactory.getLogger(BatchWriter.class);
	
	public SynchonousBatchWriter(final Connection connection, final Collection<? extends Item> source)
	{
		super(connection, source);
	}
	
	@Override
	protected BatchUploadResponse uploadIndicators(final JsonElement json,
		final String ownerName, final AttributeWriteType attributeWriteType, final Action action, int batchIndex,
		int batchTotal, BatchConfig.Version version) throws SaveItemFailedException, IOException
	{
		//only version 2 is supported
		if(BatchConfig.Version.V2.equals(version))
		{
			try
			{
				if (logger.isTraceEnabled())
				{
					logger.trace("Uploading Json Document {}/{}:", batchIndex, batchTotal);
					logger.trace(new GsonBuilder().setPrettyPrinting().create().toJson(json));
				}
				
				// create the reader/writer adapter
				AbstractBatchWriterAdapter<com.threatconnect.sdk.server.entity.Indicator> batchWriterAdapter =
					createWriterAdapter();
				
				@SuppressWarnings("unchecked")
				ApiEntitySingleResponse<Integer, ?> batchConfigResponse =
					batchWriterAdapter.create(new BatchConfig(false, attributeWriteType, action, ownerName, version));
				
				// check to see if the response was successful
				if (batchConfigResponse.isSuccess())
				{
					// retrieve the batch id and upload the file
					Integer batchID = batchConfigResponse.getItem();
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
		else
		{
			return super.uploadIndicators(json, ownerName, attributeWriteType, action, batchIndex, batchTotal, version);
		}
	}
}
