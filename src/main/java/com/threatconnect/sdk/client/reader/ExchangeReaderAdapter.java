package com.threatconnect.sdk.client.reader;

import com.threatconnect.sdk.conn.Connection;
import com.threatconnect.sdk.exception.FailedResponseException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dtineo on 9/12/15.
 */
public class ExchangeReaderAdapter extends AbstractReaderAdapter
{

    public ExchangeReaderAdapter(Connection conn)
    {
        super(conn);
    }

    public void downloadSpacesFile(int spaceId, String fileName, Path outputPath) throws FailedResponseException
    {
        downloadSpacesFile(spaceId, fileName, null, outputPath);
    }

    public void downloadSpacesFile(int spaceId, String fileName, String ownerName, Path outputPath) throws FailedResponseException
    {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", spaceId);
        paramMap.put("fileName", fileName);
        try (InputStream in = getFile("v2.spaces.file", ownerName, paramMap))
        {
            Files.copy(in, outputPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e)
        {
            e.printStackTrace();
            throw new FailedResponseException("Failed to download file");
        }
    }

}
