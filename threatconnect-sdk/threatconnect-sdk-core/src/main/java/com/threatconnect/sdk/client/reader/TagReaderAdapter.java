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
import com.threatconnect.sdk.client.response.IterableResponse;
import com.threatconnect.sdk.conn.Connection;
import com.threatconnect.sdk.exception.FailedResponseException;
import com.threatconnect.sdk.server.entity.*;
import com.threatconnect.sdk.server.response.entity.TagListResponse;
import com.threatconnect.sdk.server.response.entity.TagResponse;

import java.io.IOException;

/**
 *
 * @author dtineo
 */
public class TagReaderAdapter
    extends AbstractBaseReaderAdapter<Tag,String>
    implements UrlTypeable, GroupAssociateReadable<String>, IndicatorAssociateReadable<String>
{

    // composite pattern
    private AbstractGroupAssociateReaderAdapter<String> groupAssocReader;
    private AbstractIndicatorAssociateReaderAdapter<String> indAssocReader;


    protected TagReaderAdapter(Connection conn) {
        super(conn, TagResponse.class, Tag.class, TagListResponse.class);

        initComposite();
    }

    private void initComposite() {

        groupAssocReader = new AbstractGroupAssociateReaderAdapter<String>(
                            TagReaderAdapter.this.getConn()
                          , TagReaderAdapter.this.singleType
                          , TagReaderAdapter.this.singleItemType
                          , TagReaderAdapter.this.listType) {
            @Override
            protected String getUrlBasePrefix() {
                return TagReaderAdapter.this.getUrlBasePrefix();
            }

            @Override
            public String getUrlType() {
                return TagReaderAdapter.this.getUrlType();
            }
        };

        indAssocReader = new AbstractIndicatorAssociateReaderAdapter<String>(
                            TagReaderAdapter.this.getConn()
                          , TagReaderAdapter.this.singleType
                          , TagReaderAdapter.this.singleItemType
                          , TagReaderAdapter.this.listType
            ) {
            @Override
            protected String getUrlBasePrefix() {
                return TagReaderAdapter.this.getUrlBasePrefix();
            }
            @Override
            public String getUrlType() {
                return TagReaderAdapter.this.getUrlType();
            }
        };

    }


    @Override
    public String getUrlType() {
        return "tags";
    }

    @Override
    protected String getUrlBasePrefix() {
        return "v2.tags";
    }

    @Override
    public IterableResponse<Group> getAssociatedGroups(String uniqueId) throws IOException, FailedResponseException
    {
        return groupAssocReader.getAssociatedGroups(uniqueId);
    }

    @Override
    public IterableResponse<Group> getAssociatedGroups(String uniqueId, String ownerName) throws IOException, FailedResponseException {
        return groupAssocReader.getAssociatedGroups(uniqueId,ownerName);
    }

    @Override
    public IterableResponse<Adversary> getAssociatedGroupAdversaries(String uniqueId) throws IOException, FailedResponseException {
        return groupAssocReader.getAssociatedGroupAdversaries(uniqueId);
    }

    @Override
    public IterableResponse<Adversary> getAssociatedGroupAdversaries(String uniqueId, String ownerName) throws IOException, FailedResponseException {
        return groupAssocReader.getAssociatedGroupAdversaries(uniqueId,ownerName);
    }

    @Override
    public Adversary getAssociatedGroupAdversary(String uniqueId, Long adversaryId) throws IOException, FailedResponseException {
        return groupAssocReader.getAssociatedGroupAdversary(uniqueId,adversaryId);
    }

    @Override
    public Adversary getAssociatedGroupAdversary(String uniqueId, Long adversaryId, String ownerName) throws IOException, FailedResponseException {
        return groupAssocReader.getAssociatedGroupAdversary(uniqueId,adversaryId,ownerName);
    }

    @Override
    public IterableResponse<Email> getAssociatedGroupEmails(String uniqueId) throws IOException, FailedResponseException {
        return groupAssocReader.getAssociatedGroupEmails(uniqueId);
    }

    @Override
    public IterableResponse<Email> getAssociatedGroupEmails(String uniqueId, String ownerName) throws IOException, FailedResponseException {
        return groupAssocReader.getAssociatedGroupEmails(uniqueId,ownerName);
    }

    @Override
    public Email getAssociatedGroupEmail(String uniqueId, Long emailId) throws IOException, FailedResponseException {
        return groupAssocReader.getAssociatedGroupEmail(uniqueId,emailId);
    }

    @Override
    public Email getAssociatedGroupEmail(String uniqueId, Long emailId, String ownerName) throws IOException, FailedResponseException {
        return groupAssocReader.getAssociatedGroupEmail(uniqueId, emailId, ownerName);
    }

    @Override
    public IterableResponse<Incident> getAssociatedGroupIncidents(String uniqueId) throws IOException, FailedResponseException {
        return groupAssocReader.getAssociatedGroupIncidents(uniqueId);
    }

    @Override
    public IterableResponse<Incident> getAssociatedGroupIncidents(String uniqueId, String ownerName) throws IOException, FailedResponseException {
        return groupAssocReader.getAssociatedGroupIncidents(uniqueId, ownerName);
    }

    @Override
    public Incident getAssociatedGroupIncident(String uniqueId, Long incidentId) throws IOException, FailedResponseException {
        return groupAssocReader.getAssociatedGroupIncident(uniqueId, incidentId);
    }

    @Override
    public Incident getAssociatedGroupIncident(String uniqueId, Long incidentId, String ownerName) throws IOException, FailedResponseException {
        return groupAssocReader.getAssociatedGroupIncident(uniqueId, incidentId, ownerName);
    }

    @Override
    public IterableResponse<Document> getAssociatedGroupDocuments(String uniqueId) throws IOException, FailedResponseException
    {
        return groupAssocReader.getAssociatedGroupDocuments(uniqueId);
    }

    @Override
    public IterableResponse<Document> getAssociatedGroupDocuments(String uniqueId, String ownerName) throws IOException, FailedResponseException
    {
        return groupAssocReader.getAssociatedGroupDocuments(uniqueId, ownerName);
    }

    @Override
    public Document getAssociatedGroupDocument(String uniqueId, Long incidentId) throws IOException, FailedResponseException
    {
        return getAssociatedGroupDocument(uniqueId, incidentId, null);
    }

    @Override
    public Document getAssociatedGroupDocument(String uniqueId, Long incidentId, String ownerName) throws IOException, FailedResponseException {
        return groupAssocReader.getAssociatedGroupDocument(uniqueId, incidentId, ownerName);
    }

    @Override
    public IterableResponse<Signature> getAssociatedGroupSignatures(String uniqueId) throws IOException, FailedResponseException {
        return groupAssocReader.getAssociatedGroupSignatures(uniqueId);
    }

    @Override
    public IterableResponse<Signature> getAssociatedGroupSignatures(String uniqueId, String ownerName) throws IOException, FailedResponseException {
        return groupAssocReader.getAssociatedGroupSignatures(uniqueId, ownerName);
    }

    @Override
    public Signature getAssociatedGroupSignature(String uniqueId, Long signatureId) throws IOException, FailedResponseException {
        return groupAssocReader.getAssociatedGroupSignature(uniqueId, signatureId);
    }

    @Override
    public Signature getAssociatedGroupSignature(String uniqueId, Long signatureId, String ownerName) throws IOException, FailedResponseException {
        return groupAssocReader.getAssociatedGroupSignature(uniqueId, signatureId, ownerName);
    }
    
    @Override
    public IterableResponse<Campaign> getAssociatedGroupCampaigns(String uniqueId) throws IOException, FailedResponseException {
        return groupAssocReader.getAssociatedGroupCampaigns(uniqueId);
    }
    
    @Override
    public IterableResponse<Campaign> getAssociatedGroupCampaigns(String uniqueId, String ownerName) throws IOException, FailedResponseException {
        return groupAssocReader.getAssociatedGroupCampaigns(uniqueId, ownerName);
    }
    
    @Override
    public Campaign getAssociatedGroupCampaign(String uniqueId, Long campaignId) throws IOException, FailedResponseException {
        return groupAssocReader.getAssociatedGroupCampaign(uniqueId, campaignId);
    }
    
    @Override
    public Campaign getAssociatedGroupCampaign(String uniqueId, Long campaignId, String ownerName) throws IOException, FailedResponseException {
        return groupAssocReader.getAssociatedGroupCampaign(uniqueId, campaignId, ownerName);
    }
    
    @Override
    public IterableResponse<Threat> getAssociatedGroupThreats(String uniqueId) throws IOException, FailedResponseException {
        return groupAssocReader.getAssociatedGroupThreats(uniqueId);
    }

    @Override
    public IterableResponse<Threat> getAssociatedGroupThreats(String uniqueId, String ownerName) throws IOException, FailedResponseException {
        return groupAssocReader.getAssociatedGroupThreats(uniqueId, ownerName);
    }

    @Override
    public Threat getAssociatedGroupThreat(String uniqueId, Long threatId) throws IOException, FailedResponseException {
        return groupAssocReader.getAssociatedGroupThreat(uniqueId, threatId);
    }

    @Override
    public Threat getAssociatedGroupThreat(String uniqueId, Long threatId, String ownerName) throws IOException, FailedResponseException {
        return groupAssocReader.getAssociatedGroupThreat(uniqueId, threatId, ownerName);
    }

    @Override
    public IterableResponse<Indicator> getAssociatedIndicators(String uniqueId) throws IOException, FailedResponseException {
        return indAssocReader.getAssociatedIndicators(uniqueId);
    }

    @Override
    public IterableResponse<Indicator> getAssociatedIndicators(String uniqueId, String ownerName) throws IOException, FailedResponseException {
        return indAssocReader.getAssociatedIndicators(uniqueId, ownerName);
    }

    @Override
    public IterableResponse<Address> getAssociatedIndicatorAddresses(String uniqueId) throws IOException, FailedResponseException {
        return indAssocReader.getAssociatedIndicatorAddresses(uniqueId);
    }

    @Override
    public IterableResponse<Address> getAssociatedIndicatorAddresses(String uniqueId, String ownerName) throws IOException, FailedResponseException {
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
    public IterableResponse<EmailAddress> getAssociatedIndicatorEmailAddresses(String uniqueId) throws IOException, FailedResponseException {
        return indAssocReader.getAssociatedIndicatorEmailAddresses(uniqueId);
    }

    @Override
    public IterableResponse<EmailAddress> getAssociatedIndicatorEmailAddresses(String uniqueId, String ownerName) throws IOException, FailedResponseException {
        return indAssocReader.getAssociatedIndicatorEmailAddresses(uniqueId, ownerName);
    }

    @Override
    public EmailAddress getAssociatedIndicatorEmailAddress(String uniqueId, String emailAddress) throws IOException, FailedResponseException {
        return indAssocReader.getAssociatedIndicatorEmailAddress(uniqueId, emailAddress);
    }

    @Override
    public EmailAddress getAssociatedIndicatorEmailAddress(String uniqueId, String emailAddress, String ownerName) throws IOException, FailedResponseException {
        return indAssocReader.getAssociatedIndicatorEmailAddress(uniqueId, emailAddress, ownerName);
    }

    @Override
    public IterableResponse<File> getAssociatedIndicatorFiles(String uniqueId) throws IOException, FailedResponseException {
        return indAssocReader.getAssociatedIndicatorFiles(uniqueId);
    }

    @Override
    public IterableResponse<File> getAssociatedIndicatorFiles(String uniqueId, String ownerName) throws IOException, FailedResponseException {
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
    public IterableResponse<Host> getAssociatedIndicatorHosts(String uniqueId) throws IOException, FailedResponseException {
        return indAssocReader.getAssociatedIndicatorHosts(uniqueId);
    }

    @Override
    public IterableResponse<Host> getAssociatedIndicatorHosts(String uniqueId, String ownerName) throws IOException, FailedResponseException {
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
    public IterableResponse<Url> getAssociatedIndicatorUrls(String uniqueId) throws IOException, FailedResponseException {
        return indAssocReader.getAssociatedIndicatorUrls(uniqueId);
    }

    @Override
    public IterableResponse<Url> getAssociatedIndicatorUrls(String uniqueId, String ownerName) throws IOException, FailedResponseException {
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
	@Override
	public IterableResponse<? extends Indicator> getAssociatedIndicatorsForCustomIndicators(String uniqueId,
			String associationType) throws IOException, FailedResponseException {
		return indAssocReader.getAssociatedIndicatorsForCustomIndicators(uniqueId, associationType);
	}

	@Override
	public IterableResponse<? extends Indicator> getAssociatedIndicatorsForCustomIndicators(String uniqueId,
			String associationType, String targetType) throws IOException, FailedResponseException {
		return indAssocReader.getAssociatedIndicatorsForCustomIndicators(uniqueId, associationType, targetType);
	}

	

}