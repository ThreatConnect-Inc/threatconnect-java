/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.client.reader.associate;

import com.threatconnect.sdk.client.UrlTypeable;
import com.threatconnect.sdk.client.reader.AbstractBaseReaderAdapter;
import com.threatconnect.sdk.client.response.IterableResponse;
import com.threatconnect.sdk.conn.Connection;
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
import java.util.Map;

/**
 *
 * @author dtineo
 */
public abstract class AbstractVictimAssetAssociateReaderAdapter<P> extends AbstractBaseReaderAdapter implements VictimAssetAssociateReadable<P>, UrlTypeable
{

    public AbstractVictimAssetAssociateReaderAdapter(Connection conn, Class singleType, Class singleItemType, Class listType) {
        super(conn, singleType, singleItemType, listType);
    }

    @Override
    public IterableResponse<VictimAsset> getAssociatedVictimAssets(P uniqueId, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", uniqueId, "type", getUrlType());
        return getItems(getUrlBasePrefix() + ".byId.victimAssets", VictimAssetListResponse.class, VictimAsset.class, ownerName, map);
    }

    @Override
    public IterableResponse<VictimEmailAddress> getAssociatedVictimAssetEmailAddresses(P uniqueId) throws IOException, FailedResponseException {
        return getAssociatedVictimAssetEmailAddresses(uniqueId, null);
    }

    @Override
    public IterableResponse<VictimEmailAddress> getAssociatedVictimAssetEmailAddresses(P uniqueId, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", uniqueId, "type", getUrlType());
        return getItems(getUrlBasePrefix() + ".byId.victimAssets.emailAddresses", VictimEmailAddressListResponse.class, VictimEmailAddress.class, ownerName, map);
    }

    @Override
    public VictimEmailAddress getAssociatedVictimAssetEmailAddress(P uniqueId, Integer assetId) throws IOException, FailedResponseException {
        return getAssociatedVictimAssetEmailAddress(uniqueId, assetId, null);
    }

    @Override
    public VictimEmailAddress getAssociatedVictimAssetEmailAddress(P uniqueId, Integer assetId, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", uniqueId, "assetId", assetId, "type", getUrlType());
        VictimEmailAddressResponse data = getItem(getUrlBasePrefix() + ".byId.victimAssets.emailAddresses.byAssetId", VictimEmailAddressResponse.class, ownerName, map);

        return (VictimEmailAddress) data.getData().getData();
    }

    @Override
    public IterableResponse<VictimNetworkAccount> getAssociatedVictimAssetNetworkAccounts(P uniqueId) throws IOException, FailedResponseException {
        return getAssociatedVictimAssetNetworkAccounts(uniqueId, null);
    }

    @Override
    public IterableResponse<VictimNetworkAccount> getAssociatedVictimAssetNetworkAccounts(P uniqueId, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", uniqueId, "type", getUrlType());
        return getItems(getUrlBasePrefix() + ".byId.victimAssets.networkAccounts", VictimNetworkAccountListResponse.class, VictimNetworkAccount.class, ownerName, map);
    }

    @Override
    public VictimNetworkAccount getAssociatedVictimAssetNetworkAccount(P uniqueId, Integer assetId) throws IOException, FailedResponseException {
        return getAssociatedVictimAssetNetworkAccount(uniqueId, assetId, null);
    }

    @Override
    public VictimNetworkAccount getAssociatedVictimAssetNetworkAccount(P uniqueId, Integer assetId, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", uniqueId, "assetId", assetId, "type", getUrlType());
        VictimNetworkAccountResponse data = getItem(getUrlBasePrefix() + ".byId.victimAssets.networkAccounts.byAssetId", VictimNetworkAccountResponse.class, ownerName, map);

        return (VictimNetworkAccount) data.getData().getData();
    }

    @Override
    public IterableResponse<VictimPhone> getAssociatedVictimAssetPhoneNumbers(P uniqueId) throws IOException, FailedResponseException {
        return getAssociatedVictimAssetPhoneNumbers(uniqueId, null);
    }

    @Override
    public IterableResponse<VictimPhone> getAssociatedVictimAssetPhoneNumbers(P uniqueId, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", uniqueId, "type", getUrlType());
        return getItems(getUrlBasePrefix() + ".byId.victimAssets.phoneNumbers", VictimPhoneListResponse.class, VictimPhone.class, ownerName, map);
    }

    @Override
    public VictimPhone getAssociatedVictimAssetPhoneNumber(P uniqueId, Integer assetId) throws IOException, FailedResponseException {
        return getAssociatedVictimAssetPhoneNumber(uniqueId, assetId, null);
    }

    @Override
    public VictimPhone getAssociatedVictimAssetPhoneNumber(P uniqueId, Integer assetId, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", uniqueId, "assetId", assetId, "type", getUrlType());
        VictimPhoneResponse data = getItem(getUrlBasePrefix() + ".byId.victimAssets.phoneNumbers.byAssetId", VictimPhoneResponse.class, ownerName, map);

        return (VictimPhone) data.getData().getData();
    }

    @Override
    public IterableResponse<VictimSocialNetwork> getAssociatedVictimAssetSocialNetworks(P uniqueId) throws IOException, FailedResponseException {
        return getAssociatedVictimAssetSocialNetworks(uniqueId, null);
    }

    @Override
    public IterableResponse<VictimSocialNetwork> getAssociatedVictimAssetSocialNetworks(P uniqueId, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", uniqueId, "type", getUrlType());
        return getItems(getUrlBasePrefix() + ".byId.victimAssets.socialNetworks", VictimSocialNetworkListResponse.class, VictimSocialNetwork.class, ownerName, map);
    }

    @Override
    public VictimSocialNetwork getAssociatedVictimAssetSocialNetwork(P uniqueId, Integer assetId) throws IOException, FailedResponseException {
        return getAssociatedVictimAssetSocialNetwork(uniqueId, assetId, null);
    }

    @Override
    public VictimSocialNetwork getAssociatedVictimAssetSocialNetwork(P uniqueId, Integer assetId, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", uniqueId, "assetId", assetId, "type", getUrlType());
        VictimSocialNetworkResponse data = getItem(getUrlBasePrefix() + ".byId.victimAssets.socialNetworks.byAssetId", VictimSocialNetworkResponse.class, ownerName, map);

        return (VictimSocialNetwork) data.getData().getData();
    }

    @Override
    public IterableResponse<VictimWebSite> getAssociatedVictimAssetWebsites(P uniqueId) throws IOException, FailedResponseException {
        return getAssociatedVictimAssetWebsites(uniqueId, null);
    }

    @Override
    public IterableResponse<VictimWebSite> getAssociatedVictimAssetWebsites(P uniqueId, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", uniqueId, "type", getUrlType());
        return getItems(getUrlBasePrefix() + ".byId.victimAssets.websites", VictimWebSiteListResponse.class, VictimWebSite.class, ownerName, map);
    }

    @Override
    public VictimWebSite getAssociatedVictimAssetWebsite(P uniqueId, Integer assetId) throws IOException, FailedResponseException {
        return getAssociatedVictimAssetWebsite(uniqueId, assetId, null);
    }

    @Override
    public VictimWebSite getAssociatedVictimAssetWebsite(P uniqueId, Integer assetId, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", uniqueId, "assetId", assetId, "type", getUrlType());
        VictimWebSiteResponse data = getItem(getUrlBasePrefix() + ".byId.victimAssets.websites.byAssetId", VictimWebSiteResponse.class, ownerName, map);

        return (VictimWebSite) data.getData().getData();
    }

}
