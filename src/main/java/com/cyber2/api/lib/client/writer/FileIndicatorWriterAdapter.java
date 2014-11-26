/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyber2.api.lib.client.writer;

import com.cyber2.api.lib.client.response.WriteListResponse;
import com.cyber2.api.lib.conn.Connection;
import com.cyber2.api.lib.conn.RequestExecutor;
import com.cyber2.api.lib.exception.FailedResponseException;
import com.cyber2.api.lib.server.entity.FileOccurrence;
import com.cyber2.api.lib.server.entity.File;
import com.cyber2.api.lib.server.response.entity.FileOccurrenceListResponse;
import com.cyber2.api.lib.server.response.entity.FileOccurrenceResponse;
import com.cyber2.api.lib.server.response.entity.FileResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author dtineo
 */
public class FileIndicatorWriterAdapter extends AbstractIndicatorWriterAdapter<File> {

    protected FileIndicatorWriterAdapter(Connection conn, RequestExecutor executor) {
        super(conn, executor,FileResponse.class);
    }

    @Override
    public String getUrlType() {
        return "files";
    }

    @Override
    public String getId(File indicator) {
        File file = ((File) indicator);
        String hash = file.getMd5();
        if (hash != null) {
            return hash;
        }

        hash = file.getSha1();
        if (hash != null) {
            return hash;
        }

        hash = file.getSha256();
        if (hash != null) {
            return hash;
        }

        return null;
    }
    
    public WriteListResponse<FileOccurrence> updateFileOccurrences(String fileHash, List<FileOccurrence> fileOccurrences)
        throws IOException {
        return updateFileOccurrences(fileHash, fileOccurrences, null);
    }

    public WriteListResponse<FileOccurrence> updateFileOccurrences(String fileHash, List<FileOccurrence> fileOccurrences, String ownerName)
        throws IOException {

        Map<String, Object> map = createParamMap("id", fileHash);
        List<Integer> idList = new ArrayList<>();
        for(FileOccurrence it : fileOccurrences)    idList.add( it.getId() );
        WriteListResponse<FileOccurrence> data = updateListWithParam("v2.indicators.type.byId.fileOccurrences.byFileOccurrenceId", FileOccurrenceListResponse.class, ownerName, map, "fileOccurrenceId", idList, fileOccurrences);

        return data;
    }

    public FileOccurrence updateFileOccurrence(String fileHash, FileOccurrence fileOccurrence) throws IOException, FailedResponseException {
        return updateFileOccurrence(fileHash, fileOccurrence, null);
    }

    public FileOccurrence updateFileOccurrence(String fileHash, FileOccurrence fileOccurrence, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", fileHash, "fileOccurrenceId", fileOccurrence.getId());
        FileOccurrenceResponse item = updateItem("v2.indicators.type.byId.fileOccurrences.byFileOccurrenceId"
                                            , FileOccurrenceResponse.class, ownerName, map, fileOccurrence);

        return (FileOccurrence) item.getData().getData();
    }

}
