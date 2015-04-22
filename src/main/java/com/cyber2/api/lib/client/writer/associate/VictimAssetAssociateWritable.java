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
public interface VictimAssetAssociateWritable<P> {

    public WriteListResponse<Integer> associateVictimAssetEmailAddresses(P uniqueId, List<Integer> assetIds) throws IOException;
    
    public WriteListResponse<Integer> associateVictimAssetEmailAddresses(P uniqueId, List<Integer> assetIds, String ownerName) 
        throws IOException;
        
    public ApiEntitySingleResponse associateVictimAssetEmailAddress(P uniqueId, Integer assetId) 
        throws IOException, FailedResponseException;
            
    public ApiEntitySingleResponse associateVictimAssetEmailAddress(P uniqueId, Integer assetId, String ownerName) 
        throws IOException, FailedResponseException;

    public WriteListResponse<Integer> associateVictimAssetNetworkAccounts(P uniqueId, List<Integer> assetIds) throws IOException;
    
    public WriteListResponse<Integer> associateVictimAssetNetworkAccounts(P uniqueId, List<Integer> assetIds, String ownerName) 
        throws IOException;
        
    public ApiEntitySingleResponse associateVictimAssetNetworkAccount(P uniqueId, Integer assetId) throws IOException, FailedResponseException;
            
    public ApiEntitySingleResponse associateVictimAssetNetworkAccount(P uniqueId, Integer assetId, String ownerName) 
        throws IOException, FailedResponseException;
        
    public WriteListResponse<Integer> associateVictimAssetPhoneNumbers(P uniqueId, List<Integer> assetIds) throws IOException;
    
    public WriteListResponse<Integer> associateVictimAssetPhoneNumbers(P uniqueId, List<Integer> assetIds, String ownerName)
        throws IOException;
        
    public ApiEntitySingleResponse associateVictimAssetPhoneNumber(P uniqueId, Integer assetId) throws IOException, FailedResponseException;
            
    public ApiEntitySingleResponse associateVictimAssetPhoneNumber(P uniqueId, Integer assetId, String ownerName)
            throws IOException, FailedResponseException;
        
    public WriteListResponse<Integer> associateVictimAssetSocialNetworks(P uniqueId, List<Integer> assetIds) throws IOException, FailedResponseException;
    
    public WriteListResponse<Integer> associateVictimAssetSocialNetworks(P uniqueId, List<Integer> assetIds, String ownerName) 
            throws IOException;
        
    public ApiEntitySingleResponse associateVictimAssetSocialNetwork(P uniqueId, Integer assetId) throws IOException, FailedResponseException;
            
    public ApiEntitySingleResponse associateVictimAssetSocialNetwork(P uniqueId, Integer assetId, String ownerName) 
            throws IOException, FailedResponseException;
        
    public WriteListResponse<Integer> associateVictimAssetWebsites(P uniqueId, List<Integer> assetIds) throws IOException;
    
    public WriteListResponse<Integer> associateVictimAssetWebsites(P uniqueId, List<Integer> assetIds, String ownerName) 
            throws IOException;
        
    public ApiEntitySingleResponse associateVictimAssetWebsite(P uniqueId, Integer assetId) throws IOException, FailedResponseException;
            
    public ApiEntitySingleResponse associateVictimAssetWebsite(P uniqueId, Integer assetId, String ownerName) 
            throws IOException, FailedResponseException;
    
    public WriteListResponse<Integer> dissociateVictimAssetEmailAddresses(P uniqueId, List<Integer> assetIds) throws IOException;
    
    public WriteListResponse<Integer> dissociateVictimAssetEmailAddresses(P uniqueId, List<Integer> assetIds, String ownerName)
        throws IOException;
        
    public ApiEntitySingleResponse dissociateVictimAssetEmailAddress(P uniqueId, Integer assetId)
        throws IOException, FailedResponseException;
            
    public ApiEntitySingleResponse dissociateVictimAssetEmailAddress(P uniqueId, Integer assetId, String ownerName)
        throws IOException, FailedResponseException;

    public WriteListResponse<Integer> dissociateVictimAssetNetworkAccounts(P uniqueId, List<Integer> assetIds) throws IOException;
    
    public WriteListResponse<Integer> dissociateVictimAssetNetworkAccounts(P uniqueId, List<Integer> assetIds, String ownerName)
        throws IOException;
        
    public ApiEntitySingleResponse dissociateVictimAssetNetworkAccount(P uniqueId, Integer assetId) throws IOException, FailedResponseException;
            
    public ApiEntitySingleResponse dissociateVictimAssetNetworkAccount(P uniqueId, Integer assetId, String ownerName)
        throws IOException, FailedResponseException;
        
    public WriteListResponse<Integer> dissociateVictimAssetPhoneNumbers(P uniqueId, List<Integer> assetIds) throws IOException;
    
    public WriteListResponse<Integer> dissociateVictimAssetPhoneNumbers(P uniqueId, List<Integer> assetIds, String ownerName)
        throws IOException;
        
    public ApiEntitySingleResponse dissociateVictimAssetPhoneNumber(P uniqueId, Integer assetId) throws IOException, FailedResponseException;
            
    public ApiEntitySingleResponse dissociateVictimAssetPhoneNumber(P uniqueId, Integer assetId, String ownerName)
            throws IOException, FailedResponseException;
        
    public WriteListResponse<Integer> dissociateVictimAssetSocialNetworks(P uniqueId, List<Integer> assetIds) throws IOException, FailedResponseException;
    
    public WriteListResponse<Integer> dissociateVictimAssetSocialNetworks(P uniqueId, List<Integer> assetIds, String ownerName)
            throws IOException;
        
    public ApiEntitySingleResponse dissociateVictimAssetSocialNetwork(P uniqueId, Integer assetId) throws IOException, FailedResponseException;
            
    public ApiEntitySingleResponse dissociateVictimAssetSocialNetwork(P uniqueId, Integer assetId, String ownerName)
            throws IOException, FailedResponseException;
        
    public WriteListResponse<Integer> dissociateVictimAssetWebsites(P uniqueId, List<Integer> assetIds) throws IOException;
    
    public WriteListResponse<Integer> dissociateVictimAssetWebsites(P uniqueId, List<Integer> assetIds, String ownerName)
            throws IOException;
        
    public ApiEntitySingleResponse dissociateVictimAssetWebsite(P uniqueId, Integer assetId) throws IOException, FailedResponseException;
            
    public ApiEntitySingleResponse dissociateVictimAssetWebsite(P uniqueId, Integer assetId, String ownerName)
            throws IOException, FailedResponseException;
    
}
