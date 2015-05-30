/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.client.reader.associate;

import com.threatconnect.sdk.client.reader.AbstractBaseReaderAdapter;
import com.threatconnect.sdk.conn.Connection;
import com.threatconnect.sdk.conn.AbstractRequestExecutor;
import com.threatconnect.sdk.exception.FailedResponseException;
import com.threatconnect.sdk.server.entity.VictimAsset;
import com.threatconnect.sdk.server.entity.VictimEmailAddress;
import com.threatconnect.sdk.server.entity.VictimNetworkAccount;
import com.threatconnect.sdk.server.entity.VictimPhone;
import com.threatconnect.sdk.server.entity.VictimSocialNetwork;
import com.threatconnect.sdk.server.entity.VictimWebSite;
import com.threatconnect.sdk.server.response.entity.VictimAssetListResponse;
import com.threatconnect.sdk.server.response.entity.VictimEmailAddressListResponse;
import com.threatconnect.sdk.server.response.entity.VictimEmailAddressResponse;
import com.threatconnect.sdk.server.response.entity.VictimNetworkAccountListResponse;
import com.threatconnect.sdk.server.response.entity.VictimNetworkAccountResponse;
import com.threatconnect.sdk.server.response.entity.VictimPhoneListResponse;
import com.threatconnect.sdk.server.response.entity.VictimPhoneResponse;
import com.threatconnect.sdk.server.response.entity.VictimSocialNetworkListResponse;
import com.threatconnect.sdk.server.response.entity.VictimSocialNetworkResponse;
import com.threatconnect.sdk.server.response.entity.VictimWebSiteListResponse;
import com.threatconnect.sdk.server.response.entity.VictimWebSiteResponse;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author dtineo
 */
public abstract class AbstractVictimAssetAssociateReaderAdapter<P> extends AbstractBaseReaderAdapter implements VictimAssetAssociateReadable<P> {

    public AbstractVictimAssetAssociateReaderAdapter(Connection conn, Class singleType, Class listType) {
        super(conn, singleType, listType);
    }

    @Override
    public List<VictimAsset> getAssociatedVictimAssets(P uniqueId, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", uniqueId);
        VictimAssetListResponse data = getList(getUrlBasePrefix() + ".byId.victimAssets", VictimAssetListResponse.class, ownerName, map);

        return (List<VictimAsset>) data.getData().getData();
    }

    @Override
    public List<VictimEmailAddress> getAssociatedVictimAssetEmailAddresses(P uniqueId) throws IOException, FailedResponseException {
        return getAssociatedVictimAssetEmailAddresses(uniqueId, null);
    }

    @Override
    public List<VictimEmailAddress> getAssociatedVictimAssetEmailAddresses(P uniqueId, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", uniqueId);
        VictimEmailAddressListResponse data = getList(getUrlBasePrefix() + ".byId.victimAssets.emailAddresses", VictimEmailAddressListResponse.class, ownerName, map);

        return (List<VictimEmailAddress>) data.getData().getData();
    }

    @Override
    public VictimEmailAddress getAssociatedVictimAssetEmailAddress(P uniqueId, Integer assetId) throws IOException, FailedResponseException {
        return getAssociatedVictimAssetEmailAddress(uniqueId, assetId, null);
    }

    @Override
    public VictimEmailAddress getAssociatedVictimAssetEmailAddress(P uniqueId, Integer assetId, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", uniqueId, "assetId", assetId);
        VictimEmailAddressResponse data = getItem(getUrlBasePrefix() + ".byId.victimAssets.emailAddresses.byAssetId", VictimEmailAddressResponse.class, ownerName, map);

        return (VictimEmailAddress) data.getData().getData();
    }

    @Override
    public List<VictimNetworkAccount> getAssociatedVictimAssetNetworkAccounts(P uniqueId) throws IOException, FailedResponseException {
        return getAssociatedVictimAssetNetworkAccounts(uniqueId, null);
    }

    @Override
    public List<VictimNetworkAccount> getAssociatedVictimAssetNetworkAccounts(P uniqueId, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", uniqueId);
        VictimNetworkAccountListResponse data = getList(getUrlBasePrefix() + ".byId.victimAssets.networkAccounts", VictimNetworkAccountListResponse.class, ownerName, map);

        return (List<VictimNetworkAccount>) data.getData().getData();
    }

