package com.threatconnect.sdk.client.reader;

import com.threatconnect.sdk.conn.Connection;
import com.threatconnect.sdk.conn.AbstractRequestExecutor;
import com.threatconnect.sdk.exception.FailedResponseException;
import com.threatconnect.sdk.server.entity.Document;
import com.threatconnect.sdk.server.response.entity.DocumentListResponse;
import com.threatconnect.sdk.server.response.entity.DocumentResponse;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by cblades on 4/20/2015.
 */
public class DocumentReaderAdapter extends AbstractGroupReaderAdapter<Document> {

    protected DocumentReaderAdapter(Connection conn) {
        super(conn, DocumentResponse.class, Document.class, DocumentListResponse.class);
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
