/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.client.writer.associate;

import com.threatconnect.sdk.client.UrlTypeable;
import com.threatconnect.sdk.client.response.WriteListResponse;
import com.threatconnect.sdk.client.writer.AbstractBaseWriterAdapter;
import com.threatconnect.sdk.conn.Connection;
import com.threatconnect.sdk.conn.AbstractRequestExecutor;
import com.threatconnect.sdk.exception.FailedResponseException;
import com.threatconnect.sdk.server.response.entity.ApiEntitySingleResponse;
import com.threatconnect.sdk.server.response.entity.VictimEmailAddressResponse;
import com.threatconnect.sdk.server.response.entity.VictimNetworkAccountResponse;
import com.threatconnect.sdk.server.response.entity.VictimPhoneResponse;
import com.threatconnect.sdk.server.response.entity.VictimSocialNetworkResponse;
import com.threatconnect.sdk.server.response.entity.VictimWebSiteResponse;
import com.threatconnect.sdk.client.AbstractClientAdapter;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author dtineo
 */
public abstract class AbstractVictimAssetAssociateWriterAdapter<T,P> extends AbstractBaseWriterAdapter<T,P> implements VictimAssetAssociateWritable<P>, UrlTypeable
{

    public AbstractVictimAssetAssociateWriterAdapter(Connection conn, Class singleType) {
        super(conn, singleType);
    }

    @Override
    public WriteListResponse<Long> associateVictimAssetEmailAddresses(P uniqueId, List<Long> assetIds) throws IOException {
        return associateVictimAssetEmailAddresses(uniqueId, assetIds,null);
    }

    @Override
    public WriteListResponse<Long> associateVictimAssetEmailAddresses(P uniqueId, List<Long> assetIds, String ownerName) throws IOException {

        Map<String, Object> map = AbstractClientAdapter.createParamMap("id", uniqueId);
        WriteListResponse<Long> data = createListWithParam(getUrlBasePrefix() + ".byId.victimAssets.emailAddresses.byAssetId", VictimEmailAddressResponse.class, ownerName, map, "assetId", assetIds);

        return data;

    }

    @Override
    public ApiEntitySingleResponse associateVictimAssetEmailAddress(P uniqueId, Long assetId) throws IOException, FailedResponseException
    {
        return associateVictimAssetEmailAddress(uniqueId, assetId, null);
    }

    @Override
    public ApiEntitySingleResponse associateVictimAssetEmailAddress(P uniqueId, Long assetId, String ownerName) throws IOException, FailedResponseException {
        Map<String, Object> map = AbstractClientAdapter.createParamMap("id", uniqueId, "assetId", assetId);
        VictimEmailAddressResponse data = createItem( getUrlBasePrefix() + ".byId.victimAssets.emailAddresses.byAssetId", VictimEmailAddressResponse.class, ownerName, map, null);

        return data;
    }

    @Override
    public WriteListResponse<Long> associateVictimAssetNetworkAccounts(P uniqueId, List<Long> assetIds) throws IOException {
        return associateVictimAssetEmailAddresses(uniqueId, assetIds, null);
    }

    @Override
    public WriteListResponse<Long> associateVictimAssetNetworkAccounts(P uniqueId, List<Long> assetIds, String ownerName) throws IOException {
        Map<String, Object> map = AbstractClientAdapter.createParamMap("id", uniqueId);
        WriteListResponse<Long> data = createListWithParam(getUrlBasePrefix() + ".byId.victimAssets.networkAccounts.byAssetId", VictimNetworkAccountResponse.class, ownerName, map, "assetId", assetIds);

        return data;
    }

    @Override
    public ApiEntitySingleResponse associateVictimAssetNetworkAccount(P uniqueId, Long assetId) throws IOException, FailedResponseException {
        return associateVictimAssetNetworkAccount(uniqueId, assetId, null);
    }

