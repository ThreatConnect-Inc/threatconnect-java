/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.client.reader;

import com.threatconnect.sdk.client.reader.associate.IndicatorAssociateReadable;
import com.threatconnect.sdk.client.reader.associate.GroupAssociateReadable;
import com.threatconnect.sdk.client.reader.associate.AbstractIndicatorAssociateReaderAdapter;
import com.threatconnect.sdk.client.reader.associate.AbstractGroupAssociateReaderAdapter;
import com.threatconnect.sdk.client.UrlTypeable;
import com.threatconnect.sdk.conn.Connection;
import com.threatconnect.sdk.conn.RequestExecutor;
import com.threatconnect.sdk.exception.FailedResponseException;
import com.threatconnect.sdk.server.entity.Address;
import com.threatconnect.sdk.server.entity.Adversary;
import com.threatconnect.sdk.server.entity.Email;
import com.threatconnect.sdk.server.entity.File;
import com.threatconnect.sdk.server.entity.Group;
import com.threatconnect.sdk.server.entity.Host;
import com.threatconnect.sdk.server.entity.Incident;
import com.threatconnect.sdk.server.entity.Indicator;
import com.threatconnect.sdk.server.entity.SecurityLabel;
import com.threatconnect.sdk.server.entity.Signature;
import com.threatconnect.sdk.server.entity.Threat;
import com.threatconnect.sdk.server.entity.Url;
import com.threatconnect.sdk.server.response.entity.SecurityLabelListResponse;
import com.threatconnect.sdk.server.response.entity.SecurityLabelResponse;
import com.threatconnect.sdk.client.UrlTypeable;
import com.threatconnect.sdk.client.reader.associate.AbstractGroupAssociateReaderAdapter;
import com.threatconnect.sdk.client.reader.associate.AbstractIndicatorAssociateReaderAdapter;
import com.threatconnect.sdk.client.reader.associate.GroupAssociateReadable;
import com.threatconnect.sdk.client.reader.associate.IndicatorAssociateReadable;
import com.threatconnect.sdk.conn.Connection;
import com.threatconnect.sdk.conn.RequestExecutor;
import com.threatconnect.sdk.exception.FailedResponseException;
import com.threatconnect.sdk.server.entity.Address;
import com.threatconnect.sdk.server.entity.Adversary;
import com.threatconnect.sdk.server.entity.Email;
import com.threatconnect.sdk.server.entity.File;
import com.threatconnect.sdk.server.entity.Group;
import com.threatconnect.sdk.server.entity.Host;
import com.threatconnect.sdk.server.entity.Indicator;
import com.threatconnect.sdk.server.entity.SecurityLabel;
import com.threatconnect.sdk.server.entity.Signature;
import com.threatconnect.sdk.server.entity.Threat;
import com.threatconnect.sdk.server.entity.Url;
import com.threatconnect.sdk.server.response.entity.SecurityLabelListResponse;
import com.threatconnect.sdk.server.response.entity.SecurityLabelResponse;

import java.io.IOException;
import java.util.List;

/**
 *
 * @author dtineo
 */
