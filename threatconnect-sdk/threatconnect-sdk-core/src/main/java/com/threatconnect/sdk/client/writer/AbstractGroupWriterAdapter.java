package com.threatconnect.sdk.client.writer;

import java.io.IOException;
import java.util.List;

import com.threatconnect.sdk.client.UrlTypeable;
import com.threatconnect.sdk.client.response.WriteListResponse;
import com.threatconnect.sdk.client.writer.associate.AbstractAttributeAssociateWriterAdapter;
import com.threatconnect.sdk.client.writer.associate.AbstractGroupAssociateWriterAdapter;
import com.threatconnect.sdk.client.writer.associate.AbstractIndicatorAssociateWriterAdapter;
import com.threatconnect.sdk.client.writer.associate.AbstractSecurityLabelAssociateWriterAdapter;
import com.threatconnect.sdk.client.writer.associate.AbstractTagAssociateWriterAdapter;
import com.threatconnect.sdk.client.writer.associate.AbstractVictimAssetAssociateWriterAdapter;
import com.threatconnect.sdk.client.writer.associate.AbstractVictimAssociateWriterAdapter;
import com.threatconnect.sdk.client.writer.associate.AttributeAssociateWritable;
import com.threatconnect.sdk.client.writer.associate.GroupAssociateWritable;
import com.threatconnect.sdk.client.writer.associate.IndicatorAssociateWritable;
import com.threatconnect.sdk.client.writer.associate.SecurityLabelAssociateWritable;
import com.threatconnect.sdk.client.writer.associate.TagAssociateWritable;
import com.threatconnect.sdk.client.writer.associate.VictimAssetAssociateWritable;
import com.threatconnect.sdk.client.writer.associate.VictimAssociateWritable;
import com.threatconnect.sdk.conn.Connection;
import com.threatconnect.sdk.exception.FailedResponseException;
import com.threatconnect.sdk.server.entity.Attribute;
import com.threatconnect.sdk.server.entity.Group;
import com.threatconnect.sdk.server.response.entity.ApiEntitySingleResponse;

/**
 * 
 *
 * @author dtineo
 * @param <T> Parameter
 */
