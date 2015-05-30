/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.client.reader.associate;

import com.threatconnect.sdk.exception.FailedResponseException;
import com.threatconnect.sdk.server.entity.Address;
import com.threatconnect.sdk.server.entity.Email;
import com.threatconnect.sdk.server.entity.File;
import com.threatconnect.sdk.server.entity.Host;
import com.threatconnect.sdk.server.entity.Indicator;
import com.threatconnect.sdk.server.entity.Url;
import com.threatconnect.sdk.server.entity.Address;
import com.threatconnect.sdk.server.entity.Email;
import com.threatconnect.sdk.server.entity.File;
import com.threatconnect.sdk.server.entity.Host;
import com.threatconnect.sdk.server.entity.Url;

import java.io.IOException;
import java.util.List;

/**
 *
 * @author dtineo
 */
public interface IndicatorAssociateReadable<P> {
    
    public List<Indicator> getAssociatedIndicators(P uniqueId) throws Exception;

    public List<Indicator> getAssociatedIndicators(P uniqueId, String ownerName)
            throws Exception;

    public List<Address> getAssociatedIndicatorAddresses(P uniqueId) throws Exception;

    public List<Address> getAssociatedIndicatorAddresses(P uniqueId, String ownerName)
            throws Exception;

    public Address getAssociatedIndicatorAddress(P uniqueId, String ipAddress) throws Exception;

    public Address getAssociatedIndicatorAddress(P uniqueId, String ipAddress, String ownerName)
            throws Exception;

    public List<Email> getAssociatedIndicatorEmails(P uniqueId) throws Exception;

    public List<Email> getAssociatedIndicatorEmails(P uniqueId, String ownerName)
            throws Exception;

    public Email getAssociatedIndicatorEmail(P uniqueId, String emailAddress) throws Exception;

    public Email getAssociatedIndicatorEmail(P uniqueId, String emailAddress, String ownerName)
            throws Exception;

    public List<File> getAssociatedIndicatorFiles(P uniqueId) throws Exception;

    public List<File> getAssociatedIndicatorFiles(P uniqueId, String ownerName)
            throws Exception;

    public File getAssociatedIndicatorFile(P uniqueId, String fileHash) throws Exception;

    public File getAssociatedIndicatorFile(P uniqueId, String fileHash, String ownerName)
            throws Exception;

    public List<Host> getAssociatedIndicatorHosts(P uniqueId) throws Exception;

    public List<Host> getAssociatedIndicatorHosts(P uniqueId, String ownerName)
            throws Exception;

    public Host getAssociatedIndicatorHost(P uniqueId, String hostName) throws Exception;

    public Host getAssociatedIndicatorHost(P uniqueId, String hostName, String ownerName)
            throws Exception;

    public List<Url> getAssociatedIndicatorUrls(P uniqueId) throws Exception;

    public List<Url> getAssociatedIndicatorUrls(P uniqueId, String ownerName)
            throws Exception;

    public Url getAssociatedIndicatorUrl(P uniqueId, String urlText) throws Exception;

    public Url getAssociatedIndicatorUrl(P uniqueId, String urlText, String ownerName)
            throws Exception;

}