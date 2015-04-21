package com.cyber2.api.lib.client.writer;

import com.cyber2.api.lib.conn.Connection;
import com.cyber2.api.lib.conn.RequestExecutor;
import com.cyber2.api.lib.exception.FailedResponseException;
import com.cyber2.api.lib.server.entity.Document;
import com.cyber2.api.lib.server.response.entity.ApiEntitySingleResponse;
import com.cyber2.api.lib.server.response.entity.DocumentResponse;

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
