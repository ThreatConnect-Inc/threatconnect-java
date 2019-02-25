package com.threatconnect.sdk.client.writer;

import com.google.gson.Gson;
import com.threatconnect.sdk.client.UrlTypeable;
import com.threatconnect.sdk.conn.Connection;
import com.threatconnect.sdk.exception.FailedResponseException;
import com.threatconnect.sdk.server.entity.BatchConfig;
import com.threatconnect.sdk.server.entity.BatchStatus;
import com.threatconnect.sdk.server.response.entity.ApiEntitySingleResponse;
import com.threatconnect.sdk.server.response.entity.BatchResponse;
import com.threatconnect.sdk.server.response.entity.BatchStatusResponse;
import com.threatconnect.sdk.server.response.entity.data.BatchStatusResponseData;
import com.threatconnect.sdk.util.UploadMethodType;
import org.apache.http.HttpEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dtineo on 6/26/15.
 */
public abstract class AbstractBatchWriterAdapter<T> extends AbstractWriterAdapter implements UrlTypeable
{
	
	public AbstractBatchWriterAdapter(Connection conn)
	{
		super(conn);
	}
	
	public ApiEntitySingleResponse create(BatchConfig item) throws IOException, FailedResponseException
	{
		return create(item, item.getOwner());
	}
	
	public ApiEntitySingleResponse create(BatchConfig item, String ownerName)
		throws IOException, FailedResponseException
	{
		
		ApiEntitySingleResponse data = createItem("v2.batch", BatchResponse.class, ownerName, null, item);
		
		return data;
	}
	
	public ApiEntitySingleResponse<BatchStatus, BatchStatusResponseData> uploadFile(long batchId, File file,
		UploadMethodType uploadMethodType) throws IOException, FailedResponseException
	{
		return uploadFile(batchId, new FileInputStream(file), uploadMethodType);
	}
	
	public ApiEntitySingleResponse<BatchStatus, BatchStatusResponseData> uploadFile(long batchId,
		InputStream inputStream, UploadMethodType uploadMethodType) throws IOException, FailedResponseException
	{
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("id", batchId);
		ApiEntitySingleResponse<BatchStatus, BatchStatusResponseData> data =
			uploadFile("v2.batch.upload", BatchStatusResponse.class, inputStream, paramMap, uploadMethodType);
		
		return data;
	}
	
	public ApiEntitySingleResponse<BatchStatus, BatchStatusResponseData> createAndUploadFile(BatchConfig batchConfig,
		String contents, UploadMethodType uploadMethodType) throws IOException, FailedResponseException
	{
		HttpEntity multiPartEntity = MultipartEntityBuilder.create()
			.addPart("config", new StringBody(new Gson().toJson(batchConfig)))
			.addPart("content",new StringBody(contents))
			.build();
		
		ApiEntitySingleResponse<BatchStatus, BatchStatusResponseData> data =
			uploadFile("v2.batch.createAndUpload", BatchStatusResponse.class, batchConfig.getOwner(), multiPartEntity,
				null, uploadMethodType, null, null);
		
		return data;
	}
}
