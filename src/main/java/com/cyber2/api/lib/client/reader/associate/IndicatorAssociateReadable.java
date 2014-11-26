/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyber2.api.lib.client.reader.associate;

import com.cyber2.api.lib.exception.FailedResponseException;
import com.cyber2.api.lib.server.entity.Address;
import com.cyber2.api.lib.server.entity.Email;
import com.cyber2.api.lib.server.entity.File;
import com.cyber2.api.lib.server.entity.Host;
import com.cyber2.api.lib.server.entity.Indicator;
import com.cyber2.api.lib.server.entity.Url;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author dtineo
 */
public interface IndicatorAssociateReadable<P> {
    
    public List<Indicator> getAssociatedIndicators(P uniqueId) throws IOException, FailedResponseException;

    public List<Indicator> getAssociatedIndicators(P uniqueId, String ownerName) 
            throws IOException, FailedResponseException;

    public List<Address> getAssociatedIndicatorAddresses(P uniqueId) throws IOException, FailedResponseException;

    public List<Address> getAssociatedIndicatorAddresses(P uniqueId, String ownerName) 
            throws IOException, FailedResponseException;

    public Address getAssociatedIndicatorAddress(P uniqueId, String ipAddress) throws IOException, FailedResponseException;

    public Address getAssociatedIndicatorAddress(P uniqueId, String ipAddress, String ownerName) 
            throws IOException, FailedResponseException;

    public List<Email> getAssociatedIndicatorEmails(P uniqueId) throws IOException, FailedResponseException;

    public List<Email> getAssociatedIndicatorEmails(P uniqueId, String ownerName) 
            throws IOException, FailedResponseException;

    public Email getAssociatedIndicatorEmail(P uniqueId, String emailAddress) throws IOException, FailedResponseException;

    public Email getAssociatedIndicatorEmail(P uniqueId, String emailAddress, String ownerName) 
            throws IOException, FailedResponseException;

    public List<File> getAssociatedIndicatorFiles(P uniqueId) throws IOException, FailedResponseException;

    public List<File> getAssociatedIndicatorFiles(P uniqueId, String ownerName)   
            throws IOException, FailedResponseException;

    public File getAssociatedIndicatorFile(P uniqueId, String fileHash) throws IOException, FailedResponseException;

    public File getAssociatedIndicatorFile(P uniqueId, String fileHash, String ownerName)   
            throws IOException, FailedResponseException;

    public List<Host> getAssociatedIndicatorHosts(P uniqueId) throws IOException, FailedResponseException;

    public List<Host> getAssociatedIndicatorHosts(P uniqueId, String ownerName)   
            throws IOException, FailedResponseException;

    public Host getAssociatedIndicatorHost(P uniqueId, String hostName) throws IOException, FailedResponseException;

    public Host getAssociatedIndicatorHost(P uniqueId, String hostName, String ownerName)   
            throws IOException, FailedResponseException;

    public List<Url> getAssociatedIndicatorUrls(P uniqueId) throws IOException, FailedResponseException;

    public List<Url> getAssociatedIndicatorUrls(P uniqueId, String ownerName)     
            throws IOException, FailedResponseException;

    public Url getAssociatedIndicatorUrl(P uniqueId, String urlText) throws IOException, FailedResponseException;

    public Url getAssociatedIndicatorUrl(P uniqueId, String urlText, String ownerName)     
            throws IOException, FailedResponseException;

}