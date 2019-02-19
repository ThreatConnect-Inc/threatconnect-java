package com.threatconnect.sdk.client.reader;

import com.threatconnect.sdk.client.UrlTypeable;
import com.threatconnect.sdk.client.reader.associate.AbstractAttributeAssociateReaderAdapter;
import com.threatconnect.sdk.client.reader.associate.AbstractGroupAssociateReaderAdapter;
import com.threatconnect.sdk.client.reader.associate.AbstractIndicatorAssociateReaderAdapter;
import com.threatconnect.sdk.client.reader.associate.AbstractSecurityLabelAssociateReaderAdapter;
import com.threatconnect.sdk.client.reader.associate.AbstractTagAssociateReaderAdapter;
import com.threatconnect.sdk.client.reader.associate.AbstractVictimAssetAssociateReaderAdapter;
import com.threatconnect.sdk.client.reader.associate.AttributeAssociateReadable;
import com.threatconnect.sdk.client.reader.associate.GroupAssociateReadable;
import com.threatconnect.sdk.client.reader.associate.IndicatorAssociateReadable;
import com.threatconnect.sdk.client.reader.associate.SecurityLabelAssociateReadable;
import com.threatconnect.sdk.client.reader.associate.TagAssociateReadable;
import com.threatconnect.sdk.client.reader.associate.VictimAssetAssociateReadable;
import com.threatconnect.sdk.client.response.IterableResponse;
import com.threatconnect.sdk.conn.Connection;
import com.threatconnect.sdk.exception.FailedResponseException;
import com.threatconnect.sdk.server.entity.Address;
import com.threatconnect.sdk.server.entity.Adversary;
import com.threatconnect.sdk.server.entity.Attribute;
import com.threatconnect.sdk.server.entity.Campaign;
import com.threatconnect.sdk.server.entity.Document;
import com.threatconnect.sdk.server.entity.Email;
import com.threatconnect.sdk.server.entity.EmailAddress;
import com.threatconnect.sdk.server.entity.File;
import com.threatconnect.sdk.server.entity.Group;
import com.threatconnect.sdk.server.entity.Host;
import com.threatconnect.sdk.server.entity.Incident;
import com.threatconnect.sdk.server.entity.Indicator;
import com.threatconnect.sdk.server.entity.SecurityLabel;
import com.threatconnect.sdk.server.entity.Signature;
import com.threatconnect.sdk.server.entity.Tag;
import com.threatconnect.sdk.server.entity.Threat;
import com.threatconnect.sdk.server.entity.Url;
import com.threatconnect.sdk.server.entity.VictimAsset;
import com.threatconnect.sdk.server.entity.VictimEmailAddress;
import com.threatconnect.sdk.server.entity.VictimNetworkAccount;
import com.threatconnect.sdk.server.entity.VictimPhone;
import com.threatconnect.sdk.server.entity.VictimSocialNetwork;
import com.threatconnect.sdk.server.entity.VictimWebSite;
import com.threatconnect.sdk.server.response.entity.ApiEntityListResponse;
import com.threatconnect.sdk.server.response.entity.ApiEntitySingleResponse;
import com.threatconnect.sdk.server.response.entity.GroupListResponse;
import org.apache.http.entity.ContentType;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * EmailReaderAdapter is the primary client adapter for all Email group level objects.
 * It uses the {@link Connection} object to execute requests against the {@link com.threatconnect.sdk.conn.AbstractRequestExecutor} object.
 * The responsibility of this class is to encapsulate all the low level ThreatConnect API calls
 * specifically targeted at data under the Email group type.
 * 
 *
 * @author dtineo
 * @param <T> Parameter
 */
