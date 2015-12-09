/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.client.reader.associate;

import com.threatconnect.sdk.client.response.IterableResponse;
import com.threatconnect.sdk.exception.FailedResponseException;
import com.threatconnect.sdk.server.entity.VictimAsset;
import com.threatconnect.sdk.server.entity.VictimEmailAddress;
import com.threatconnect.sdk.server.entity.VictimNetworkAccount;
import com.threatconnect.sdk.server.entity.VictimPhone;
import com.threatconnect.sdk.server.entity.VictimSocialNetwork;
import com.threatconnect.sdk.server.entity.VictimWebSite;
import java.io.IOException;

/**
 *
 * @author dtineo
 */
public interface VictimAssetAssociateReadable<P> {

    public IterableResponse<VictimAsset> getAssociatedVictimAssets(P uniqueId, String ownerName) throws IOException, FailedResponseException;

    public IterableResponse<VictimEmailAddress> getAssociatedVictimAssetEmailAddresses(P uniqueId) throws IOException, FailedResponseException;
    
    public IterableResponse<VictimEmailAddress> getAssociatedVictimAssetEmailAddresses(P uniqueId, String ownerName)
        throws IOException, FailedResponseException;
        
    public VictimEmailAddress getAssociatedVictimAssetEmailAddress(P uniqueId, Integer assetId) 
        throws IOException, FailedResponseException;
            
    public VictimEmailAddress getAssociatedVictimAssetEmailAddress(P uniqueId, Integer assetId, String ownerName) 
        throws IOException, FailedResponseException;

    public IterableResponse<VictimNetworkAccount> getAssociatedVictimAssetNetworkAccounts(P uniqueId) throws IOException, FailedResponseException;
    
    public IterableResponse<VictimNetworkAccount> getAssociatedVictimAssetNetworkAccounts(P uniqueId, String ownerName)
        throws IOException, FailedResponseException;
        
    public VictimNetworkAccount getAssociatedVictimAssetNetworkAccount(P uniqueId, Integer assetId) throws IOException, FailedResponseException;
            
    public VictimNetworkAccount getAssociatedVictimAssetNetworkAccount(P uniqueId, Integer assetId, String ownerName) 
        throws IOException, FailedResponseException;
        
    public IterableResponse<VictimPhone> getAssociatedVictimAssetPhoneNumbers(P uniqueId) throws IOException, FailedResponseException;
    
    public IterableResponse<VictimPhone> getAssociatedVictimAssetPhoneNumbers(P uniqueId, String ownerName)
        throws IOException, FailedResponseException;
        
    public VictimPhone getAssociatedVictimAssetPhoneNumber(P uniqueId, Integer assetId) throws IOException, FailedResponseException;
            
    public VictimPhone getAssociatedVictimAssetPhoneNumber(P uniqueId, Integer assetId, String ownerName)
            throws IOException, FailedResponseException;
        
    public IterableResponse<VictimSocialNetwork> getAssociatedVictimAssetSocialNetworks(P uniqueId) throws IOException, FailedResponseException;
    
    public IterableResponse<VictimSocialNetwork> getAssociatedVictimAssetSocialNetworks(P uniqueId, String ownerName)
            throws IOException, FailedResponseException;
        
    public VictimSocialNetwork getAssociatedVictimAssetSocialNetwork(P uniqueId, Integer assetId) throws IOException, FailedResponseException;
            
    public VictimSocialNetwork getAssociatedVictimAssetSocialNetwork(P uniqueId, Integer assetId, String ownerName) 
            throws IOException, FailedResponseException;
        
    public IterableResponse<VictimWebSite> getAssociatedVictimAssetWebsites(P uniqueId) throws IOException, FailedResponseException;
    
    public IterableResponse<VictimWebSite> getAssociatedVictimAssetWebsites(P uniqueId, String ownerName)
            throws IOException, FailedResponseException;
        
    public VictimWebSite getAssociatedVictimAssetWebsite(P uniqueId, Integer assetId) throws IOException, FailedResponseException;
            
    public VictimWebSite getAssociatedVictimAssetWebsite(P uniqueId, Integer assetId, String ownerName) 
            throws IOException, FailedResponseException;
    
}
