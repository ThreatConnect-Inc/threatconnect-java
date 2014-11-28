package com.cyber2.api.lib.client.reader;

import com.cyber2.api.lib.client.reader.associate.AbstractAttributeAssociateReaderAdapter;
import com.cyber2.api.lib.client.reader.associate.SecurityLabelAssociateReadable;
import com.cyber2.api.lib.client.reader.associate.VictimAssetAssociateReadable;
import com.cyber2.api.lib.client.reader.associate.TagAssociateReadable;
import com.cyber2.api.lib.client.reader.associate.VictimAssociateReadable;
import com.cyber2.api.lib.client.reader.associate.OwnerAssociateReadable;
import com.cyber2.api.lib.client.reader.associate.AbstractVictimAssociateReaderAdapter;
import com.cyber2.api.lib.client.reader.associate.AbstractVictimAssetAssociateReaderAdapter;
import com.cyber2.api.lib.client.reader.associate.AbstractSecurityLabelAssociateReaderAdapter;
import com.cyber2.api.lib.client.reader.associate.AbstractTagAssociateReaderAdapter;
import com.cyber2.api.lib.client.reader.associate.IndicatorAssociateReadable;
import com.cyber2.api.lib.client.reader.associate.AttributeAssociateReadable;
import com.cyber2.api.lib.client.reader.associate.AbstractOwnerAssociateReaderAdapter;
import com.cyber2.api.lib.client.reader.associate.GroupAssociateReadable;
import com.cyber2.api.lib.client.reader.associate.AbstractIndicatorAssociateReaderAdapter;
import com.cyber2.api.lib.client.reader.associate.AbstractGroupAssociateReaderAdapter;
import com.cyber2.api.lib.client.UrlTypeable;
import com.cyber2.api.lib.conn.Connection;
import com.cyber2.api.lib.conn.RequestExecutor;
import com.cyber2.api.lib.exception.FailedResponseException;
import com.cyber2.api.lib.server.entity.Address;
import com.cyber2.api.lib.server.entity.Adversary;
import com.cyber2.api.lib.server.entity.Attribute;
import com.cyber2.api.lib.server.entity.Email;
import com.cyber2.api.lib.server.entity.File;
import com.cyber2.api.lib.server.entity.Group;
import com.cyber2.api.lib.server.entity.Host;
import com.cyber2.api.lib.server.entity.Incident;
import com.cyber2.api.lib.server.entity.Indicator;
import com.cyber2.api.lib.server.entity.Owner;
import com.cyber2.api.lib.server.entity.SecurityLabel;
import com.cyber2.api.lib.server.entity.Signature;
import com.cyber2.api.lib.server.entity.Tag;
import com.cyber2.api.lib.server.entity.Threat;
import com.cyber2.api.lib.server.entity.Url;
import com.cyber2.api.lib.server.entity.Victim;
import com.cyber2.api.lib.server.entity.VictimAsset;
import com.cyber2.api.lib.server.entity.VictimEmailAddress;
import com.cyber2.api.lib.server.entity.VictimNetworkAccount;
import com.cyber2.api.lib.server.entity.VictimPhone;
import com.cyber2.api.lib.server.entity.VictimSocialNetwork;
import com.cyber2.api.lib.server.entity.VictimWebSite;
import com.cyber2.api.lib.server.response.entity.ApiEntityListResponse;
import com.cyber2.api.lib.server.response.entity.ApiEntitySingleResponse;
import com.cyber2.api.lib.server.response.entity.IndicatorListResponse;
import java.io.IOException;
import java.util.List;

/**
 * AbstractIndicatorReaderAdapter is the primary client adapter for all Adversary group level objects.
 * It uses the {@link Connection} object to execute requests against the {@link RequestExecutor} object.
 * The responsibility of this class is to encapsulate all the low level ThreatConnect API calls
 * specifically targeted at data under the Adversary group type.
 * 
 *
 * @author dtineo
 * @param <T>
 */
