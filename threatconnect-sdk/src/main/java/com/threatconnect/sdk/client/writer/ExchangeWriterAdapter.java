package com.threatconnect.sdk.client.writer;

import com.threatconnect.sdk.client.reader.AbstractReaderAdapter;
import com.threatconnect.sdk.conn.Connection;
import com.threatconnect.sdk.exception.FailedResponseException;
import com.threatconnect.sdk.server.entity.SpaceState;
import com.threatconnect.sdk.server.response.entity.SpaceStateResponse;

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
public class ExchangeWriterAdapter extends AbstractWriterAdapter
{

    public ExchangeWriterAdapter(Connection conn)
    {
        super(conn);
    }

    /*
    public void uploadSpacesFile(int spaceId, String fileName, Path inputPath) throws FailedResponseException
    {
        uploadSpacesFile(spaceId, fileName, null, inputPath);
    }

    public void uploadSpacesFile(int spaceId, String fileName, String ownerName, Path inputPath) throws FailedResponseException
    {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", spaceId);
        paramMap.put("fileName", fileName);

        try (InputStream in = uploadFile("v2.spaces.file", SpaceStateResponse.class, ownerName, paramMap))
        {
            Files.copy(in, outputPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e)
        {
            e.printStackTrace();
            throw new FailedResponseException("Failed to download file");
        }
    }
    */

    public SpaceState saveSpaceState(SpaceState spaceState, String ownerName) throws IOException, FailedResponseException {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", spaceState.getSpaceId());

        SpaceStateResponse data = createItem("v2.spaces.state", SpaceStateResponse.class, ownerName, paramMap, spaceState);

        return (SpaceState) data.getData().getData();
    }

}
