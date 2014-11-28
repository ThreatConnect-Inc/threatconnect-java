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
public interface VictimAssetAssociateWritable<P> {

    public WriteListResponse<Integer> associateVictimAssetEmailAddresses(P uniqueId, List<Integer> assetIds) throws IOException;
    
    public WriteListResponse<Integer> associateVictimAssetEmailAddresses(P uniqueId, List<Integer> assetIds, String ownerName) 
        throws IOException;
        
    public boolean associateVictimAssetEmailAddress(P uniqueId, Integer assetId) 
        throws IOException, FailedResponseException;
            
    public boolean associateVictimAssetEmailAddress(P uniqueId, Integer assetId, String ownerName) 
        throws IOException, FailedResponseException;

    public WriteListResponse<Integer> associateVictimAssetNetworkAccounts(P uniqueId, List<Integer> assetIds) throws IOException;
    
    public WriteListResponse<Integer> associateVictimAssetNetworkAccounts(P uniqueId, List<Integer> assetIds, String ownerName) 
        throws IOException;
        
    public boolean associateVictimAssetNetworkAccount(P uniqueId, Integer assetId) throws IOException, FailedResponseException;
            
    public boolean associateVictimAssetNetworkAccount(P uniqueId, Integer assetId, String ownerName) 
        throws IOException, FailedResponseException;
        
    public WriteListResponse<Integer> associateVictimAssetPhoneNumbers(P uniqueId, List<Integer> assetIds) throws IOException;
    
    public WriteListResponse<Integer> associateVictimAssetPhoneNumbers(P uniqueId, List<Integer> assetIds, String ownerName)
        throws IOException;
        
    public boolean associateVictimAssetPhoneNumber(P uniqueId, Integer assetId) throws IOException, FailedResponseException;
            
    public boolean associateVictimAssetPhoneNumber(P uniqueId, Integer assetId, String ownerName)
            throws IOException, FailedResponseException;
        
    public WriteListResponse<Integer> associateVictimAssetSocialNetworks(P uniqueId, List<Integer> assetIds) throws IOException, FailedResponseException;
    
    public WriteListResponse<Integer> associateVictimAssetSocialNetworks(P uniqueId, List<Integer> assetIds, String ownerName) 
            throws IOException;
        
    public boolean associateVictimAssetSocialNetwork(P uniqueId, Integer assetId) throws IOException, FailedResponseException;
            
    public boolean associateVictimAssetSocialNetwork(P uniqueId, Integer assetId, String ownerName) 
            throws IOException, FailedResponseException;
        
    public WriteListResponse<Integer> associateVictimAssetWebsites(P uniqueId, List<Integer> assetIds) throws IOException;
    
    public WriteListResponse<Integer> associateVictimAssetWebsites(P uniqueId, List<Integer> assetIds, String ownerName) 
            throws IOException;
        
    public boolean associateVictimAssetWebsite(P uniqueId, Integer assetId) throws IOException, FailedResponseException;
            
    public boolean associateVictimAssetWebsite(P uniqueId, Integer assetId, String ownerName) 
            throws IOException, FailedResponseException;
    
    public WriteListResponse<Integer> deleteAssociatedVictimAssetEmailAddresses(P uniqueId, List<Integer> assetIds) throws IOException;
    
    public WriteListResponse<Integer> deleteAssociatedVictimAssetEmailAddresses(P uniqueId, List<Integer> assetIds, String ownerName) 
        throws IOException;
        
    public boolean deleteAssociatedVictimAssetEmailAddress(P uniqueId, Integer assetId) 
        throws IOException, FailedResponseException;
            
    public boolean deleteAssociatedVictimAssetEmailAddress(P uniqueId, Integer assetId, String ownerName) 
        throws IOException, FailedResponseException;

    public WriteListResponse<Integer> deleteAssociatedVictimAssetNetworkAccounts(P uniqueId, List<Integer> assetIds) throws IOException;
    
    public WriteListResponse<Integer> deleteAssociatedVictimAssetNetworkAccounts(P uniqueId, List<Integer> assetIds, String ownerName) 
        throws IOException;
        
    public boolean deleteAssociatedVictimAssetNetworkAccount(P uniqueId, Integer assetId) throws IOException, FailedResponseException;
            
    public boolean deleteAssociatedVictimAssetNetworkAccount(P uniqueId, Integer assetId, String ownerName) 
        throws IOException, FailedResponseException;
        
    public WriteListResponse<Integer> deleteAssociatedVictimAssetPhoneNumbers(P uniqueId, List<Integer> assetIds) throws IOException;
    
    public WriteListResponse<Integer> deleteAssociatedVictimAssetPhoneNumbers(P uniqueId, List<Integer> assetIds, String ownerName)
        throws IOException;
        
    public boolean deleteAssociatedVictimAssetPhoneNumber(P uniqueId, Integer assetId) throws IOException, FailedResponseException;
            
    public boolean deleteAssociatedVictimAssetPhoneNumber(P uniqueId, Integer assetId, String ownerName)
            throws IOException, FailedResponseException;
        
    public WriteListResponse<Integer> deleteAssociatedVictimAssetSocialNetworks(P uniqueId, List<Integer> assetIds) throws IOException, FailedResponseException;
    
    public WriteListResponse<Integer> deleteAssociatedVictimAssetSocialNetworks(P uniqueId, List<Integer> assetIds, String ownerName) 
            throws IOException;
        
    public boolean deleteAssociatedVictimAssetSocialNetwork(P uniqueId, Integer assetId) throws IOException, FailedResponseException;
            
    public boolean deleteAssociatedVictimAssetSocialNetwork(P uniqueId, Integer assetId, String ownerName) 
            throws IOException, FailedResponseException;
        
    public WriteListResponse<Integer> deleteAssociatedVictimAssetWebsites(P uniqueId, List<Integer> assetIds) throws IOException;
    
    public WriteListResponse<Integer> deleteAssociatedVictimAssetWebsites(P uniqueId, List<Integer> assetIds, String ownerName) 
            throws IOException;
        
    public boolean deleteAssociatedVictimAssetWebsite(P uniqueId, Integer assetId) throws IOException, FailedResponseException;
            
    public boolean deleteAssociatedVictimAssetWebsite(P uniqueId, Integer assetId, String ownerName) 
            throws IOException, FailedResponseException;
    
}