    @Override
    public VictimNetworkAccount getAssociatedVictimAssetNetworkAccount(P uniqueId, Integer assetId) throws IOException, FailedResponseException {
        return getAssociatedVictimAssetNetworkAccount(uniqueId, assetId, null);
    }

    @Override
    public VictimNetworkAccount getAssociatedVictimAssetNetworkAccount(P uniqueId, Integer assetId, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", uniqueId, "assetId", assetId);
        VictimNetworkAccountResponse data = getItem(getUrlBasePrefix() + ".byId.victimAssets.networkAccounts.byAssetId", VictimNetworkAccountResponse.class, ownerName, map);

        return (VictimNetworkAccount) data.getData().getData();
    }

    @Override
    public List<VictimPhone> getAssociatedVictimAssetPhoneNumbers(P uniqueId) throws IOException, FailedResponseException {
        return getAssociatedVictimAssetPhoneNumbers(uniqueId, null);
    }

    @Override
    public List<VictimPhone> getAssociatedVictimAssetPhoneNumbers(P uniqueId, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", uniqueId);
        VictimPhoneListResponse data = getList(getUrlBasePrefix() + ".byId.victimAssets.phoneNumbers", VictimPhoneListResponse.class, ownerName, map);

        return (List<VictimPhone>) data.getData().getData();
    }

    @Override
    public VictimPhone getAssociatedVictimAssetPhoneNumber(P uniqueId, Integer assetId) throws IOException, FailedResponseException {
        return getAssociatedVictimAssetPhoneNumber(uniqueId, assetId, null);
    }

    @Override
    public VictimPhone getAssociatedVictimAssetPhoneNumber(P uniqueId, Integer assetId, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", uniqueId, "assetId", assetId);
        VictimPhoneResponse data = getItem(getUrlBasePrefix() + ".byId.victimAssets.phoneNumbers.byAssetId", VictimPhoneResponse.class, ownerName, map);

        return (VictimPhone) data.getData().getData();
    }

    @Override
    public List<VictimSocialNetwork> getAssociatedVictimAssetSocialNetworks(P uniqueId) throws IOException, FailedResponseException {
        return getAssociatedVictimAssetSocialNetworks(uniqueId, null);
    }

    @Override
    public List<VictimSocialNetwork> getAssociatedVictimAssetSocialNetworks(P uniqueId, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", uniqueId);
        VictimSocialNetworkListResponse data = getList(getUrlBasePrefix() + ".byId.victimAssets.socialNetworks", VictimSocialNetworkListResponse.class, ownerName, map);

        return (List<VictimSocialNetwork>) data.getData().getData();
    }

    @Override
    public VictimSocialNetwork getAssociatedVictimAssetSocialNetwork(P uniqueId, Integer assetId) throws IOException, FailedResponseException {
        return getAssociatedVictimAssetSocialNetwork(uniqueId, assetId, null);
    }

    @Override
    public VictimSocialNetwork getAssociatedVictimAssetSocialNetwork(P uniqueId, Integer assetId, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", uniqueId, "assetId", assetId);
        VictimSocialNetworkResponse data = getItem(getUrlBasePrefix() + ".byId.victimAssets.socialNetworks.byAssetId", VictimSocialNetworkResponse.class, ownerName, map);

        return (VictimSocialNetwork) data.getData().getData();
    }

    @Override
    public List<VictimWebSite> getAssociatedVictimAssetWebsites(P uniqueId) throws IOException, FailedResponseException {
        return getAssociatedVictimAssetWebsites(uniqueId, null);
    }

    @Override
    public List<VictimWebSite> getAssociatedVictimAssetWebsites(P uniqueId, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", uniqueId);
        VictimWebSiteListResponse data = getList(getUrlBasePrefix() + ".byId.victimAssets.websites", VictimWebSiteListResponse.class, ownerName, map);

        return (List<VictimWebSite>) data.getData().getData();
    }

    @Override
    public VictimWebSite getAssociatedVictimAssetWebsite(P uniqueId, Integer assetId) throws IOException, FailedResponseException {
        return getAssociatedVictimAssetWebsite(uniqueId, assetId, null);
    }

    @Override
    public VictimWebSite getAssociatedVictimAssetWebsite(P uniqueId, Integer assetId, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", uniqueId, "assetId", assetId);
        VictimWebSiteResponse data = getItem(getUrlBasePrefix() + ".byId.victimAssets.websites.byAssetId", VictimWebSiteResponse.class, ownerName, map);

        return (VictimWebSite) data.getData().getData();
    }

}
