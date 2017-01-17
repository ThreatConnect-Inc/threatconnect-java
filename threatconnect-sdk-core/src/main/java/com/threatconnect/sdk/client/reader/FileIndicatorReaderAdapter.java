/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.client.reader;

import com.threatconnect.sdk.client.response.IterableResponse;
import com.threatconnect.sdk.conn.Connection;
import com.threatconnect.sdk.exception.FailedResponseException;
import com.threatconnect.sdk.server.entity.File;
import com.threatconnect.sdk.server.entity.FileOccurrence;
import com.threatconnect.sdk.server.response.entity.FileListResponse;
import com.threatconnect.sdk.server.response.entity.FileOccurrenceListResponse;
import com.threatconnect.sdk.server.response.entity.FileOccurrenceResponse;
import com.threatconnect.sdk.server.response.entity.FileResponse;

import java.io.IOException;
import java.util.Map;

/**
 *
 * @author dtineo
 */
public class FileIndicatorReaderAdapter extends AbstractIndicatorReaderAdapter<File> {

    protected FileIndicatorReaderAdapter(Connection conn) {
        super(conn, FileResponse.class, File.class, FileListResponse.class);

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
        FileOccurrenceResponse data = getItem(getUrlBasePrefix() + ".byId.fileOccurrences.byFileOccurrenceId", FileOccurrenceResponse.class, ownerName, map);

        return (FileOccurrence) data.getData().getData();
    }

    public IterableResponse<FileOccurrence> getFileOccurrences(String fileHash) throws IOException, FailedResponseException {
        return getFileOccurrences(fileHash, null);
    }

    public IterableResponse<FileOccurrence> getFileOccurrences(String fileHash, String ownerName) throws IOException, FailedResponseException {
        Map<String, Object> map = createParamMap("id", fileHash);

        return getItems(getUrlBasePrefix() + ".byId.fileOccurrences", FileOccurrenceListResponse.class, FileOccurrence.class, ownerName, map);
    }

	

}