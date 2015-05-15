/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.client.reader.associate;

import com.threatconnect.sdk.client.UrlTypeable;
import com.threatconnect.sdk.client.reader.AbstractBaseReaderAdapter;
import com.threatconnect.sdk.conn.Connection;
import com.threatconnect.sdk.conn.RequestExecutor;
import com.threatconnect.sdk.exception.FailedResponseException;
import com.threatconnect.sdk.server.entity.Address;
import com.threatconnect.sdk.server.entity.Email;
import com.threatconnect.sdk.server.entity.File;
import com.threatconnect.sdk.server.entity.Host;
import com.threatconnect.sdk.server.entity.Indicator;
import com.threatconnect.sdk.server.entity.Url;
import com.threatconnect.sdk.server.response.entity.AddressListResponse;
import com.threatconnect.sdk.server.response.entity.AddressResponse;
import com.threatconnect.sdk.server.response.entity.EmailListResponse;
import com.threatconnect.sdk.server.response.entity.EmailResponse;
import com.threatconnect.sdk.server.response.entity.FileListResponse;
import com.threatconnect.sdk.server.response.entity.FileResponse;
import com.threatconnect.sdk.server.response.entity.HostListResponse;
import com.threatconnect.sdk.server.response.entity.HostResponse;
import com.threatconnect.sdk.server.response.entity.IndicatorListResponse;
import com.threatconnect.sdk.server.response.entity.UrlListResponse;
import com.threatconnect.sdk.server.response.entity.UrlResponse;
import com.threatconnect.sdk.server.response.entity.AddressListResponse;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author dtineo
 */
public abstract class AbstractIndicatorAssociateReaderAdapter<P> extends AbstractBaseReaderAdapter implements IndicatorAssociateReadable<P>, UrlTypeable {

    public AbstractIndicatorAssociateReaderAdapter(Connection conn, RequestExecutor executor, Class singleType, Class listType) {
        super(conn, executor, singleType, listType);
    }

    @Override
        public List<Indicator> getAssociatedIndicators(P uniqueId) throws IOException, FailedResponseException {
        return getAssociatedIndicators(uniqueId, null); 
    }

    @Override
    public List<Indicator> getAssociatedIndicators(P uniqueId, String ownerName) 
            throws IOException, FailedResponseException {

        Map<String,Object> map = createParamMap("id", uniqueId);
        IndicatorListResponse data = getList(getUrlBasePrefix() + ".byId.indicators", IndicatorListResponse.class, ownerName, map);

        return (List<Indicator>)data.getData().getData();
    }

    @Override
    public List<Address> getAssociatedIndicatorAddresses(P uniqueId) throws IOException, FailedResponseException {
        return getAssociatedIndicatorAddresses(uniqueId, null); 
    }

    @Override
    public List<Address> getAssociatedIndicatorAddresses(P uniqueId, String ownerName) 
            throws IOException, FailedResponseException {

        Map<String,Object> map = createParamMap("id", uniqueId);
        AddressListResponse data = getList(getUrlBasePrefix() + ".byId.indicators.addresses", AddressListResponse.class, ownerName, map);

        return (List<Address>)data.getData().getData();
    }

    @Override
    public Address getAssociatedIndicatorAddress(P uniqueId, String ipAddress) throws IOException, FailedResponseException {
        return getAssociatedIndicatorAddress(uniqueId, ipAddress, null); 
    }

    @Override
    public Address getAssociatedIndicatorAddress(P uniqueId, String ipAddress, String ownerName) 
            throws IOException, FailedResponseException {

        Map<String,Object> map = createParamMap("id", uniqueId, "indicatorId", ipAddress);
        AddressResponse data = getItem(getUrlBasePrefix() + ".byId.indicators.addresses.byIndicatorId", AddressResponse.class, ownerName, map);

        return (Address)data.getData().getData();
    }

    @Override
    public List<Email> getAssociatedIndicatorEmails(P uniqueId) throws IOException, FailedResponseException { 
        return getAssociatedIndicatorEmails(uniqueId, null); 
    }

    @Override
    public List<Email> getAssociatedIndicatorEmails(P uniqueId, String ownerName) 
            throws IOException, FailedResponseException {

        Map<String,Object> map = createParamMap("id", uniqueId);
        EmailListResponse data = getList(getUrlBasePrefix() + ".byId.indicators.emailAddresses", EmailListResponse.class, ownerName, map);

        return (List<Email>)data.getData().getData();
    }