    @Override
    public ApiEntitySingleResponse associateVictimAssetNetworkAccount(P uniqueId, Long assetId, String ownerName) throws IOException, FailedResponseException {
        Map<String, Object> map = AbstractClientAdapter.createParamMap("id", uniqueId, "assetId", assetId);
        VictimNetworkAccountResponse data = createItem( getUrlBasePrefix() + ".byId.victimAssets.networkAccounts.byAssetId", VictimNetworkAccountResponse.class, ownerName, map, null);

        return data;
    }

    @Override
    public WriteListResponse<Long> associateVictimAssetPhoneNumbers(P uniqueId, List<Long> assetIds) throws IOException {
        return associateVictimAssetPhoneNumbers(uniqueId, assetIds, null);
    }

    @Override
    public WriteListResponse<Long> associateVictimAssetPhoneNumbers(P uniqueId, List<Long> assetIds, String ownerName) throws IOException {

        Map<String, Object> map = AbstractClientAdapter.createParamMap("id", uniqueId);
        WriteListResponse<Long> data = createListWithParam(getUrlBasePrefix() + ".byId.victimAssets.phoneNumbers.byAssetId", VictimPhoneResponse.class, ownerName, map, "assetId", assetIds);

        return data;
    }

    @Override
    public ApiEntitySingleResponse associateVictimAssetPhoneNumber(P uniqueId, Long assetId) throws IOException, FailedResponseException {
        return associateVictimAssetPhoneNumber(uniqueId, assetId, null);
    }

    @Override
    public ApiEntitySingleResponse associateVictimAssetPhoneNumber(P uniqueId, Long assetId, String ownerName) throws IOException, FailedResponseException {

        Map<String, Object> map = AbstractClientAdapter.createParamMap("id", uniqueId, "assetId", assetId);
        VictimNetworkAccountResponse data = createItem( getUrlBasePrefix() + ".byId.victimAssets.phoneNumbers.byAssetId", VictimNetworkAccountResponse.class, ownerName, map, null);

        return data;
    }

    @Override
    public WriteListResponse<Long> associateVictimAssetSocialNetworks(P uniqueId, List<Long> assetIds) throws IOException, FailedResponseException {
        return associateVictimAssetSocialNetworks(uniqueId, assetIds, null);
    }

    @Override
    public WriteListResponse<Long> associateVictimAssetSocialNetworks(P uniqueId, List<Long> assetIds, String ownerName) throws IOException {
        Map<String, Object> map = AbstractClientAdapter.createParamMap("id", uniqueId);
        WriteListResponse<Long> data = createListWithParam(getUrlBasePrefix() + ".byId.victimAssets.socialNetworks.byAssetId", VictimSocialNetworkResponse.class, ownerName, map, "assetId", assetIds);

        return data;
    }

    @Override
    public ApiEntitySingleResponse associateVictimAssetSocialNetwork(P uniqueId, Long assetId) throws IOException, FailedResponseException {
        return associateVictimAssetSocialNetwork(uniqueId, assetId, null);
    }

    @Override
    public ApiEntitySingleResponse associateVictimAssetSocialNetwork(P uniqueId, Long assetId, String ownerName) throws IOException, FailedResponseException {

        Map<String, Object> map = AbstractClientAdapter.createParamMap("id", uniqueId, "assetId", assetId);
        VictimSocialNetworkResponse data = createItem( getUrlBasePrefix() + ".byId.victimAssets.socialNetworks.byAssetId", VictimSocialNetworkResponse.class, ownerName, map, null);

        return data;
    }

    @Override
    public WriteListResponse<Long> associateVictimAssetWebsites(P uniqueId, List<Long> assetIds) throws IOException {
        return associateVictimAssetWebsites(uniqueId, assetIds, null);
    }

    @Override
    public WriteListResponse<Long> associateVictimAssetWebsites(P uniqueId, List<Long> assetIds, String ownerName) throws IOException {

        Map<String, Object> map = AbstractClientAdapter.createParamMap("id", uniqueId);
        WriteListResponse<Long> data = createListWithParam(getUrlBasePrefix() + ".byId.victimAssets.websites.byAssetId", VictimWebSiteResponse.class, ownerName, map, "assetId", assetIds);

        return data;
    }

