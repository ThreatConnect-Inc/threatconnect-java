/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyber2.api.lib.client.reader;

import com.cyber2.api.lib.conn.Connection;
import com.cyber2.api.lib.conn.RequestExecutor;
import com.cyber2.api.lib.exception.FailedResponseException;
import com.cyber2.api.lib.server.entity.FileOccurrence;
import com.cyber2.api.lib.server.response.entity.FileListResponse;
import com.cyber2.api.lib.server.response.entity.FileOccurrenceResponse;
import com.cyber2.api.lib.server.response.entity.FileResponse;
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