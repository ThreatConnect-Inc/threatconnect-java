package com.threatconnect.sdk.client.reader;

import com.threatconnect.sdk.client.AbstractClientAdapter;
import com.threatconnect.sdk.client.UrlTypeable;
import com.threatconnect.sdk.client.reader.associate.AbstractAttributeAssociateReaderAdapter;
import com.threatconnect.sdk.client.reader.associate.AbstractGroupAssociateReaderAdapter;
import com.threatconnect.sdk.client.reader.associate.AbstractIndicatorAssociateReaderAdapter;
import com.threatconnect.sdk.client.reader.associate.AbstractOwnerAssociateReaderAdapter;
import com.threatconnect.sdk.client.reader.associate.AbstractSecurityLabelAssociateReaderAdapter;
import com.threatconnect.sdk.client.reader.associate.AbstractTagAssociateReaderAdapter;
import com.threatconnect.sdk.client.reader.associate.AbstractVictimAssetAssociateReaderAdapter;
import com.threatconnect.sdk.client.reader.associate.AbstractVictimAssociateReaderAdapter;
import com.threatconnect.sdk.client.reader.associate.AttributeAssociateReadable;
import com.threatconnect.sdk.client.reader.associate.GroupAssociateReadable;
import com.threatconnect.sdk.client.reader.associate.IndicatorAssociateReadable;
import com.threatconnect.sdk.client.reader.associate.OwnerAssociateReadable;
import com.threatconnect.sdk.client.reader.associate.SecurityLabelAssociateReadable;
import com.threatconnect.sdk.client.reader.associate.TagAssociateReadable;
import com.threatconnect.sdk.client.reader.associate.VictimAssetAssociateReadable;
import com.threatconnect.sdk.client.reader.associate.VictimAssociateReadable;
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
import com.threatconnect.sdk.server.entity.FalsePositive;
import com.threatconnect.sdk.server.entity.File;
import com.threatconnect.sdk.server.entity.Group;
import com.threatconnect.sdk.server.entity.Host;
import com.threatconnect.sdk.server.entity.Incident;
import com.threatconnect.sdk.server.entity.Indicator;
import com.threatconnect.sdk.server.entity.Observation;
import com.threatconnect.sdk.server.entity.ObservationCount;
import com.threatconnect.sdk.server.entity.Owner;
import com.threatconnect.sdk.server.entity.SecurityLabel;
import com.threatconnect.sdk.server.entity.Signature;
import com.threatconnect.sdk.server.entity.Tag;
import com.threatconnect.sdk.server.entity.Threat;
import com.threatconnect.sdk.server.entity.Url;
import com.threatconnect.sdk.server.entity.Victim;
import com.threatconnect.sdk.server.entity.VictimAsset;
import com.threatconnect.sdk.server.entity.VictimEmailAddress;
import com.threatconnect.sdk.server.entity.VictimNetworkAccount;
import com.threatconnect.sdk.server.entity.VictimPhone;
import com.threatconnect.sdk.server.entity.VictimSocialNetwork;
import com.threatconnect.sdk.server.entity.VictimWebSite;
import com.threatconnect.sdk.server.response.entity.ApiEntityListResponse;
import com.threatconnect.sdk.server.response.entity.ApiEntitySingleResponse;
import com.threatconnect.sdk.server.response.entity.BulkStatusResponse;
import com.threatconnect.sdk.server.response.entity.IndicatorListResponse;
import com.threatconnect.sdk.server.response.entity.ObservationListResponse;
import org.apache.commons.io.IOUtils;
import org.apache.http.entity.ContentType;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
 * @param <T> Parameter
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
     * @param singleType Class for single type
     * @param listType Class for list type
     * @param singleItemType Class for single item type
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
        downloadBulkIndicatorJson(ownerName, new FileOutputStream(outputPath.toFile()));
    }

    public void downloadBulkIndicatorJson(String ownerName, OutputStream output) throws IOException
    {
        Map<String, Object> param = new HashMap<>();
        param.put("format", "json");

        InputStream in = downloadBulkHelper(ownerName, param);

        if (null != in) {
            IOUtils.copy(in, output);
        }
    }

    public void downloadBulkIndicatorCsv(String ownerName, Path outputPath) throws IOException
    {
    	downloadBulkIndicatorCsv(ownerName, new FileOutputStream(outputPath.toFile()));
    }

    public void downloadBulkIndicatorCsv(String ownerName, OutputStream output) throws IOException
    {
        Map<String, Object> param = new HashMap<>();
        param.put("format", "csv");

        InputStream in = downloadBulkHelper(ownerName, param);

        if (null != in) {
            IOUtils.copy(in, output);
        }
    }

    private InputStream downloadBulkHelper(String ownerName, Map<String, Object> param) throws IOException
    {
        return getFile("v2.bulk.download", ownerName, param, ContentType.APPLICATION_JSON);
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
    public IterableResponse<Document> getAssociatedGroupDocuments(String uniqueId) throws IOException, FailedResponseException {
        return groupAssocReader.getAssociatedGroupDocuments(uniqueId);
    }

    @Override
    public IterableResponse<Document> getAssociatedGroupDocuments(String uniqueId, String ownerName) throws IOException, FailedResponseException {
        return groupAssocReader.getAssociatedGroupDocuments(uniqueId, ownerName);
    }

    @Override
    public Document getAssociatedGroupDocument(String uniqueId, Long incidentId) throws IOException, FailedResponseException {
        return groupAssocReader.getAssociatedGroupDocument(uniqueId, incidentId);
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
    public IterableResponse<Attribute> getAttributes(String uniqueId) throws IOException, FailedResponseException {
        return attribReader.getAttributes(uniqueId);
    }

    @Override
    public IterableResponse<Attribute> getAttributes(String uniqueId, String ownerName) throws IOException, FailedResponseException {
        return attribReader.getAttributes(uniqueId, ownerName);
    }

    @Override
    public Attribute getAttribute(String uniqueId, Long attributeId) throws IOException, FailedResponseException {
        return attribReader.getAttribute(uniqueId, attributeId);
    }

    @Override
    public Attribute getAttribute(String uniqueId, Long attributeId, String ownerName) throws IOException, FailedResponseException {
        return attribReader.getAttribute(uniqueId, attributeId, ownerName);
    }

    @Override
    public IterableResponse<SecurityLabel> getAttributeSecurityLabels(String uniqueId, Long attributeId) throws IOException, FailedResponseException {
        return attribReader.getAttributeSecurityLabels(uniqueId, attributeId);
    }

    @Override
    public IterableResponse<SecurityLabel> getAttributeSecurityLabels(String uniqueId, Long attributeId, String ownerName) throws IOException, FailedResponseException {
        return attribReader.getAttributeSecurityLabels(uniqueId, attributeId, ownerName);
    }

    @Override
    public SecurityLabel getAttributeSecurityLabel(String uniqueId, Long attributeId, String securityLabel) throws IOException, FailedResponseException {
        return attribReader.getAttributeSecurityLabel(uniqueId, attributeId, securityLabel);
    }

    @Override
    public SecurityLabel getAttributeSecurityLabel(String uniqueId, Long attributeId, String securityLabel, String ownerName) throws IOException, FailedResponseException {
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
    public VictimEmailAddress getAssociatedVictimAssetEmailAddress(String uniqueId, Long assetId) throws IOException, FailedResponseException {
        return victimAssetAssocReader.getAssociatedVictimAssetEmailAddress(uniqueId, assetId);
    }

    @Override
    public VictimEmailAddress getAssociatedVictimAssetEmailAddress(String uniqueId, Long assetId, String ownerName) throws IOException, FailedResponseException {
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
    public VictimNetworkAccount getAssociatedVictimAssetNetworkAccount(String uniqueId, Long assetId) throws IOException, FailedResponseException {
        return victimAssetAssocReader.getAssociatedVictimAssetNetworkAccount(uniqueId, assetId);
    }

    @Override
    public VictimNetworkAccount getAssociatedVictimAssetNetworkAccount(String uniqueId, Long assetId, String ownerName) throws IOException, FailedResponseException {
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
    public VictimPhone getAssociatedVictimAssetPhoneNumber(String uniqueId, Long assetId) throws IOException, FailedResponseException {
        return victimAssetAssocReader.getAssociatedVictimAssetPhoneNumber(uniqueId, assetId);
    }

    @Override
    public VictimPhone getAssociatedVictimAssetPhoneNumber(String uniqueId, Long assetId, String ownerName) throws IOException, FailedResponseException {
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
    public VictimSocialNetwork getAssociatedVictimAssetSocialNetwork(String uniqueId, Long assetId) throws IOException, FailedResponseException {
        return victimAssetAssocReader.getAssociatedVictimAssetSocialNetwork(uniqueId, assetId);
    }

    @Override
    public VictimSocialNetwork getAssociatedVictimAssetSocialNetwork(String uniqueId, Long assetId, String ownerName) throws IOException, FailedResponseException {
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
    public VictimWebSite getAssociatedVictimAssetWebsite(String uniqueId, Long assetId) throws IOException, FailedResponseException {
        return victimAssetAssocReader.getAssociatedVictimAssetWebsite(uniqueId, assetId);
    }

    @Override
    public VictimWebSite getAssociatedVictimAssetWebsite(String uniqueId, Long assetId, String ownerName) throws IOException, FailedResponseException {
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
    public Victim getAssociatedVictim(String uniqueId, Long victimId) throws IOException, FailedResponseException {
        return victimAssocReader.getAssociatedVictim(uniqueId, victimId);
    }

    @Override
    public Victim getAssociatedVictim(String uniqueId, Long victimId, String ownerName) throws IOException, FailedResponseException {
        return victimAssocReader.getAssociatedVictim(uniqueId, victimId, ownerName);
    }

    public IterableResponse<Observation> getObservations(String uniqueId) throws IOException
    {
        return getObservations(uniqueId, null);
    }

    public IterableResponse<Observation> getObservations(String uniqueId, String ownerName) throws IOException
    {
        Map<String, Object> map = AbstractClientAdapter.createParamMap("id", uniqueId);
        return getItems(getUrlBasePrefix() + ".byId.observations", ObservationListResponse.class, Observation.class, ownerName, map);

    }

    public ObservationCount getObservationCount(String uniqueId) throws IOException
    {
        return getObservationCount(uniqueId, null);
    }

    public ObservationCount getObservationCount(String uniqueId, String ownerName) throws IOException
    {
        Map<String, Object> map = AbstractClientAdapter.createParamMap("id", uniqueId);
        ApiEntitySingleResponse item = getItem(getUrlBasePrefix() + ".byId.observationCount", singleType, ownerName, map);

        return (ObservationCount) item.getData().getData();

    }

    public FalsePositive getFalsePositive(String uniqueId) throws IOException
    {
        return getFalsePositive(uniqueId, null);
    }

    public FalsePositive getFalsePositive(String uniqueId, String ownerName) throws IOException
    {
        Map<String, Object> map = AbstractClientAdapter.createParamMap("id", uniqueId);
        ApiEntitySingleResponse item = getItem(getUrlBasePrefix() + ".byId.falsePositive", singleType, ownerName, map);

        return (FalsePositive) item.getData().getData();

    }
    
    @Override
    public IterableResponse<? extends Indicator> getAssociatedIndicatorsForCustomIndicators(String uniqueId, String associationType, String targetType)
            throws IOException, FailedResponseException {
    	return indAssocReader.getAssociatedIndicatorsForCustomIndicators(uniqueId, associationType, targetType);
    }
    @Override
    public IterableResponse<? extends Indicator> getAssociatedIndicatorsForCustomIndicators(String uniqueId, String associationType)
            throws IOException, FailedResponseException {
    	return indAssocReader.getAssociatedIndicatorsForCustomIndicators(uniqueId, associationType);
    }
	
}