    @Override
    public ApiEntitySingleResponse associateVictimAssetWebsite(P uniqueId, Long assetId) throws IOException, FailedResponseException {
        return associateVictimAssetWebsite(uniqueId, assetId, null);
    }

    @Override
    public ApiEntitySingleResponse associateVictimAssetWebsite(P uniqueId, Long assetId, String ownerName) throws IOException, FailedResponseException {
        Map<String, Object> map = AbstractClientAdapter.createParamMap("id", uniqueId, "assetId", assetId);
        VictimWebSiteResponse data = createItem( getUrlBasePrefix() + ".byId.victimAssets.websites.byAssetId", VictimWebSiteResponse.class, ownerName, map, null);

        return data;
    }

    @Override
    public WriteListResponse<Long> dissociateVictimAssetEmailAddresses(P uniqueId, List<Long> assetIds) throws IOException {
        return dissociateVictimAssetEmailAddresses(uniqueId, assetIds, null);
    }

    @Override
    public WriteListResponse<Long> dissociateVictimAssetEmailAddresses(P uniqueId, List<Long> assetIds, String ownerName) throws IOException {

        Map<String, Object> map = AbstractClientAdapter.createParamMap("id", uniqueId);
        WriteListResponse<Long> data = deleteList(getUrlBasePrefix() + ".byId.victimAssets.emailAddresses.byAssetId", VictimEmailAddressResponse.class, ownerName, map, "assetId", assetIds);

        return data;
    }

    @Override
    public ApiEntitySingleResponse dissociateVictimAssetEmailAddress(P uniqueId, Long assetId) throws IOException, FailedResponseException {
        return dissociateVictimAssetEmailAddress(uniqueId, assetId, null);
    }

    @Override
    public ApiEntitySingleResponse dissociateVictimAssetEmailAddress(P uniqueId, Long assetId, String ownerName) throws IOException, FailedResponseException {

        Map<String, Object> map = AbstractClientAdapter.createParamMap("id", uniqueId, "assetId", assetId);
        VictimEmailAddressResponse item = deleteItem(getUrlBasePrefix() + ".byId.victimAssets.emailAddresses.byAssetId", VictimEmailAddressResponse.class, ownerName, map);

        return item;
    }

    @Override
    public WriteListResponse<Long> dissociateVictimAssetNetworkAccounts(P uniqueId, List<Long> assetIds) throws IOException {
        return dissociateVictimAssetNetworkAccounts(uniqueId, assetIds, null);
    }

    @Override
    public WriteListResponse<Long> dissociateVictimAssetNetworkAccounts(P uniqueId, List<Long> assetIds, String ownerName) throws IOException {

        Map<String, Object> map = AbstractClientAdapter.createParamMap("id", uniqueId);
        WriteListResponse<Long> data = deleteList(getUrlBasePrefix() + ".byId.victimAssets.networkAccounts.byAssetId", VictimNetworkAccountResponse.class, ownerName, map, "assetId", assetIds);

        return data;
    }

    @Override
    public ApiEntitySingleResponse dissociateVictimAssetNetworkAccount(P uniqueId, Long assetId) throws IOException, FailedResponseException {
        return dissociateVictimAssetNetworkAccount(uniqueId, assetId, null);
    }

    @Override
    public ApiEntitySingleResponse dissociateVictimAssetNetworkAccount(P uniqueId, Long assetId, String ownerName) throws IOException, FailedResponseException {

        Map<String, Object> map = AbstractClientAdapter.createParamMap("id", uniqueId, "assetId", assetId);
        VictimNetworkAccountResponse item = deleteItem(getUrlBasePrefix() + ".byId.victimAssets.networkAccounts.byAssetId", VictimNetworkAccountResponse.class, ownerName, map);

        return item;
    }

    @Override
    public WriteListResponse<Long> dissociateVictimAssetPhoneNumbers(P uniqueId, List<Long> assetIds) throws IOException {
        return dissociateVictimAssetPhoneNumbers(uniqueId, assetIds, null);
    }

