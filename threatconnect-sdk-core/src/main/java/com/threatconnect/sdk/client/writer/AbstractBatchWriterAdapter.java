package com.threatconnect.sdk.client.writer;

import com.threatconnect.sdk.client.UrlTypeable;
import com.threatconnect.sdk.conn.Connection;
import com.threatconnect.sdk.exception.FailedResponseException;
import com.threatconnect.sdk.server.entity.BatchConfig;
import com.threatconnect.sdk.server.entity.BatchStatus;
import com.threatconnect.sdk.server.response.entity.ApiEntitySingleResponse;
import com.threatconnect.sdk.server.response.entity.BatchResponse;
import com.threatconnect.sdk.server.response.entity.BatchStatusResponse;
import com.threatconnect.sdk.server.response.entity.data.BatchResponseData;
import com.threatconnect.sdk.server.response.entity.data.BatchStatusResponseData;

import java.io.File;
import java.io.IOException;
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
        return create(item, null);
    }

    public ApiEntitySingleResponse create(BatchConfig item, String ownerName) throws IOException, FailedResponseException {

        ApiEntitySingleResponse data = createItem("v2.batch", BatchResponse.class, ownerName, null, item);

        return data;
    }

    public ApiEntitySingleResponse<BatchStatus, BatchStatusResponseData> uploadFile(int batchId, File file) throws IOException, FailedResponseException
    {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", batchId);
        ApiEntitySingleResponse<BatchStatus, BatchStatusResponseData> data = uploadFile("v2.batch.upload", BatchStatusResponse.class, file, paramMap);

        return data;
    }
}
