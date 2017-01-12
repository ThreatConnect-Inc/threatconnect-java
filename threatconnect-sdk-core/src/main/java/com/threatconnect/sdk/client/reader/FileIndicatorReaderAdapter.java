/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.client.reader;

import com.threatconnect.sdk.client.response.IterableResponse;
import com.threatconnect.sdk.conn.Connection;
import com.threatconnect.sdk.conn.AbstractRequestExecutor;
import com.threatconnect.sdk.exception.FailedResponseException;
import com.threatconnect.sdk.server.entity.Address;
import com.threatconnect.sdk.server.entity.File;
import com.threatconnect.sdk.server.entity.FileOccurrence;
import com.threatconnect.sdk.server.entity.Host;
import com.threatconnect.sdk.server.entity.Indicator;
import com.threatconnect.sdk.server.entity.Url;
import com.threatconnect.sdk.server.response.entity.AddressListResponse;
import com.threatconnect.sdk.server.entity.Indicator;
import com.threatconnect.sdk.server.response.entity.FileListResponse;
import com.threatconnect.sdk.server.response.entity.FileOccurrenceListResponse;
import com.threatconnect.sdk.server.response.entity.FileOccurrenceResponse;
import com.threatconnect.sdk.server.response.entity.FileResponse;
import com.threatconnect.sdk.server.response.entity.HostListResponse;
import com.threatconnect.sdk.server.response.entity.IndicatorListResponse;
import com.threatconnect.sdk.server.response.entity.UrlListResponse;
import com.threatconnect.sdk.util.FileActionType;
import com.threatconnect.sdk.util.FileRelationshipIndicatorType;

import java.io.IOException;
import java.util.Map;
import java.util.logging.Logger;

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

    //get all indicators for specific file
    public IterableResponse<Indicator> getFileActionRelatedIndicators(String fileHash, FileActionType action, String ownerName) 
    		throws IOException, FailedResponseException {
    	Map<String, Object> map = createParamMap("uniqueId", fileHash,"fileActionType", action.getName());
        return getItems(getUrlBasePrefix() + ".byId.fileActionIndicators", IndicatorListResponse.class, Indicator.class, ownerName, map);
    }

    //get specific type of indicators for a specific file
    public IterableResponse<? extends Indicator> getFileActionRelatedIndicatorsByType(String fileHash, FileActionType action, FileRelationshipIndicatorType rType, String ownerName) 
    		throws IOException, FailedResponseException {
    	Map<String, Object> map = createParamMap("uniqueId", fileHash,"fileActionType", action.getName(), "indicatorType", rType.getName());
    	switch(rType) {
    	case ADDRESSES:
    		 return getItems(getUrlBasePrefix() + ".byId.fileActionSpecificTypeIndicators.get", AddressListResponse.class, Address.class, ownerName, map);
    		//break;
    	case FILES:
   		 	return getItems(getUrlBasePrefix() + ".byId.fileActionSpecificTypeIndicators.get", FileListResponse.class, File.class, ownerName, map);
    	case URLS:
   		 	return getItems(getUrlBasePrefix() + ".byId.fileActionSpecificTypeIndicators.get", UrlListResponse.class, Url.class, ownerName, map);
    	case HOSTS:
   		 	return getItems(getUrlBasePrefix() + ".byId.fileActionSpecificTypeIndicators.get", HostListResponse.class, Host.class, ownerName, map);
    	}
        throw new FailedResponseException("Not supported relationship type from TC client");
    }


    //get all files(returned type is indicator as implemented on the server side) for a specific indicator
    public IterableResponse<Indicator> getIndicatorRelatedFileAction(FileActionType action, FileRelationshipIndicatorType rType, String indicatorId, String ownerName) 
    		throws IOException, FailedResponseException {
    	 Map<String, Object> map = createParamMap("fileActionType", action.getName(), "indicatorType", rType.getName(), "indicatorId",indicatorId);

        return getItems(getUrlBasePrefix() + ".byId.indicatorFileAction.get", IndicatorListResponse.class, Indicator.class, ownerName, map);
    }
}