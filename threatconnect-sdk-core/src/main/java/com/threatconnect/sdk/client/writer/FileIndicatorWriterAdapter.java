/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.client.writer;

import com.threatconnect.sdk.client.response.WriteListResponse;
import com.threatconnect.sdk.conn.Connection;
import com.threatconnect.sdk.conn.AbstractRequestExecutor;
import com.threatconnect.sdk.exception.FailedResponseException;
import com.threatconnect.sdk.server.entity.FileOccurrence;
import com.threatconnect.sdk.server.entity.File;
import com.threatconnect.sdk.server.response.entity.ApiEntitySingleResponse;
import com.threatconnect.sdk.server.response.entity.FileOccurrenceListResponse;
import com.threatconnect.sdk.server.response.entity.FileOccurrenceResponse;
import com.threatconnect.sdk.server.response.entity.FileResponse;
import com.threatconnect.sdk.util.FileActionType;
import com.threatconnect.sdk.util.FileRelationshipIndicatorType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author dtineo
 */
public class FileIndicatorWriterAdapter extends AbstractIndicatorWriterAdapter<File> {

    protected FileIndicatorWriterAdapter(Connection conn) {
        super(conn, FileResponse.class);
    }

    @Override
    public String getUrlType() {
        return "files";
    }

    @Override
    public String getId(File file) {
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

    public FileOccurrence createFileOccurrence(String fileHash, FileOccurrence fileOccurrence) throws IOException, FailedResponseException {
        return createFileOccurrence(fileHash, fileOccurrence, null);
    }

    public FileOccurrence createFileOccurrence(String fileHash, FileOccurrence fileOccurrence, String ownerName)
            throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", fileHash);
        FileOccurrenceResponse item = createItem(getUrlBasePrefix() + ".byId.fileOccurrences"
                , FileOccurrenceResponse.class, ownerName, map, fileOccurrence);

        return (FileOccurrence) item.getData().getData();
    }

    public ApiEntitySingleResponse deleteFileOccurrence(String fileHash, FileOccurrence fileOccurrence) throws IOException, FailedResponseException {
        return deleteFileOccurrence(fileHash, fileOccurrence, null);
    }

    public ApiEntitySingleResponse deleteFileOccurrence(String fileHash, FileOccurrence fileOccurrence, String ownerName)
            throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", fileHash, "fileOccurrenceId", fileOccurrence.getId());
        ApiEntitySingleResponse item = deleteItem(getUrlBasePrefix() + ".byId.fileOccurrences.byFileOccurrenceId"
                , FileOccurrenceResponse.class, ownerName, map);

        return item;
    }
    
    public ApiEntitySingleResponse createFileActionIndicatorRelationship(String fileHash, FileActionType action, FileRelationshipIndicatorType rType, String indicatorId, String ownerName)
            throws IOException, FailedResponseException {
        Map<String, Object> map = createParamMap("uniqueId", fileHash,"fileActionType", action.getName(), "indicatorType", rType.getName(), "indicatorId",indicatorId);
        //I will reuse fileReponse here instead of creating a new one. 
        FileResponse item = createItem(getUrlBasePrefix() + ".byId.fileActionSpecificTypeIndicators.set", FileResponse.class, ownerName, map, null);
        return item;
    }

    public ApiEntitySingleResponse deleteFileActionIndicatorRelationship(String fileHash, FileActionType action, FileRelationshipIndicatorType rType, String indicatorId, String ownerName)
    		throws IOException, FailedResponseException {
    	Map<String, Object> map = createParamMap("uniqueId", fileHash,"fileActionType", action.getName(), "indicatorType", rType.getName(), "indicatorId",indicatorId);
        ApiEntitySingleResponse item = deleteItem(getUrlBasePrefix() + ".byId.fileActionSpecificTypeIndicators.set", FileResponse.class, ownerName, map);

        return item;
    }

    
    public ApiEntitySingleResponse createIndicatorFileActionRelationship(String fileHash, FileActionType action, FileRelationshipIndicatorType rType, String indicatorId, String ownerName)
            throws IOException, FailedResponseException {
        Map<String, Object> map = createParamMap("uniqueId", fileHash,"fileActionType", action.getName(), "indicatorType", rType.getName(), "indicatorId",indicatorId);
        //I will reuse fileReponse here instead of creating a new one. 
        FileResponse item = createItem(getUrlBasePrefix() + ".byId.indicatorFileAction.set", FileResponse.class, ownerName, map, null);

        return item;
    }

    public ApiEntitySingleResponse deleteIndicatorFileActionRelationship(String fileHash, FileActionType action, FileRelationshipIndicatorType rType, String indicatorId, String ownerName)
    		throws IOException, FailedResponseException {
    	Map<String, Object> map = createParamMap("uniqueId", fileHash,"fileActionType", action.getName(), "indicatorType", rType.getName(), "indicatorId",indicatorId);
        ApiEntitySingleResponse item = deleteItem(getUrlBasePrefix() + ".byId.indicatorFileAction.set", FileResponse.class, ownerName, map);

        return item;
    }

}