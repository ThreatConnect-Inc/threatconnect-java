/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.client.writer;

import com.threatconnect.sdk.client.response.WriteListResponse;
import com.threatconnect.sdk.conn.Connection;
import com.threatconnect.sdk.conn.RequestExecutor;
import com.threatconnect.sdk.exception.FailedResponseException;
import com.threatconnect.sdk.server.entity.FileOccurrence;
import com.threatconnect.sdk.server.entity.File;
import com.threatconnect.sdk.server.response.entity.FileOccurrenceListResponse;
import com.threatconnect.sdk.server.response.entity.FileOccurrenceResponse;
import com.threatconnect.sdk.server.response.entity.FileResponse;
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
        WriteListResponse<FileOccurrence> data = updateListWithParam(getUrlBasePrefix() + ".byId.fileOccurrences.byFileOccurrenceId", FileOccurrenceListResponse.class, ownerName, map, "fileOccurrenceId", idList, fileOccurrences);

        return data;
    }

    public FileOccurrence updateFileOccurrence(String fileHash, FileOccurrence fileOccurrence) throws IOException, FailedResponseException {
        return updateFileOccurrence(fileHash, fileOccurrence, null);
    }

    public FileOccurrence updateFileOccurrence(String fileHash, FileOccurrence fileOccurrence, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", fileHash, "fileOccurrenceId", fileOccurrence.getId());
        FileOccurrenceResponse item = updateItem(getUrlBasePrefix() + ".byId.fileOccurrences.byFileOccurrenceId"
                                            , FileOccurrenceResponse.class, ownerName, map, fileOccurrence);

        return (FileOccurrence) item.getData().getData();
    }

}