package com.threatconnect.sdk.client.writer;

import com.threatconnect.sdk.conn.Connection;
import com.threatconnect.sdk.exception.FailedResponseException;
import com.threatconnect.sdk.server.entity.Document;
import com.threatconnect.sdk.server.response.entity.ApiEntitySingleResponse;
import com.threatconnect.sdk.server.response.entity.DocumentResponse;
import com.threatconnect.sdk.util.UploadMethodType;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by cblades on 4/20/2015.
 */
public class DocumentWriterAdapter extends AbstractGroupWriterAdapter<Document>
{
	protected DocumentWriterAdapter(Connection conn)
	{
		super(conn, DocumentResponse.class);
	}
	
	@Override
	public String getUrlType()
	{
		return "documents";
	}
	
	public ApiEntitySingleResponse uploadFile(String uniqueId, File file, UploadMethodType uploadMethodType)
		throws IOException, FailedResponseException
	{
		return uploadFile(uniqueId, file, null, uploadMethodType, null);
	}
	
	public ApiEntitySingleResponse uploadFile(long uniqueId, File file, UploadMethodType uploadMethodType)
		throws IOException, FailedResponseException
	{
		return uploadFile(uniqueId, file, null, uploadMethodType);
	}
	
	public ApiEntitySingleResponse uploadFile(long uniqueId, File file, String ownerName,
		UploadMethodType uploadMethodType) throws IOException, FailedResponseException
	{
		return uploadFile(Long.toString(uniqueId), file, ownerName, uploadMethodType, null);
	}
	
	public ApiEntitySingleResponse uploadFile(String uniqueId, File file, String ownerName,
		UploadMethodType uploadMethodType, Boolean updateIfExists) throws IOException, FailedResponseException
	{
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("id", uniqueId);
		
		return uploadFile("v2.documents.upload", DocumentResponse.class, ownerName, new FileInputStream(file), paramMap,
			uploadMethodType, updateIfExists);
	}
	
	@Override
	public ApiEntitySingleResponse associateCustomIndicatorToIndicator(Long uniqueId, String targetId,
		String assciateType, String targetType) throws IOException, FailedResponseException
	{
		throw new RuntimeException("not implemented yet");
		//return null;
	}
	
}
