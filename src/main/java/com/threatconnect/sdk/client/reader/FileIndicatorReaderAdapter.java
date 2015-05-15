/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.client.reader;

import com.threatconnect.sdk.conn.Connection;
import com.threatconnect.sdk.conn.RequestExecutor;
import com.threatconnect.sdk.exception.FailedResponseException;
import com.threatconnect.sdk.server.entity.FileOccurrence;
import com.threatconnect.sdk.server.response.entity.FileListResponse;
import com.threatconnect.sdk.server.response.entity.FileOccurrenceResponse;
import com.threatconnect.sdk.server.response.entity.FileResponse;
import com.threatconnect.sdk.conn.Connection;
import com.threatconnect.sdk.server.entity.FileOccurrence;
import com.threatconnect.sdk.server.response.entity.FileListResponse;
import com.threatconnect.sdk.server.response.entity.FileOccurrenceResponse;

import java.io.IOException;
import java.util.Map;

/**
 *
 * @author dtineo
 */
public class FileIndicatorReaderAdapter<File> extends AbstractIndicatorReaderAdapter {

    public FileIndicatorReaderAdapter(Connection conn, RequestExecutor executor) {
        super(conn, executor, FileResponse.class, FileListResponse.class);

    }

    @Override
    public String getUrlType() {
        return "files";
    }

    public FileOccurrence getFileOccurrence(String uniqueId, Integer fileOccurrencId) throws IOException, FailedResponseException {
        return getFileOccurrence(uniqueId, fileOccurrencId, null);
    }

    public FileOccurrence getFileOccurrence(String uniqueId, Integer fileOccurrencId, String ownerName) throws IOException, FailedResponseException {
        Map<String, Object> map = createParamMap("id", uniqueId, "fileOccurrenceId", fileOccurrencId);
        FileOccurrenceResponse data = getItem(getUrlBasePrefix() + ".byId.fileOcurrences.byFileOccurrenceId", FileOccurrenceResponse.class, ownerName, map);

        return (FileOccurrence) data.getData().getData();
    }
}