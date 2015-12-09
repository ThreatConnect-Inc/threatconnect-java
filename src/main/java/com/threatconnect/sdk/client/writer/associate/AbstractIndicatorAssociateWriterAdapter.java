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
import com.threatconnect.sdk.server.response.entity.AddressListResponse;
import com.threatconnect.sdk.server.response.entity.AddressResponse;
import com.threatconnect.sdk.server.response.entity.ApiEntitySingleResponse;
import com.threatconnect.sdk.server.response.entity.EmailAddressListResponse;
import com.threatconnect.sdk.server.response.entity.EmailAddressResponse;
import com.threatconnect.sdk.server.response.entity.FileListResponse;
import com.threatconnect.sdk.server.response.entity.FileResponse;
import com.threatconnect.sdk.server.response.entity.HostListResponse;
import com.threatconnect.sdk.server.response.entity.HostResponse;
import com.threatconnect.sdk.server.response.entity.UrlListResponse;
import com.threatconnect.sdk.server.response.entity.UrlResponse;
import com.threatconnect.sdk.client.AbstractClientAdapter;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author dtineo
 */
public abstract class AbstractIndicatorAssociateWriterAdapter<T,P> extends AbstractBaseWriterAdapter<T,P> implements IndicatorAssociateWritable<P>, UrlTypeable
{

    public AbstractIndicatorAssociateWriterAdapter(Connection conn, Class singleType) {
        super(conn, singleType);
    }

    @Override
    public WriteListResponse<String> associateIndicatorAddresses(P uniqueId, List<String> ipAddresses) throws IOException {
        return associateIndicatorAddresses(uniqueId, ipAddresses, null);
    }

    @Override
    public WriteListResponse<String> associateIndicatorAddresses(P uniqueId, List<String> ipAddresses, String ownerName) throws IOException {

        Map<String, Object> map = AbstractClientAdapter.createParamMap("id", uniqueId);
        WriteListResponse<String> data = createListWithParam(getUrlBasePrefix() + ".byId.indicators.addresses.byIndicatorId", AddressListResponse.class, ownerName, map, "indicatorId", ipAddresses);

        return data;
    }

    @Override
    public ApiEntitySingleResponse associateIndicatorAddress(P uniqueId, String ipAddress) throws IOException, FailedResponseException {
        return associateIndicatorAddress(uniqueId, ipAddress, null);
    }

    @Override
    public ApiEntitySingleResponse associateIndicatorAddress(P uniqueId, String ipAddress, String ownerName) throws IOException, FailedResponseException {
        
        Map<String, Object> map = AbstractClientAdapter.createParamMap("id", uniqueId, "indicatorId", ipAddress);
        AddressResponse data = createItem(getUrlBasePrefix() + ".byId.indicators.addresses.byIndicatorId", AddressResponse.class, ownerName, map, null);

        return data;
    }

    @Override
    public WriteListResponse<String> associateIndicatorEmailAddresses(P uniqueId, List<String> emailAddresses) throws IOException {
        return associateIndicatorEmailAddresses(uniqueId, emailAddresses, null);
    }

    @Override
    public WriteListResponse<String> associateIndicatorEmailAddresses(P uniqueId, List<String> emailAddresses, String ownerName) throws IOException {

        Map<String, Object> map = AbstractClientAdapter.createParamMap("id", uniqueId);
        WriteListResponse<String> data = createListWithParam(getUrlBasePrefix() + ".byId.indicators.emailAddresses.byIndicatorId", EmailAddressListResponse.class, ownerName, map, "indicatorId", emailAddresses);

        return data;
    }

    @Override
    public ApiEntitySingleResponse associateIndicatorEmailAddress(P uniqueId, String emailAddress) throws IOException, FailedResponseException {
        return associateIndicatorEmailAddress(uniqueId, emailAddress, null);
    }

    @Override
    public ApiEntitySingleResponse associateIndicatorEmailAddress(P uniqueId, String emailAddress, String ownerName) throws IOException, FailedResponseException {
        Map<String, Object> map = AbstractClientAdapter.createParamMap("id", uniqueId, "indicatorId", emailAddress);
        EmailAddressResponse data = createItem(getUrlBasePrefix() + ".byId.indicators.emailAddresses.byIndicatorId", EmailAddressResponse.class, ownerName, map, null);

        return data;
    }

    @Override
    public WriteListResponse<String> associateIndicatorFiles(P uniqueId, List<String> fileHashes) throws IOException {
        return associateIndicatorFiles(uniqueId, fileHashes, null);
    }

    @Override
    public WriteListResponse<String> associateIndicatorFiles(P uniqueId, List<String> fileHashes, String ownerName) throws IOException {
        Map<String, Object> map = AbstractClientAdapter.createParamMap("id", uniqueId);
        WriteListResponse<String> data = createListWithParam(getUrlBasePrefix() + ".byId.indicators.files.byIndicatorId", FileListResponse.class, ownerName, map, "indicatorId", fileHashes);

        return data;
    }

    @Override
    public ApiEntitySingleResponse associateIndicatorFile(P uniqueId, String fileHash) throws IOException, FailedResponseException {
        return associateIndicatorFile(uniqueId, fileHash, null);
    }

