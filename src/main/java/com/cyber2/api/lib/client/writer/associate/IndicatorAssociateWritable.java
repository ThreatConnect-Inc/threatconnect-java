/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyber2.api.lib.client.writer.associate;

import com.cyber2.api.lib.client.response.WriteListResponse;
import com.cyber2.api.lib.exception.FailedResponseException;
import com.cyber2.api.lib.server.response.entity.ApiEntitySingleResponse;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author dtineo
 */
public interface IndicatorAssociateWritable<P> {
    
    public WriteListResponse<String> associateIndicatorAddresses(P uniqueId, List<String> ipAddresses) throws IOException;

    public WriteListResponse<String> associateIndicatorAddresses(P uniqueId, List<String> ipAddresses, String ownerName) 
            throws IOException;

    public ApiEntitySingleResponse associateIndicatorAddress(P uniqueId, String ipAddress) throws IOException, FailedResponseException;

    public ApiEntitySingleResponse associateIndicatorAddress(P uniqueId, String ipAddress, String ownerName) 
            throws IOException, FailedResponseException;

    public WriteListResponse<String> associateIndicatorEmailAddresses(P uniqueId, List<String> emailAddresses) throws IOException;

    public WriteListResponse<String> associateIndicatorEmailAddresses(P uniqueId, List<String> emailAddresses, String ownerName) 
            throws IOException;

    public ApiEntitySingleResponse associateIndicatorEmailAddress(P uniqueId, String emailAddress) throws IOException, FailedResponseException;

    public ApiEntitySingleResponse associateIndicatorEmailAddress(P uniqueId, String emailAddress, String ownerName) 
            throws IOException, FailedResponseException;

    public WriteListResponse<String> associateIndicatorFiles(P uniqueId, List<String> fileHashes) throws IOException;

    public WriteListResponse<String> associateIndicatorFiles(P uniqueId, List<String> fileHashes, String ownerName)   
            throws IOException;

    public ApiEntitySingleResponse associateIndicatorFile(P uniqueId, String fileHash) throws IOException, FailedResponseException;

    public ApiEntitySingleResponse associateIndicatorFile(P uniqueId, String fileHash, String ownerName)   
            throws IOException, FailedResponseException;

    public WriteListResponse<String> associateIndicatorHosts(P uniqueId, List<String> hostNames) throws IOException;

    public WriteListResponse<String> associateIndicatorHosts(P uniqueId, List<String> hostNames, String ownerName)   
            throws IOException;

    public ApiEntitySingleResponse associateIndicatorHost(P uniqueId, String hostName) throws IOException, FailedResponseException;

    public ApiEntitySingleResponse associateIndicatorHost(P uniqueId, String hostName, String ownerName)   
            throws IOException, FailedResponseException;

    public WriteListResponse<String> associateIndicatorUrls(P uniqueId, List<String> urlTexts) throws IOException;

    public WriteListResponse<String> associateIndicatorUrls(P uniqueId, List<String> urlTexts, String ownerName)     
            throws IOException;

    public ApiEntitySingleResponse associateIndicatorUrl(P uniqueId, String urlText) throws IOException, FailedResponseException;

    public ApiEntitySingleResponse associateIndicatorUrl(P uniqueId, String urlText, String ownerName)     
            throws IOException, FailedResponseException;

    public WriteListResponse<String> deleteAssociatedIndicatorAddresses(P uniqueId, List<String> ipAddresses) throws IOException;

    public WriteListResponse<String> deleteAssociatedIndicatorAddresses(P uniqueId, List<String> ipAddresses, String ownerName) 
            throws IOException;

    public ApiEntitySingleResponse deleteAssociatedIndicatorAddress(P uniqueId, String ipAddress) throws IOException, FailedResponseException;

    public ApiEntitySingleResponse deleteAssociatedIndicatorAddress(P uniqueId, String ipAddress, String ownerName) 
            throws IOException, FailedResponseException;

    public WriteListResponse<String> deleteAssociatedIndicatorEmailAddresses(P uniqueId, List<String> emailAddresses) throws IOException;

    public WriteListResponse<String> deleteAssociatedIndicatorEmailAddresses(P uniqueId, List<String> emailAddresses, String ownerName) 
            throws IOException;

    public ApiEntitySingleResponse deleteAssociatedIndicatorEmailAddress(P uniqueId, String emailAddress) throws IOException, FailedResponseException;

    public ApiEntitySingleResponse deleteAssociatedIndicatorEmailAddress(P uniqueId, String emailAddress, String ownerName) 
            throws IOException, FailedResponseException;

    public WriteListResponse<String> deleteAssociatedIndicatorFiles(P uniqueId, List<String> fileHashes) throws IOException;

    public WriteListResponse<String> deleteAssociatedIndicatorFiles(P uniqueId, List<String> fileHashes, String ownerName)   
            throws IOException;

    public ApiEntitySingleResponse deleteAssociatedIndicatorFile(P uniqueId, String fileHash) throws IOException, FailedResponseException;

    public ApiEntitySingleResponse deleteAssociatedIndicatorFile(P uniqueId, String fileHash, String ownerName)   
            throws IOException, FailedResponseException;

    public WriteListResponse<String> deleteAssociatedIndicatorHosts(P uniqueId, List<String> hostNames) throws IOException;

    public WriteListResponse<String> deleteAssociatedIndicatorHosts(P uniqueId, List<String> hostNames, String ownerName)   
            throws IOException;

    public ApiEntitySingleResponse deleteAssociatedIndicatorHost(P uniqueId, String hostName) throws IOException, FailedResponseException;

    public ApiEntitySingleResponse deleteAssociatedIndicatorHost(P uniqueId, String hostName, String ownerName)   
            throws IOException, FailedResponseException;

    public WriteListResponse<String> deleteAssociatedIndicatorUrls(P uniqueId, List<String> urlTexts) throws IOException;

    public WriteListResponse<String> deleteAssociatedIndicatorUrls(P uniqueId, List<String> urlTexts, String ownerName)     
            throws IOException;

    public ApiEntitySingleResponse deleteAssociatedIndicatorUrl(P uniqueId, String urlText) throws IOException, FailedResponseException;

    public ApiEntitySingleResponse deleteAssociatedIndicatorUrl(P uniqueId, String urlText, String ownerName)     
            throws IOException, FailedResponseException;

}
