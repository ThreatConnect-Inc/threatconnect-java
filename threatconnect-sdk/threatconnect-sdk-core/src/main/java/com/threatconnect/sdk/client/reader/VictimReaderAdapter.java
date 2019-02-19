/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.client.reader;

import com.threatconnect.sdk.client.reader.associate.VictimAssetAssociateReadable;
import com.threatconnect.sdk.client.reader.associate.AbstractVictimAssetAssociateReaderAdapter;
import com.threatconnect.sdk.client.reader.associate.IndicatorAssociateReadable;
import com.threatconnect.sdk.client.reader.associate.GroupAssociateReadable;
import com.threatconnect.sdk.client.reader.associate.AbstractIndicatorAssociateReaderAdapter;
import com.threatconnect.sdk.client.reader.associate.AbstractGroupAssociateReaderAdapter;
import com.threatconnect.sdk.client.UrlTypeable;
import com.threatconnect.sdk.client.response.IterableResponse;
import com.threatconnect.sdk.conn.Connection;
import com.threatconnect.sdk.exception.FailedResponseException;
import com.threatconnect.sdk.server.entity.*;
import com.threatconnect.sdk.server.response.entity.VictimResponse;
import com.threatconnect.sdk.server.response.entity.VictimListResponse;

import java.io.IOException;

/**
 *
 * @author dtineo
 */