public abstract class AbstractGroupWriterAdapter<T extends Group>
    extends AbstractBaseWriterAdapter<T,Long> 
    implements UrlTypeable, GroupAssociateWritable<Long>, IndicatorAssociateWritable<Long>
             , AttributeAssociateWritable<Long>, VictimAssetAssociateWritable<Long>
             , TagAssociateWritable<Long>, SecurityLabelAssociateWritable<Long>
             , VictimAssociateWritable<Long> {

    // composite pattern
    private AbstractAttributeAssociateWriterAdapter<T,Long> attribWriter;
    private AbstractGroupAssociateWriterAdapter<T,Long> groupAssocWriter;
    private AbstractIndicatorAssociateWriterAdapter<T,Long> indAssocWriter;
    private AbstractSecurityLabelAssociateWriterAdapter<T,Long> secLabelAssocWriter;
    private AbstractTagAssociateWriterAdapter<T,Long> tagAssocWriter;
    private AbstractVictimAssetAssociateWriterAdapter<T,Long> victimAssetAssocWriter;
    private AbstractVictimAssociateWriterAdapter<T,Long> victimAssocWriter;

    /**
     * Package level constructor. Use the {@link WriterAdapterFactory} to access this object.
     * @param conn      Primary connection object to the ThreatConnect API
     * @param singleType Single type
     * 
     * @see WriterAdapterFactory
     */
    protected AbstractGroupWriterAdapter(Connection conn
                , Class<? extends ApiEntitySingleResponse> singleType ) {
        super(conn, singleType );

        initComposite();
    }

    private void initComposite() {
        attribWriter = new AbstractAttributeAssociateWriterAdapter<T,Long>(
                            AbstractGroupWriterAdapter.this.getConn()
                          , AbstractGroupWriterAdapter.this.singleType
            ) {
            @Override
            protected String getUrlBasePrefix() {
                return AbstractGroupWriterAdapter.this.getUrlBasePrefix();
            }

            @Override
            public Long getId(T item) {
                return AbstractGroupWriterAdapter.this.getId(item);
            }

            @Override
            public String getUrlType()
            {
                return AbstractGroupWriterAdapter.this.getUrlType();
            }

        };

        groupAssocWriter = new AbstractGroupAssociateWriterAdapter<T,Long>(
                            AbstractGroupWriterAdapter.this.getConn()
                          , AbstractGroupWriterAdapter.this.singleType
            ) {
            @Override
            protected String getUrlBasePrefix() {
                return AbstractGroupWriterAdapter.this.getUrlBasePrefix();
            }

            @Override
            public Long getId(T item) {
                return AbstractGroupWriterAdapter.this.getId(item);
            }

            @Override
            public String getUrlType()
            {
                return AbstractGroupWriterAdapter.this.getUrlType();
            }
        };

        indAssocWriter = new AbstractIndicatorAssociateWriterAdapter<T,Long>(
                            AbstractGroupWriterAdapter.this.getConn()
                          , AbstractGroupWriterAdapter.this.singleType
            ) {
            @Override
            protected String getUrlBasePrefix() {
                return AbstractGroupWriterAdapter.this.getUrlBasePrefix();
            }

            @Override
            public String getUrlType() {
                return AbstractGroupWriterAdapter.this.getUrlType();
            }

            @Override
            public Long getId(T item) {
                return AbstractGroupWriterAdapter.this.getId(item);
            }
        };

        secLabelAssocWriter = new AbstractSecurityLabelAssociateWriterAdapter<T,Long>(
                            AbstractGroupWriterAdapter.this.getConn()
                          , AbstractGroupWriterAdapter.this.singleType
            ) {
            @Override
            protected String getUrlBasePrefix() {
                return AbstractGroupWriterAdapter.this.getUrlBasePrefix();
            }

            @Override
            public Long getId(T item) {
                return AbstractGroupWriterAdapter.this.getId(item);
            }

            @Override
            public String getUrlType() {
                return AbstractGroupWriterAdapter.this.getUrlType();
            }
        };

        tagAssocWriter = new AbstractTagAssociateWriterAdapter<T,Long>(
                            AbstractGroupWriterAdapter.this.getConn()
                          , AbstractGroupWriterAdapter.this.singleType
            ) {
            @Override
            protected String getUrlBasePrefix() {
                return AbstractGroupWriterAdapter.this.getUrlBasePrefix();
            }

            @Override
            public String getUrlType() {
                return AbstractGroupWriterAdapter.this.getUrlType();
            }

            @Override
            public Long getId(T item) {
                return AbstractGroupWriterAdapter.this.getId(item);
            }
        };

        victimAssetAssocWriter = new AbstractVictimAssetAssociateWriterAdapter<T,Long>(
                            AbstractGroupWriterAdapter.this.getConn()
                          , AbstractGroupWriterAdapter.this.singleType
            ) {
            @Override
            protected String getUrlBasePrefix() {
                return AbstractGroupWriterAdapter.this.getUrlBasePrefix();
            }

            @Override
            public Long getId(T item) {
                return AbstractGroupWriterAdapter.this.getId(item);
            }

            @Override
            public String getUrlType() {
                return AbstractGroupWriterAdapter.this.getUrlType();
            }

        };
        
        victimAssocWriter = new AbstractVictimAssociateWriterAdapter<T,Long>(
                            AbstractGroupWriterAdapter.this.getConn()
                          , AbstractGroupWriterAdapter.this.singleType
            ) {

            @Override
            protected String getUrlBasePrefix()
            {
                return AbstractGroupWriterAdapter.this.getUrlBasePrefix();
            }

            @Override
            public Long getId(T item)
            {
                return AbstractGroupWriterAdapter.this.getId(item);
            }

            @Override
            public String getUrlType()
            {
                return AbstractGroupWriterAdapter.this.getUrlType();
            }
        };

    }

    @Override
    protected String getUrlBasePrefix() {
        return "v2.groups.type";
    }

    // Groups have a standard "id" field
    @Override
    public Long getId(T item) {
        return item.getId();
    }

    @Override
    public WriteListResponse<Long> associateGroupAdversaries(Long uniqueId, List<Long> adversaryIds) throws IOException {
        return groupAssocWriter.associateGroupAdversaries(uniqueId, adversaryIds);
    }

    @Override
    public WriteListResponse<Long> associateGroupAdversaries(Long uniqueId, List<Long> adversaryIds, String ownerName) throws IOException {
        return groupAssocWriter.associateGroupAdversaries(uniqueId, adversaryIds, ownerName);
    }

    @Override
    public ApiEntitySingleResponse associateGroupAdversary(Long uniqueId, Long adversaryId) throws IOException, FailedResponseException {
        return groupAssocWriter.associateGroupAdversary(uniqueId, adversaryId);
    }

    @Override
    public ApiEntitySingleResponse associateGroupAdversary(Long uniqueId, Long adversaryId, String ownerName) throws IOException, FailedResponseException {
        return groupAssocWriter.associateGroupAdversary(uniqueId, adversaryId, ownerName);
    }
    
    @Override
    public ApiEntitySingleResponse associateGroupDocument(Long uniqueId, Long adversaryId) throws IOException, FailedResponseException {
        return groupAssocWriter.associateGroupDocument(uniqueId, adversaryId);
    }
    
    @Override
    public ApiEntitySingleResponse associateGroupDocument(Long uniqueId, Long adversaryId, String ownerName) throws IOException, FailedResponseException {
        return groupAssocWriter.associateGroupDocument(uniqueId, adversaryId, ownerName);
    }

    @Override
    public WriteListResponse<Long> associateGroupEmails(Long uniqueId, List<Long> emailIds) throws IOException {
        return groupAssocWriter.associateGroupEmails(uniqueId, emailIds);
    }

    @Override
    public WriteListResponse<Long> associateGroupEmails(Long uniqueId, List<Long> emailIds, String ownerName) throws IOException {
        return groupAssocWriter.associateGroupEmails(uniqueId, emailIds, ownerName);
    }

    @Override
    public ApiEntitySingleResponse associateGroupEmail(Long uniqueId, Long emailId) throws IOException, FailedResponseException {
        return groupAssocWriter.associateGroupEmail(uniqueId, emailId);
    }

    @Override
    public ApiEntitySingleResponse associateGroupEmail(Long uniqueId, Long emailId, String ownerName) throws IOException, FailedResponseException {
        return groupAssocWriter.associateGroupEmail(uniqueId, emailId, ownerName);
    }

    @Override
    public WriteListResponse<Long> associateGroupIncidents(Long uniqueId, List<Long> incidentIds) throws IOException {
        return groupAssocWriter.associateGroupIncidents(uniqueId, incidentIds);
    }

    @Override
    public WriteListResponse<Long> associateGroupIncidents(Long uniqueId, List<Long> incidentIds, String ownerName) throws IOException {
        return groupAssocWriter.associateGroupIncidents(uniqueId, incidentIds, ownerName);
    }

    @Override
    public ApiEntitySingleResponse associateGroupIncident(Long uniqueId, Long incidentId) throws IOException, FailedResponseException {
        return groupAssocWriter.associateGroupIncident(uniqueId, incidentId);
    }

    @Override
    public ApiEntitySingleResponse associateGroupIncident(Long uniqueId, Long incidentId, String ownerName) throws IOException, FailedResponseException {
        return groupAssocWriter.associateGroupIncident(uniqueId, incidentId, ownerName);
    }

    @Override
    public WriteListResponse<Long> associateGroupSignatures(Long uniqueId, List<Long> signatureIds) throws IOException {
        return groupAssocWriter.associateGroupSignatures(uniqueId, signatureIds);
    }

    @Override
    public WriteListResponse<Long> associateGroupSignatures(Long uniqueId, List<Long> signatureIds, String ownerName) throws IOException {
        return groupAssocWriter.associateGroupSignatures(uniqueId, signatureIds, ownerName);
    }

    @Override
    public ApiEntitySingleResponse associateGroupSignature(Long uniqueId, Long signatureId) throws IOException, FailedResponseException {
        return groupAssocWriter.associateGroupSignature(uniqueId, signatureId);
    }

    @Override
    public ApiEntitySingleResponse associateGroupSignature(Long uniqueId, Long signatureId, String ownerName) throws IOException, FailedResponseException {
        return groupAssocWriter.associateGroupSignature(uniqueId, signatureId, ownerName);
    }

    @Override
    public WriteListResponse<Long> associateGroupThreats(Long uniqueId, List<Long> threatIds) throws IOException {
        return groupAssocWriter.associateGroupThreats(uniqueId, threatIds);
    }

    @Override
    public WriteListResponse<Long> associateGroupThreats(Long uniqueId, List<Long> threatIds, String ownerName) throws IOException {
        return groupAssocWriter.associateGroupThreats(uniqueId, threatIds, ownerName);
    }

    @Override
    public ApiEntitySingleResponse associateGroupThreat(Long uniqueId, Long threatId) throws IOException, FailedResponseException {
        return groupAssocWriter.associateGroupThreat(uniqueId, threatId);
    }

    @Override
    public ApiEntitySingleResponse associateGroupThreat(Long uniqueId, Long threatId, String ownerName) throws IOException, FailedResponseException {
        return groupAssocWriter.associateGroupThreat(uniqueId, threatId, ownerName);
    }

    @Override
    public WriteListResponse<String> associateIndicatorAddresses(Long uniqueId, List<String> ipAddresses) throws IOException {
        return indAssocWriter.associateIndicatorAddresses(uniqueId, ipAddresses);
    }

    @Override
    public WriteListResponse<String> associateIndicatorAddresses(Long uniqueId, List<String> ipAddresses, String ownerName) throws IOException {
        return indAssocWriter.associateIndicatorAddresses(uniqueId, ipAddresses, ownerName);
    }

    @Override
    public ApiEntitySingleResponse associateIndicatorAddress(Long uniqueId, String ipAddress) throws IOException, FailedResponseException {
        return indAssocWriter.associateIndicatorAddress(uniqueId, ipAddress);
    }

    @Override
    public ApiEntitySingleResponse associateIndicatorAddress(Long uniqueId, String ipAddress, String ownerName) throws IOException, FailedResponseException {
        return indAssocWriter.associateIndicatorAddress(uniqueId, ipAddress, ownerName);
    }

    @Override
    public WriteListResponse<String> associateIndicatorEmailAddresses(Long uniqueId, List<String> emailAddresses) throws IOException {
        return indAssocWriter.associateIndicatorEmailAddresses(uniqueId, emailAddresses);
    }

    @Override
    public WriteListResponse<String> associateIndicatorEmailAddresses(Long uniqueId, List<String> emailAddresses, String ownerName) throws IOException {
        return indAssocWriter.associateIndicatorEmailAddresses(uniqueId, emailAddresses, ownerName);
    }

    @Override
    public ApiEntitySingleResponse associateIndicatorEmailAddress(Long uniqueId, String emailAddress) throws IOException, FailedResponseException {
        return indAssocWriter.associateIndicatorEmailAddress(uniqueId, emailAddress);
    }

    @Override
    public ApiEntitySingleResponse associateIndicatorEmailAddress(Long uniqueId, String emailAddress, String ownerName) throws IOException, FailedResponseException {
        return indAssocWriter.associateIndicatorEmailAddress(uniqueId, emailAddress, ownerName);
    }

    @Override
    public WriteListResponse<String> associateIndicatorFiles(Long uniqueId, List<String> fileHashes) throws IOException {
        return indAssocWriter.associateIndicatorFiles(uniqueId, fileHashes);
    }

    @Override
    public WriteListResponse<String> associateIndicatorFiles(Long uniqueId, List<String> fileHashes, String ownerName) throws IOException {
        return indAssocWriter.associateIndicatorFiles(uniqueId, fileHashes, ownerName);
    }

    @Override
    public ApiEntitySingleResponse associateIndicatorFile(Long uniqueId, String fileHash) throws IOException, FailedResponseException {
        return indAssocWriter.associateIndicatorFile(uniqueId, fileHash);
    }

    @Override
    public ApiEntitySingleResponse associateIndicatorFile(Long uniqueId, String fileHash, String ownerName) throws IOException, FailedResponseException {
        return indAssocWriter.associateIndicatorFile(uniqueId, fileHash, ownerName);
    }

    @Override
    public WriteListResponse<String> associateIndicatorHosts(Long uniqueId, List<String> hostNames) throws IOException {
        return indAssocWriter.associateIndicatorHosts(uniqueId, hostNames);
    }

    @Override
    public WriteListResponse<String> associateIndicatorHosts(Long uniqueId, List<String> hostNames, String ownerName) throws IOException {
        return indAssocWriter.associateIndicatorHosts(uniqueId, hostNames, ownerName);
    }

    @Override
    public ApiEntitySingleResponse associateIndicatorHost(Long uniqueId, String hostName) throws IOException, FailedResponseException {
        return indAssocWriter.associateIndicatorHost(uniqueId, hostName);
    }

    @Override
    public ApiEntitySingleResponse associateIndicatorHost(Long uniqueId, String hostName, String ownerName) throws IOException, FailedResponseException {
        return indAssocWriter.associateIndicatorHost(uniqueId, hostName, ownerName);
    }

    @Override
    public WriteListResponse<String> associateIndicatorUrls(Long uniqueId, List<String> urlTexts) throws IOException {
        return indAssocWriter.associateIndicatorUrls(uniqueId, urlTexts);
    }

    @Override
    public WriteListResponse<String> associateIndicatorUrls(Long uniqueId, List<String> urlTexts, String ownerName) throws IOException {
        return indAssocWriter.associateIndicatorUrls(uniqueId, urlTexts, ownerName);
    }

    @Override
    public ApiEntitySingleResponse associateIndicatorUrl(Long uniqueId, String urlText) throws IOException, FailedResponseException {
        return indAssocWriter.associateIndicatorUrl(uniqueId, urlText);
    }

    @Override
    public ApiEntitySingleResponse associateIndicatorUrl(Long uniqueId, String urlText, String ownerName) throws IOException, FailedResponseException {
        return indAssocWriter.associateIndicatorUrl(uniqueId, urlText, ownerName);
    }

    @Override
    public WriteListResponse<Attribute> addAttributes(Long uniqueId, List<Attribute> attributes) throws IOException {
        return attribWriter.addAttributes(uniqueId, attributes);
    }

    @Override
    public WriteListResponse<Attribute> addAttributes(Long uniqueId, List<Attribute> attribute, String ownerName) throws IOException {
        return attribWriter.addAttributes(uniqueId, attribute, ownerName);
    }

    @Override
    public ApiEntitySingleResponse addAttribute(Long uniqueId, Attribute attribute) throws IOException, FailedResponseException {
        return attribWriter.addAttribute(uniqueId, attribute);
    }

    @Override
    public ApiEntitySingleResponse addAttribute(Long uniqueId, Attribute attribute, String ownerName) throws IOException, FailedResponseException {
        return attribWriter.addAttribute(uniqueId, attribute, ownerName);
    }

    @Override
    public WriteListResponse<String> addAttributeSecurityLabels(Long uniqueId, Long attributeId, List<String> securityLabels) throws IOException {
        return attribWriter.addAttributeSecurityLabels(uniqueId, attributeId, securityLabels);
    }

    @Override
    public WriteListResponse<String> addAttributeSecurityLabels(Long uniqueId, Long attributeId, List<String> securityLabels, String ownerName) throws IOException {
        return attribWriter.addAttributeSecurityLabels(uniqueId, attributeId, securityLabels, ownerName);
    }

    @Override
    public ApiEntitySingleResponse addAttributeSecurityLabel(Long uniqueId, Long attributeId, String securityLabel) throws IOException, FailedResponseException {
        return attribWriter.addAttributeSecurityLabel(uniqueId, attributeId, securityLabel);
    }

    @Override
    public ApiEntitySingleResponse addAttributeSecurityLabel(Long uniqueId, Long attributeId, String securityLabel, String ownerName) throws IOException, FailedResponseException {
        return attribWriter.addAttributeSecurityLabel(uniqueId, attributeId, securityLabel, ownerName);
    }

    @Override
    public WriteListResponse<Long> associateVictimAssetEmailAddresses(Long uniqueId, List<Long> assetIds) throws IOException {
        return victimAssetAssocWriter.associateVictimAssetEmailAddresses(uniqueId, assetIds);
    }

    @Override
    public WriteListResponse<Long> associateVictimAssetEmailAddresses(Long uniqueId, List<Long> assetIds, String ownerName) throws IOException {
        return victimAssetAssocWriter. associateVictimAssetEmailAddresses(uniqueId, assetIds, ownerName);
    }

    @Override
    public ApiEntitySingleResponse associateVictimAssetEmailAddress(Long uniqueId, Long assetId) throws IOException, FailedResponseException {
        return victimAssetAssocWriter.associateVictimAssetEmailAddress(uniqueId, assetId);
    }

    @Override
    public ApiEntitySingleResponse associateVictimAssetEmailAddress(Long uniqueId, Long assetId, String ownerName) throws IOException, FailedResponseException {
        return victimAssetAssocWriter.associateVictimAssetEmailAddress(uniqueId, assetId, ownerName);
    }

    @Override
    public WriteListResponse<Long> associateVictimAssetNetworkAccounts(Long uniqueId, List<Long> assetIds) throws IOException {
        return victimAssetAssocWriter.associateVictimAssetNetworkAccounts(uniqueId, assetIds);
    }

    @Override
    public WriteListResponse<Long> associateVictimAssetNetworkAccounts(Long uniqueId, List<Long> assetIds, String ownerName) throws IOException {
        return victimAssetAssocWriter.associateVictimAssetNetworkAccounts(uniqueId, assetIds, ownerName);
    }

    @Override
    public ApiEntitySingleResponse associateVictimAssetNetworkAccount(Long uniqueId, Long assetId) throws IOException, FailedResponseException {
        return victimAssetAssocWriter.associateVictimAssetNetworkAccount(uniqueId, assetId);
    }

    @Override
    public ApiEntitySingleResponse associateVictimAssetNetworkAccount(Long uniqueId, Long assetId, String ownerName) throws IOException, FailedResponseException {
        return victimAssetAssocWriter.associateVictimAssetNetworkAccount(uniqueId, assetId, ownerName);
    }

    @Override
    public WriteListResponse<Long> associateVictimAssetPhoneNumbers(Long uniqueId, List<Long> assetIds) throws IOException {
        return victimAssetAssocWriter.associateVictimAssetPhoneNumbers(uniqueId, assetIds);
    }

    @Override
    public WriteListResponse<Long> associateVictimAssetPhoneNumbers(Long uniqueId, List<Long> assetIds, String ownerName) throws IOException {
        return victimAssetAssocWriter.associateVictimAssetPhoneNumbers(uniqueId, assetIds, ownerName);
    }

    @Override
    public ApiEntitySingleResponse associateVictimAssetPhoneNumber(Long uniqueId, Long assetId) throws IOException, FailedResponseException {
        return victimAssetAssocWriter.associateVictimAssetPhoneNumber(uniqueId, assetId);
    }

    @Override
    public ApiEntitySingleResponse associateVictimAssetPhoneNumber(Long uniqueId, Long assetId, String ownerName) throws IOException, FailedResponseException {
        return victimAssetAssocWriter.associateVictimAssetPhoneNumber(uniqueId, assetId, ownerName);
    }

    @Override
    public WriteListResponse<Long> associateVictimAssetSocialNetworks(Long uniqueId, List<Long> assetIds) throws IOException, FailedResponseException {
        return victimAssetAssocWriter.associateVictimAssetSocialNetworks(uniqueId, assetIds);
    }

    @Override
    public WriteListResponse<Long> associateVictimAssetSocialNetworks(Long uniqueId, List<Long> assetIds, String ownerName) throws IOException {
        return victimAssetAssocWriter.associateVictimAssetSocialNetworks(uniqueId, assetIds, ownerName);
    }

    @Override
    public ApiEntitySingleResponse associateVictimAssetSocialNetwork(Long uniqueId, Long assetId) throws IOException, FailedResponseException {
        return victimAssetAssocWriter.associateVictimAssetSocialNetwork(uniqueId, assetId);
    }

    @Override
    public ApiEntitySingleResponse associateVictimAssetSocialNetwork(Long uniqueId, Long assetId, String ownerName) throws IOException, FailedResponseException {
        return victimAssetAssocWriter.associateVictimAssetSocialNetwork(uniqueId, assetId, ownerName);
    }

    @Override
    public WriteListResponse<Long> associateVictimAssetWebsites(Long uniqueId, List<Long> assetIds) throws IOException {
        return victimAssetAssocWriter.associateVictimAssetWebsites(uniqueId, assetIds);
    }

    @Override
    public WriteListResponse<Long> associateVictimAssetWebsites(Long uniqueId, List<Long> assetIds, String ownerName) throws IOException {
        return victimAssetAssocWriter.associateVictimAssetWebsites(uniqueId, assetIds, ownerName);
    }

    @Override
    public ApiEntitySingleResponse associateVictimAssetWebsite(Long uniqueId, Long assetId) throws IOException, FailedResponseException {
        return victimAssetAssocWriter.associateVictimAssetWebsite(uniqueId, assetId);
    }

    @Override
    public ApiEntitySingleResponse associateVictimAssetWebsite(Long uniqueId, Long assetId, String ownerName) throws IOException, FailedResponseException {
        return victimAssetAssocWriter.associateVictimAssetWebsite(uniqueId, assetId, ownerName);
    }

    @Override
    public WriteListResponse<String> associateTags(Long uniqueId, List<String> tagNames) throws IOException {
        return tagAssocWriter.associateTags(uniqueId, tagNames);
    }

    @Override
    public WriteListResponse<String> associateTags(Long uniqueId, List<String> tagNames, String ownerName) throws IOException {
        return tagAssocWriter.associateTags(uniqueId, tagNames, ownerName);
    }

    @Override
    public ApiEntitySingleResponse associateTag(Long uniqueId, String tagName) throws IOException, FailedResponseException {
        return tagAssocWriter.associateTag(uniqueId, tagName);
    }

    @Override
    public ApiEntitySingleResponse associateTag(Long uniqueId, String tagName, String ownerName) throws IOException, FailedResponseException {
        return tagAssocWriter.associateTag(uniqueId, tagName, ownerName);
    }

    @Override
    public WriteListResponse<String> associateSecurityLabels(Long uniqueId, List<String> securityLabels) throws IOException {
        return secLabelAssocWriter.associateSecurityLabels(uniqueId, securityLabels);
    }

    @Override
    public WriteListResponse<String> associateSecurityLabels(Long uniqueId, List<String> securityLabels, String ownerName) throws IOException {
        return secLabelAssocWriter.associateSecurityLabels(uniqueId, securityLabels, ownerName);
    }

    @Override
    public ApiEntitySingleResponse associateSecurityLabel(Long uniqueId, String securityLabel) throws IOException, FailedResponseException {
        return secLabelAssocWriter.associateSecurityLabel(uniqueId, securityLabel);
    }

    @Override
    public ApiEntitySingleResponse associateSecurityLabel(Long uniqueId, String securityLabel, String ownerName) throws IOException, FailedResponseException {
        return secLabelAssocWriter.associateSecurityLabel(uniqueId, securityLabel, ownerName);
    }

        @Override
    public WriteListResponse<Long> dissociateGroupAdversaries(Long uniqueId, List<Long> adversaryIds) throws IOException {
        return groupAssocWriter.dissociateGroupAdversaries(uniqueId, adversaryIds);
    }

    @Override
    public WriteListResponse<Long> dissociateGroupAdversaries(Long uniqueId, List<Long> adversaryIds, String ownerName) throws IOException {
        return groupAssocWriter.dissociateGroupAdversaries(uniqueId, adversaryIds, ownerName);
    }

    @Override
    public ApiEntitySingleResponse dissociateGroupAdversary(Long uniqueId, Long adversaryId) throws IOException, FailedResponseException {
        return groupAssocWriter.dissociateGroupAdversary(uniqueId, adversaryId);
    }

    @Override
    public ApiEntitySingleResponse dissociateGroupAdversary(Long uniqueId, Long adversaryId, String ownerName) throws IOException, FailedResponseException {
        return groupAssocWriter.dissociateGroupAdversary(uniqueId, adversaryId, ownerName);
    }

    @Override
    public WriteListResponse<Long> dissociateGroupEmails(Long uniqueId, List<Long> emailIds) throws IOException {
        return groupAssocWriter.dissociateGroupEmails(uniqueId, emailIds);
    }

    @Override
    public WriteListResponse<Long> dissociateGroupEmails(Long uniqueId, List<Long> emailIds, String ownerName) throws IOException {
        return groupAssocWriter.dissociateGroupEmails(uniqueId, emailIds, ownerName);
    }

    @Override
    public ApiEntitySingleResponse dissociateGroupEmail(Long uniqueId, Long emailId) throws IOException, FailedResponseException {
        return groupAssocWriter.dissociateGroupEmail(uniqueId, emailId);
    }

    @Override
    public ApiEntitySingleResponse dissociateGroupEmail(Long uniqueId, Long emailId, String ownerName) throws IOException, FailedResponseException {
        return groupAssocWriter.dissociateGroupEmail(uniqueId, emailId, ownerName);
    }

    @Override
    public WriteListResponse<Long> dissociateGroupIncidents(Long uniqueId, List<Long> incidentIds) throws IOException {
        return groupAssocWriter.dissociateGroupIncidents(uniqueId, incidentIds);
    }

    @Override
    public WriteListResponse<Long> dissociateGroupIncidents(Long uniqueId, List<Long> incidentIds, String ownerName) throws IOException {
        return groupAssocWriter.dissociateGroupIncidents(uniqueId, incidentIds, ownerName);
    }

    @Override
    public ApiEntitySingleResponse dissociateGroupIncident(Long uniqueId, Long incidentId) throws IOException, FailedResponseException {
        return groupAssocWriter.dissociateGroupIncident(uniqueId, incidentId);
    }

    @Override
    public ApiEntitySingleResponse dissociateGroupIncident(Long uniqueId, Long incidentId, String ownerName) throws IOException, FailedResponseException {
        return groupAssocWriter.dissociateGroupIncident(uniqueId, incidentId, ownerName);
    }

    @Override
    public WriteListResponse<Long> dissociateGroupSignatures(Long uniqueId, List<Long> signatureIds) throws IOException {
        return groupAssocWriter.dissociateGroupSignatures(uniqueId, signatureIds);
    }

    @Override
    public WriteListResponse<Long> dissociateGroupSignatures(Long uniqueId, List<Long> signatureIds, String ownerName) throws IOException {
        return groupAssocWriter.dissociateGroupSignatures(uniqueId, signatureIds, ownerName);
    }

    @Override
    public ApiEntitySingleResponse dissociateGroupSignature(Long uniqueId, Long signatureId) throws IOException, FailedResponseException {
        return groupAssocWriter.dissociateGroupSignature(uniqueId, signatureId);
    }

    @Override
    public ApiEntitySingleResponse dissociateGroupSignature(Long uniqueId, Long signatureId, String ownerName) throws IOException, FailedResponseException {
        return groupAssocWriter.dissociateGroupSignature(uniqueId, signatureId, ownerName);
    }

    @Override
    public WriteListResponse<Long> dissociateGroupThreats(Long uniqueId, List<Long> threatIds) throws IOException {
        return groupAssocWriter.dissociateGroupThreats(uniqueId, threatIds);
    }

    @Override
    public WriteListResponse<Long> dissociateGroupThreats(Long uniqueId, List<Long> threatIds, String ownerName) throws IOException {
        return groupAssocWriter.dissociateGroupThreats(uniqueId, threatIds, ownerName);
    }

    @Override
    public ApiEntitySingleResponse dissociateGroupThreat(Long uniqueId, Long threatId) throws IOException, FailedResponseException {
        return groupAssocWriter.dissociateGroupThreat(uniqueId, threatId);
    }

    @Override
    public ApiEntitySingleResponse dissociateGroupThreat(Long uniqueId, Long threatId, String ownerName) throws IOException, FailedResponseException {
        return groupAssocWriter.dissociateGroupThreat(uniqueId, threatId, ownerName);
    }

    @Override
    public WriteListResponse<String> dissociateIndicatorAddresses(Long uniqueId, List<String> ipAddresses) throws IOException {
        return indAssocWriter.dissociateIndicatorAddresses(uniqueId, ipAddresses);
    }

    @Override
    public WriteListResponse<String> dissociateIndicatorAddresses(Long uniqueId, List<String> ipAddresses, String ownerName) throws IOException {
        return indAssocWriter.dissociateIndicatorAddresses(uniqueId, ipAddresses, ownerName);
    }

    @Override
    public ApiEntitySingleResponse dissociateIndicatorAddress(Long uniqueId, String ipAddress) throws IOException, FailedResponseException {
        return indAssocWriter.dissociateIndicatorAddress(uniqueId, ipAddress);
    }

    @Override
    public ApiEntitySingleResponse dissociateIndicatorAddress(Long uniqueId, String ipAddress, String ownerName) throws IOException, FailedResponseException {
        return indAssocWriter.dissociateIndicatorAddress(uniqueId, ipAddress, ownerName);
    }

    @Override
    public WriteListResponse<String> dissociateIndicatorEmailAddresses(Long uniqueId, List<String> emailAddresses) throws IOException {
        return indAssocWriter.dissociateIndicatorEmailAddresses(uniqueId, emailAddresses);
    }

    @Override
    public WriteListResponse<String> dissociateIndicatorEmailAddresses(Long uniqueId, List<String> emailAddresses, String ownerName) throws IOException {
        return indAssocWriter.dissociateIndicatorEmailAddresses(uniqueId, emailAddresses, ownerName);
    }

    @Override
    public ApiEntitySingleResponse dissociateIndicatorEmailAddress(Long uniqueId, String emailAddress) throws IOException, FailedResponseException {
        return indAssocWriter.dissociateIndicatorEmailAddress(uniqueId, emailAddress);
    }

    @Override
    public ApiEntitySingleResponse dissociateIndicatorEmailAddress(Long uniqueId, String emailAddress, String ownerName) throws IOException, FailedResponseException {
        return indAssocWriter.dissociateIndicatorEmailAddress(uniqueId, emailAddress, ownerName);
    }

    @Override
    public WriteListResponse<String> dissociateIndicatorFiles(Long uniqueId, List<String> fileHashes) throws IOException {
        return indAssocWriter.dissociateIndicatorFiles(uniqueId, fileHashes);
    }

    @Override
    public WriteListResponse<String> dissociateIndicatorFiles(Long uniqueId, List<String> fileHashes, String ownerName) throws IOException {
        return indAssocWriter.dissociateIndicatorFiles(uniqueId, fileHashes, ownerName);
    }

    @Override
    public ApiEntitySingleResponse dissociateIndicatorFile(Long uniqueId, String fileHash) throws IOException, FailedResponseException {
        return indAssocWriter.dissociateIndicatorFile(uniqueId, fileHash);
    }

    @Override
    public ApiEntitySingleResponse dissociateIndicatorFile(Long uniqueId, String fileHash, String ownerName) throws IOException, FailedResponseException {
        return indAssocWriter.dissociateIndicatorFile(uniqueId, fileHash, ownerName);
    }

    @Override
    public WriteListResponse<String> dissociateIndicatorHosts(Long uniqueId, List<String> hostNames) throws IOException {
        return indAssocWriter.dissociateIndicatorHosts(uniqueId, hostNames);
    }

    @Override
    public WriteListResponse<String> dissociateIndicatorHosts(Long uniqueId, List<String> hostNames, String ownerName) throws IOException {
        return indAssocWriter.dissociateIndicatorHosts(uniqueId, hostNames, ownerName);
    }

    @Override
    public ApiEntitySingleResponse dissociateIndicatorHost(Long uniqueId, String hostName) throws IOException, FailedResponseException {
        return indAssocWriter.dissociateIndicatorHost(uniqueId, hostName);
    }

    @Override
    public ApiEntitySingleResponse dissociateIndicatorHost(Long uniqueId, String hostName, String ownerName) throws IOException, FailedResponseException {
        return indAssocWriter.dissociateIndicatorHost(uniqueId, hostName, ownerName);
    }

    @Override
    public WriteListResponse<String> dissociateIndicatorUrls(Long uniqueId, List<String> urlTexts) throws IOException {
        return indAssocWriter.dissociateIndicatorUrls(uniqueId, urlTexts);
    }

    @Override
    public WriteListResponse<String> dissociateIndicatorUrls(Long uniqueId, List<String> urlTexts, String ownerName) throws IOException {
        return indAssocWriter.dissociateIndicatorUrls(uniqueId, urlTexts, ownerName);
    }

    @Override
    public ApiEntitySingleResponse dissociateIndicatorUrl(Long uniqueId, String urlText) throws IOException, FailedResponseException {
        return indAssocWriter.dissociateIndicatorUrl(uniqueId, urlText);
    }

    @Override
    public ApiEntitySingleResponse dissociateIndicatorUrl(Long uniqueId, String urlText, String ownerName) throws IOException, FailedResponseException {
        return indAssocWriter.dissociateIndicatorUrl(uniqueId, urlText, ownerName);
    }

    @Override
    public WriteListResponse<Attribute> updateAttributes(Long uniqueId, List<Attribute> attributes) throws IOException {
        return attribWriter.updateAttributes(uniqueId, attributes);
    }

    @Override
    public WriteListResponse<Attribute> updateAttributes(Long uniqueId, List<Attribute> attribute, String ownerName) throws IOException {
        return attribWriter.updateAttributes(uniqueId, attribute, ownerName);
    }

    @Override
    public ApiEntitySingleResponse updateAttribute(Long uniqueId, Attribute attribute) throws IOException, FailedResponseException {
        return attribWriter.updateAttribute(uniqueId, attribute);
    }

    @Override
    public ApiEntitySingleResponse updateAttribute(Long uniqueId, Attribute attribute, String ownerName) throws IOException, FailedResponseException {
        return attribWriter.updateAttribute(uniqueId, attribute, ownerName);
    }

    @Override
    public WriteListResponse<Long> deleteAttributes(Long uniqueId, List<Long> attributes) throws IOException {
        return attribWriter.deleteAttributes(uniqueId, attributes);
    }

    @Override
    public WriteListResponse<Long> deleteAttributes(Long uniqueId, List<Long> attribute, String ownerName) throws IOException {
        return attribWriter.deleteAttributes(uniqueId, attribute, ownerName);
    }

    @Override
    public ApiEntitySingleResponse deleteAttribute(Long uniqueId, Long attribute) throws IOException, FailedResponseException {
        return attribWriter.deleteAttribute(uniqueId, attribute);
    }

    @Override
    public ApiEntitySingleResponse deleteAttribute(Long uniqueId, Long attribute, String ownerName) throws IOException, FailedResponseException {
        return attribWriter.deleteAttribute(uniqueId, attribute, ownerName);
    }

    @Override
    public WriteListResponse<String> deleteAttributeSecurityLabels(Long uniqueId, Long attributeId, List<String> securityLabels) throws IOException, FailedResponseException {
        return attribWriter.deleteAttributeSecurityLabels(uniqueId, attributeId, securityLabels);
    }

    @Override
    public WriteListResponse<String> deleteAttributeSecurityLabels(Long uniqueId, Long attributeId, List<String> securityLabels, String ownerName) throws IOException, FailedResponseException {
       return attribWriter.deleteAttributeSecurityLabels(uniqueId, attributeId, securityLabels, ownerName);
    }

    @Override
    public ApiEntitySingleResponse deleteAttributeSecurityLabel(Long uniqueId, Long attributeId, String securityLabel) throws IOException, FailedResponseException {
        return attribWriter.deleteAttributeSecurityLabel(uniqueId, attributeId, securityLabel);
    }

    @Override
    public ApiEntitySingleResponse deleteAttributeSecurityLabel(Long uniqueId, Long attributeId, String securityLabel, String ownerName) throws IOException, FailedResponseException {
        return attribWriter.deleteAttributeSecurityLabel(uniqueId, attributeId, securityLabel, ownerName);
    }

    @Override
    public WriteListResponse<Long> dissociateVictimAssetEmailAddresses(Long uniqueId, List<Long> assetIds) throws IOException {
        return victimAssetAssocWriter.dissociateVictimAssetEmailAddresses(uniqueId, assetIds);
    }

    @Override
    public WriteListResponse<Long> dissociateVictimAssetEmailAddresses(Long uniqueId, List<Long> assetIds, String ownerName) throws IOException {
        return victimAssetAssocWriter.dissociateVictimAssetEmailAddresses(uniqueId, assetIds, ownerName);
    }

    @Override
    public ApiEntitySingleResponse dissociateVictimAssetEmailAddress(Long uniqueId, Long assetId) throws IOException, FailedResponseException {
        return victimAssetAssocWriter.dissociateVictimAssetEmailAddress(uniqueId, assetId);
    }

    @Override
    public ApiEntitySingleResponse dissociateVictimAssetEmailAddress(Long uniqueId, Long assetId, String ownerName) throws IOException, FailedResponseException {
        return victimAssetAssocWriter.dissociateVictimAssetEmailAddress(uniqueId, assetId, ownerName);
    }

    @Override
    public WriteListResponse<Long> dissociateVictimAssetNetworkAccounts(Long uniqueId, List<Long> assetIds) throws IOException {
        return victimAssetAssocWriter.dissociateVictimAssetNetworkAccounts(uniqueId, assetIds);
    }

    @Override
    public WriteListResponse<Long> dissociateVictimAssetNetworkAccounts(Long uniqueId, List<Long> assetIds, String ownerName) throws IOException {
        return victimAssetAssocWriter.dissociateVictimAssetNetworkAccounts(uniqueId, assetIds, ownerName);
    }

    @Override
    public ApiEntitySingleResponse dissociateVictimAssetNetworkAccount(Long uniqueId, Long assetId) throws IOException, FailedResponseException {
        return victimAssetAssocWriter.associateVictimAssetNetworkAccount(uniqueId, assetId);
    }

    @Override
    public ApiEntitySingleResponse dissociateVictimAssetNetworkAccount(Long uniqueId, Long assetId, String ownerName) throws IOException, FailedResponseException {
        return victimAssetAssocWriter.dissociateVictimAssetNetworkAccount(uniqueId, assetId, ownerName);
    }

    @Override
    public WriteListResponse<Long> dissociateVictimAssetPhoneNumbers(Long uniqueId, List<Long> assetIds) throws IOException {
        return victimAssetAssocWriter.dissociateVictimAssetPhoneNumbers(uniqueId, assetIds);
    }

    @Override
    public WriteListResponse<Long> dissociateVictimAssetPhoneNumbers(Long uniqueId, List<Long> assetIds, String ownerName) throws IOException {
        return victimAssetAssocWriter.dissociateVictimAssetPhoneNumbers(uniqueId, assetIds, ownerName);
    }

    @Override
    public ApiEntitySingleResponse dissociateVictimAssetPhoneNumber(Long uniqueId, Long assetId) throws IOException, FailedResponseException {
        return victimAssetAssocWriter.dissociateVictimAssetPhoneNumber(uniqueId, assetId);
    }

    @Override
    public ApiEntitySingleResponse dissociateVictimAssetPhoneNumber(Long uniqueId, Long assetId, String ownerName) throws IOException, FailedResponseException {
        return victimAssetAssocWriter.dissociateVictimAssetPhoneNumber(uniqueId, assetId, ownerName);
    }

    @Override
    public WriteListResponse<Long> dissociateVictimAssetSocialNetworks(Long uniqueId, List<Long> assetIds) throws IOException, FailedResponseException {
        return victimAssetAssocWriter.dissociateVictimAssetSocialNetworks(uniqueId, assetIds);
    }

    @Override
    public WriteListResponse<Long> dissociateVictimAssetSocialNetworks(Long uniqueId, List<Long> assetIds, String ownerName) throws IOException {
        return victimAssetAssocWriter.dissociateVictimAssetSocialNetworks(uniqueId, assetIds, ownerName);
    }

    @Override
    public ApiEntitySingleResponse dissociateVictimAssetSocialNetwork(Long uniqueId, Long assetId) throws IOException, FailedResponseException {
        return victimAssetAssocWriter.dissociateVictimAssetSocialNetwork(uniqueId, assetId);
    }

    @Override
    public ApiEntitySingleResponse dissociateVictimAssetSocialNetwork(Long uniqueId, Long assetId, String ownerName) throws IOException, FailedResponseException {
        return victimAssetAssocWriter.dissociateVictimAssetSocialNetwork(uniqueId, assetId, ownerName);
    }

    @Override
    public WriteListResponse<Long> dissociateVictimAssetWebsites(Long uniqueId, List<Long> assetIds) throws IOException {
        return victimAssetAssocWriter.dissociateVictimAssetWebsites(uniqueId, assetIds);
    }

    @Override
    public WriteListResponse<Long> dissociateVictimAssetWebsites(Long uniqueId, List<Long> assetIds, String ownerName) throws IOException {
        return victimAssetAssocWriter.dissociateVictimAssetWebsites(uniqueId, assetIds, ownerName);
    }

    @Override
    public ApiEntitySingleResponse dissociateVictimAssetWebsite(Long uniqueId, Long assetId) throws IOException, FailedResponseException {
        return victimAssetAssocWriter.dissociateVictimAssetWebsite(uniqueId, assetId);
    }

    @Override
    public ApiEntitySingleResponse dissociateVictimAssetWebsite(Long uniqueId, Long assetId, String ownerName) throws IOException, FailedResponseException {
        return victimAssetAssocWriter.dissociateVictimAssetWebsite(uniqueId, assetId, ownerName);
    }

    @Override
    public WriteListResponse<String> dissociateTags(Long uniqueId, List<String> tagNames) throws IOException {
        return tagAssocWriter.dissociateTags(uniqueId, tagNames);
    }

    @Override
    public WriteListResponse<String> dissociateTags(Long uniqueId, List<String> tagNames, String ownerName) throws IOException {
        return tagAssocWriter.dissociateTags(uniqueId, tagNames, ownerName);
    }

    @Override
    public ApiEntitySingleResponse dissociateTag(Long uniqueId, String tagName) throws IOException, FailedResponseException {
        return tagAssocWriter.dissociateTag(uniqueId, tagName);
    }

    @Override
    public ApiEntitySingleResponse dissociateTag(Long uniqueId, String tagName, String ownerName) throws IOException, FailedResponseException {
        return tagAssocWriter.dissociateTag(uniqueId, tagName, ownerName);
    }

    @Override
    public WriteListResponse<String> dissociateSecurityLabel(Long uniqueId, List<String> securityLabels) throws IOException {
        return secLabelAssocWriter.dissociateSecurityLabel(uniqueId, securityLabels);
    }

    @Override
    public WriteListResponse<String> dissociateSecurityLabel(Long uniqueId, List<String> securityLabels, String ownerName) throws IOException {
        return secLabelAssocWriter.dissociateSecurityLabel(uniqueId, securityLabels, ownerName);
    }

    @Override
    public ApiEntitySingleResponse dissociateSecurityLabel(Long uniqueId, String securityLabel) throws IOException, FailedResponseException {
        return secLabelAssocWriter.dissociateSecurityLabel(uniqueId, securityLabel);
    }

    @Override
    public ApiEntitySingleResponse dissociateSecurityLabel(Long uniqueId, String securityLabel, String ownerName) throws IOException, FailedResponseException {
        return secLabelAssocWriter.dissociateSecurityLabel(uniqueId, securityLabel, ownerName);
    }

    @Override
    public WriteListResponse<Long> associateVictims(Long uniqueId, List<Long> victimIds) throws IOException {
        return victimAssocWriter.associateVictims(uniqueId, victimIds);
    }

    @Override
    public WriteListResponse<Long> associateVictims(Long uniqueId, List<Long> victimIds, String ownerName) throws IOException {
        return victimAssocWriter.associateVictims(uniqueId, victimIds, ownerName);
    }

    @Override
    public ApiEntitySingleResponse associateVictim(Long uniqueId, Long victimId) throws IOException, FailedResponseException {
        return victimAssocWriter.associateVictim(uniqueId, victimId);
    }

    @Override
    public ApiEntitySingleResponse associateVictim(Long uniqueId, Long victimId, String ownerName) throws IOException, FailedResponseException { return victimAssocWriter.associateVictim(uniqueId, victimId, ownerName);
    }

    @Override
    public WriteListResponse<Long> dissociateVictims(Long uniqueId, List<Long> victimIds) throws IOException {
        return victimAssocWriter.dissociateVictims(uniqueId, victimIds);
    }

    @Override
    public WriteListResponse<Long> dissociateVictims(Long uniqueId, List<Long> victimIds, String ownerName) throws IOException {
        return victimAssocWriter.dissociateVictims(uniqueId, victimIds, ownerName);
    }

    @Override
    public ApiEntitySingleResponse dissociateVictim(Long uniqueId, Long victimId) throws IOException, FailedResponseException {
        return victimAssocWriter.dissociateVictim(uniqueId, victimId);
    }

    @Override
    public ApiEntitySingleResponse dissociateVictim(Long uniqueId, Long victimId, String ownerName) throws IOException, FailedResponseException {
        return victimAssocWriter.dissociateVictim(uniqueId, victimId, ownerName);
    }

}