    @Override
    public ApiEntitySingleResponse associateIndicatorFile(P uniqueId, String fileHash, String ownerName) throws IOException, FailedResponseException {
        Map<String, Object> map = AbstractClientAdapter.createParamMap("id", uniqueId, "indicatorId", fileHash);
        FileResponse data = createItem(getUrlBasePrefix() + ".byId.indicators.files.byIndicatorId", FileResponse.class, ownerName, map, null);

        return data;
    }

    @Override
    public WriteListResponse<String> associateIndicatorHosts(P uniqueId, List<String> hostNames) throws IOException {
        return associateIndicatorHosts(uniqueId, hostNames, null);
    }

    @Override
    public WriteListResponse<String> associateIndicatorHosts(P uniqueId, List<String> hostNames, String ownerName) throws IOException {
        Map<String, Object> map = AbstractClientAdapter.createParamMap("id", uniqueId);
        WriteListResponse<String> data = createListWithParam(getUrlBasePrefix() + ".byId.indicators.hosts.byIndicatorId", HostListResponse.class, ownerName, map, "indicatorId", hostNames);

        return data;
    }

    @Override
    public ApiEntitySingleResponse associateIndicatorHost(P uniqueId, String hostName) throws IOException, FailedResponseException {
        return associateIndicatorHost(uniqueId, hostName, null);
    }

    @Override
    public ApiEntitySingleResponse associateIndicatorHost(P uniqueId, String hostName, String ownerName) throws IOException, FailedResponseException {
        Map<String, Object> map = AbstractClientAdapter.createParamMap("id", uniqueId, "indicatorId", hostName);
        HostResponse data = createItem(getUrlBasePrefix() + ".byId.indicators.hosts.byIndicatorId", HostResponse.class, ownerName, map, null);

        return data;
    }

    @Override
    public WriteListResponse<String> associateIndicatorUrls(P uniqueId, List<String> urlTexts) throws IOException {
        return associateIndicatorUrls(uniqueId, urlTexts, null);
    }

    @Override
    public WriteListResponse<String> associateIndicatorUrls(P uniqueId, List<String> urlTexts, String ownerName) throws IOException {
        Map<String, Object> map = AbstractClientAdapter.createParamMap("id", uniqueId);
        WriteListResponse<String> data = createListWithParam(getUrlBasePrefix() + ".byId.indicators.urls.byIndicatorId", UrlListResponse.class, ownerName, map, "indicatorId", urlTexts);

        return data;
    }

    @Override
    public ApiEntitySingleResponse associateIndicatorUrl(P uniqueId, String urlText) throws IOException, FailedResponseException {
        return associateIndicatorUrl(uniqueId, urlText, null);
    }

    @Override
    public ApiEntitySingleResponse associateIndicatorUrl(P uniqueId, String urlText, String ownerName) throws IOException, FailedResponseException {
        Map<String, Object> map = AbstractClientAdapter.createParamMap("id", uniqueId, "indicatorId", urlText);
        UrlResponse data = createItem(getUrlBasePrefix() + ".byId.indicators.urls.byIndicatorId", UrlResponse.class, ownerName, map, null);

        return data;
    }

    @Override
    public WriteListResponse<String> dissociateIndicatorAddresses(P uniqueId, List<String> ipAddresses) throws IOException {
        return dissociateIndicatorAddresses(uniqueId, ipAddresses, null);
    }

    @Override
    public WriteListResponse<String> dissociateIndicatorAddresses(P uniqueId, List<String> ipAddresses, String ownerName) throws IOException {
        Map<String, Object> map = AbstractClientAdapter.createParamMap("id", uniqueId);
        WriteListResponse<String> data = deleteList(getUrlBasePrefix() + ".byId.indicators.addresses.byIndicatorId", AddressResponse.class, ownerName, map, "indicatorId", ipAddresses);

        return data;
    }

    @Override
    public ApiEntitySingleResponse dissociateIndicatorAddress(P uniqueId, String ipAddress) throws IOException, FailedResponseException {
        return dissociateIndicatorAddress(uniqueId, ipAddress, null);
    }

    @Override
    public ApiEntitySingleResponse dissociateIndicatorAddress(P uniqueId, String ipAddress, String ownerName) throws IOException, FailedResponseException {
        Map<String, Object> map = AbstractClientAdapter.createParamMap("id", uniqueId, "indicatorId", ipAddress);
        AddressResponse data = deleteItem(getUrlBasePrefix() + ".byId.indicators.addresses.byIndicatorId", AddressResponse.class, ownerName, map);

        return data;
    }

    @Override
    public WriteListResponse<String> dissociateIndicatorEmailAddresses(P uniqueId, List<String> emailAddresses) throws IOException {
        return dissociateIndicatorEmailAddresses(uniqueId, emailAddresses, null);
    }