public abstract class AbstractIndicatorReaderAdapter<T extends Indicator> 
    extends AbstractBaseReaderAdapter<T,String>
    implements UrlTypeable, GroupAssociateReadable<String>, IndicatorAssociateReadable<String>
             , AttributeAssociateReadable<String>, VictimAssetAssociateReadable<String>, TagAssociateReadable<String>
             , SecurityLabelAssociateReadable<String>, OwnerAssociateReadable<String>, VictimAssociateReadable<String>  {

    // composite pattern
    private AbstractGroupAssociateReaderAdapter<String> groupAssocReader;
    private AbstractIndicatorAssociateReaderAdapter<String> indAssocReader;
    private AbstractAttributeAssociateReaderAdapter<String> attribReader;
    private AbstractVictimAssetAssociateReaderAdapter<String> victimAssetAssocReader;
    private AbstractSecurityLabelAssociateReaderAdapter<String> secLabelAssocReader;
    private AbstractTagAssociateReaderAdapter<String> tagAssocReader;
    private AbstractOwnerAssociateReaderAdapter<String> ownerAssocReader;
    private AbstractVictimAssociateReaderAdapter<String> victimAssocReader;

    /**
     * Package level constructor. Use the {@link ClientReaderAdapterFactory} to
     * access this object.
     *
     * @param conn Primary connection object to the ThreatConnect API
     * @param executor Executor handling low level HTTPS calls to the
     * ThreatConnect API
     * @param singleType
     * @param listType
     *
     * @see ReaderAdapterFactory
     */
    public AbstractIndicatorReaderAdapter(Connection conn, RequestExecutor executor
                , Class<? extends ApiEntitySingleResponse> singleType, Class<? extends ApiEntityListResponse> listType) { 
        super(conn, executor, singleType, listType);

        initComposite();
    }

    private void initComposite() {
        attribReader = new AbstractAttributeAssociateReaderAdapter<String>(
                            AbstractIndicatorReaderAdapter.this.getConn()
                          , AbstractIndicatorReaderAdapter.this.executor
                          , AbstractIndicatorReaderAdapter.this.singleType
                          , AbstractIndicatorReaderAdapter.this.listType
            ) {
            @Override
            protected String getUrlBasePrefix() {
                return AbstractIndicatorReaderAdapter.this.getUrlBasePrefix();
            }
        };

        groupAssocReader = new AbstractGroupAssociateReaderAdapter<String>(
                            AbstractIndicatorReaderAdapter.this.getConn()
                          , AbstractIndicatorReaderAdapter.this.executor
                          , AbstractIndicatorReaderAdapter.this.singleType
                          , AbstractIndicatorReaderAdapter.this.listType
            ) {
            @Override
            protected String getUrlBasePrefix() {
                return AbstractIndicatorReaderAdapter.this.getUrlBasePrefix();
            }

            @Override
            public String getUrlType() {
                return AbstractIndicatorReaderAdapter.this.getUrlType();
            }
        };

        indAssocReader = new AbstractIndicatorAssociateReaderAdapter<String>(
                            AbstractIndicatorReaderAdapter.this.getConn()
                          , AbstractIndicatorReaderAdapter.this.executor
                          , AbstractIndicatorReaderAdapter.this.singleType
                          , AbstractIndicatorReaderAdapter.this.listType
            ) {
            @Override
            protected String getUrlBasePrefix() {
                return AbstractIndicatorReaderAdapter.this.getUrlBasePrefix();
            }

            @Override
            public String getUrlType() {
                return AbstractIndicatorReaderAdapter.this.getUrlType();
            }
        };

        secLabelAssocReader = new AbstractSecurityLabelAssociateReaderAdapter<String>(
                            AbstractIndicatorReaderAdapter.this.getConn()
                          , AbstractIndicatorReaderAdapter.this.executor
                          , AbstractIndicatorReaderAdapter.this.singleType
                          , AbstractIndicatorReaderAdapter.this.listType
            ) {
            @Override
            protected String getUrlBasePrefix() {
                return AbstractIndicatorReaderAdapter.this.getUrlBasePrefix();
            }
        };

        tagAssocReader = new AbstractTagAssociateReaderAdapter<String>(
                            AbstractIndicatorReaderAdapter.this.getConn()
                          , AbstractIndicatorReaderAdapter.this.executor
                          , AbstractIndicatorReaderAdapter.this.singleType
                          , AbstractIndicatorReaderAdapter.this.listType
            ) {
            @Override
            protected String getUrlBasePrefix() {
                return AbstractIndicatorReaderAdapter.this.getUrlBasePrefix();
            }
            @Override
            public String getUrlType() {
                return AbstractIndicatorReaderAdapter.this.getUrlType();
            }
        };

        victimAssetAssocReader = new AbstractVictimAssetAssociateReaderAdapter<String>(
                            AbstractIndicatorReaderAdapter.this.getConn()
                          , AbstractIndicatorReaderAdapter.this.executor
                          , AbstractIndicatorReaderAdapter.this.singleType
                          , AbstractIndicatorReaderAdapter.this.listType
            ) {
            @Override
            protected String getUrlBasePrefix() {
                return AbstractIndicatorReaderAdapter.this.getUrlBasePrefix();
            }
        };
        
        ownerAssocReader = new AbstractOwnerAssociateReaderAdapter<String>(
                            AbstractIndicatorReaderAdapter.this.getConn()
                          , AbstractIndicatorReaderAdapter.this.executor
                          , AbstractIndicatorReaderAdapter.this.singleType
                          , AbstractIndicatorReaderAdapter.this.listType
            ) {
            @Override
            protected String getUrlBasePrefix() {
                return AbstractIndicatorReaderAdapter.this.getUrlBasePrefix();
            }
        };

        victimAssocReader = new AbstractVictimAssociateReaderAdapter<String>(
                            AbstractIndicatorReaderAdapter.this.getConn()
                          , AbstractIndicatorReaderAdapter.this.executor
                          , AbstractIndicatorReaderAdapter.this.singleType
                          , AbstractIndicatorReaderAdapter.this.listType
            ) {
            @Override
            protected String getUrlBasePrefix() {
                return AbstractIndicatorReaderAdapter.this.getUrlBasePrefix();
            }
        };
        
    }

    @Override
    protected String getUrlBasePrefix() {
        return "v2.indicators.type";
    }

    public String getIndicatorsAsText() throws IOException {
        return getAsText("v2.indicators.list");
    }

    public List<Indicator> getIndicators() throws IOException, FailedResponseException {
	    IndicatorListResponse data =  getList("v2.indicators.list", IndicatorListResponse.class);

        return (List<Indicator>) data.getData().getData();
    }

    @Override
    public List<Group> getAssociatedGroups(String uniqueId) throws IOException, FailedResponseException {
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

    @Override
    public List<Attribute> getAttributes(String uniqueId) throws IOException, FailedResponseException {
        return attribReader.getAttributes(uniqueId);
    }

    @Override
    public List<Attribute> getAttributes(String uniqueId, String ownerName) throws IOException, FailedResponseException {
        return attribReader.getAttributes(uniqueId, ownerName);
    }

    @Override
    public Attribute getAttribute(String uniqueId, Integer attributeId) throws IOException, FailedResponseException {
        return attribReader.getAttribute(uniqueId, attributeId);
    }

    @Override
    public Attribute getAttribute(String uniqueId, Integer attributeId, String ownerName) throws IOException, FailedResponseException {
        return attribReader.getAttribute(uniqueId, attributeId, ownerName);
    }

    @Override
    public List<SecurityLabel> getAttributeSecurityLabels(String uniqueId, Integer attributeId) throws IOException, FailedResponseException {
        return attribReader.getAttributeSecurityLabels(uniqueId, attributeId);
    }

    @Override
    public List<SecurityLabel> getAttributeSecurityLabels(String uniqueId, Integer attributeId, String ownerName) throws IOException, FailedResponseException {
        return attribReader.getAttributeSecurityLabels(uniqueId, attributeId, ownerName);
    }

    @Override
    public SecurityLabel getAttributeSecurityLabel(String uniqueId, Integer attributeId, String securityLabel) throws IOException, FailedResponseException {
        return attribReader.getAttributeSecurityLabel(uniqueId, attributeId, securityLabel);
    }

    @Override
    public SecurityLabel getAttributeSecurityLabel(String uniqueId, Integer attributeId, String securityLabel, String ownerName) throws IOException, FailedResponseException {
        return attribReader.getAttributeSecurityLabel(uniqueId, attributeId, securityLabel, ownerName);
    }

    @Override
    public List<VictimAsset> getAssociatedVictimAssets(String uniqueId, String ownerName) throws IOException, FailedResponseException {
        return victimAssetAssocReader.getAssociatedVictimAssets(uniqueId, ownerName);
    }

    @Override
    public List<VictimEmailAddress> getAssociatedVictimAssetEmailAddresses(String uniqueId) throws IOException, FailedResponseException {
        return victimAssetAssocReader.getAssociatedVictimAssetEmailAddresses(uniqueId);
    }

    @Override
    public List<VictimEmailAddress> getAssociatedVictimAssetEmailAddresses(String uniqueId, String ownerName) throws IOException, FailedResponseException {
        return victimAssetAssocReader.getAssociatedVictimAssetEmailAddresses(uniqueId, ownerName);
    }

    @Override
    public VictimEmailAddress getAssociatedVictimAssetEmailAddress(String uniqueId, Integer assetId) throws IOException, FailedResponseException {
        return victimAssetAssocReader.getAssociatedVictimAssetEmailAddress(uniqueId, assetId);
    }

    @Override
    public VictimEmailAddress getAssociatedVictimAssetEmailAddress(String uniqueId, Integer assetId, String ownerName) throws IOException, FailedResponseException {
        return victimAssetAssocReader.getAssociatedVictimAssetEmailAddress(uniqueId, assetId, ownerName);
    }

    @Override
    public List<VictimNetworkAccount> getAssociatedVictimAssetNetworkAccounts(String uniqueId) throws IOException, FailedResponseException {
        return victimAssetAssocReader.getAssociatedVictimAssetNetworkAccounts(uniqueId);
    }

    @Override
    public List<VictimNetworkAccount> getAssociatedVictimAssetNetworkAccounts(String uniqueId, String ownerName) throws IOException, FailedResponseException {
        return victimAssetAssocReader.getAssociatedVictimAssetNetworkAccounts(uniqueId, ownerName);
    }

    @Override
    public VictimNetworkAccount getAssociatedVictimAssetNetworkAccount(String uniqueId, Integer assetId) throws IOException, FailedResponseException {
        return victimAssetAssocReader.getAssociatedVictimAssetNetworkAccount(uniqueId, assetId);
    }

    @Override
    public VictimNetworkAccount getAssociatedVictimAssetNetworkAccount(String uniqueId, Integer assetId, String ownerName) throws IOException, FailedResponseException {
        return victimAssetAssocReader.getAssociatedVictimAssetNetworkAccount(uniqueId, assetId, ownerName);
    }

    @Override
    public List<VictimPhone> getAssociatedVictimAssetPhoneNumbers(String uniqueId) throws IOException, FailedResponseException {
        return victimAssetAssocReader.getAssociatedVictimAssetPhoneNumbers(uniqueId);
    }

    @Override
    public List<VictimPhone> getAssociatedVictimAssetPhoneNumbers(String uniqueId, String ownerName) throws IOException, FailedResponseException {
        return victimAssetAssocReader.getAssociatedVictimAssetPhoneNumbers(uniqueId, ownerName);
    }

    @Override
    public VictimPhone getAssociatedVictimAssetPhoneNumber(String uniqueId, Integer assetId) throws IOException, FailedResponseException {
        return victimAssetAssocReader.getAssociatedVictimAssetPhoneNumber(uniqueId, assetId);
    }

    @Override
    public VictimPhone getAssociatedVictimAssetPhoneNumber(String uniqueId, Integer assetId, String ownerName) throws IOException, FailedResponseException {
        return victimAssetAssocReader.getAssociatedVictimAssetPhoneNumber(uniqueId, assetId, ownerName);
    }

    @Override
    public List<VictimSocialNetwork> getAssociatedVictimAssetSocialNetworks(String uniqueId) throws IOException, FailedResponseException {
        return victimAssetAssocReader.getAssociatedVictimAssetSocialNetworks(uniqueId);
    }

    @Override
    public List<VictimSocialNetwork> getAssociatedVictimAssetSocialNetworks(String uniqueId, String ownerName) throws IOException, FailedResponseException {
        return victimAssetAssocReader.getAssociatedVictimAssetSocialNetworks(uniqueId, ownerName);
    }

    @Override
    public VictimSocialNetwork getAssociatedVictimAssetSocialNetwork(String uniqueId, Integer assetId) throws IOException, FailedResponseException {
        return victimAssetAssocReader.getAssociatedVictimAssetSocialNetwork(uniqueId, assetId);
    }

    @Override
    public VictimSocialNetwork getAssociatedVictimAssetSocialNetwork(String uniqueId, Integer assetId, String ownerName) throws IOException, FailedResponseException {
        return victimAssetAssocReader.getAssociatedVictimAssetSocialNetwork(uniqueId, assetId, ownerName);
    }

    @Override
    public List<VictimWebSite> getAssociatedVictimAssetWebsites(String uniqueId) throws IOException, FailedResponseException {
        return victimAssetAssocReader.getAssociatedVictimAssetWebsites(uniqueId);
    }

    @Override
    public List<VictimWebSite> getAssociatedVictimAssetWebsites(String uniqueId, String ownerName) throws IOException, FailedResponseException {
        return victimAssetAssocReader.getAssociatedVictimAssetWebsites(uniqueId, ownerName);
    }

    @Override
    public VictimWebSite getAssociatedVictimAssetWebsite(String uniqueId, Integer assetId) throws IOException, FailedResponseException {
        return victimAssetAssocReader.getAssociatedVictimAssetWebsite(uniqueId, assetId);
    }

    @Override
    public VictimWebSite getAssociatedVictimAssetWebsite(String uniqueId, Integer assetId, String ownerName) throws IOException, FailedResponseException {
        return victimAssetAssocReader.getAssociatedVictimAssetWebsite(uniqueId, assetId, ownerName);
    }

    @Override
    public List<Tag> getAssociatedTags(String uniqueId) throws IOException, FailedResponseException {
        return tagAssocReader.getAssociatedTags(uniqueId);
    }

    @Override
    public List<Tag> getAssociatedTags(String uniqueId, String ownerName) throws IOException, FailedResponseException {
        return tagAssocReader.getAssociatedTags(uniqueId, ownerName);
    }

    @Override
    public Tag getAssociatedTag(String uniqueId, String tagName) throws IOException, FailedResponseException {
        return tagAssocReader.getAssociatedTag(uniqueId, tagName);
    }

    @Override
    public Tag getAssociatedTag(String uniqueId, String tagName, String ownerName) throws IOException, FailedResponseException {
        return tagAssocReader.getAssociatedTag(uniqueId, tagName, ownerName);
    }

    @Override
    public List<SecurityLabel> getAssociatedSecurityLabels(String uniqueId) throws IOException, FailedResponseException {
        return secLabelAssocReader.getAssociatedSecurityLabels(uniqueId);
    }

    @Override
    public List<SecurityLabel> getAssociatedSecurityLabels(String uniqueId, String ownerName) throws IOException, FailedResponseException {
        return secLabelAssocReader.getAssociatedSecurityLabels(uniqueId, ownerName);
    }

    @Override
    public SecurityLabel getAssociatedSecurityLabel(String uniqueId, String securityLabel) throws IOException, FailedResponseException {
        return secLabelAssocReader.getAssociatedSecurityLabel(uniqueId, securityLabel);
    }

    @Override
    public SecurityLabel getAssociatedSecurityLabel(String uniqueId, String securityLabel, String ownerName) throws IOException, FailedResponseException {
        return secLabelAssocReader.getAssociatedSecurityLabel(uniqueId, securityLabel, ownerName);
    }

    @Override
    public List<Owner> getAssociatedOwners(String uniqueId) throws IOException, FailedResponseException {
        return ownerAssocReader.getAssociatedOwners(uniqueId);
    }

    @Override
    public List<Owner> getAssociatedOwners(String uniqueId, String ownerName) throws IOException, FailedResponseException {
        return ownerAssocReader.getAssociatedOwners(uniqueId, ownerName);
    }

    @Override
    public List<Victim> getAssociatedVictims(String uniqueId) throws IOException, FailedResponseException {
        return victimAssocReader.getAssociatedVictims(uniqueId);
    }

    @Override
    public List<Victim> getAssociatedVictims(String uniqueId, String ownerName) throws IOException, FailedResponseException {
        return victimAssocReader.getAssociatedVictims(uniqueId, ownerName);
    }

    @Override
    public Victim getAssociatedVictim(String uniqueId, Integer victimId) throws IOException, FailedResponseException {
        return victimAssocReader.getAssociatedVictim(uniqueId, victimId);
    }

    @Override
    public Victim getAssociatedVictim(String uniqueId, Integer victimId, String ownerName) throws IOException, FailedResponseException {
        return victimAssocReader.getAssociatedVictim(uniqueId, victimId, ownerName);
    }

}
