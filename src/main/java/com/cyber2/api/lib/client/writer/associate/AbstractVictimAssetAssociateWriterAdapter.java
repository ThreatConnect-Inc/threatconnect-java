/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyber2.api.lib.client.writer.associate;

import com.cyber2.api.lib.client.UrlTypeable;
import com.cyber2.api.lib.client.response.WriteListResponse;
import com.cyber2.api.lib.client.writer.AbstractBaseWriterAdapter;
import com.cyber2.api.lib.conn.Connection;
import com.cyber2.api.lib.conn.RequestExecutor;
import com.cyber2.api.lib.exception.FailedResponseException;
import com.cyber2.api.lib.server.response.entity.ApiEntitySingleResponse;
import com.cyber2.api.lib.server.response.entity.VictimEmailAddressResponse;
import com.cyber2.api.lib.server.response.entity.VictimNetworkAccountResponse;
import com.cyber2.api.lib.server.response.entity.VictimPhoneResponse;
import com.cyber2.api.lib.server.response.entity.VictimSocialNetworkResponse;
import com.cyber2.api.lib.server.response.entity.VictimWebSiteResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author dtineo
 */
public abstract class AbstractVictimAssetAssociateWriterAdapter<T,P> extends AbstractBaseWriterAdapter<T,P> implements VictimAssetAssociateWritable<P>, UrlTypeable {

    public AbstractVictimAssetAssociateWriterAdapter(Connection conn, RequestExecutor executor, Class singleType) {
        super(conn, executor, singleType);
    }

    @Override
    public WriteListResponse<Integer> associateVictimAssetEmailAddresses(P uniqueId, List<Integer> assetIds) throws IOException {
        return associateVictimAssetEmailAddresses(uniqueId, assetIds,null);
    }

    @Override
    public WriteListResponse<Integer> associateVictimAssetEmailAddresses(P uniqueId, List<Integer> assetIds, String ownerName) throws IOException {

        Map<String, Object> map = createParamMap("id", uniqueId);
        WriteListResponse<Integer> data = createListWithParam(getUrlBasePrefix() + ".type.byId.victimAssets.emailAddresses.byAssetId", VictimEmailAddressResponse.class, ownerName, map, "assetId", assetIds);

        return data;

    }

    @Override
    public ApiEntitySingleResponse associateVictimAssetEmailAddress(P uniqueId, Integer assetId) throws IOException, FailedResponseException {
        return associateVictimAssetEmailAddress(uniqueId, assetId, null);
    }

    @Override
    public ApiEntitySingleResponse associateVictimAssetEmailAddress(P uniqueId, Integer assetId, String ownerName) throws IOException, FailedResponseException {
        Map<String, Object> map = createParamMap("id", uniqueId, "assetId", assetId);
        VictimEmailAddressResponse data = createItem( getUrlBasePrefix() + ".type.byId.victimAssets.emailAddresses.byAssetId", VictimEmailAddressResponse.class, ownerName, map, null);

        return data;
    }

    @Override
    public WriteListResponse<Integer> associateVictimAssetNetworkAccounts(P uniqueId, List<Integer> assetIds) throws IOException {
        return associateVictimAssetEmailAddresses(uniqueId, assetIds, null);
    }

    @Override
    public WriteListResponse<Integer> associateVictimAssetNetworkAccounts(P uniqueId, List<Integer> assetIds, String ownerName) throws IOException {
        Map<String, Object> map = createParamMap("id", uniqueId);
        WriteListResponse<Integer> data = createListWithParam(getUrlBasePrefix() + ".type.byId.victimAssets.networkAccounts.byAssetId", VictimNetworkAccountResponse.class, ownerName, map, "assetId", assetIds);

        return data;
    }

    @Override
    public ApiEntitySingleResponse associateVictimAssetNetworkAccount(P uniqueId, Integer assetId) throws IOException, FailedResponseException {
        return associateVictimAssetNetworkAccount(uniqueId, assetId, null);
    }

