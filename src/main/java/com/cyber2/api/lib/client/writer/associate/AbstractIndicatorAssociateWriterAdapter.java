/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyber2.api.lib.client.writer.associate;

import com.cyber2.api.lib.client.response.WriteListResponse;
import com.cyber2.api.lib.client.writer.AbstractBaseWriterAdapter;
import com.cyber2.api.lib.conn.Connection;
import com.cyber2.api.lib.conn.RequestExecutor;
import com.cyber2.api.lib.exception.FailedResponseException;
import com.cyber2.api.lib.server.response.entity.AddressListResponse;
import com.cyber2.api.lib.server.response.entity.AddressResponse;
import com.cyber2.api.lib.server.response.entity.EmailAddressListResponse;
import com.cyber2.api.lib.server.response.entity.EmailAddressResponse;
import com.cyber2.api.lib.server.response.entity.FileListResponse;
import com.cyber2.api.lib.server.response.entity.FileResponse;
import com.cyber2.api.lib.server.response.entity.HostListResponse;
import com.cyber2.api.lib.server.response.entity.HostResponse;
import com.cyber2.api.lib.server.response.entity.UrlListResponse;
import com.cyber2.api.lib.server.response.entity.UrlResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author dtineo
 */
public abstract class AbstractIndicatorAssociateWriterAdapter<T,P> extends AbstractBaseWriterAdapter<T,P> implements IndicatorAssociateWritable<P> {

    public AbstractIndicatorAssociateWriterAdapter(Connection conn, RequestExecutor executor, Class singleType) {
        super(conn, executor, singleType, /*creatReturnsObject=*/false);
    }

    @Override
    public WriteListResponse<String> associateIndicatorAddresses(P uniqueId, List<String> ipAddresses) throws IOException {
        return associateIndicatorAddresses(uniqueId, ipAddresses, null);
    }

    @Override
    public WriteListResponse<String> associateIndicatorAddresses(P uniqueId, List<String> ipAddresses, String ownerName) throws IOException {

        Map<String, Object> map = createParamMap("id", uniqueId);
        WriteListResponse<String> data = createListWithParam(getUrlBasePrefix() + ".type.byId.indicators.addresses.byIndicatorId", AddressListResponse.class, ownerName, map, "indicatorId", ipAddresses);

        return data;
    }

    @Override
    public boolean associateIndicatorAddress(P uniqueId, String ipAddress) throws IOException, FailedResponseException {
        return associateIndicatorAddress(uniqueId, ipAddress, null);
    }

    @Override
    public boolean associateIndicatorAddress(P uniqueId, String ipAddress, String ownerName) throws IOException, FailedResponseException {
        
        Map<String, Object> map = createParamMap("id", uniqueId, "indicatorId", ipAddress);
        AddressResponse data = createItem(getUrlBasePrefix() + ".type.byId.indicators.addresses.byIndicatorId", AddressResponse.class, ownerName, map, null);

        return data.isSuccess();
    }

    @Override
    public WriteListResponse<String> associateIndicatorEmailAddresses(P uniqueId, List<String> emailAddresses) throws IOException {
        return associateIndicatorEmailAddresses(uniqueId, emailAddresses, null);
    }

    @Override
    public WriteListResponse<String> associateIndicatorEmailAddresses(P uniqueId, List<String> emailAddresses, String ownerName) throws IOException {

        Map<String, Object> map = createParamMap("id", uniqueId);
        WriteListResponse<String> data = createListWithParam(getUrlBasePrefix() + ".type.byId.indicators.emailAddresses.byIndicatorId", EmailAddressListResponse.class, ownerName, map, "indicatorId", emailAddresses);

        return data;
    }

    @Override
    public boolean associateIndicatorEmailAddress(P uniqueId, String emailAddress) throws IOException, FailedResponseException {
        return associateIndicatorEmailAddress(uniqueId, emailAddress, null);
    }

