/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.client.reader.associate;

import com.threatconnect.sdk.client.AbstractClientAdapter;
import com.threatconnect.sdk.client.UrlTypeable;
import com.threatconnect.sdk.client.reader.AbstractBaseReaderAdapter;
import com.threatconnect.sdk.client.response.IterableResponse;
import com.threatconnect.sdk.conn.Connection;
import com.threatconnect.sdk.exception.FailedResponseException;
import com.threatconnect.sdk.server.entity.Address;
import com.threatconnect.sdk.server.entity.CustomIndicator;
import com.threatconnect.sdk.server.entity.Email;
import com.threatconnect.sdk.server.entity.EmailAddress;
import com.threatconnect.sdk.server.entity.File;
import com.threatconnect.sdk.server.entity.Host;
import com.threatconnect.sdk.server.entity.Indicator;
import com.threatconnect.sdk.server.entity.Url;
import com.threatconnect.sdk.server.response.entity.AddressListResponse;
import com.threatconnect.sdk.server.response.entity.AddressResponse;
import com.threatconnect.sdk.server.response.entity.CustomIndicatorListResponse;
import com.threatconnect.sdk.server.response.entity.CustomIndicatorResponse;
import com.threatconnect.sdk.server.response.entity.EmailAddressListResponse;
import com.threatconnect.sdk.server.response.entity.EmailAddressResponse;
import com.threatconnect.sdk.server.response.entity.EmailListResponse;
import com.threatconnect.sdk.server.response.entity.EmailResponse;
import com.threatconnect.sdk.server.response.entity.FileListResponse;
import com.threatconnect.sdk.server.response.entity.FileResponse;
import com.threatconnect.sdk.server.response.entity.GenericIndicatorListResponse;
import com.threatconnect.sdk.server.response.entity.HostListResponse;
import com.threatconnect.sdk.server.response.entity.HostResponse;
import com.threatconnect.sdk.server.response.entity.IndicatorListResponse;
import com.threatconnect.sdk.server.response.entity.UrlListResponse;
import com.threatconnect.sdk.server.response.entity.UrlResponse;

import java.io.IOException;
import java.util.Map;

/**
 *
 * @author dtineo
 */
public abstract class AbstractIndicatorAssociateReaderAdapter<P> extends AbstractBaseReaderAdapter implements IndicatorAssociateReadable<P>, UrlTypeable {

    public AbstractIndicatorAssociateReaderAdapter(Connection conn, Class singleType, Class singleItemType, Class listType) {
        super(conn, singleType, singleItemType, listType);
    }

    @Override
        public IterableResponse<Indicator> getAssociatedIndicators(P uniqueId) throws IOException, FailedResponseException {
        return getAssociatedIndicators(uniqueId, null); 
    }

    @Override
    public IterableResponse<Indicator> getAssociatedIndicators(P uniqueId, String ownerName)
            throws IOException, FailedResponseException {

        Map<String,Object> map = createParamMap("id", uniqueId);
        return getItems(getUrlBasePrefix() + ".byId.indicators", IndicatorListResponse.class, Indicator.class, ownerName, map);
    }

    @Override
    public IterableResponse<Address> getAssociatedIndicatorAddresses(P uniqueId) throws IOException, FailedResponseException {
        return getAssociatedIndicatorAddresses(uniqueId, null); 
    }