public abstract class AbstractGroupReaderAdapter<T extends Group>
    extends AbstractBaseReaderAdapter<T,Long>
    implements UrlTypeable, GroupAssociateReadable<Long>, IndicatorAssociateReadable<Long>
             , AttributeAssociateReadable<Long>, VictimAssetAssociateReadable<Long>, TagAssociateReadable<Long>
             , SecurityLabelAssociateReadable<Long>  {

    // composite pattern
    private AbstractAttributeAssociateReaderAdapter<Long> attribReader;
    private AbstractGroupAssociateReaderAdapter<Long> groupAssocReader;
    private AbstractIndicatorAssociateReaderAdapter<Long> indAssocReader;
    private AbstractSecurityLabelAssociateReaderAdapter<Long> secLabelAssocReader;
    private AbstractTagAssociateReaderAdapter<Long> tagAssocReader;
    private AbstractVictimAssetAssociateReaderAdapter<Long> victimAssetAssocReader;

    /**
     * Package level constructor. Use the {@link ReaderAdapterFactory} to access this object.
     * @param conn      Primary connection object to the ThreatConnect API
     * @param singleType Class of single type
     * @param listType Class of list type
     * 
     * @see ReaderAdapterFactory
     */
    protected AbstractGroupReaderAdapter(Connection conn
                , Class<? extends ApiEntitySingleResponse> singleType, Class<T> singleItemType,
                                         Class<? extends ApiEntityListResponse> listType) {
        super(conn, singleType, singleItemType, listType);

        initComposite();
    }

    private void initComposite() {
        attribReader = new AbstractAttributeAssociateReaderAdapter<Long>(
                            AbstractGroupReaderAdapter.this.getConn()
                          , AbstractGroupReaderAdapter.this.singleType
                            , AbstractGroupReaderAdapter.this.singleItemType
                          , AbstractGroupReaderAdapter.this.listType
            ) {
            @Override
            protected String getUrlBasePrefix() {
                return AbstractGroupReaderAdapter.this.getUrlBasePrefix();
            }

            @Override
            public String getUrlType() {
                return AbstractGroupReaderAdapter.this.getUrlType();
            }
        };

        groupAssocReader = new AbstractGroupAssociateReaderAdapter<Long>(
                            AbstractGroupReaderAdapter.this.getConn()
                          , AbstractGroupReaderAdapter.this.singleType
                          , AbstractGroupReaderAdapter.this.singleItemType
                          , AbstractGroupReaderAdapter.this.listType) {
            @Override
            protected String getUrlBasePrefix() {
                return AbstractGroupReaderAdapter.this.getUrlBasePrefix();
            }
            @Override
            public String getUrlType() {
                return AbstractGroupReaderAdapter.this.getUrlType();
            }
        };

        indAssocReader = new AbstractIndicatorAssociateReaderAdapter<Long>(
                            AbstractGroupReaderAdapter.this.getConn()
                          , AbstractGroupReaderAdapter.this.singleType
                          , AbstractGroupReaderAdapter.this.singleItemType
                          , AbstractGroupReaderAdapter.this.listType
            ) {
            @Override
            protected String getUrlBasePrefix() {
                return AbstractGroupReaderAdapter.this.getUrlBasePrefix();
            }

            @Override
            public String getUrlType() {
                return AbstractGroupReaderAdapter.this.getUrlType();
            }
        };

        secLabelAssocReader = new AbstractSecurityLabelAssociateReaderAdapter<Long>(
                            AbstractGroupReaderAdapter.this.getConn()
                          , AbstractGroupReaderAdapter.this.singleType
                          , AbstractGroupReaderAdapter.this.singleItemType
                          , AbstractGroupReaderAdapter.this.listType) {
            @Override
            protected String getUrlBasePrefix() {
                return AbstractGroupReaderAdapter.this.getUrlBasePrefix();
            }
        };

        tagAssocReader = new AbstractTagAssociateReaderAdapter<Long>(
                            AbstractGroupReaderAdapter.this.getConn()
                          , AbstractGroupReaderAdapter.this.singleType
                          , AbstractGroupReaderAdapter.this.singleItemType
                          , AbstractGroupReaderAdapter.this.listType) {
            @Override
            protected String getUrlBasePrefix() {
                return AbstractGroupReaderAdapter.this.getUrlBasePrefix();
            }
            @Override
            public String getUrlType() {
                return AbstractGroupReaderAdapter.this.getUrlType();
            }
        };

        victimAssetAssocReader = new AbstractVictimAssetAssociateReaderAdapter<Long>(
                            AbstractGroupReaderAdapter.this.getConn()
                          , AbstractGroupReaderAdapter.this.singleType
                          , AbstractGroupReaderAdapter.this.singleItemType
                          , AbstractGroupReaderAdapter.this.listType) {
            @Override
            protected String getUrlBasePrefix() {
                return AbstractGroupReaderAdapter.this.getUrlBasePrefix();
            }
            @Override
            public String getUrlType() {
                return AbstractGroupReaderAdapter.this.getUrlType();
            }
        };
        
    }

    @Override
    protected String getUrlBasePrefix() {
        return "v2.groups.type";
    }

     public String getGroupsAsText() throws IOException {
        return getAsText("v2.groups.list");
    }

    public IterableResponse<Group> getGroups() throws IOException, FailedResponseException {
        return getGroups(null);
    }

    public IterableResponse<Group> getGroups(String ownerName) throws IOException, FailedResponseException {
	    return getItems("v2.groups.list", GroupListResponse.class, Group.class, ownerName, null);
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
    public IterableResponse<Document> getAssociatedGroupDocuments(Long uniqueId) throws IOException, FailedResponseException {
        return groupAssocReader.getAssociatedGroupDocuments(uniqueId);
    }

    @Override
    public IterableResponse<Document> getAssociatedGroupDocuments(Long uniqueId, String ownerName) throws IOException, FailedResponseException {
        return groupAssocReader.getAssociatedGroupDocuments(uniqueId, ownerName);
    }

    @Override
    public Document getAssociatedGroupDocument(Long uniqueId, Long incidentId) throws IOException, FailedResponseException {
        return groupAssocReader.getAssociatedGroupDocument(uniqueId, incidentId);
    }

    @Override
    public Document getAssociatedGroupDocument(Long uniqueId, Long incidentId, String ownerName) throws IOException, FailedResponseException {
        return groupAssocReader.getAssociatedGroupDocument(uniqueId, incidentId, ownerName);
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
    public IterableResponse<Attribute> getAttributes(Long uniqueId) throws IOException, FailedResponseException {
        return attribReader.getAttributes(uniqueId);
    }

    @Override
    public IterableResponse<Attribute> getAttributes(Long uniqueId, String ownerName) throws IOException, FailedResponseException {
        return attribReader.getAttributes(uniqueId, ownerName);
    }

    @Override
    public Attribute getAttribute(Long uniqueId, Long attributeId) throws IOException, FailedResponseException {
        return attribReader.getAttribute(uniqueId, attributeId);
    }

    @Override
    public Attribute getAttribute(Long uniqueId, Long attributeId, String ownerName) throws IOException, FailedResponseException {
        return attribReader.getAttribute(uniqueId, attributeId, ownerName);
    }

    @Override
    public IterableResponse<SecurityLabel> getAttributeSecurityLabels(Long uniqueId, Long attributeId) throws IOException, FailedResponseException {
        return attribReader.getAttributeSecurityLabels(uniqueId, attributeId);
    }

    @Override
    public IterableResponse<SecurityLabel> getAttributeSecurityLabels(Long uniqueId, Long attributeId, String ownerName) throws IOException, FailedResponseException {
        return attribReader.getAttributeSecurityLabels(uniqueId, attributeId, ownerName);
    }

    @Override
    public SecurityLabel getAttributeSecurityLabel(Long uniqueId, Long attributeId, String securityLabel) throws IOException, FailedResponseException {
        return attribReader.getAttributeSecurityLabel(uniqueId, attributeId, securityLabel);
    }

    @Override
    public SecurityLabel getAttributeSecurityLabel(Long uniqueId, Long attributeId, String securityLabel, String ownerName) throws IOException, FailedResponseException {
        return attribReader.getAttributeSecurityLabel(uniqueId, attributeId, securityLabel, ownerName);
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
    public IterableResponse<Tag> getAssociatedTags(Long uniqueId) throws IOException, FailedResponseException {
        return tagAssocReader.getAssociatedTags(uniqueId);
    }

    @Override
    public IterableResponse<Tag> getAssociatedTags(Long uniqueId, String ownerName) throws IOException, FailedResponseException {
        return tagAssocReader.getAssociatedTags(uniqueId, ownerName);
    }

    @Override
    public Tag getAssociatedTag(Long uniqueId, String tagName) throws IOException, FailedResponseException {
        return tagAssocReader.getAssociatedTag(uniqueId, tagName);
    }

    @Override
    public Tag getAssociatedTag(Long uniqueId, String tagName, String ownerName) throws IOException, FailedResponseException {
        return tagAssocReader.getAssociatedTag(uniqueId, tagName, ownerName);
    }

    @Override
    public IterableResponse<SecurityLabel> getAssociatedSecurityLabels(Long uniqueId) throws IOException, FailedResponseException {
        return secLabelAssocReader.getAssociatedSecurityLabels(uniqueId);
    }

    @Override
    public IterableResponse<SecurityLabel> getAssociatedSecurityLabels(Long uniqueId, String ownerName) throws IOException, FailedResponseException {
        return secLabelAssocReader.getAssociatedSecurityLabels(uniqueId, ownerName);
    }

    @Override
    public SecurityLabel getAssociatedSecurityLabel(Long uniqueId, String securityLabel) throws IOException, FailedResponseException {
        return secLabelAssocReader.getAssociatedSecurityLabel(uniqueId, securityLabel);
    }

    @Override
    public SecurityLabel getAssociatedSecurityLabel(Long uniqueId, String securityLabel, String ownerName) throws IOException, FailedResponseException {
        return secLabelAssocReader.getAssociatedSecurityLabel(uniqueId, securityLabel, ownerName);
    }
	@Override
	public IterableResponse<? extends Indicator> getAssociatedIndicatorsForCustomIndicators(Long uniqueId,
			String associationType) throws IOException, FailedResponseException {
		throw new RuntimeException("not implemented yet");
	}

	@Override
	public IterableResponse<? extends Indicator> getAssociatedIndicatorsForCustomIndicators(Long uniqueId,
			String associationType, String targetType) throws IOException, FailedResponseException {
		throw new RuntimeException("not implemented yet");
	}

	public InputStream downloadSignatureFile(Long uniqueId, String ownerName) throws IOException {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", uniqueId);
        return getFile("v2.signatures.download", ownerName, paramMap, ContentType.TEXT_PLAIN);
    }

}