    @Override
    public ApiEntitySingleResponse associateVictimAssetNetworkAccount(P uniqueId, Integer assetId, String ownerName) throws IOException, FailedResponseException {
        Map<String, Object> map = createParamMap("id", uniqueId, "assetId", assetId);
        VictimNetworkAccountResponse data = createItem( getUrlBasePrefix() + ".type.byId.victimAssets.networkAccounts.byAssetId", VictimNetworkAccountResponse.class, ownerName, map, null);

        return data;
    }

    @Override
    public WriteListResponse<Integer> associateVictimAssetPhoneNumbers(P uniqueId, List<Integer> assetIds) throws IOException {
        return associateVictimAssetPhoneNumbers(uniqueId, assetIds, null);
    }

    @Override
    public WriteListResponse<Integer> associateVictimAssetPhoneNumbers(P uniqueId, List<Integer> assetIds, String ownerName) throws IOException {

        Map<String, Object> map = createParamMap("id", uniqueId);
        WriteListResponse<Integer> data = createListWithParam(getUrlBasePrefix() + ".type.byId.victimAssets.phoneNumbers.byAssetId", VictimPhoneResponse.class, ownerName, map, "assetId", assetIds);

        return data;
    }

    @Override
    public ApiEntitySingleResponse associateVictimAssetPhoneNumber(P uniqueId, Integer assetId) throws IOException, FailedResponseException {
        return associateVictimAssetPhoneNumber(uniqueId, assetId, null);
    }

    @Override
    public ApiEntitySingleResponse associateVictimAssetPhoneNumber(P uniqueId, Integer assetId, String ownerName) throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", uniqueId, "assetId", assetId);
        VictimNetworkAccountResponse data = createItem( getUrlBasePrefix() + ".type.byId.victimAssets.phoneNumbers.byAssetId", VictimNetworkAccountResponse.class, ownerName, map, null);