    @Override
    public boolean associateIndicatorEmailAddress(P uniqueId, String emailAddress, String ownerName) throws IOException, FailedResponseException {
        Map<String, Object> map = createParamMap("id", uniqueId, "indicatorId", emailAddress);
        EmailAddressResponse data = createItem(getUrlBasePrefix() + ".type.byId.indicators.emailAddresses.byIndicatorId", EmailAddressResponse.class, ownerName, map, null);

        return data.isSuccess();
    }

    @Override
    public WriteListResponse<String> associateIndicatorFiles(P uniqueId, List<String> fileHashes) throws IOException {
        return associateIndicatorFiles(uniqueId, fileHashes, null);
    }

    @Override
    public WriteListResponse<String> associateIndicatorFiles(P uniqueId, List<String> fileHashes, String ownerName) throws IOException {
        Map<String, Object> map = createParamMap("id", uniqueId);
        WriteListResponse<String> data = createListWithParam(getUrlBasePrefix() + ".type.byId.indicators.files.byIndicatorId", FileListResponse.class, ownerName, map, "indicatorId", fileHashes);

        return data;
    }

    @Override
    public boolean associateIndicatorFile(P uniqueId, String incidentId) throws IOException, FailedResponseException {
        return associateIndicatorFile(uniqueId, incidentId, null);
    }

    @Override
    public boolean associateIndicatorFile(P uniqueId, String incidentId, String ownerName) throws IOException, FailedResponseException {
        Map<String, Object> map = createParamMap("id", uniqueId, "indicatorId", incidentId);
        FileResponse data = createItem(getUrlBasePrefix() + ".type.byId.indicators.files.byIndicatorId", FileResponse.class, ownerName, map, null);

        return data.isSuccess();
    }

    @Override
    public WriteListResponse<String> associateIndicatorHosts(P uniqueId, List<String> hostNames) throws IOException {
        return associateIndicatorHosts(uniqueId, hostNames, null);
    }

    @Override
    public WriteListResponse<String> associateIndicatorHosts(P uniqueId, List<String> hostNames, String ownerName) throws IOException {
        Map<String, Object> map = createParamMap("id", uniqueId);
        WriteListResponse<String> data = createListWithParam(getUrlBasePrefix() + ".type.byId.indicators.hosts.byIndicatorId", HostListResponse.class, ownerName, map, "indicatorId", hostNames);

        return data;
    }

    @Override
    public boolean associateIndicatorHost(P uniqueId, String signatureId) throws IOException, FailedResponseException {
        return associateIndicatorHost(uniqueId, signatureId, null);
    }

    @Override
    public boolean associateIndicatorHost(P uniqueId, String signatureId, String ownerName) throws IOException, FailedResponseException {
        Map<String, Object> map = createParamMap("id", uniqueId, "indicatorId", signatureId);
        HostResponse data = createItem(getUrlBasePrefix() + ".type.byId.indicators.hosts.byIndicatorId", HostResponse.class, ownerName, map, null);

        return data.isSuccess();
    }

    @Override
    public WriteListResponse<String> associateIndicatorUrls(P uniqueId, List<String> urlTexts) throws IOException {
        return associateIndicatorUrls(uniqueId, urlTexts, null);
    }

    @Override
    public WriteListResponse<String> associateIndicatorUrls(P uniqueId, List<String> urlTexts, String ownerName) throws IOException {
        Map<String, Object> map = createParamMap("id", uniqueId);
        WriteListResponse<String> data = createListWithParam(getUrlBasePrefix() + ".type.byId.indicators.urls.byIndicatorId", UrlListResponse.class, ownerName, map, "indicatorId", urlTexts);

        return data;
    }

    @Override
    public boolean associateIndicatorUrl(P uniqueId, String urlText) throws IOException, FailedResponseException {
        return associateIndicatorUrl(uniqueId, urlText, null);
    }

    @Override
    public boolean associateIndicatorUrl(P uniqueId, String urlText, String ownerName) throws IOException, FailedResponseException {
        Map<String, Object> map = createParamMap("id", uniqueId, "indicatorId", urlText);
        UrlResponse data = createItem(getUrlBasePrefix() + ".type.byId.indicators.urls.byIndicatorId", UrlResponse.class, ownerName, map, null);

        return data.isSuccess();
    }


}
