package com.threatconnect.sdk.client.reader;

import com.threatconnect.sdk.client.UrlTypeable;
import com.threatconnect.sdk.client.writer.AbstractWriterAdapter;
import com.threatconnect.sdk.conn.Connection;
import com.threatconnect.sdk.exception.FailedResponseException;
import com.threatconnect.sdk.server.entity.BatchConfig;
import com.threatconnect.sdk.server.entity.BatchStatus;
import com.threatconnect.sdk.server.response.entity.ApiEntitySingleResponse;
import com.threatconnect.sdk.server.response.entity.BatchResponse;
import com.threatconnect.sdk.server.response.entity.BatchStatusResponse;
import com.threatconnect.sdk.server.response.entity.data.BatchStatusResponseData;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dtineo on 6/26/15.
 */
public class BatchReaderAdapter<T> extends AbstractReaderAdapter
{

    public BatchReaderAdapter(Connection conn)
    {
        super(conn);
    }

    public ApiEntitySingleResponse<BatchStatus, BatchStatusResponseData> getStatus(int batchId) throws IOException, FailedResponseException
    {
        return getStatus(batchId, null);
    }

    public ApiEntitySingleResponse<BatchStatus, BatchStatusResponseData> getStatus(int batchId, String ownerName) throws IOException, FailedResponseException
    {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", batchId);
        ApiEntitySingleResponse<BatchStatus, BatchStatusResponseData> data = getItem("v2.batch.status", BatchStatusResponse.class, ownerName, paramMap);

        return data;
    }

    public void downloadErrors(int batchId, Path outputPath) throws FailedResponseException
    {
        downloadErrors(batchId, null, outputPath);
    }

    public void downloadErrors(int batchId, String ownerName, Path outputPath) throws FailedResponseException
    {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", batchId);
        try (InputStream in = getFile("v2.batch.errors", ownerName, paramMap))
        {
            Files.copy(in, outputPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e)
        {
            e.printStackTrace();
            throw new FailedResponseException("Failed to download file");
        }
    }

}
