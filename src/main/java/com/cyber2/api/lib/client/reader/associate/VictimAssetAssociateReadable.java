/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyber2.api.lib.client.reader.associate;

import com.cyber2.api.lib.exception.FailedResponseException;
import com.cyber2.api.lib.server.entity.VictimAsset;
import com.cyber2.api.lib.server.entity.VictimEmailAddress;
import com.cyber2.api.lib.server.entity.VictimNetworkAccount;
import com.cyber2.api.lib.server.entity.VictimPhone;
import com.cyber2.api.lib.server.entity.VictimSocialNetwork;
import com.cyber2.api.lib.server.entity.VictimWebSite;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author dtineo
 */
public interface VictimAssetAssociateReadable<P> {

    public List<VictimAsset> getAssociatedVictimAssets(P uniqueId, String ownerName) throws IOException, FailedResponseException;

    public List<VictimEmailAddress> getAssociatedVictimAssetEmailAddresses(P uniqueId) throws IOException, FailedResponseException;
    
    public List<VictimEmailAddress> getAssociatedVictimAssetEmailAddresses(P uniqueId, String ownerName) 
        throws IOException, FailedResponseException;
        
    public VictimEmailAddress getAssociatedVictimAssetEmailAddress(P uniqueId, Integer assetId) 
        throws IOException, FailedResponseException;
            
    public VictimEmailAddress getAssociatedVictimAssetEmailAddress(P uniqueId, Integer assetId, String ownerName) 
        throws IOException, FailedResponseException;

    public List<VictimNetworkAccount> getAssociatedVictimAssetNetworkAccounts(P uniqueId) throws IOException, FailedResponseException;
    
    public List<VictimNetworkAccount> getAssociatedVictimAssetNetworkAccounts(P uniqueId, String ownerName) 
        throws IOException, FailedResponseException;
        
    public VictimNetworkAccount getAssociatedVictimAssetNetworkAccount(P uniqueId, Integer assetId) throws IOException, FailedResponseException;
            
    public VictimNetworkAccount getAssociatedVictimAssetNetworkAccount(P uniqueId, Integer assetId, String ownerName) 
        throws IOException, FailedResponseException;
        
    public List<VictimPhone> getAssociatedVictimAssetPhoneNumbers(P uniqueId) throws IOException, FailedResponseException;
    
    public List<VictimPhone> getAssociatedVictimAssetPhoneNumbers(P uniqueId, String ownerName)
        throws IOException, FailedResponseException;
        
    public VictimPhone getAssociatedVictimAssetPhoneNumber(P uniqueId, Integer assetId) throws IOException, FailedResponseException;
            
    public VictimPhone getAssociatedVictimAssetPhoneNumber(P uniqueId, Integer assetId, String ownerName)
            throws IOException, FailedResponseException;
        
    public List<VictimSocialNetwork> getAssociatedVictimAssetSocialNetworks(P uniqueId) throws IOException, FailedResponseException;
    
    public List<VictimSocialNetwork> getAssociatedVictimAssetSocialNetworks(P uniqueId, String ownerName) 
            throws IOException, FailedResponseException;
        
    public VictimSocialNetwork getAssociatedVictimAssetSocialNetwork(P uniqueId, Integer assetId) throws IOException, FailedResponseException;
            
    public VictimSocialNetwork getAssociatedVictimAssetSocialNetwork(P uniqueId, Integer assetId, String ownerName) 
            throws IOException, FailedResponseException;
        
    public List<VictimWebSite> getAssociatedVictimAssetWebsites(P uniqueId) throws IOException, FailedResponseException;
    
    public List<VictimWebSite> getAssociatedVictimAssetWebsites(P uniqueId, String ownerName) 
            throws IOException, FailedResponseException;
        
    public VictimWebSite getAssociatedVictimAssetWebsite(P uniqueId, Integer assetId) throws IOException, FailedResponseException;
            
    public VictimWebSite getAssociatedVictimAssetWebsite(P uniqueId, Integer assetId, String ownerName) 
            throws IOException, FailedResponseException;
    
}