public class SecurityLabelReaderAdapter
    extends AbstractBaseReaderAdapter<SecurityLabel,String>
    implements UrlTypeable, GroupAssociateReadable<String>, IndicatorAssociateReadable<String>
{

    // composite pattern
    private AbstractGroupAssociateReaderAdapter<String> groupAssocReader;
    private AbstractIndicatorAssociateReaderAdapter<String> indAssocReader;


    protected SecurityLabelReaderAdapter(Connection conn, RequestExecutor executor) {
        super(conn, executor, SecurityLabelResponse.class, SecurityLabelListResponse.class);

        initComposite();
    }

    private void initComposite() {

        groupAssocReader = new AbstractGroupAssociateReaderAdapter<String>(
                            SecurityLabelReaderAdapter.this.getConn()
                          , SecurityLabelReaderAdapter.this.executor
                          , SecurityLabelReaderAdapter.this.singleType
                          , SecurityLabelReaderAdapter.this.listType
            ) {
            @Override
            protected String getUrlBasePrefix() {
                return SecurityLabelReaderAdapter.this.getUrlBasePrefix();
            }

            @Override
            public String getUrlType() {
                return SecurityLabelReaderAdapter.this.getUrlType();
            }
        };

        indAssocReader = new AbstractIndicatorAssociateReaderAdapter<String>(
                            SecurityLabelReaderAdapter.this.getConn()
                          , SecurityLabelReaderAdapter.this.executor
                          , SecurityLabelReaderAdapter.this.singleType
                          , SecurityLabelReaderAdapter.this.listType
            ) {
            @Override
            protected String getUrlBasePrefix() {
                return SecurityLabelReaderAdapter.this.getUrlBasePrefix();
            }
            @Override
            public String getUrlType() {
                return SecurityLabelReaderAdapter.this.getUrlType();
            }
        };

    }

    @Override
    public String getUrlType() {
        return "securityLabels";
    }

    @Override
    protected String getUrlBasePrefix() {
        return "v2.securityLabels";
    }

    @Override
    public List<Group> getAssociatedGroups(String uniqueId) throws IOException, FailedResponseException
    {
        return groupAssocReader.getAssociatedGroups(uniqueId);
    }

    @Override
    public List<Group> getAssociatedGroups(String uniqueId, String ownerName) throws IOException, FailedResponseException {
        return groupAssocReader.getAssociatedGroups(uniqueId,ownerName);
    }

    @Override
    public List<Adversary> getAssociatedGroupAdversaries(String uniqueId) throws IOException, FailedResponseException {
        return groupAssocReader.getAssociatedGroupAdversaries(uniqueId);
    }

    @Override
    public List<Adversary> getAssociatedGroupAdversaries(String uniqueId, String ownerName) throws IOException, FailedResponseException {
        return groupAssocReader.getAssociatedGroupAdversaries(uniqueId,ownerName);
    }

    @Override
    public Adversary getAssociatedGroupAdversary(String uniqueId, Integer adversaryId) throws IOException, FailedResponseException {
        return groupAssocReader.getAssociatedGroupAdversary(uniqueId,adversaryId);
    }

    @Override
    public Adversary getAssociatedGroupAdversary(String uniqueId, Integer adversaryId, String ownerName) throws IOException, FailedResponseException {
        return groupAssocReader.getAssociatedGroupAdversary(uniqueId,adversaryId,ownerName);
    }

    @Override
    public List<Email> getAssociatedGroupEmails(String uniqueId) throws IOException, FailedResponseException {
        return groupAssocReader.getAssociatedGroupEmails(uniqueId);
    }

    @Override
    public List<Email> getAssociatedGroupEmails(String uniqueId, String ownerName) throws IOException, FailedResponseException {
        return groupAssocReader.getAssociatedGroupEmails(uniqueId,ownerName);
    }

    @Override
    public Email getAssociatedGroupEmail(String uniqueId, Integer emailId) throws IOException, FailedResponseException {
        return groupAssocReader.getAssociatedGroupEmail(uniqueId,emailId);
    }

    @Override
    public Email getAssociatedGroupEmail(String uniqueId, Integer emailId, String ownerName) throws IOException, FailedResponseException {
        return groupAssocReader.getAssociatedGroupEmail(uniqueId, emailId, ownerName);
    }

    @Override
    public List<Incident> getAssociatedGroupIncidents(String uniqueId) throws IOException, FailedResponseException {
        return groupAssocReader.getAssociatedGroupIncidents(uniqueId);
    }

    @Override
    public List<Incident> getAssociatedGroupIncidents(String uniqueId, String ownerName) throws IOException, FailedResponseException {
        return groupAssocReader.getAssociatedGroupIncidents(uniqueId, ownerName);
    }

    @Override
    public Incident getAssociatedGroupIncident(String uniqueId, Integer incidentId) throws IOException, FailedResponseException {
        return groupAssocReader.getAssociatedGroupIncident(uniqueId, incidentId);
    }

    @Override
    public Incident getAssociatedGroupIncident(String uniqueId, Integer incidentId, String ownerName) throws IOException, FailedResponseException {
        return groupAssocReader.getAssociatedGroupIncident(uniqueId, incidentId, ownerName);
    }

    @Override
    public List<Signature> getAssociatedGroupSignatures(String uniqueId) throws IOException, FailedResponseException {
        return groupAssocReader.getAssociatedGroupSignatures(uniqueId);
    }

    @Override
    public List<Signature> getAssociatedGroupSignatures(String uniqueId, String ownerName) throws IOException, FailedResponseException {
        return groupAssocReader.getAssociatedGroupSignatures(uniqueId, ownerName);
    }

    @Override
    public Signature getAssociatedGroupSignature(String uniqueId, Integer signatureId) throws IOException, FailedResponseException {
        return groupAssocReader.getAssociatedGroupSignature(uniqueId, signatureId);
    }

    @Override
    public Signature getAssociatedGroupSignature(String uniqueId, Integer signatureId, String ownerName) throws IOException, FailedResponseException {
        return groupAssocReader.getAssociatedGroupSignature(uniqueId, signatureId, ownerName);
    }

    @Override
    public List<Threat> getAssociatedGroupThreats(String uniqueId) throws IOException, FailedResponseException {
        return groupAssocReader.getAssociatedGroupThreats(uniqueId);
    }

    @Override
    public List<Threat> getAssociatedGroupThreats(String uniqueId, String ownerName) throws IOException, FailedResponseException {
        return groupAssocReader.getAssociatedGroupThreats(uniqueId, ownerName);
    }

    @Override
    public Threat getAssociatedGroupThreat(String uniqueId, Integer threatId) throws IOException, FailedResponseException {
        return groupAssocReader.getAssociatedGroupThreat(uniqueId, threatId);
    }

    @Override
    public Threat getAssociatedGroupThreat(String uniqueId, Integer threatId, String ownerName) throws IOException, FailedResponseException {
        return groupAssocReader.getAssociatedGroupThreat(uniqueId, threatId, ownerName);
    }

    @Override
    public List<Indicator> getAssociatedIndicators(String uniqueId) throws IOException, FailedResponseException {
        return indAssocReader.getAssociatedIndicators(uniqueId);
    }

    @Override
    public List<Indicator> getAssociatedIndicators(String uniqueId, String ownerName) throws IOException, FailedResponseException {
        return indAssocReader.getAssociatedIndicators(uniqueId, ownerName);
    }

    @Override
    public List<Address> getAssociatedIndicatorAddresses(String uniqueId) throws IOException, FailedResponseException {
        return indAssocReader.getAssociatedIndicatorAddresses(uniqueId);
    }

    @Override
    public List<Address> getAssociatedIndicatorAddresses(String uniqueId, String ownerName) throws IOException, FailedResponseException {
        return indAssocReader.getAssociatedIndicatorAddresses(uniqueId, ownerName);
    }

    @Override
    public Address getAssociatedIndicatorAddress(String uniqueId, String ipAddress) throws IOException, FailedResponseException {
        return indAssocReader.getAssociatedIndicatorAddress(uniqueId, ipAddress);
    }

    @Override
    public Address getAssociatedIndicatorAddress(String uniqueId, String ipAddress, String ownerName) throws IOException, FailedResponseException {
        return indAssocReader.getAssociatedIndicatorAddress(uniqueId, ipAddress, ownerName);
    }

    @Override
    public List<Email> getAssociatedIndicatorEmails(String uniqueId) throws IOException, FailedResponseException {
        return indAssocReader.getAssociatedIndicatorEmails(uniqueId);
    }

    @Override
    public List<Email> getAssociatedIndicatorEmails(String uniqueId, String ownerName) throws IOException, FailedResponseException {
        return indAssocReader.getAssociatedIndicatorEmails(uniqueId, ownerName);
    }

    @Override
    public Email getAssociatedIndicatorEmail(String uniqueId, String emailAddress) throws IOException, FailedResponseException {
        return indAssocReader.getAssociatedIndicatorEmail(uniqueId, emailAddress);
    }

    @Override
    public Email getAssociatedIndicatorEmail(String uniqueId, String emailAddress, String ownerName) throws IOException, FailedResponseException {
        return indAssocReader.getAssociatedIndicatorEmail(uniqueId, emailAddress, ownerName);
    }

    @Override
    public List<File> getAssociatedIndicatorFiles(String uniqueId) throws IOException, FailedResponseException {
        return indAssocReader.getAssociatedIndicatorFiles(uniqueId);
    }

    @Override
    public List<File> getAssociatedIndicatorFiles(String uniqueId, String ownerName) throws IOException, FailedResponseException {
        return indAssocReader.getAssociatedIndicatorFiles(uniqueId, ownerName);
    }

    @Override
    public File getAssociatedIndicatorFile(String uniqueId, String fileHash) throws IOException, FailedResponseException {
        return indAssocReader.getAssociatedIndicatorFile(uniqueId, fileHash);
    }

    @Override
    public File getAssociatedIndicatorFile(String uniqueId, String fileHash, String ownerName) throws IOException, FailedResponseException {
        return indAssocReader.getAssociatedIndicatorFile(uniqueId, fileHash, ownerName);
    }

    @Override
    public List<Host> getAssociatedIndicatorHosts(String uniqueId) throws IOException, FailedResponseException {
        return indAssocReader.getAssociatedIndicatorHosts(uniqueId);
    }

    @Override
    public List<Host> getAssociatedIndicatorHosts(String uniqueId, String ownerName) throws IOException, FailedResponseException {
        return indAssocReader.getAssociatedIndicatorHosts(uniqueId, ownerName);
    }

    @Override
    public Host getAssociatedIndicatorHost(String uniqueId, String hostName) throws IOException, FailedResponseException {
        return indAssocReader.getAssociatedIndicatorHost(uniqueId, hostName);
    }

    @Override
    public Host getAssociatedIndicatorHost(String uniqueId, String hostName, String ownerName) throws IOException, FailedResponseException {
        return indAssocReader.getAssociatedIndicatorHost(uniqueId, hostName, ownerName);
    }

    @Override
    public List<Url> getAssociatedIndicatorUrls(String uniqueId) throws IOException, FailedResponseException {
        return indAssocReader.getAssociatedIndicatorUrls(uniqueId);
    }

    @Override
    public List<Url> getAssociatedIndicatorUrls(String uniqueId, String ownerName) throws IOException, FailedResponseException {
        return indAssocReader.getAssociatedIndicatorUrls(uniqueId, ownerName);
    }

    @Override
    public Url getAssociatedIndicatorUrl(String uniqueId, String urlText) throws IOException, FailedResponseException {
        return indAssocReader.getAssociatedIndicatorUrl(uniqueId, urlText);
    }

    @Override
    public Url getAssociatedIndicatorUrl(String uniqueId, String urlText, String ownerName) throws IOException, FailedResponseException {
        return indAssocReader.getAssociatedIndicatorUrl(uniqueId, urlText, ownerName);
    }

}