public class VictimReaderAdapter
    extends AbstractBaseReaderAdapter
    implements UrlTypeable, GroupAssociateReadable<Long>, IndicatorAssociateReadable<Long>
             , VictimAssetAssociateReadable<Long>
{

    // composite pattern
    private AbstractGroupAssociateReaderAdapter<Long> groupAssocReader;
    private AbstractIndicatorAssociateReaderAdapter<Long> indAssocReader;
    private AbstractVictimAssetAssociateReaderAdapter<Long> victimAssetAssocReader;


    protected VictimReaderAdapter(Connection conn) {
        super(conn, VictimResponse.class, Victim.class, VictimListResponse.class);

        initComposite();
    }

    private void initComposite() {

        groupAssocReader = new AbstractGroupAssociateReaderAdapter<Long>(
                            VictimReaderAdapter.this.getConn()
                          , VictimReaderAdapter.this.singleType
                          , VictimReaderAdapter.this.singleItemType
                          , VictimReaderAdapter.this.listType) {
            @Override
            protected String getUrlBasePrefix() {
                return VictimReaderAdapter.this.getUrlBasePrefix();
            }

            @Override
            public String getUrlType() {
                return VictimReaderAdapter.this.getUrlType();
            }
        };

        indAssocReader = new AbstractIndicatorAssociateReaderAdapter<Long>(
                            VictimReaderAdapter.this.getConn()
                          , VictimReaderAdapter.this.singleType
                          , VictimReaderAdapter.this.singleItemType
                          , VictimReaderAdapter.this.listType
            ) {
            @Override
            protected String getUrlBasePrefix() {
                return VictimReaderAdapter.this.getUrlBasePrefix();
            }
            @Override
            public String getUrlType() {
                return VictimReaderAdapter.this.getUrlType();
            }
        };

        victimAssetAssocReader = new AbstractVictimAssetAssociateReaderAdapter<Long>(
                            VictimReaderAdapter.this.getConn()
                          , VictimReaderAdapter.this.singleType
                          , VictimReaderAdapter.this.singleItemType
                          , VictimReaderAdapter.this.listType) {
            @Override
            protected String getUrlBasePrefix() {
                return VictimReaderAdapter.this.getUrlBasePrefix();
            }
            @Override
            public String getUrlType() {
                return VictimReaderAdapter.this.getUrlType();
            }
        };

    }

    @Override
    public String getUrlType() {
        return "victims";
    }

    @Override
    protected String getUrlBasePrefix() {
        return "v2.victims";
    }

    @Override
    public IterableResponse<Group> getAssociatedGroups(Long uniqueId) throws IOException, FailedResponseException {
        return groupAssocReader.getAssociatedGroups(uniqueId);
    }

    @Override
    public IterableResponse<Group> getAssociatedGroups(Long uniqueId, String ownerName) throws IOException, FailedResponseException {
        return groupAssocReader.getAssociatedGroups(uniqueId,ownerName);
    }

    @Override
    public IterableResponse<Adversary> getAssociatedGroupAdversaries(Long uniqueId) throws IOException, FailedResponseException {
        return groupAssocReader.getAssociatedGroupAdversaries(uniqueId);
    }

    @Override
    public IterableResponse<Adversary> getAssociatedGroupAdversaries(Long uniqueId, String ownerName) throws IOException, FailedResponseException {
        return groupAssocReader.getAssociatedGroupAdversaries(uniqueId,ownerName);
    }

    @Override
    public Adversary getAssociatedGroupAdversary(Long uniqueId, Long adversaryId) throws IOException, FailedResponseException {
        return groupAssocReader.getAssociatedGroupAdversary(uniqueId,adversaryId);
    }

    @Override
    public Adversary getAssociatedGroupAdversary(Long uniqueId, Long adversaryId, String ownerName) throws IOException, FailedResponseException {
        return groupAssocReader.getAssociatedGroupAdversary(uniqueId,adversaryId,ownerName);
    }

    @Override
    public IterableResponse<Email> getAssociatedGroupEmails(Long uniqueId) throws IOException, FailedResponseException {
        return groupAssocReader.getAssociatedGroupEmails(uniqueId);
    }

    @Override
    public IterableResponse<Email> getAssociatedGroupEmails(Long uniqueId, String ownerName) throws IOException, FailedResponseException {
        return groupAssocReader.getAssociatedGroupEmails(uniqueId,ownerName);
    }

    @Override
    public Email getAssociatedGroupEmail(Long uniqueId, Long emailId) throws IOException, FailedResponseException {
        return groupAssocReader.getAssociatedGroupEmail(uniqueId,emailId);
    }

    @Override
    public Email getAssociatedGroupEmail(Long uniqueId, Long emailId, String ownerName) throws IOException, FailedResponseException {
        return groupAssocReader.getAssociatedGroupEmail(uniqueId, emailId, ownerName);
    }

    @Override
    public IterableResponse<Incident> getAssociatedGroupIncidents(Long uniqueId) throws IOException, FailedResponseException {
        return groupAssocReader.getAssociatedGroupIncidents(uniqueId);
    }

    @Override
    public IterableResponse<Incident> getAssociatedGroupIncidents(Long uniqueId, String ownerName) throws IOException, FailedResponseException {
        return groupAssocReader.getAssociatedGroupIncidents(uniqueId, ownerName);
    }

    @Override
    public Incident getAssociatedGroupIncident(Long uniqueId, Long incidentId) throws IOException, FailedResponseException {
        return groupAssocReader.getAssociatedGroupIncident(uniqueId, incidentId);
    }

    @Override
    public Incident getAssociatedGroupIncident(Long uniqueId, Long incidentId, String ownerName) throws IOException, FailedResponseException {
        return groupAssocReader.getAssociatedGroupIncident(uniqueId, incidentId, ownerName);
    }

    @Override
    public IterableResponse<Document> getAssociatedGroupDocuments(Long uniqueId) throws IOException, FailedResponseException
    {
        return groupAssocReader.getAssociatedGroupDocuments(uniqueId);
    }

    @Override
    public IterableResponse<Document> getAssociatedGroupDocuments(Long uniqueId, String ownerName) throws IOException, FailedResponseException
    {
        return groupAssocReader.getAssociatedGroupDocuments(uniqueId, ownerName);
    }

    @Override
    public Document getAssociatedGroupDocument(Long uniqueId, Long incidentId) throws IOException, FailedResponseException
    {
        return groupAssocReader.getAssociatedGroupDocument(uniqueId, incidentId);
    }

    @Override
    public Document getAssociatedGroupDocument(Long uniqueId, Long incidentId, String ownerName) throws IOException, FailedResponseException
    {
        return groupAssocReader.getAssociatedGroupDocument(uniqueId, incidentId, ownerName);
    }

    @Override
    public IterableResponse<Signature> getAssociatedGroupSignatures(Long uniqueId) throws IOException, FailedResponseException {
        return groupAssocReader.getAssociatedGroupSignatures(uniqueId);
    }

    @Override
    public IterableResponse<Signature> getAssociatedGroupSignatures(Long uniqueId, String ownerName) throws IOException, FailedResponseException {
        return groupAssocReader.getAssociatedGroupSignatures(uniqueId, ownerName);
    }

    @Override
    public Signature getAssociatedGroupSignature(Long uniqueId, Long signatureId) throws IOException, FailedResponseException {
        return groupAssocReader.getAssociatedGroupSignature(uniqueId, signatureId);
    }

    @Override
    public Signature getAssociatedGroupSignature(Long uniqueId, Long signatureId, String ownerName) throws IOException, FailedResponseException {
        return groupAssocReader.getAssociatedGroupSignature(uniqueId, signatureId, ownerName);
    }
    
    @Override
    public IterableResponse<Campaign> getAssociatedGroupCampaigns(Long uniqueId) throws IOException, FailedResponseException {
        return groupAssocReader.getAssociatedGroupCampaigns(uniqueId);
    }
    
    @Override
    public IterableResponse<Campaign> getAssociatedGroupCampaigns(Long uniqueId, String ownerName) throws IOException, FailedResponseException {
        return groupAssocReader.getAssociatedGroupCampaigns(uniqueId, ownerName);
    }
    
    @Override
    public Campaign getAssociatedGroupCampaign(Long uniqueId, Long campaignId) throws IOException, FailedResponseException {
        return groupAssocReader.getAssociatedGroupCampaign(uniqueId, campaignId);
    }
    
    @Override
    public Campaign getAssociatedGroupCampaign(Long uniqueId, Long campaignId, String ownerName) throws IOException, FailedResponseException {
        return groupAssocReader.getAssociatedGroupCampaign(uniqueId, campaignId, ownerName);
    }
    
    @Override
    public IterableResponse<Threat> getAssociatedGroupThreats(Long uniqueId) throws IOException, FailedResponseException {
        return groupAssocReader.getAssociatedGroupThreats(uniqueId);
    }

    @Override
    public IterableResponse<Threat> getAssociatedGroupThreats(Long uniqueId, String ownerName) throws IOException, FailedResponseException {
        return groupAssocReader.getAssociatedGroupThreats(uniqueId, ownerName);
    }

    @Override
    public Threat getAssociatedGroupThreat(Long uniqueId, Long threatId) throws IOException, FailedResponseException {
        return groupAssocReader.getAssociatedGroupThreat(uniqueId, threatId);
    }

    @Override
    public Threat getAssociatedGroupThreat(Long uniqueId, Long threatId, String ownerName) throws IOException, FailedResponseException {
        return groupAssocReader.getAssociatedGroupThreat(uniqueId, threatId, ownerName);
    }

    @Override
    public IterableResponse<Indicator> getAssociatedIndicators(Long uniqueId) throws IOException, FailedResponseException {
        return indAssocReader.getAssociatedIndicators(uniqueId);
    }

    @Override
    public IterableResponse<Indicator> getAssociatedIndicators(Long uniqueId, String ownerName) throws IOException, FailedResponseException {
        return indAssocReader.getAssociatedIndicators(uniqueId, ownerName);
    }

    @Override
    public IterableResponse<Address> getAssociatedIndicatorAddresses(Long uniqueId) throws IOException, FailedResponseException {
        return indAssocReader.getAssociatedIndicatorAddresses(uniqueId);
    }

    @Override
    public IterableResponse<Address> getAssociatedIndicatorAddresses(Long uniqueId, String ownerName) throws IOException, FailedResponseException {
        return indAssocReader.getAssociatedIndicatorAddresses(uniqueId, ownerName);
    }

    @Override
    public Address getAssociatedIndicatorAddress(Long uniqueId, String ipAddress) throws IOException, FailedResponseException {
        return indAssocReader.getAssociatedIndicatorAddress(uniqueId, ipAddress);
    }

    @Override
    public Address getAssociatedIndicatorAddress(Long uniqueId, String ipAddress, String ownerName) throws IOException, FailedResponseException {
        return indAssocReader.getAssociatedIndicatorAddress(uniqueId, ipAddress, ownerName);
    }

    @Override
    public IterableResponse<EmailAddress> getAssociatedIndicatorEmailAddresses(Long uniqueId) throws IOException, FailedResponseException {
        return indAssocReader.getAssociatedIndicatorEmailAddresses(uniqueId);
    }

    @Override
    public IterableResponse<EmailAddress> getAssociatedIndicatorEmailAddresses(Long uniqueId, String ownerName) throws IOException, FailedResponseException {
        return indAssocReader.getAssociatedIndicatorEmailAddresses(uniqueId, ownerName);
    }

    @Override
    public EmailAddress getAssociatedIndicatorEmailAddress(Long uniqueId, String emailAddress) throws IOException, FailedResponseException {
        return indAssocReader.getAssociatedIndicatorEmailAddress(uniqueId, emailAddress);
    }

    @Override
    public EmailAddress getAssociatedIndicatorEmailAddress(Long uniqueId, String emailAddress, String ownerName) throws IOException, FailedResponseException {
        return indAssocReader.getAssociatedIndicatorEmailAddress(uniqueId, emailAddress, ownerName);
    }

    @Override
    public IterableResponse<File> getAssociatedIndicatorFiles(Long uniqueId) throws IOException, FailedResponseException {
        return indAssocReader.getAssociatedIndicatorFiles(uniqueId);
    }

    @Override
    public IterableResponse<File> getAssociatedIndicatorFiles(Long uniqueId, String ownerName) throws IOException, FailedResponseException {
        return indAssocReader.getAssociatedIndicatorFiles(uniqueId, ownerName);
    }

    @Override
    public File getAssociatedIndicatorFile(Long uniqueId, String fileHash) throws IOException, FailedResponseException {
        return indAssocReader.getAssociatedIndicatorFile(uniqueId, fileHash);
    }

    @Override
    public File getAssociatedIndicatorFile(Long uniqueId, String fileHash, String ownerName) throws IOException, FailedResponseException {
        return indAssocReader.getAssociatedIndicatorFile(uniqueId, fileHash, ownerName);
    }

    @Override
    public IterableResponse<Host> getAssociatedIndicatorHosts(Long uniqueId) throws IOException, FailedResponseException {
        return indAssocReader.getAssociatedIndicatorHosts(uniqueId);
    }

    @Override
    public IterableResponse<Host> getAssociatedIndicatorHosts(Long uniqueId, String ownerName) throws IOException, FailedResponseException {
        return indAssocReader.getAssociatedIndicatorHosts(uniqueId, ownerName);
    }

    @Override
    public Host getAssociatedIndicatorHost(Long uniqueId, String hostName) throws IOException, FailedResponseException {
        return indAssocReader.getAssociatedIndicatorHost(uniqueId, hostName);
    }

    @Override
    public Host getAssociatedIndicatorHost(Long uniqueId, String hostName, String ownerName) throws IOException, FailedResponseException {
        return indAssocReader.getAssociatedIndicatorHost(uniqueId, hostName, ownerName);
    }

    @Override
    public IterableResponse<Url> getAssociatedIndicatorUrls(Long uniqueId) throws IOException, FailedResponseException {
        return indAssocReader.getAssociatedIndicatorUrls(uniqueId);
    }

    @Override
    public IterableResponse<Url> getAssociatedIndicatorUrls(Long uniqueId, String ownerName) throws IOException, FailedResponseException {
        return indAssocReader.getAssociatedIndicatorUrls(uniqueId, ownerName);
    }

    @Override
    public Url getAssociatedIndicatorUrl(Long uniqueId, String urlText) throws IOException, FailedResponseException {
        return indAssocReader.getAssociatedIndicatorUrl(uniqueId, urlText);
    }

    @Override
    public Url getAssociatedIndicatorUrl(Long uniqueId, String urlText, String ownerName) throws IOException, FailedResponseException {
        return indAssocReader.getAssociatedIndicatorUrl(uniqueId, urlText, ownerName);
    }

    @Override
    public IterableResponse<VictimAsset> getAssociatedVictimAssets(Long uniqueId, String ownerName) throws IOException, FailedResponseException {
        return victimAssetAssocReader.getAssociatedVictimAssets(uniqueId, ownerName);
    }

    @Override
    public IterableResponse<VictimEmailAddress> getAssociatedVictimAssetEmailAddresses(Long uniqueId) throws IOException, FailedResponseException {
        return victimAssetAssocReader.getAssociatedVictimAssetEmailAddresses(uniqueId);
    }

    @Override
    public IterableResponse<VictimEmailAddress> getAssociatedVictimAssetEmailAddresses(Long uniqueId, String ownerName) throws IOException, FailedResponseException {
        return victimAssetAssocReader.getAssociatedVictimAssetEmailAddresses(uniqueId, ownerName);
    }

    @Override
    public VictimEmailAddress getAssociatedVictimAssetEmailAddress(Long uniqueId, Long assetId) throws IOException, FailedResponseException {
        return victimAssetAssocReader.getAssociatedVictimAssetEmailAddress(uniqueId, assetId);
    }

    @Override
    public VictimEmailAddress getAssociatedVictimAssetEmailAddress(Long uniqueId, Long assetId, String ownerName) throws IOException, FailedResponseException {
        return victimAssetAssocReader.getAssociatedVictimAssetEmailAddress(uniqueId, assetId, ownerName);
    }

    @Override
    public IterableResponse<VictimNetworkAccount> getAssociatedVictimAssetNetworkAccounts(Long uniqueId) throws IOException, FailedResponseException {
        return victimAssetAssocReader.getAssociatedVictimAssetNetworkAccounts(uniqueId);
    }

    @Override
    public IterableResponse<VictimNetworkAccount> getAssociatedVictimAssetNetworkAccounts(Long uniqueId, String ownerName) throws IOException, FailedResponseException {
        return victimAssetAssocReader.getAssociatedVictimAssetNetworkAccounts(uniqueId, ownerName);
    }

    @Override
    public VictimNetworkAccount getAssociatedVictimAssetNetworkAccount(Long uniqueId, Long assetId) throws IOException, FailedResponseException {
        return victimAssetAssocReader.getAssociatedVictimAssetNetworkAccount(uniqueId, assetId);
    }

    @Override
    public VictimNetworkAccount getAssociatedVictimAssetNetworkAccount(Long uniqueId, Long assetId, String ownerName) throws IOException, FailedResponseException {
        return victimAssetAssocReader.getAssociatedVictimAssetNetworkAccount(uniqueId, assetId, ownerName);
    }

    @Override
    public IterableResponse<VictimPhone> getAssociatedVictimAssetPhoneNumbers(Long uniqueId) throws IOException, FailedResponseException {
        return victimAssetAssocReader.getAssociatedVictimAssetPhoneNumbers(uniqueId);
    }

    @Override
    public IterableResponse<VictimPhone> getAssociatedVictimAssetPhoneNumbers(Long uniqueId, String ownerName) throws IOException, FailedResponseException {
        return victimAssetAssocReader.getAssociatedVictimAssetPhoneNumbers(uniqueId, ownerName);
    }

    @Override
    public VictimPhone getAssociatedVictimAssetPhoneNumber(Long uniqueId, Long assetId) throws IOException, FailedResponseException {
        return victimAssetAssocReader.getAssociatedVictimAssetPhoneNumber(uniqueId, assetId);
    }

    @Override
    public VictimPhone getAssociatedVictimAssetPhoneNumber(Long uniqueId, Long assetId, String ownerName) throws IOException, FailedResponseException {
        return victimAssetAssocReader.getAssociatedVictimAssetPhoneNumber(uniqueId, assetId, ownerName);
    }

    @Override
    public IterableResponse<VictimSocialNetwork> getAssociatedVictimAssetSocialNetworks(Long uniqueId) throws IOException, FailedResponseException {
        return victimAssetAssocReader.getAssociatedVictimAssetSocialNetworks(uniqueId);
    }

    @Override
    public IterableResponse<VictimSocialNetwork> getAssociatedVictimAssetSocialNetworks(Long uniqueId, String ownerName) throws IOException, FailedResponseException {
        return victimAssetAssocReader.getAssociatedVictimAssetSocialNetworks(uniqueId, ownerName);
    }

    @Override
    public VictimSocialNetwork getAssociatedVictimAssetSocialNetwork(Long uniqueId, Long assetId) throws IOException, FailedResponseException {
        return victimAssetAssocReader.getAssociatedVictimAssetSocialNetwork(uniqueId, assetId);
    }

    @Override
    public VictimSocialNetwork getAssociatedVictimAssetSocialNetwork(Long uniqueId, Long assetId, String ownerName) throws IOException, FailedResponseException {
        return victimAssetAssocReader.getAssociatedVictimAssetSocialNetwork(uniqueId, assetId, ownerName);
    }

    @Override
    public IterableResponse<VictimWebSite> getAssociatedVictimAssetWebsites(Long uniqueId) throws IOException, FailedResponseException {
        return victimAssetAssocReader.getAssociatedVictimAssetWebsites(uniqueId);
    }

    @Override
    public IterableResponse<VictimWebSite> getAssociatedVictimAssetWebsites(Long uniqueId, String ownerName) throws IOException, FailedResponseException {
        return victimAssetAssocReader.getAssociatedVictimAssetWebsites(uniqueId, ownerName);
    }

    @Override
    public VictimWebSite getAssociatedVictimAssetWebsite(Long uniqueId, Long assetId) throws IOException, FailedResponseException {
        return victimAssetAssocReader.getAssociatedVictimAssetWebsite(uniqueId, assetId);
    }

    @Override
    public VictimWebSite getAssociatedVictimAssetWebsite(Long uniqueId, Long assetId, String ownerName) throws IOException, FailedResponseException {
        return victimAssetAssocReader.getAssociatedVictimAssetWebsite(uniqueId, assetId, ownerName);
    }

	@Override
	public IterableResponse<Indicator> getAssociatedIndicatorsForCustomIndicators(Long uniqueId,
			String associationType) throws IOException, FailedResponseException {
		throw new RuntimeException("not implemented yet");
	}

	@Override
	public IterableResponse<Indicator> getAssociatedIndicatorsForCustomIndicators(Long uniqueId,
			String associationType, String targetType) throws IOException, FailedResponseException {
		throw new RuntimeException("not implemented yet");
	}


	

}
