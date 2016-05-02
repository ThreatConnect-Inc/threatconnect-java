package com.threatconnect.sdk.parser.service.writer;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.threatconnect.sdk.client.writer.AbstractBatchWriterAdapter;
import com.threatconnect.sdk.client.writer.WriterAdapterFactory;
import com.threatconnect.sdk.conn.Connection;
import com.threatconnect.sdk.parser.model.Indicator;
import com.threatconnect.sdk.parser.service.bulk.BulkIndicatorConverter;
import com.threatconnect.sdk.server.entity.BatchConfig;
import com.threatconnect.sdk.server.entity.BatchConfig.Action;
import com.threatconnect.sdk.server.entity.BatchConfig.AttributeWriteType;
import com.threatconnect.sdk.server.response.entity.ApiEntitySingleResponse;

public class BatchIndicatorWriter extends Writer
{
	private final Collection<Indicator> source;
	
	public BatchIndicatorWriter(final Connection connection, final Collection<Indicator> source)
	{
		super(connection);
		this.source = source;
	}
	
	public void saveIndicators(final String ownerName) throws IOException
	{
		// create a new bulk indicator converter
		BulkIndicatorConverter converter = new BulkIndicatorConverter();
		JsonElement json = converter.convertToJson(source);
		
		// create the writer adapter
		AbstractBatchWriterAdapter<com.threatconnect.sdk.server.entity.Indicator> batchWriterAdapter =
			createWriterAdapter();
			
		ApiEntitySingleResponse<Integer, ?> batchConfigResponse =
			batchWriterAdapter.create(new BatchConfig(false, AttributeWriteType.Replace, Action.Create, ownerName));
			
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
			
			}
			else
			{
			
			}
		}
		else
		{
		
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
}