    @Override
    public WriteListResponse<String> dissociateIndicatorEmailAddresses(P uniqueId, List<String> emailAddresses, String ownerName) throws IOException {
        Map<String, Object> map = AbstractClientAdapter.createParamMap("id", uniqueId);
        WriteListResponse<String> data = deleteList(getUrlBasePrefix() + ".byId.indicators.emailAddresses.byIndicatorId", AddressResponse.class, ownerName, map, "indicatorId", emailAddresses);

        return data;
    }

    @Override
    public ApiEntitySingleResponse dissociateIndicatorEmailAddress(P uniqueId, String emailAddress) throws IOException, FailedResponseException {
        return dissociateIndicatorEmailAddress(uniqueId, emailAddress, null);
    }

    @Override
    public ApiEntitySingleResponse dissociateIndicatorEmailAddress(P uniqueId, String emailAddress, String ownerName) throws IOException, FailedResponseException {
        Map<String, Object> map = AbstractClientAdapter.createParamMap("id", uniqueId, "indicatorId", emailAddress);
        EmailAddressResponse data = deleteItem(getUrlBasePrefix() + ".byId.indicators.emailAddresses.byIndicatorId", EmailAddressResponse.class, ownerName, map);

        return data;
    }

    @Override
    public WriteListResponse<String> dissociateIndicatorFiles(P uniqueId, List<String> fileHashes) throws IOException {
        return dissociateIndicatorFiles(uniqueId, fileHashes, null);
    }

    @Override
    public WriteListResponse<String> dissociateIndicatorFiles(P uniqueId, List<String> fileHashes, String ownerName) throws IOException {
        Map<String, Object> map = AbstractClientAdapter.createParamMap("id", uniqueId);
        WriteListResponse<String> data = deleteList(getUrlBasePrefix() + ".byId.indicators.files.byIndicatorId", FileResponse.class, ownerName, map, "indicatorId", fileHashes);

        return data;
    }

    @Override
    public ApiEntitySingleResponse dissociateIndicatorFile(P uniqueId, String fileHash) throws IOException, FailedResponseException {
        return dissociateIndicatorFile(uniqueId, fileHash, null);
    }

    @Override
    public ApiEntitySingleResponse dissociateIndicatorFile(P uniqueId, String fileHash, String ownerName) throws IOException, FailedResponseException {
        Map<String, Object> map = AbstractClientAdapter.createParamMap("id", uniqueId, "indicatorId", fileHash);
        FileResponse data = deleteItem(getUrlBasePrefix() + ".byId.indicators.files.byIndicatorId", FileResponse.class, ownerName, map);

        return data;
    }

    @Override
    public WriteListResponse<String> dissociateIndicatorHosts(P uniqueId, List<String> hostNames) throws IOException {
        return dissociateIndicatorHosts(uniqueId, hostNames, null);
    }

    @Override
    public WriteListResponse<String> dissociateIndicatorHosts(P uniqueId, List<String> hostNames, String ownerName) throws IOException {

        Map<String, Object> map = AbstractClientAdapter.createParamMap("id", uniqueId);
        WriteListResponse<String> data = deleteList(getUrlBasePrefix() + ".byId.indicators.hosts.byIndicatorId", HostResponse.class, ownerName, map, "indicatorId", hostNames);

        return data;
    }

    @Override
    public ApiEntitySingleResponse dissociateIndicatorHost(P uniqueId, String hostName) throws IOException, FailedResponseException {
        return dissociateIndicatorHost(uniqueId, hostName, null);
    }

    @Override
    public ApiEntitySingleResponse dissociateIndicatorHost(P uniqueId, String hostName, String ownerName) throws IOException, FailedResponseException {
        Map<String, Object> map = AbstractClientAdapter.createParamMap("id", uniqueId, "indicatorId", hostName);
        HostResponse data = deleteItem(getUrlBasePrefix() + ".byId.indicators.hosts.byIndicatorId", HostResponse.class, ownerName, map);

        return data;
    }

    @Override
    public WriteListResponse<String> dissociateIndicatorUrls(P uniqueId, List<String> urlTexts) throws IOException {
        return dissociateIndicatorUrls(uniqueId, urlTexts, null);
    }

    @Override
    public WriteListResponse<String> dissociateIndicatorUrls(P uniqueId, List<String> urlTexts, String ownerName) throws IOException {
        Map<String, Object> map = AbstractClientAdapter.createParamMap("id", uniqueId);
        WriteListResponse<String> data = deleteList(getUrlBasePrefix() + ".byId.indicators.urls.byIndicatorId", UrlResponse.class, ownerName, map, "indicatorId", urlTexts);

        return data;
    }

    @Override
    public ApiEntitySingleResponse dissociateIndicatorUrl(P uniqueId, String urlText) throws IOException, FailedResponseException {
        return dissociateIndicatorUrl(uniqueId, urlText, null);
    }

    @Override
    public ApiEntitySingleResponse dissociateIndicatorUrl(P uniqueId, String urlText, String ownerName) throws IOException, FailedResponseException {
        Map<String, Object> map = AbstractClientAdapter.createParamMap("id", uniqueId, "indicatorId", urlText);
        UrlResponse data = deleteItem(getUrlBasePrefix() + ".byId.indicators.urls.byIndicatorId", UrlResponse.class, ownerName, map);

        return data;
    }

}
