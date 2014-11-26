/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyber2.api.lib.client.writer.associate;

import com.cyber2.api.lib.client.response.WriteListResponse;
import com.cyber2.api.lib.exception.FailedResponseException;
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

    public boolean associateIndicatorAddress(P uniqueId, String ipAddress) throws IOException, FailedResponseException;

    public boolean associateIndicatorAddress(P uniqueId, String ipAddress, String ownerName) 
            throws IOException, FailedResponseException;

    public WriteListResponse<String> associateIndicatorEmailAddresses(P uniqueId, List<String> emailAddresses) throws IOException;

    public WriteListResponse<String> associateIndicatorEmailAddresses(P uniqueId, List<String> emailAddresses, String ownerName) 
            throws IOException;

    public boolean associateIndicatorEmailAddress(P uniqueId, String emailAddress) throws IOException, FailedResponseException;

    public boolean associateIndicatorEmailAddress(P uniqueId, String emailAddress, String ownerName) 
            throws IOException, FailedResponseException;

    public WriteListResponse<String> associateIndicatorFiles(P uniqueId, List<String> fileHashes) throws IOException;

    public WriteListResponse<String> associateIndicatorFiles(P uniqueId, List<String> fileHashes, String ownerName)   
            throws IOException;

    public boolean associateIndicatorFile(P uniqueId, String fileHash) throws IOException, FailedResponseException;

    public boolean associateIndicatorFile(P uniqueId, String fileHash, String ownerName)   
            throws IOException, FailedResponseException;

    public WriteListResponse<String> associateIndicatorHosts(P uniqueId, List<String> hostNames) throws IOException;

    public WriteListResponse<String> associateIndicatorHosts(P uniqueId, List<String> hostNames, String ownerName)   
            throws IOException;

    public boolean associateIndicatorHost(P uniqueId, String hostName) throws IOException, FailedResponseException;

    public boolean associateIndicatorHost(P uniqueId, String hostName, String ownerName)   
            throws IOException, FailedResponseException;

    public WriteListResponse<String> associateIndicatorUrls(P uniqueId, List<String> urlTexts) throws IOException;

    public WriteListResponse<String> associateIndicatorUrls(P uniqueId, List<String> urlTexts, String ownerName)     
            throws IOException;

    public boolean associateIndicatorUrl(P uniqueId, String urlText) throws IOException, FailedResponseException;

    public boolean associateIndicatorUrl(P uniqueId, String urlText, String ownerName)     
            throws IOException, FailedResponseException;

}