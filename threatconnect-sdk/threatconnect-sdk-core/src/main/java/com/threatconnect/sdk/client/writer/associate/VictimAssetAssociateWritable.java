/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.client.writer.associate;

import com.threatconnect.sdk.client.response.WriteListResponse;
import com.threatconnect.sdk.exception.FailedResponseException;
import com.threatconnect.sdk.server.response.entity.ApiEntitySingleResponse;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author dtineo
 */
public interface VictimAssetAssociateWritable<P> {

    public WriteListResponse<Long> associateVictimAssetEmailAddresses(P uniqueId, List<Long> assetIds) throws IOException;
    
    public WriteListResponse<Long> associateVictimAssetEmailAddresses(P uniqueId, List<Long> assetIds, String ownerName) 
        throws IOException;
        
    public ApiEntitySingleResponse associateVictimAssetEmailAddress(P uniqueId, Long assetId) 
        throws IOException, FailedResponseException;
            
    public ApiEntitySingleResponse associateVictimAssetEmailAddress(P uniqueId, Long assetId, String ownerName) 
        throws IOException, FailedResponseException;

    public WriteListResponse<Long> associateVictimAssetNetworkAccounts(P uniqueId, List<Long> assetIds) throws IOException;
    
    public WriteListResponse<Long> associateVictimAssetNetworkAccounts(P uniqueId, List<Long> assetIds, String ownerName) 
        throws IOException;
        
    public ApiEntitySingleResponse associateVictimAssetNetworkAccount(P uniqueId, Long assetId) throws IOException, FailedResponseException;
            
    public ApiEntitySingleResponse associateVictimAssetNetworkAccount(P uniqueId, Long assetId, String ownerName) 
        throws IOException, FailedResponseException;
        
    public WriteListResponse<Long> associateVictimAssetPhoneNumbers(P uniqueId, List<Long> assetIds) throws IOException;
    
    public WriteListResponse<Long> associateVictimAssetPhoneNumbers(P uniqueId, List<Long> assetIds, String ownerName)
        throws IOException;
        
    public ApiEntitySingleResponse associateVictimAssetPhoneNumber(P uniqueId, Long assetId) throws IOException, FailedResponseException;
            
    public ApiEntitySingleResponse associateVictimAssetPhoneNumber(P uniqueId, Long assetId, String ownerName)
            throws IOException, FailedResponseException;
        
    public WriteListResponse<Long> associateVictimAssetSocialNetworks(P uniqueId, List<Long> assetIds) throws IOException, FailedResponseException;
    
    public WriteListResponse<Long> associateVictimAssetSocialNetworks(P uniqueId, List<Long> assetIds, String ownerName) 
            throws IOException;
        
    public ApiEntitySingleResponse associateVictimAssetSocialNetwork(P uniqueId, Long assetId) throws IOException, FailedResponseException;
            
    public ApiEntitySingleResponse associateVictimAssetSocialNetwork(P uniqueId, Long assetId, String ownerName) 
            throws IOException, FailedResponseException;
        
    public WriteListResponse<Long> associateVictimAssetWebsites(P uniqueId, List<Long> assetIds) throws IOException;
    
    public WriteListResponse<Long> associateVictimAssetWebsites(P uniqueId, List<Long> assetIds, String ownerName) 
            throws IOException;
        
    public ApiEntitySingleResponse associateVictimAssetWebsite(P uniqueId, Long assetId) throws IOException, FailedResponseException;
            
    public ApiEntitySingleResponse associateVictimAssetWebsite(P uniqueId, Long assetId, String ownerName) 
            throws IOException, FailedResponseException;
    
    public WriteListResponse<Long> dissociateVictimAssetEmailAddresses(P uniqueId, List<Long> assetIds) throws IOException;
    
    public WriteListResponse<Long> dissociateVictimAssetEmailAddresses(P uniqueId, List<Long> assetIds, String ownerName)
        throws IOException;
        
    public ApiEntitySingleResponse dissociateVictimAssetEmailAddress(P uniqueId, Long assetId)
        throws IOException, FailedResponseException;
            
    public ApiEntitySingleResponse dissociateVictimAssetEmailAddress(P uniqueId, Long assetId, String ownerName)
        throws IOException, FailedResponseException;

    public WriteListResponse<Long> dissociateVictimAssetNetworkAccounts(P uniqueId, List<Long> assetIds) throws IOException;
    
    public WriteListResponse<Long> dissociateVictimAssetNetworkAccounts(P uniqueId, List<Long> assetIds, String ownerName)
        throws IOException;
        
    public ApiEntitySingleResponse dissociateVictimAssetNetworkAccount(P uniqueId, Long assetId) throws IOException, FailedResponseException;
            
    public ApiEntitySingleResponse dissociateVictimAssetNetworkAccount(P uniqueId, Long assetId, String ownerName)
        throws IOException, FailedResponseException;
        
    public WriteListResponse<Long> dissociateVictimAssetPhoneNumbers(P uniqueId, List<Long> assetIds) throws IOException;
    
    public WriteListResponse<Long> dissociateVictimAssetPhoneNumbers(P uniqueId, List<Long> assetIds, String ownerName)
        throws IOException;
        
    public ApiEntitySingleResponse dissociateVictimAssetPhoneNumber(P uniqueId, Long assetId) throws IOException, FailedResponseException;
            
    public ApiEntitySingleResponse dissociateVictimAssetPhoneNumber(P uniqueId, Long assetId, String ownerName)
            throws IOException, FailedResponseException;
        
    public WriteListResponse<Long> dissociateVictimAssetSocialNetworks(P uniqueId, List<Long> assetIds) throws IOException, FailedResponseException;
    
    public WriteListResponse<Long> dissociateVictimAssetSocialNetworks(P uniqueId, List<Long> assetIds, String ownerName)
            throws IOException, Exception;
        
    public ApiEntitySingleResponse dissociateVictimAssetSocialNetwork(P uniqueId, Long assetId) throws IOException, FailedResponseException;
            
    public ApiEntitySingleResponse dissociateVictimAssetSocialNetwork(P uniqueId, Long assetId, String ownerName)
            throws IOException, FailedResponseException;
        
    public WriteListResponse<Long> dissociateVictimAssetWebsites(P uniqueId, List<Long> assetIds) throws Exception;
    
    public WriteListResponse<Long> dissociateVictimAssetWebsites(P uniqueId, List<Long> assetIds, String ownerName)
            throws IOException;
        
    public ApiEntitySingleResponse dissociateVictimAssetWebsite(P uniqueId, Long assetId) throws IOException, FailedResponseException;
            
    public ApiEntitySingleResponse dissociateVictimAssetWebsite(P uniqueId, Long assetId, String ownerName)
            throws IOException, FailedResponseException;
    
}