    @Override
    public WriteListResponse<Long> dissociateVictimAssetPhoneNumbers(P uniqueId, List<Long> assetIds, String ownerName) throws IOException {

        Map<String, Object> map = AbstractClientAdapter.createParamMap("id", uniqueId);
        WriteListResponse<Long> data = deleteList(getUrlBasePrefix() + ".byId.victimAssets.phoneNumbers.byAssetId", VictimPhoneResponse.class, ownerName, map, "assetId", assetIds);

        return data;
    }

    @Override
    public ApiEntitySingleResponse dissociateVictimAssetPhoneNumber(P uniqueId, Long assetId) throws IOException, FailedResponseException {
        return dissociateVictimAssetPhoneNumber(uniqueId, assetId, null);
    }

    @Override
    public ApiEntitySingleResponse dissociateVictimAssetPhoneNumber(P uniqueId, Long assetId, String ownerName) throws IOException, FailedResponseException {

        Map<String, Object> map = AbstractClientAdapter.createParamMap("id", uniqueId, "assetId", assetId);
        VictimPhoneResponse item = deleteItem(getUrlBasePrefix() + ".byId.victimAssets.phoneNumbers.byAssetId", VictimPhoneResponse.class, ownerName, map);

        return item;
    }

    @Override
    public WriteListResponse<Long> dissociateVictimAssetSocialNetworks(P uniqueId, List<Long> assetIds) throws IOException, FailedResponseException {
        return dissociateVictimAssetSocialNetworks(uniqueId, assetIds, null);
    }

    @Override
    public WriteListResponse<Long> dissociateVictimAssetSocialNetworks(P uniqueId, List<Long> assetIds, String ownerName) throws IOException {
        Map<String, Object> map = AbstractClientAdapter.createParamMap("id", uniqueId);
        WriteListResponse<Long> data = deleteList(getUrlBasePrefix() + ".byId.victimAssets.socialNetworks.byAssetId", VictimNetworkAccountResponse.class, ownerName, map, "assetId", assetIds);

        return data;
    }

    @Override
    public ApiEntitySingleResponse dissociateVictimAssetSocialNetwork(P uniqueId, Long assetId) throws IOException, FailedResponseException {
        return dissociateVictimAssetSocialNetwork(uniqueId, assetId, null);
    }

    @Override
    public ApiEntitySingleResponse dissociateVictimAssetSocialNetwork(P uniqueId, Long assetId, String ownerName) throws IOException, FailedResponseException {
        Map<String, Object> map = AbstractClientAdapter.createParamMap("id", uniqueId, "assetId", assetId);
        VictimSocialNetworkResponse item = deleteItem(getUrlBasePrefix() + ".byId.victimAssets.socialNetworks.byAssetId", VictimSocialNetworkResponse.class, ownerName, map);

        return item;
    }

    @Override
    public WriteListResponse<Long> dissociateVictimAssetWebsites(P uniqueId, List<Long> assetIds) throws IOException {
        return dissociateVictimAssetWebsites(uniqueId, assetIds, null);
    }

    @Override
    public WriteListResponse<Long> dissociateVictimAssetWebsites(P uniqueId, List<Long> assetIds, String ownerName) throws IOException {

        Map<String, Object> map = AbstractClientAdapter.createParamMap("id", uniqueId);
        WriteListResponse<Long> data = deleteList(getUrlBasePrefix() + ".byId.victimAssets.websites.byAssetId", VictimWebSiteResponse.class, ownerName, map, "assetId", assetIds);

        return data;
    }

    @Override
    public ApiEntitySingleResponse dissociateVictimAssetWebsite(P uniqueId, Long assetId) throws IOException, FailedResponseException {
        return dissociateVictimAssetWebsite(uniqueId, assetId, null);
    }

    @Override
    public ApiEntitySingleResponse dissociateVictimAssetWebsite(P uniqueId, Long assetId, String ownerName) throws IOException, FailedResponseException {
        Map<String, Object> map = AbstractClientAdapter.createParamMap("id", uniqueId, "assetId", assetId);
        VictimWebSiteResponse item = deleteItem(getUrlBasePrefix() + ".byId.victimAssets.website.byAssetId", VictimWebSiteResponse.class, ownerName, map);

        return item;
    }


}
