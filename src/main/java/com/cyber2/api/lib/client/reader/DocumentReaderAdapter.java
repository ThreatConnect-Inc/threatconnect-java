package com.cyber2.api.lib.client.reader;

import com.cyber2.api.lib.conn.Connection;
import com.cyber2.api.lib.conn.RequestExecutor;
import com.cyber2.api.lib.exception.FailedResponseException;
import com.cyber2.api.lib.server.entity.Document;
import com.cyber2.api.lib.server.entity.File;
import com.cyber2.api.lib.server.response.entity.ApiEntityListResponse;
import com.cyber2.api.lib.server.response.entity.ApiEntitySingleResponse;
import com.cyber2.api.lib.server.response.entity.DocumentListResponse;
import com.cyber2.api.lib.server.response.entity.DocumentResponse;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by cblades on 4/20/2015.
 */
public class DocumentReaderAdapter extends AbstractGroupReaderAdapter<Document> {

    protected DocumentReaderAdapter(Connection conn, RequestExecutor executor) {
        super(conn, executor, DocumentResponse.class, DocumentListResponse.class);
    }

    @Override
    protected String getUrlBasePrefix() {
        return "v2.documents";
    }

    @Override
    public String getUrlType()
    {
        return "documents";
    }

    public void downloadFile(int uniqueId, String ownerName, Path outputPath) throws FailedResponseException
    {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", uniqueId);
        try (InputStream in = getFile("v2.documents.download", ownerName, paramMap))
        {
            Files.copy(in, outputPath);
        } catch (IOException e)
        {
            throw new FailedResponseException("Failed to download file");
        }
    }
}
