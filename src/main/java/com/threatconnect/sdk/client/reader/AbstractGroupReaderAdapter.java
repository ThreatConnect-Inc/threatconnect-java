package com.threatconnect.sdk.client.reader;

import com.threatconnect.sdk.client.UrlTypeable;
import com.threatconnect.sdk.client.reader.associate.*;
import com.threatconnect.sdk.client.response.IterableResponse;
import com.threatconnect.sdk.conn.Connection;
import com.threatconnect.sdk.exception.FailedResponseException;
import com.threatconnect.sdk.server.entity.*;
import com.threatconnect.sdk.server.response.entity.ApiEntityListResponse;
import com.threatconnect.sdk.server.response.entity.ApiEntitySingleResponse;
import com.threatconnect.sdk.server.response.entity.GroupListResponse;

import java.io.IOException;

/**
 * EmailReaderAdapter is the primary client adapter for all Email group level objects.
 * It uses the {@link Connection} object to execute requests against the {@link com.threatconnect.sdk.conn.AbstractRequestExecutor} object.
 * The responsibility of this class is to encapsulate all the low level ThreatConnect API calls
 * specifically targeted at data under the Email group type.
 * 
 *
 * @author dtineo
 * @param <T>
 */
public abstract class AbstractGroupReaderAdapter<T extends Group>
    extends AbstractBaseReaderAdapter<T,Integer>
    implements UrlTypeable, GroupAssociateReadable<Integer>, IndicatorAssociateReadable<Integer>
             , AttributeAssociateReadable<Integer>, VictimAssetAssociateReadable<Integer>, TagAssociateReadable<Integer>
             , SecurityLabelAssociateReadable<Integer>  {

    // composite pattern
    private AbstractAttributeAssociateReaderAdapter<Integer> attribReader;
    private AbstractGroupAssociateReaderAdapter<Integer> groupAssocReader;
    private AbstractIndicatorAssociateReaderAdapter<Integer> indAssocReader;
    private AbstractSecurityLabelAssociateReaderAdapter<Integer> secLabelAssocReader;
    private AbstractTagAssociateReaderAdapter<Integer> tagAssocReader;
    private AbstractVictimAssetAssociateReaderAdapter<Integer> victimAssetAssocReader;

    /**
     * Package level constructor. Use the {@link ReaderAdapterFactory} to access this object.
     * @param conn      Primary connection object to the ThreatConnect API
     * @param singleType
     * @param listType
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
        attribReader = new AbstractAttributeAssociateReaderAdapter<Integer>(
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

        groupAssocReader = new AbstractGroupAssociateReaderAdapter<Integer>(
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

        indAssocReader = new AbstractIndicatorAssociateReaderAdapter<Integer>(
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

        secLabelAssocReader = new AbstractSecurityLabelAssociateReaderAdapter<Integer>(
                            AbstractGroupReaderAdapter.this.getConn()
                          , AbstractGroupReaderAdapter.this.singleType
                          , AbstractGroupReaderAdapter.this.singleItemType
                          , AbstractGroupReaderAdapter.this.listType) {
            @Override
            protected String getUrlBasePrefix() {
                return AbstractGroupReaderAdapter.this.getUrlBasePrefix();
            }
        };

        tagAssocReader = new AbstractTagAssociateReaderAdapter<Integer>(
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

        victimAssetAssocReader = new AbstractVictimAssetAssociateReaderAdapter<Integer>(
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
    public IterableResponse<Group> getAssociatedGroups(Integer uniqueId) throws IOException, FailedResponseException {
        return groupAssocReader.getAssociatedGroups(uniqueId);
    }

    @Override
    public IterableResponse<Group> getAssociatedGroups(Integer uniqueId, String ownerName) throws IOException, FailedResponseException {
        return groupAssocReader.getAssociatedGroups(uniqueId,ownerName);
    }

    @Override
    public IterableResponse<Adversary> getAssociatedGroupAdversaries(Integer uniqueId) throws IOException, FailedResponseException {
        return groupAssocReader.getAssociatedGroupAdversaries(uniqueId);
    }

    @Override
    public IterableResponse<Adversary> getAssociatedGroupAdversaries(Integer uniqueId, String ownerName) throws IOException, FailedResponseException {
        return groupAssocReader.getAssociatedGroupAdversaries(uniqueId,ownerName);
    }

    @Override
    public Adversary getAssociatedGroupAdversary(Integer uniqueId, Integer adversaryId) throws IOException, FailedResponseException {
        return groupAssocReader.getAssociatedGroupAdversary(uniqueId,adversaryId);
    }

    @Override
    public Adversary getAssociatedGroupAdversary(Integer uniqueId, Integer adversaryId, String ownerName) throws IOException, FailedResponseException {
        return groupAssocReader.getAssociatedGroupAdversary(uniqueId,adversaryId,ownerName);
    }

    @Override
    public IterableResponse<Email> getAssociatedGroupEmails(Integer uniqueId) throws IOException, FailedResponseException {
        return groupAssocReader.getAssociatedGroupEmails(uniqueId);
    }

    @Override
    public IterableResponse<Email> getAssociatedGroupEmails(Integer uniqueId, String ownerName) throws IOException, FailedResponseException {
        return groupAssocReader.getAssociatedGroupEmails(uniqueId,ownerName);
    }

    @Override
    public Email getAssociatedGroupEmail(Integer uniqueId, Integer emailId) throws IOException, FailedResponseException {
        return groupAssocReader.getAssociatedGroupEmail(uniqueId,emailId);
    }

    @Override
    public Email getAssociatedGroupEmail(Integer uniqueId, Integer emailId, String ownerName) throws IOException, FailedResponseException {
        return groupAssocReader.getAssociatedGroupEmail(uniqueId, emailId, ownerName);
    }

    @Override
    public IterableResponse<Incident> getAssociatedGroupIncidents(Integer uniqueId) throws IOException, FailedResponseException {
        return groupAssocReader.getAssociatedGroupIncidents(uniqueId);
    }

    @Override
    public IterableResponse<Incident> getAssociatedGroupIncidents(Integer uniqueId, String ownerName) throws IOException, FailedResponseException {
        return groupAssocReader.getAssociatedGroupIncidents(uniqueId, ownerName);
    }

    @Override
    public Incident getAssociatedGroupIncident(Integer uniqueId, Integer incidentId) throws IOException, FailedResponseException {
        return groupAssocReader.getAssociatedGroupIncident(uniqueId, incidentId);
    }

    @Override
    public Incident getAssociatedGroupIncident(Integer uniqueId, Integer incidentId, String ownerName) throws IOException, FailedResponseException {
        return groupAssocReader.getAssociatedGroupIncident(uniqueId, incidentId, ownerName);
    }

    @Override
    public IterableResponse<Signature> getAssociatedGroupSignatures(Integer uniqueId) throws IOException, FailedResponseException {
        return groupAssocReader.getAssociatedGroupSignatures(uniqueId);
    }

    @Override
    public IterableResponse<Signature> getAssociatedGroupSignatures(Integer uniqueId, String ownerName) throws IOException, FailedResponseException {
        return groupAssocReader.getAssociatedGroupSignatures(uniqueId, ownerName);
    }

    @Override
    public Signature getAssociatedGroupSignature(Integer uniqueId, Integer signatureId) throws IOException, FailedResponseException {
        return groupAssocReader.getAssociatedGroupSignature(uniqueId, signatureId);
    }

    @Override
    public Signature getAssociatedGroupSignature(Integer uniqueId, Integer signatureId, String ownerName) throws IOException, FailedResponseException {
        return groupAssocReader.getAssociatedGroupSignature(uniqueId, signatureId, ownerName);
    }

    @Override
    public IterableResponse<Threat> getAssociatedGroupThreats(Integer uniqueId) throws IOException, FailedResponseException {
        return groupAssocReader.getAssociatedGroupThreats(uniqueId);
    }

    @Override
    public IterableResponse<Threat> getAssociatedGroupThreats(Integer uniqueId, String ownerName) throws IOException, FailedResponseException {
        return groupAssocReader.getAssociatedGroupThreats(uniqueId, ownerName);
    }

    @Override
    public Threat getAssociatedGroupThreat(Integer uniqueId, Integer threatId) throws IOException, FailedResponseException {
        return groupAssocReader.getAssociatedGroupThreat(uniqueId, threatId);
    }

    @Override
    public Threat getAssociatedGroupThreat(Integer uniqueId, Integer threatId, String ownerName) throws IOException, FailedResponseException {
        return groupAssocReader.getAssociatedGroupThreat(uniqueId, threatId, ownerName);
    }

    @Override
    public IterableResponse<Indicator> getAssociatedIndicators(Integer uniqueId) throws IOException, FailedResponseException {
        return indAssocReader.getAssociatedIndicators(uniqueId);
    }

    @Override
    public IterableResponse<Indicator> getAssociatedIndicators(Integer uniqueId, String ownerName) throws IOException, FailedResponseException {
        return indAssocReader.getAssociatedIndicators(uniqueId, ownerName);
    }

    @Override
    public IterableResponse<Address> getAssociatedIndicatorAddresses(Integer uniqueId) throws IOException, FailedResponseException {
        return indAssocReader.getAssociatedIndicatorAddresses(uniqueId);
    }

    @Override
    public IterableResponse<Address> getAssociatedIndicatorAddresses(Integer uniqueId, String ownerName) throws IOException, FailedResponseException {
        return indAssocReader.getAssociatedIndicatorAddresses(uniqueId, ownerName);
    }

    @Override
    public Address getAssociatedIndicatorAddress(Integer uniqueId, String ipAddress) throws IOException, FailedResponseException {
        return indAssocReader.getAssociatedIndicatorAddress(uniqueId, ipAddress);
    }

    @Override
    public Address getAssociatedIndicatorAddress(Integer uniqueId, String ipAddress, String ownerName) throws IOException, FailedResponseException {
        return indAssocReader.getAssociatedIndicatorAddress(uniqueId, ipAddress, ownerName);
    }

    @Override
    public IterableResponse<Email> getAssociatedIndicatorEmails(Integer uniqueId) throws IOException, FailedResponseException {
        return indAssocReader.getAssociatedIndicatorEmails(uniqueId);
    }

    @Override
    public IterableResponse<Email> getAssociatedIndicatorEmails(Integer uniqueId, String ownerName) throws IOException, FailedResponseException {
        return indAssocReader.getAssociatedIndicatorEmails(uniqueId, ownerName);
    }

    @Override
    public Email getAssociatedIndicatorEmail(Integer uniqueId, String emailAddress) throws IOException, FailedResponseException {
        return indAssocReader.getAssociatedIndicatorEmail(uniqueId, emailAddress);
    }

    @Override
    public Email getAssociatedIndicatorEmail(Integer uniqueId, String emailAddress, String ownerName) throws IOException, FailedResponseException {
        return indAssocReader.getAssociatedIndicatorEmail(uniqueId, emailAddress, ownerName);
    }

    @Override
    public IterableResponse<File> getAssociatedIndicatorFiles(Integer uniqueId) throws IOException, FailedResponseException {
        return indAssocReader.getAssociatedIndicatorFiles(uniqueId);
    }

    @Override
    public IterableResponse<File> getAssociatedIndicatorFiles(Integer uniqueId, String ownerName) throws IOException, FailedResponseException {
        return indAssocReader.getAssociatedIndicatorFiles(uniqueId, ownerName);
    }

    @Override
    public File getAssociatedIndicatorFile(Integer uniqueId, String fileHash) throws IOException, FailedResponseException {
        return indAssocReader.getAssociatedIndicatorFile(uniqueId, fileHash);
    }

    @Override
    public File getAssociatedIndicatorFile(Integer uniqueId, String fileHash, String ownerName) throws IOException, FailedResponseException {
        return indAssocReader.getAssociatedIndicatorFile(uniqueId, fileHash, ownerName);
    }

    @Override
    public IterableResponse<Host> getAssociatedIndicatorHosts(Integer uniqueId) throws IOException, FailedResponseException {
        return indAssocReader.getAssociatedIndicatorHosts(uniqueId);
    }

    @Override
    public IterableResponse<Host> getAssociatedIndicatorHosts(Integer uniqueId, String ownerName) throws IOException, FailedResponseException {
        return indAssocReader.getAssociatedIndicatorHosts(uniqueId, ownerName);
    }

    @Override
    public Host getAssociatedIndicatorHost(Integer uniqueId, String hostName) throws IOException, FailedResponseException {
        return indAssocReader.getAssociatedIndicatorHost(uniqueId, hostName);
    }

    @Override
    public Host getAssociatedIndicatorHost(Integer uniqueId, String hostName, String ownerName) throws IOException, FailedResponseException {
        return indAssocReader.getAssociatedIndicatorHost(uniqueId, hostName, ownerName);
    }

    @Override
    public IterableResponse<Url> getAssociatedIndicatorUrls(Integer uniqueId) throws IOException, FailedResponseException {
        return indAssocReader.getAssociatedIndicatorUrls(uniqueId);
    }

    @Override
    public IterableResponse<Url> getAssociatedIndicatorUrls(Integer uniqueId, String ownerName) throws IOException, FailedResponseException {
        return indAssocReader.getAssociatedIndicatorUrls(uniqueId, ownerName);
    }

    @Override
    public Url getAssociatedIndicatorUrl(Integer uniqueId, String urlText) throws IOException, FailedResponseException {
        return indAssocReader.getAssociatedIndicatorUrl(uniqueId, urlText);
    }

    @Override
    public Url getAssociatedIndicatorUrl(Integer uniqueId, String urlText, String ownerName) throws IOException, FailedResponseException {
        return indAssocReader.getAssociatedIndicatorUrl(uniqueId, urlText, ownerName);
    }

    @Override
    public IterableResponse<Attribute> getAttributes(Integer uniqueId) throws IOException, FailedResponseException {
        return attribReader.getAttributes(uniqueId);
    }

    @Override
    public IterableResponse<Attribute> getAttributes(Integer uniqueId, String ownerName) throws IOException, FailedResponseException {
        return attribReader.getAttributes(uniqueId, ownerName);
    }

    @Override
    public Attribute getAttribute(Integer uniqueId, Integer attributeId) throws IOException, FailedResponseException {
        return attribReader.getAttribute(uniqueId, attributeId);
    }

    @Override
    public Attribute getAttribute(Integer uniqueId, Integer attributeId, String ownerName) throws IOException, FailedResponseException {
        return attribReader.getAttribute(uniqueId, attributeId, ownerName);
    }

    @Override
    public IterableResponse<SecurityLabel> getAttributeSecurityLabels(Integer uniqueId, Integer attributeId) throws IOException, FailedResponseException {
        return attribReader.getAttributeSecurityLabels(uniqueId, attributeId);
    }

    @Override
    public IterableResponse<SecurityLabel> getAttributeSecurityLabels(Integer uniqueId, Integer attributeId, String ownerName) throws IOException, FailedResponseException {
        return attribReader.getAttributeSecurityLabels(uniqueId, attributeId, ownerName);
    }

    @Override
    public SecurityLabel getAttributeSecurityLabel(Integer uniqueId, Integer attributeId, String securityLabel) throws IOException, FailedResponseException {
        return attribReader.getAttributeSecurityLabel(uniqueId, attributeId, securityLabel);
    }

    @Override
    public SecurityLabel getAttributeSecurityLabel(Integer uniqueId, Integer attributeId, String securityLabel, String ownerName) throws IOException, FailedResponseException {
        return attribReader.getAttributeSecurityLabel(uniqueId, attributeId, securityLabel, ownerName);
    }

    @Override
    public IterableResponse<VictimAsset> getAssociatedVictimAssets(Integer uniqueId, String ownerName) throws IOException, FailedResponseException {
        return victimAssetAssocReader.getAssociatedVictimAssets(uniqueId, ownerName);
    }

    @Override
    public IterableResponse<VictimEmailAddress> getAssociatedVictimAssetEmailAddresses(Integer uniqueId) throws IOException, FailedResponseException {
        return victimAssetAssocReader.getAssociatedVictimAssetEmailAddresses(uniqueId);
    }

    @Override
    public IterableResponse<VictimEmailAddress> getAssociatedVictimAssetEmailAddresses(Integer uniqueId, String ownerName) throws IOException, FailedResponseException {
        return victimAssetAssocReader.getAssociatedVictimAssetEmailAddresses(uniqueId, ownerName);
    }

    @Override
    public VictimEmailAddress getAssociatedVictimAssetEmailAddress(Integer uniqueId, Integer assetId) throws IOException, FailedResponseException {
        return victimAssetAssocReader.getAssociatedVictimAssetEmailAddress(uniqueId, assetId);
    }

    @Override
    public VictimEmailAddress getAssociatedVictimAssetEmailAddress(Integer uniqueId, Integer assetId, String ownerName) throws IOException, FailedResponseException {
        return victimAssetAssocReader.getAssociatedVictimAssetEmailAddress(uniqueId, assetId, ownerName);
    }

    @Override
    public IterableResponse<VictimNetworkAccount> getAssociatedVictimAssetNetworkAccounts(Integer uniqueId) throws IOException, FailedResponseException {
        return victimAssetAssocReader.getAssociatedVictimAssetNetworkAccounts(uniqueId);
    }

    @Override
    public IterableResponse<VictimNetworkAccount> getAssociatedVictimAssetNetworkAccounts(Integer uniqueId, String ownerName) throws IOException, FailedResponseException {
        return victimAssetAssocReader.getAssociatedVictimAssetNetworkAccounts(uniqueId, ownerName);
    }

    @Override
    public VictimNetworkAccount getAssociatedVictimAssetNetworkAccount(Integer uniqueId, Integer assetId) throws IOException, FailedResponseException {
        return victimAssetAssocReader.getAssociatedVictimAssetNetworkAccount(uniqueId, assetId);
    }

    @Override
    public VictimNetworkAccount getAssociatedVictimAssetNetworkAccount(Integer uniqueId, Integer assetId, String ownerName) throws IOException, FailedResponseException {
        return victimAssetAssocReader.getAssociatedVictimAssetNetworkAccount(uniqueId, assetId, ownerName);
    }

    @Override
    public IterableResponse<VictimPhone> getAssociatedVictimAssetPhoneNumbers(Integer uniqueId) throws IOException, FailedResponseException {
        return victimAssetAssocReader.getAssociatedVictimAssetPhoneNumbers(uniqueId);
    }

    @Override
    public IterableResponse<VictimPhone> getAssociatedVictimAssetPhoneNumbers(Integer uniqueId, String ownerName) throws IOException, FailedResponseException {
        return victimAssetAssocReader.getAssociatedVictimAssetPhoneNumbers(uniqueId, ownerName);
    }

    @Override
    public VictimPhone getAssociatedVictimAssetPhoneNumber(Integer uniqueId, Integer assetId) throws IOException, FailedResponseException {
        return victimAssetAssocReader.getAssociatedVictimAssetPhoneNumber(uniqueId, assetId);
    }

    @Override
    public VictimPhone getAssociatedVictimAssetPhoneNumber(Integer uniqueId, Integer assetId, String ownerName) throws IOException, FailedResponseException {
        return victimAssetAssocReader.getAssociatedVictimAssetPhoneNumber(uniqueId, assetId, ownerName);
    }

    @Override
    public IterableResponse<VictimSocialNetwork> getAssociatedVictimAssetSocialNetworks(Integer uniqueId) throws IOException, FailedResponseException {
        return victimAssetAssocReader.getAssociatedVictimAssetSocialNetworks(uniqueId);
    }

    @Override
    public IterableResponse<VictimSocialNetwork> getAssociatedVictimAssetSocialNetworks(Integer uniqueId, String ownerName) throws IOException, FailedResponseException {
        return victimAssetAssocReader.getAssociatedVictimAssetSocialNetworks(uniqueId, ownerName);
    }

    @Override
    public VictimSocialNetwork getAssociatedVictimAssetSocialNetwork(Integer uniqueId, Integer assetId) throws IOException, FailedResponseException {
        return victimAssetAssocReader.getAssociatedVictimAssetSocialNetwork(uniqueId, assetId);
    }

    @Override
    public VictimSocialNetwork getAssociatedVictimAssetSocialNetwork(Integer uniqueId, Integer assetId, String ownerName) throws IOException, FailedResponseException {
        return victimAssetAssocReader.getAssociatedVictimAssetSocialNetwork(uniqueId, assetId, ownerName);
    }

    @Override
    public IterableResponse<VictimWebSite> getAssociatedVictimAssetWebsites(Integer uniqueId) throws IOException, FailedResponseException {
        return victimAssetAssocReader.getAssociatedVictimAssetWebsites(uniqueId);
    }

    @Override
    public IterableResponse<VictimWebSite> getAssociatedVictimAssetWebsites(Integer uniqueId, String ownerName) throws IOException, FailedResponseException {
        return victimAssetAssocReader.getAssociatedVictimAssetWebsites(uniqueId, ownerName);
    }

    @Override
    public VictimWebSite getAssociatedVictimAssetWebsite(Integer uniqueId, Integer assetId) throws IOException, FailedResponseException {
        return victimAssetAssocReader.getAssociatedVictimAssetWebsite(uniqueId, assetId);
    }

    @Override
    public VictimWebSite getAssociatedVictimAssetWebsite(Integer uniqueId, Integer assetId, String ownerName) throws IOException, FailedResponseException {
        return victimAssetAssocReader.getAssociatedVictimAssetWebsite(uniqueId, assetId, ownerName);
    }

    @Override
    public IterableResponse<Tag> getAssociatedTags(Integer uniqueId) throws IOException, FailedResponseException {
        return tagAssocReader.getAssociatedTags(uniqueId);
    }

    @Override
    public IterableResponse<Tag> getAssociatedTags(Integer uniqueId, String ownerName) throws IOException, FailedResponseException {
        return tagAssocReader.getAssociatedTags(uniqueId, ownerName);
    }

    @Override
    public Tag getAssociatedTag(Integer uniqueId, String tagName) throws IOException, FailedResponseException {
        return tagAssocReader.getAssociatedTag(uniqueId, tagName);
    }

    @Override
    public Tag getAssociatedTag(Integer uniqueId, String tagName, String ownerName) throws IOException, FailedResponseException {
        return tagAssocReader.getAssociatedTag(uniqueId, tagName, ownerName);
    }

    @Override
    public IterableResponse<SecurityLabel> getAssociatedSecurityLabels(Integer uniqueId) throws IOException, FailedResponseException {
        return secLabelAssocReader.getAssociatedSecurityLabels(uniqueId);
    }

    @Override
    public IterableResponse<SecurityLabel> getAssociatedSecurityLabels(Integer uniqueId, String ownerName) throws IOException, FailedResponseException {
        return secLabelAssocReader.getAssociatedSecurityLabels(uniqueId, ownerName);
    }

    @Override
    public SecurityLabel getAssociatedSecurityLabel(Integer uniqueId, String securityLabel) throws IOException, FailedResponseException {
        return secLabelAssocReader.getAssociatedSecurityLabel(uniqueId, securityLabel);
    }

    @Override
    public SecurityLabel getAssociatedSecurityLabel(Integer uniqueId, String securityLabel, String ownerName) throws IOException, FailedResponseException {
        return secLabelAssocReader.getAssociatedSecurityLabel(uniqueId, securityLabel, ownerName);
    }


}