    @Override
    public Email getAssociatedIndicatorEmail(P uniqueId, String emailAddress) throws IOException, FailedResponseException { 
        return getAssociatedIndicatorEmail(uniqueId, emailAddress, null); 
    }

    @Override
    public Email getAssociatedIndicatorEmail(P uniqueId, String emailAddress, String ownerName) 
            throws IOException, FailedResponseException {

        Map<String,Object> map = createParamMap("id", uniqueId, "indicatorId", emailAddress);
        EmailResponse data = getItem(getUrlBasePrefix() + ".byId.indicators.emailAddresses.byIndicatorId", EmailResponse.class, ownerName, map);

        return (Email)data.getData().getData();
    }

    @Override
    public List<File> getAssociatedIndicatorFiles(P uniqueId) throws IOException, FailedResponseException {
        return getAssociatedIndicatorFiles(uniqueId, null);  
    }

    @Override
    public List<File> getAssociatedIndicatorFiles(P uniqueId, String ownerName)   
            throws IOException, FailedResponseException {

        Map<String,Object> map = createParamMap("id", uniqueId);
        FileListResponse data = getList(getUrlBasePrefix() + ".byId.indicators.files", FileListResponse.class, ownerName, map);

        return (List<File>)data.getData().getData();
    }

    @Override
    public File getAssociatedIndicatorFile(P uniqueId, String fileHash) throws IOException, FailedResponseException {
        return getAssociatedIndicatorFile(uniqueId, fileHash, null);  
    }

    @Override
    public File getAssociatedIndicatorFile(P uniqueId, String fileHash, String ownerName)   
            throws IOException, FailedResponseException {

        Map<String,Object> map = createParamMap("id", uniqueId, "indicatorId", fileHash);
        FileResponse data = getItem(getUrlBasePrefix() + ".byId.indicators.files.byIndicatorId", FileResponse.class, ownerName, map);

        return (File)data.getData().getData();
    }

    @Override
    public List<Host> getAssociatedIndicatorHosts(P uniqueId) throws IOException, FailedResponseException {
        return getAssociatedIndicatorHosts(uniqueId, null);  
    }

    @Override
    public List<Host> getAssociatedIndicatorHosts(P uniqueId, String ownerName)   
            throws IOException, FailedResponseException {

        Map<String,Object> map = createParamMap("id", uniqueId);
        HostListResponse data = getList(getUrlBasePrefix() + ".byId.indicators.hosts", HostListResponse.class, ownerName, map);

        return (List<Host>)data.getData().getData();
    }

    @Override
    public Host getAssociatedIndicatorHost(P uniqueId, String hostName) throws IOException, FailedResponseException {
        return getAssociatedIndicatorHost(uniqueId, hostName, null);  
    }

    @Override
    public Host getAssociatedIndicatorHost(P uniqueId, String hostName, String ownerName)   
            throws IOException, FailedResponseException {

        Map<String,Object> map = createParamMap("id", uniqueId, "indicatorId", hostName);
        HostResponse data = getItem(getUrlBasePrefix() + ".byId.indicators.hosts.byIndicatorId", HostResponse.class, ownerName, map);

        return (Host)data.getData().getData();
    }

    @Override
    public List<Url> getAssociatedIndicatorUrls(P uniqueId) throws IOException, FailedResponseException {
        return getAssociatedIndicatorUrls(uniqueId, null);   
    }

    @Override
    public List<Url> getAssociatedIndicatorUrls(P uniqueId, String ownerName)     
            throws IOException, FailedResponseException {

        Map<String,Object> map = createParamMap("id", uniqueId);
        UrlListResponse data = getList(getUrlBasePrefix() + ".byId.indicators.urls", UrlListResponse.class, ownerName, map);

        return (List<Url>)data.getData().getData();
    }

    @Override
    public Url getAssociatedIndicatorUrl(P uniqueId, String urlText) throws IOException, FailedResponseException {
        return getAssociatedIndicatorUrl(uniqueId, urlText, null);   
    }

    @Override
    public Url getAssociatedIndicatorUrl(P uniqueId, String urlText, String ownerName)     
            throws IOException, FailedResponseException {

        Map<String,Object> map = createParamMap("id", uniqueId, "indicatorId", urlText);
        UrlResponse data = getItem(getUrlBasePrefix() + ".byId.indicators.urls.byIndicatorId", UrlResponse.class, ownerName, map);

        return (Url)data.getData().getData();
    }

}
