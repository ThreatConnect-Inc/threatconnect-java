package com.threatconnect.sdk.client.reader;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.threatconnect.sdk.conn.Connection;
import com.threatconnect.sdk.exception.FailedResponseException;
import com.threatconnect.sdk.server.entity.BatchStatus;
import com.threatconnect.sdk.server.response.entity.ApiEntitySingleResponse;
import com.threatconnect.sdk.server.response.entity.BatchStatusResponse;
import com.threatconnect.sdk.server.response.entity.data.BatchStatusResponseData;

/**
 * Created by dtineo on 6/26/15.
 */
public class BatchReaderAdapter<T> extends AbstractReaderAdapter
{
	private static final Logger logger = LoggerFactory.getLogger(BatchReaderAdapter.class);
	
	public BatchReaderAdapter(Connection conn)
	{
		super(conn);
	}
	
	public ApiEntitySingleResponse<BatchStatus, BatchStatusResponseData> getStatus(long batchId)
		throws IOException, FailedResponseException
	{
		return getStatus(batchId, null);
	}
	
	public ApiEntitySingleResponse<BatchStatus, BatchStatusResponseData> getStatus(long batchId, String ownerName)
		throws IOException, FailedResponseException
	{
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("id", batchId);
		ApiEntitySingleResponse<BatchStatus, BatchStatusResponseData> data =
			getItem("v2.batch.status", BatchStatusResponse.class, ownerName, paramMap);
			
		return data;
	}
	
	public void downloadErrors(long batchId, Path outputPath) throws FailedResponseException, FileNotFoundException
	{
		downloadErrors(batchId, null, outputPath);
	}
	
	public void downloadErrors(long batchId, String ownerName, Path outputPath) throws FailedResponseException, FileNotFoundException
	{
		downloadErrors(batchId, ownerName, new FileOutputStream(outputPath.toFile()));
	}
	
	public void downloadErrors(long batchId, String ownerName, OutputStream output) throws FailedResponseException
	{
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("id", batchId);
		try (InputStream in = getFile("v2.batch.errors", ownerName, paramMap))
		{
			IOUtils.copy(in, output);
		}
		catch (IOException e)
		{
			logger.error(e.getMessage(), e);
			throw new FailedResponseException("Failed to download file");
		}
	}
}