        return data;
    }

    @Override
    public WriteListResponse<Integer> associateVictimAssetSocialNetworks(P uniqueId, List<Integer> assetIds) throws IOException, FailedResponseException {
        return associateVictimAssetSocialNetworks(uniqueId, assetIds, null);
    }

    @Override
    public WriteListResponse<Integer> associateVictimAssetSocialNetworks(P uniqueId, List<Integer> assetIds, String ownerName) throws IOException {
        Map<String, Object> map = createParamMap("id", uniqueId);
        WriteListResponse<Integer> data = createListWithParam(getUrlBasePrefix() + ".type.byId.victimAssets.socialNetworks.byAssetId", VictimSocialNetworkResponse.class, ownerName, map, "assetId", assetIds);

        return data;
    }

    @Override
    public ApiEntitySingleResponse associateVictimAssetSocialNetwork(P uniqueId, Integer assetId) throws IOException, FailedResponseException {
        return associateVictimAssetSocialNetwork(uniqueId, assetId, null);
    }

    @Override
    public ApiEntitySingleResponse associateVictimAssetSocialNetwork(P uniqueId, Integer assetId, String ownerName) throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", uniqueId, "assetId", assetId);
        VictimSocialNetworkResponse data = createItem( getUrlBasePrefix() + ".type.byId.victimAssets.socialNetworks.byAssetId", VictimSocialNetworkResponse.class, ownerName, map, null);

        return data;
    }

    @Override
    public WriteListResponse<Integer> associateVictimAssetWebsites(P uniqueId, List<Integer> assetIds) throws IOException {
        return associateVictimAssetWebsites(uniqueId, assetIds, null);
    }

    @Override
    public WriteListResponse<Integer> associateVictimAssetWebsites(P uniqueId, List<Integer> assetIds, String ownerName) throws IOException {

        Map<String, Object> map = createParamMap("id", uniqueId);
        WriteListResponse<Integer> data = createListWithParam(getUrlBasePrefix() + ".type.byId.victimAssets.websites.byAssetId", VictimWebSiteResponse.class, ownerName, map, "assetId", assetIds);

        return data;
    }

    @Override
    public ApiEntitySingleResponse associateVictimAssetWebsite(P uniqueId, Integer assetId) throws IOException, FailedResponseException {
        return associateVictimAssetWebsite(uniqueId, assetId, null);
    }

    @Override
    public ApiEntitySingleResponse associateVictimAssetWebsite(P uniqueId, Integer assetId, String ownerName) throws IOException, FailedResponseException {
        Map<String, Object> map = createParamMap("id", uniqueId, "assetId", assetId);
        VictimWebSiteResponse data = createItem( getUrlBasePrefix() + ".type.byId.victimAssets.websites.byAssetId", VictimWebSiteResponse.class, ownerName, map, null);

        return data;
    }

    @Override
    public WriteListResponse<Integer> deleteAssociatedVictimAssetEmailAddresses(P uniqueId, List<Integer> assetIds) throws IOException {
        return deleteAssociatedVictimAssetEmailAddresses(uniqueId, assetIds, null);
    }

    @Override
    public WriteListResponse<Integer> deleteAssociatedVictimAssetEmailAddresses(P uniqueId, List<Integer> assetIds, String ownerName) throws IOException {

        Map<String, Object> map = createParamMap("id", uniqueId);
        WriteListResponse<Integer> data = deleteList(getUrlBasePrefix() + ".type.byId.victimAssets.emailAddresses.byAssetId", VictimEmailAddressResponse.class, ownerName, map, "assetId", assetIds);

        return data;
    }

    @Override
    public ApiEntitySingleResponse deleteAssociatedVictimAssetEmailAddress(P uniqueId, Integer assetId) throws IOException, FailedResponseException {
        return deleteAssociatedVictimAssetEmailAddress(uniqueId, assetId, null);
    }

    @Override
    public ApiEntitySingleResponse deleteAssociatedVictimAssetEmailAddress(P uniqueId, Integer assetId, String ownerName) throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", uniqueId, "assetId", assetId);
        VictimEmailAddressResponse item = deleteItem(getUrlBasePrefix() + ".type.byId.victimAssets.emailAddresses.byAssetId", VictimEmailAddressResponse.class, ownerName, map);

        return item;
    }

    @Override
    public WriteListResponse<Integer> deleteAssociatedVictimAssetNetworkAccounts(P uniqueId, List<Integer> assetIds) throws IOException {
        return deleteAssociatedVictimAssetNetworkAccounts(uniqueId, assetIds, null);
    }

    @Override
    public WriteListResponse<Integer> deleteAssociatedVictimAssetNetworkAccounts(P uniqueId, List<Integer> assetIds, String ownerName) throws IOException {

        Map<String, Object> map = createParamMap("id", uniqueId);
        WriteListResponse<Integer> data = deleteList(getUrlBasePrefix() + ".type.byId.victimAssets.networkAccounts.byAssetId", VictimNetworkAccountResponse.class, ownerName, map, "assetId", assetIds);

        return data;
    }

    @Override
    public ApiEntitySingleResponse deleteAssociatedVictimAssetNetworkAccount(P uniqueId, Integer assetId) throws IOException, FailedResponseException {
        return deleteAssociatedVictimAssetNetworkAccount(uniqueId, assetId, null);
    }

    @Override
    public ApiEntitySingleResponse deleteAssociatedVictimAssetNetworkAccount(P uniqueId, Integer assetId, String ownerName) throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", uniqueId, "assetId", assetId);
        VictimNetworkAccountResponse item = deleteItem(getUrlBasePrefix() + ".type.byId.victimAssets.networkAccounts.byAssetId", VictimNetworkAccountResponse.class, ownerName, map);

        return item;
    }

    @Override
    public WriteListResponse<Integer> deleteAssociatedVictimAssetPhoneNumbers(P uniqueId, List<Integer> assetIds) throws IOException {
        return deleteAssociatedVictimAssetPhoneNumbers(uniqueId, assetIds, null);
    }

    @Override
    public WriteListResponse<Integer> deleteAssociatedVictimAssetPhoneNumbers(P uniqueId, List<Integer> assetIds, String ownerName) throws IOException {

        Map<String, Object> map = createParamMap("id", uniqueId);
        WriteListResponse<Integer> data = deleteList(getUrlBasePrefix() + ".type.byId.victimAssets.phoneNumbers.byAssetId", VictimPhoneResponse.class, ownerName, map, "assetId", assetIds);

        return data;
    }

    @Override
    public ApiEntitySingleResponse deleteAssociatedVictimAssetPhoneNumber(P uniqueId, Integer assetId) throws IOException, FailedResponseException {
        return deleteAssociatedVictimAssetPhoneNumber(uniqueId, assetId, null);
    }

    @Override
    public ApiEntitySingleResponse deleteAssociatedVictimAssetPhoneNumber(P uniqueId, Integer assetId, String ownerName) throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", uniqueId, "assetId", assetId);
        VictimPhoneResponse item = deleteItem(getUrlBasePrefix() + ".type.byId.victimAssets.phoneNumbers.byAssetId", VictimPhoneResponse.class, ownerName, map);

        return item;
    }

    @Override
    public WriteListResponse<Integer> deleteAssociatedVictimAssetSocialNetworks(P uniqueId, List<Integer> assetIds) throws IOException, FailedResponseException {
        return deleteAssociatedVictimAssetSocialNetworks(uniqueId, assetIds, null);
    }

    @Override
    public WriteListResponse<Integer> deleteAssociatedVictimAssetSocialNetworks(P uniqueId, List<Integer> assetIds, String ownerName) throws IOException {
        Map<String, Object> map = createParamMap("id", uniqueId);
        WriteListResponse<Integer> data = deleteList(getUrlBasePrefix() + ".type.byId.victimAssets.socialNetworks.byAssetId", VictimNetworkAccountResponse.class, ownerName, map, "assetId", assetIds);

        return data;
    }

    @Override
    public ApiEntitySingleResponse deleteAssociatedVictimAssetSocialNetwork(P uniqueId, Integer assetId) throws IOException, FailedResponseException {
        return deleteAssociatedVictimAssetSocialNetwork(uniqueId, assetId, null);
    }

    @Override
    public ApiEntitySingleResponse deleteAssociatedVictimAssetSocialNetwork(P uniqueId, Integer assetId, String ownerName) throws IOException, FailedResponseException {
        Map<String, Object> map = createParamMap("id", uniqueId, "assetId", assetId);
        VictimSocialNetworkResponse item = deleteItem(getUrlBasePrefix() + ".type.byId.victimAssets.socialNetworks.byAssetId", VictimSocialNetworkResponse.class, ownerName, map);

        return item;
    }

    @Override
    public WriteListResponse<Integer> deleteAssociatedVictimAssetWebsites(P uniqueId, List<Integer> assetIds) throws IOException {
        return deleteAssociatedVictimAssetWebsites(uniqueId, assetIds, null);
    }

    @Override
    public WriteListResponse<Integer> deleteAssociatedVictimAssetWebsites(P uniqueId, List<Integer> assetIds, String ownerName) throws IOException {

        Map<String, Object> map = createParamMap("id", uniqueId);
        WriteListResponse<Integer> data = deleteList(getUrlBasePrefix() + ".type.byId.victimAssets.websites.byAssetId", VictimWebSiteResponse.class, ownerName, map, "assetId", assetIds);

        return data;
    }

    @Override
    public ApiEntitySingleResponse deleteAssociatedVictimAssetWebsite(P uniqueId, Integer assetId) throws IOException, FailedResponseException {
        return deleteAssociatedVictimAssetWebsite(uniqueId, assetId, null);
    }

    @Override
    public ApiEntitySingleResponse deleteAssociatedVictimAssetWebsite(P uniqueId, Integer assetId, String ownerName) throws IOException, FailedResponseException {
        Map<String, Object> map = createParamMap("id", uniqueId, "assetId", assetId);
        VictimWebSiteResponse item = deleteItem(getUrlBasePrefix() + ".type.byId.victimAssets.website.byAssetId", VictimWebSiteResponse.class, ownerName, map);

        return item;
    }


}
