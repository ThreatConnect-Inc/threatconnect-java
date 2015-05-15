package com.threatconnect.sdk.client.writer;

import com.threatconnect.sdk.conn.Connection;
import com.threatconnect.sdk.conn.RequestExecutor;
import com.threatconnect.sdk.exception.FailedResponseException;
import com.threatconnect.sdk.server.entity.Document;
import com.threatconnect.sdk.server.response.entity.ApiEntitySingleResponse;
import com.threatconnect.sdk.server.response.entity.DocumentResponse;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by cblades on 4/20/2015.
 */
public class DocumentWriterAdapter extends AbstractGroupWriterAdapter<Document>
{
    protected DocumentWriterAdapter(Connection conn, RequestExecutor executor)
    {
        super(conn, executor, DocumentResponse.class);
    }

    @Override
    public String getUrlType()
    {
        return "documents";
    }


    public void uploadFile(int uniqueId, File file) throws FailedResponseException
    {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", uniqueId);
        uploadFile("v2.documents.upload", file, paramMap);
    }
}