    @Override
    public IterableResponse<Address> getAssociatedIndicatorAddresses(P uniqueId, String ownerName)
            throws IOException, FailedResponseException {

        Map<String,Object> map = createParamMap("id", uniqueId);
        return getItems(getUrlBasePrefix() + ".byId.indicators.addresses", AddressListResponse.class, Address.class, ownerName, map);
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
    public IterableResponse<EmailAddress> getAssociatedIndicatorEmailAddresses(P uniqueId) throws IOException, FailedResponseException {
        return getAssociatedIndicatorEmailAddresses(uniqueId, null);
    }

    @Override
    public IterableResponse<EmailAddress> getAssociatedIndicatorEmailAddresses(P uniqueId, String ownerName)
            throws IOException, FailedResponseException {

        Map<String,Object> map = createParamMap("id", uniqueId);
        return getItems(getUrlBasePrefix() + ".byId.indicators.emailAddresses", EmailAddressListResponse.class, EmailAddress.class, ownerName, map);
    }

    @Override
    public EmailAddress getAssociatedIndicatorEmailAddress(P uniqueId, String emailAddress) throws IOException, FailedResponseException {
        return getAssociatedIndicatorEmailAddress(uniqueId, emailAddress, null);
    }

    @Override
    public EmailAddress getAssociatedIndicatorEmailAddress(P uniqueId, String emailAddress, String ownerName)
            throws IOException, FailedResponseException {

        Map<String,Object> map = createParamMap("id", uniqueId, "indicatorId", emailAddress);
        EmailAddressResponse data = getItem(getUrlBasePrefix() + ".byId.indicators.emailAddresses.byIndicatorId", EmailAddressResponse.class, ownerName, map);

        return (EmailAddress)data.getData().getData();
    }

    @Override
    public IterableResponse<File> getAssociatedIndicatorFiles(P uniqueId) throws IOException, FailedResponseException {
        return getAssociatedIndicatorFiles(uniqueId, null);  
    }

    @Override
    public IterableResponse<File> getAssociatedIndicatorFiles(P uniqueId, String ownerName)
            throws IOException, FailedResponseException {

        Map<String,Object> map = createParamMap("id", uniqueId);
        return getItems(getUrlBasePrefix() + ".byId.indicators.files", FileListResponse.class, File.class, ownerName, map);
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
    public IterableResponse<Host> getAssociatedIndicatorHosts(P uniqueId) throws IOException, FailedResponseException {
        return getAssociatedIndicatorHosts(uniqueId, null);  
    }

    @Override
    public IterableResponse<Host> getAssociatedIndicatorHosts(P uniqueId, String ownerName)
            throws IOException, FailedResponseException {

        Map<String,Object> map = createParamMap("id", uniqueId);
        return getItems(getUrlBasePrefix() + ".byId.indicators.hosts", HostListResponse.class, Host.class, ownerName, map);
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
    public IterableResponse<Url> getAssociatedIndicatorUrls(P uniqueId) throws IOException, FailedResponseException {
        return getAssociatedIndicatorUrls(uniqueId, null);   
    }

    @Override
    public IterableResponse<Url> getAssociatedIndicatorUrls(P uniqueId, String ownerName)
            throws IOException, FailedResponseException {

        Map<String,Object> map = createParamMap("id", uniqueId);
        return getItems(getUrlBasePrefix() + ".byId.indicators.urls", UrlListResponse.class, Url.class, ownerName, map);
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
    
    @Override
    public IterableResponse<? extends Indicator> getAssociatedIndicatorsForCustomIndicators(P uniqueId, String associationType) throws IOException, FailedResponseException {
    	Map<String, Object> map = AbstractClientAdapter.createParamMap("uniqueId", uniqueId, "associationType",associationType);
        String url = getUrlBasePrefix() + ".byId.indicators.customIndicator.association.byType";
        //System.out.println("getAssociatedIndicatorsForCustomIndicators url name="+url);
        IterableResponse<Indicator> data = getItems(url, GenericIndicatorListResponse.class, Indicator.class, null, map);
          return data;
    	//throw new RuntimeException("not implemented yet");
    }

    @Override
    public IterableResponse<? extends Indicator> getAssociatedIndicatorsForCustomIndicators(P uniqueId, String associationType, String targetType)
            throws IOException, FailedResponseException {
    	Map<String, Object> map = AbstractClientAdapter.createParamMap("uniqueId", uniqueId, "associationType",associationType,"targetType",targetType);
        String url = getUrlBasePrefix() + ".byId.indicators.customIndicator.association.byTargetType";
        //System.out.println("getAssociatedIndicatorsForCustomIndicators url name="+url);
        IterableResponse<Indicator> data = getItems(url, GenericIndicatorListResponse.class, Indicator.class, null, map);
          return data;
    	//throw new RuntimeException("not implemented yet");
    }


}
