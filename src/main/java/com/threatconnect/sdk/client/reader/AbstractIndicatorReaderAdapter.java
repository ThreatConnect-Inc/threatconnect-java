package com.threatconnect.sdk.client.reader;

import com.threatconnect.sdk.client.UrlTypeable;
import com.threatconnect.sdk.client.reader.associate.*;
import com.threatconnect.sdk.client.response.IterableResponse;
import com.threatconnect.sdk.conn.Connection;
import com.threatconnect.sdk.exception.FailedResponseException;
import com.threatconnect.sdk.server.entity.*;
import com.threatconnect.sdk.server.response.entity.ApiEntityListResponse;
import com.threatconnect.sdk.server.response.entity.ApiEntitySingleResponse;
import com.threatconnect.sdk.server.response.entity.BulkStatusResponse;
import com.threatconnect.sdk.server.response.entity.IndicatorListResponse;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * AbstractIndicatorReaderAdapter is the primary client adapter for all Adversary group level objects.
 * It uses the {@link com.threatconnect.sdk.conn.Connection} object to execute requests against the {@link com.threatconnect.sdk.conn.AbstractRequestExecutor} object.
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
             , SecurityLabelAssociateReadable<String>, OwnerAssociateReadable<String>, VictimAssociateReadable<String>
{

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
     * Package level constructor. Use the {@link ReaderAdapterFactory} to
     * access this object.
     *
     * @param conn Primary connection object to the ThreatConnect API
     * ThreatConnect API
     * @param singleType
     * @param listType
     *
     * @see ReaderAdapterFactory
     */
    public AbstractIndicatorReaderAdapter(Connection conn
                , Class<? extends ApiEntitySingleResponse> singleType, Class singleItemType
                , Class<? extends ApiEntityListResponse> listType) {
        super(conn, singleType, singleItemType, listType);

        initComposite();
    }

    private void initComposite() {
        attribReader = new AbstractAttributeAssociateReaderAdapter<String>(
                            AbstractIndicatorReaderAdapter.this.getConn()
                          , AbstractIndicatorReaderAdapter.this.singleType
                          , AbstractIndicatorReaderAdapter.this.singleItemType
                          , AbstractIndicatorReaderAdapter.this.listType
            ) {
            @Override
            public String getUrlType()
            {
                return AbstractIndicatorReaderAdapter.this.getUrlType();
            }

            @Override
            protected String getUrlBasePrefix() {
                return AbstractIndicatorReaderAdapter.this.getUrlBasePrefix();
            }
        };

        groupAssocReader = new AbstractGroupAssociateReaderAdapter<String>(
                            AbstractIndicatorReaderAdapter.this.getConn()
                          , AbstractIndicatorReaderAdapter.this.singleType
                          , AbstractIndicatorReaderAdapter.this.singleItemType
                          , AbstractIndicatorReaderAdapter.this.listType) {
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
                          , AbstractIndicatorReaderAdapter.this.singleType
                          , AbstractIndicatorReaderAdapter.this.singleItemType
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
                          , AbstractIndicatorReaderAdapter.this.singleType
                          , AbstractIndicatorReaderAdapter.this.singleItemType
                          , AbstractIndicatorReaderAdapter.this.listType) {
            @Override
            protected String getUrlBasePrefix() {
                return AbstractIndicatorReaderAdapter.this.getUrlBasePrefix();
            }
        };

        tagAssocReader = new AbstractTagAssociateReaderAdapter<String>(
                            AbstractIndicatorReaderAdapter.this.getConn()
                          , AbstractIndicatorReaderAdapter.this.singleType
                          , AbstractIndicatorReaderAdapter.this.singleItemType
                          , AbstractIndicatorReaderAdapter.this.listType) {
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
                          , AbstractIndicatorReaderAdapter.this.singleType
                          , AbstractIndicatorReaderAdapter.this.singleItemType
                          , AbstractIndicatorReaderAdapter.this.listType) {
            @Override
            protected String getUrlBasePrefix() {
                return AbstractIndicatorReaderAdapter.this.getUrlBasePrefix();
            }
            @Override
            public String getUrlType() {
                return AbstractIndicatorReaderAdapter.this.getUrlType();
            }
        };
        
        ownerAssocReader = new AbstractOwnerAssociateReaderAdapter<String>(
                            AbstractIndicatorReaderAdapter.this.getConn()
                          , AbstractIndicatorReaderAdapter.this.singleType
                          , AbstractIndicatorReaderAdapter.this.singleItemType
                          , AbstractIndicatorReaderAdapter.this.listType) {
            @Override
            protected String getUrlBasePrefix() {
                return AbstractIndicatorReaderAdapter.this.getUrlBasePrefix();
            }
        };

        victimAssocReader = new AbstractVictimAssociateReaderAdapter<String>(
                            AbstractIndicatorReaderAdapter.this.getConn()
                          , AbstractIndicatorReaderAdapter.this.singleType
                          , AbstractIndicatorReaderAdapter.this.singleItemType
                          , AbstractIndicatorReaderAdapter.this.listType) {
            @Override
            protected String getUrlBasePrefix() {
                return AbstractIndicatorReaderAdapter.this.getUrlBasePrefix();
            }
            @Override
            public String getUrlType() {
                return AbstractIndicatorReaderAdapter.this.getUrlType();
            }
        };
        
    }

    @Override
    protected String getUrlBasePrefix() {
        return "v2.indicators.type";
    }


    public BulkStatusResponse getBulkStatus(String ownerName) throws IOException, FailedResponseException
    {
        return getItem("v2.bulk", BulkStatusResponse.class, ownerName, null);
    }

    public void downloadBulkIndicatorJson(String ownerName, Path outputPath) throws IOException
    {
        Map<String, Object> param = new HashMap<>();
        param.put("format", "json");

        InputStream in = downloadBulkHelper(ownerName, param);

        if (null != in) {
            Files.copy(in, outputPath);
        }
    }

    public void downloadBulkIndicatorCsv(String ownerName, Path outputPath) throws IOException
    {
        Map<String, Object> param = new HashMap<>();
        param.put("format", "csv");

        InputStream in = downloadBulkHelper(ownerName, param);

        if (null != in) {
            Files.copy(in, outputPath);
        }
    }

    private InputStream downloadBulkHelper(String ownerName, Map<String, Object> param) throws IOException
    {
        return getFile("v2.bulk.download", ownerName, param);
    }

    public String getIndicatorsAsText() throws IOException {
        return getAsText("v2.indicators.list");
    }

    public IterableResponse<Indicator> getIndicators() throws IOException, FailedResponseException {
	    return getItems("v2.indicators.list", IndicatorListResponse.class, Indicator.class);
    }

    @Override
    public IterableResponse<Group> getAssociatedGroups(String uniqueId) throws IOException, FailedResponseException {
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
    public Adversary getAssociatedGroupAdversary(String uniqueId, Integer adversaryId) throws IOException, FailedResponseException {
        return groupAssocReader.getAssociatedGroupAdversary(uniqueId,adversaryId);
    }

    @Override
    public Adversary getAssociatedGroupAdversary(String uniqueId, Integer adversaryId, String ownerName) throws IOException, FailedResponseException {
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
    public Email getAssociatedGroupEmail(String uniqueId, Integer emailId) throws IOException, FailedResponseException {
        return groupAssocReader.getAssociatedGroupEmail(uniqueId,emailId);
    }

    @Override
    public Email getAssociatedGroupEmail(String uniqueId, Integer emailId, String ownerName) throws IOException, FailedResponseException {
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
    public Incident getAssociatedGroupIncident(String uniqueId, Integer incidentId) throws IOException, FailedResponseException {
        return groupAssocReader.getAssociatedGroupIncident(uniqueId, incidentId);
    }

    @Override
    public Incident getAssociatedGroupIncident(String uniqueId, Integer incidentId, String ownerName) throws IOException, FailedResponseException {
        return groupAssocReader.getAssociatedGroupIncident(uniqueId, incidentId, ownerName);
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
    public Signature getAssociatedGroupSignature(String uniqueId, Integer signatureId) throws IOException, FailedResponseException {
        return groupAssocReader.getAssociatedGroupSignature(uniqueId, signatureId);
    }

    @Override
    public Signature getAssociatedGroupSignature(String uniqueId, Integer signatureId, String ownerName) throws IOException, FailedResponseException {
        return groupAssocReader.getAssociatedGroupSignature(uniqueId, signatureId, ownerName);
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
    public Threat getAssociatedGroupThreat(String uniqueId, Integer threatId) throws IOException, FailedResponseException {
        return groupAssocReader.getAssociatedGroupThreat(uniqueId, threatId);
    }

    @Override
    public Threat getAssociatedGroupThreat(String uniqueId, Integer threatId, String ownerName) throws IOException, FailedResponseException {
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
    public IterableResponse<Attribute> getAttributes(String uniqueId) throws IOException, FailedResponseException {
        return attribReader.getAttributes(uniqueId);
    }

    @Override
    public IterableResponse<Attribute> getAttributes(String uniqueId, String ownerName) throws IOException, FailedResponseException {
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
    public IterableResponse<SecurityLabel> getAttributeSecurityLabels(String uniqueId, Integer attributeId) throws IOException, FailedResponseException {
        return attribReader.getAttributeSecurityLabels(uniqueId, attributeId);
    }

    @Override
    public IterableResponse<SecurityLabel> getAttributeSecurityLabels(String uniqueId, Integer attributeId, String ownerName) throws IOException, FailedResponseException {
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
    public IterableResponse<VictimAsset> getAssociatedVictimAssets(String uniqueId, String ownerName) throws IOException, FailedResponseException {
        return victimAssetAssocReader.getAssociatedVictimAssets(uniqueId, ownerName);
    }

    @Override
    public IterableResponse<VictimEmailAddress> getAssociatedVictimAssetEmailAddresses(String uniqueId) throws IOException, FailedResponseException {
        return victimAssetAssocReader.getAssociatedVictimAssetEmailAddresses(uniqueId);
    }

    @Override
    public IterableResponse<VictimEmailAddress> getAssociatedVictimAssetEmailAddresses(String uniqueId, String ownerName) throws IOException, FailedResponseException {
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
    public IterableResponse<VictimNetworkAccount> getAssociatedVictimAssetNetworkAccounts(String uniqueId) throws IOException, FailedResponseException {
        return victimAssetAssocReader.getAssociatedVictimAssetNetworkAccounts(uniqueId);
    }

    @Override
    public IterableResponse<VictimNetworkAccount> getAssociatedVictimAssetNetworkAccounts(String uniqueId, String ownerName) throws IOException, FailedResponseException {
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
    public IterableResponse<VictimPhone> getAssociatedVictimAssetPhoneNumbers(String uniqueId) throws IOException, FailedResponseException {
        return victimAssetAssocReader.getAssociatedVictimAssetPhoneNumbers(uniqueId);
    }

    @Override
    public IterableResponse<VictimPhone> getAssociatedVictimAssetPhoneNumbers(String uniqueId, String ownerName) throws IOException, FailedResponseException {
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
    public IterableResponse<VictimSocialNetwork> getAssociatedVictimAssetSocialNetworks(String uniqueId) throws IOException, FailedResponseException {
        return victimAssetAssocReader.getAssociatedVictimAssetSocialNetworks(uniqueId);
    }

    @Override
    public IterableResponse<VictimSocialNetwork> getAssociatedVictimAssetSocialNetworks(String uniqueId, String ownerName) throws IOException, FailedResponseException {
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
    public IterableResponse<VictimWebSite> getAssociatedVictimAssetWebsites(String uniqueId) throws IOException, FailedResponseException {
        return victimAssetAssocReader.getAssociatedVictimAssetWebsites(uniqueId);
    }

    @Override
    public IterableResponse<VictimWebSite> getAssociatedVictimAssetWebsites(String uniqueId, String ownerName) throws IOException, FailedResponseException {
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
    public IterableResponse<Tag> getAssociatedTags(String uniqueId) throws IOException, FailedResponseException {
        return tagAssocReader.getAssociatedTags(uniqueId);
    }

    @Override
    public IterableResponse<Tag> getAssociatedTags(String uniqueId, String ownerName) throws IOException, FailedResponseException {
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
    public IterableResponse<SecurityLabel> getAssociatedSecurityLabels(String uniqueId) throws IOException, FailedResponseException {
        return secLabelAssocReader.getAssociatedSecurityLabels(uniqueId);
    }

    @Override
    public IterableResponse<SecurityLabel> getAssociatedSecurityLabels(String uniqueId, String ownerName) throws IOException, FailedResponseException {
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
    public IterableResponse<Owner> getAssociatedOwners(String uniqueId) throws IOException, FailedResponseException {
        return ownerAssocReader.getAssociatedOwners(uniqueId);
    }

    @Override
    public IterableResponse<Owner> getAssociatedOwners(String uniqueId, String ownerName) throws IOException, FailedResponseException {
        return ownerAssocReader.getAssociatedOwners(uniqueId, ownerName);
    }

    @Override
    public IterableResponse<Victim> getAssociatedVictims(String uniqueId) throws IOException, FailedResponseException {
        return victimAssocReader.getAssociatedVictims(uniqueId);
    }

    @Override
    public IterableResponse<Victim> getAssociatedVictims(String uniqueId, String ownerName) throws IOException, FailedResponseException {
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
