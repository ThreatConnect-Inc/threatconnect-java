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
 * @param <T>
 */
public abstract class AbstractGroupWriterAdapter<T extends Group>
    extends AbstractBaseWriterAdapter<T,Integer> 
    implements UrlTypeable, GroupAssociateWritable<Integer>, IndicatorAssociateWritable<Integer>
             , AttributeAssociateWritable<Integer>, VictimAssetAssociateWritable<Integer>
             , TagAssociateWritable<Integer>, SecurityLabelAssociateWritable<Integer>
             , VictimAssociateWritable<Integer> {

    // composite pattern
    private AbstractAttributeAssociateWriterAdapter<T,Integer> attribWriter;
    private AbstractGroupAssociateWriterAdapter<T,Integer> groupAssocWriter;
    private AbstractIndicatorAssociateWriterAdapter<T,Integer> indAssocWriter;
    private AbstractSecurityLabelAssociateWriterAdapter<T,Integer> secLabelAssocWriter;
    private AbstractTagAssociateWriterAdapter<T,Integer> tagAssocWriter;
    private AbstractVictimAssetAssociateWriterAdapter<T,Integer> victimAssetAssocWriter;
    private AbstractVictimAssociateWriterAdapter<T,Integer> victimAssocWriter;

    /**
     * Package level constructor. Use the {@link WriterAdapterFactory} to access this object.
     * @param conn      Primary connection object to the ThreatConnect API
     * @param singleType
     * 
     * @see WriterAdapterFactory
     */
    protected AbstractGroupWriterAdapter(Connection conn
                , Class<? extends ApiEntitySingleResponse> singleType ) {
        super(conn, singleType );

        initComposite();
    }

    private void initComposite() {
        attribWriter = new AbstractAttributeAssociateWriterAdapter<T,Integer>(
                            AbstractGroupWriterAdapter.this.getConn()
                          , AbstractGroupWriterAdapter.this.singleType
            ) {
            @Override
            protected String getUrlBasePrefix() {
                return AbstractGroupWriterAdapter.this.getUrlBasePrefix();
            }

            @Override
            public Integer getId(T item) {
                return AbstractGroupWriterAdapter.this.getId(item);
            }

            @Override
            public String getUrlType()
            {
                return AbstractGroupWriterAdapter.this.getUrlType();
            }

        };

        groupAssocWriter = new AbstractGroupAssociateWriterAdapter<T,Integer>(
                            AbstractGroupWriterAdapter.this.getConn()
                          , AbstractGroupWriterAdapter.this.singleType
            ) {
            @Override
            protected String getUrlBasePrefix() {
                return AbstractGroupWriterAdapter.this.getUrlBasePrefix();
            }

            @Override
            public Integer getId(T item) {
                return AbstractGroupWriterAdapter.this.getId(item);
            }

            @Override
            public String getUrlType()
            {
                return AbstractGroupWriterAdapter.this.getUrlType();
            }
        };

        indAssocWriter = new AbstractIndicatorAssociateWriterAdapter<T,Integer>(
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
            public Integer getId(T item) {
                return AbstractGroupWriterAdapter.this.getId(item);
            }
        };

        secLabelAssocWriter = new AbstractSecurityLabelAssociateWriterAdapter<T,Integer>(
                            AbstractGroupWriterAdapter.this.getConn()
                          , AbstractGroupWriterAdapter.this.singleType
            ) {
            @Override
            protected String getUrlBasePrefix() {
                return AbstractGroupWriterAdapter.this.getUrlBasePrefix();
            }

            @Override
            public Integer getId(T item) {
                return AbstractGroupWriterAdapter.this.getId(item);
            }

            @Override
            public String getUrlType() {
                return AbstractGroupWriterAdapter.this.getUrlType();
            }
        };

        tagAssocWriter = new AbstractTagAssociateWriterAdapter<T,Integer>(
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
            public Integer getId(T item) {
                return AbstractGroupWriterAdapter.this.getId(item);
            }
        };

        victimAssetAssocWriter = new AbstractVictimAssetAssociateWriterAdapter<T,Integer>(
                            AbstractGroupWriterAdapter.this.getConn()
                          , AbstractGroupWriterAdapter.this.singleType
            ) {
            @Override
            protected String getUrlBasePrefix() {
                return AbstractGroupWriterAdapter.this.getUrlBasePrefix();
            }

            @Override
            public Integer getId(T item) {
                return AbstractGroupWriterAdapter.this.getId(item);
            }

            @Override
            public String getUrlType() {
                return AbstractGroupWriterAdapter.this.getUrlType();
            }

        };
        
        victimAssocWriter = new AbstractVictimAssociateWriterAdapter<T,Integer>(
                            AbstractGroupWriterAdapter.this.getConn()
                          , AbstractGroupWriterAdapter.this.singleType
            ) {

            @Override
            protected String getUrlBasePrefix()
            {
                return AbstractGroupWriterAdapter.this.getUrlBasePrefix();
            }

            @Override
            public Integer getId(T item)
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
    public Integer getId(T item) {
        return item.getId();
    }

    @Override
    public WriteListResponse<Integer> associateGroupAdversaries(Integer uniqueId, List<Integer> adversaryIds) throws IOException {
        return groupAssocWriter.associateGroupAdversaries(uniqueId, adversaryIds);
    }

    @Override
    public WriteListResponse<Integer> associateGroupAdversaries(Integer uniqueId, List<Integer> adversaryIds, String ownerName) throws IOException {
        return groupAssocWriter.associateGroupAdversaries(uniqueId, adversaryIds, ownerName);
    }

    @Override
    public ApiEntitySingleResponse associateGroupAdversary(Integer uniqueId, Integer adversaryId) throws IOException, FailedResponseException {
        return groupAssocWriter.associateGroupAdversary(uniqueId, adversaryId);
    }

    @Override
    public ApiEntitySingleResponse associateGroupAdversary(Integer uniqueId, Integer adversaryId, String ownerName) throws IOException, FailedResponseException {
        return groupAssocWriter.associateGroupAdversary(uniqueId, adversaryId, ownerName);
    }
    
    @Override
    public ApiEntitySingleResponse associateGroupDocument(Integer uniqueId, Integer adversaryId) throws IOException, FailedResponseException {
        return groupAssocWriter.associateGroupDocument(uniqueId, adversaryId);
    }
    
    @Override
    public ApiEntitySingleResponse associateGroupDocument(Integer uniqueId, Integer adversaryId, String ownerName) throws IOException, FailedResponseException {
        return groupAssocWriter.associateGroupDocument(uniqueId, adversaryId, ownerName);
    }

    @Override
    public WriteListResponse<Integer> associateGroupEmails(Integer uniqueId, List<Integer> emailIds) throws IOException {
        return groupAssocWriter.associateGroupEmails(uniqueId, emailIds);
    }

    @Override
    public WriteListResponse<Integer> associateGroupEmails(Integer uniqueId, List<Integer> emailIds, String ownerName) throws IOException {
        return groupAssocWriter.associateGroupEmails(uniqueId, emailIds, ownerName);
    }

    @Override
    public ApiEntitySingleResponse associateGroupEmail(Integer uniqueId, Integer emailId) throws IOException, FailedResponseException {
        return groupAssocWriter.associateGroupEmail(uniqueId, emailId);
    }

    @Override
    public ApiEntitySingleResponse associateGroupEmail(Integer uniqueId, Integer emailId, String ownerName) throws IOException, FailedResponseException {
        return groupAssocWriter.associateGroupEmail(uniqueId, emailId, ownerName);
    }

    @Override
    public WriteListResponse<Integer> associateGroupIncidents(Integer uniqueId, List<Integer> incidentIds) throws IOException {
        return groupAssocWriter.associateGroupIncidents(uniqueId, incidentIds);
    }

    @Override
    public WriteListResponse<Integer> associateGroupIncidents(Integer uniqueId, List<Integer> incidentIds, String ownerName) throws IOException {
        return groupAssocWriter.associateGroupIncidents(uniqueId, incidentIds, ownerName);
    }

    @Override
    public ApiEntitySingleResponse associateGroupIncident(Integer uniqueId, Integer incidentId) throws IOException, FailedResponseException {
        return groupAssocWriter.associateGroupIncident(uniqueId, incidentId);
    }

    @Override
    public ApiEntitySingleResponse associateGroupIncident(Integer uniqueId, Integer incidentId, String ownerName) throws IOException, FailedResponseException {
        return groupAssocWriter.associateGroupIncident(uniqueId, incidentId, ownerName);
    }

    @Override
    public WriteListResponse<Integer> associateGroupSignatures(Integer uniqueId, List<Integer> signatureIds) throws IOException {
        return groupAssocWriter.associateGroupSignatures(uniqueId, signatureIds);
    }

    @Override
    public WriteListResponse<Integer> associateGroupSignatures(Integer uniqueId, List<Integer> signatureIds, String ownerName) throws IOException {
        return groupAssocWriter.associateGroupSignatures(uniqueId, signatureIds, ownerName);
    }

    @Override
    public ApiEntitySingleResponse associateGroupSignature(Integer uniqueId, Integer signatureId) throws IOException, FailedResponseException {
        return groupAssocWriter.associateGroupSignature(uniqueId, signatureId);
    }

    @Override
    public ApiEntitySingleResponse associateGroupSignature(Integer uniqueId, Integer signatureId, String ownerName) throws IOException, FailedResponseException {
        return groupAssocWriter.associateGroupSignature(uniqueId, signatureId, ownerName);
    }

    @Override
    public WriteListResponse<Integer> associateGroupThreats(Integer uniqueId, List<Integer> threatIds) throws IOException {
        return groupAssocWriter.associateGroupThreats(uniqueId, threatIds);
    }

    @Override
    public WriteListResponse<Integer> associateGroupThreats(Integer uniqueId, List<Integer> threatIds, String ownerName) throws IOException {
        return groupAssocWriter.associateGroupThreats(uniqueId, threatIds, ownerName);
    }

    @Override
    public ApiEntitySingleResponse associateGroupThreat(Integer uniqueId, Integer threatId) throws IOException, FailedResponseException {
        return groupAssocWriter.associateGroupThreat(uniqueId, threatId);
    }

    @Override
    public ApiEntitySingleResponse associateGroupThreat(Integer uniqueId, Integer threatId, String ownerName) throws IOException, FailedResponseException {
        return groupAssocWriter.associateGroupThreat(uniqueId, threatId, ownerName);
    }

    @Override
    public WriteListResponse<String> associateIndicatorAddresses(Integer uniqueId, List<String> ipAddresses) throws IOException {
        return indAssocWriter.associateIndicatorAddresses(uniqueId, ipAddresses);
    }

    @Override
    public WriteListResponse<String> associateIndicatorAddresses(Integer uniqueId, List<String> ipAddresses, String ownerName) throws IOException {
        return indAssocWriter.associateIndicatorAddresses(uniqueId, ipAddresses, ownerName);
    }

    @Override
    public ApiEntitySingleResponse associateIndicatorAddress(Integer uniqueId, String ipAddress) throws IOException, FailedResponseException {
        return indAssocWriter.associateIndicatorAddress(uniqueId, ipAddress);
    }

    @Override
    public ApiEntitySingleResponse associateIndicatorAddress(Integer uniqueId, String ipAddress, String ownerName) throws IOException, FailedResponseException {
        return indAssocWriter.associateIndicatorAddress(uniqueId, ipAddress, ownerName);
    }

    @Override
    public WriteListResponse<String> associateIndicatorEmailAddresses(Integer uniqueId, List<String> emailAddresses) throws IOException {
        return indAssocWriter.associateIndicatorEmailAddresses(uniqueId, emailAddresses);
    }

    @Override
    public WriteListResponse<String> associateIndicatorEmailAddresses(Integer uniqueId, List<String> emailAddresses, String ownerName) throws IOException {
        return indAssocWriter.associateIndicatorEmailAddresses(uniqueId, emailAddresses, ownerName);
    }

    @Override
    public ApiEntitySingleResponse associateIndicatorEmailAddress(Integer uniqueId, String emailAddress) throws IOException, FailedResponseException {
        return indAssocWriter.associateIndicatorEmailAddress(uniqueId, emailAddress);
    }

    @Override
    public ApiEntitySingleResponse associateIndicatorEmailAddress(Integer uniqueId, String emailAddress, String ownerName) throws IOException, FailedResponseException {
        return indAssocWriter.associateIndicatorAddress(uniqueId, emailAddress, ownerName);
    }

    @Override
    public WriteListResponse<String> associateIndicatorFiles(Integer uniqueId, List<String> fileHashes) throws IOException {
        return indAssocWriter.associateIndicatorFiles(uniqueId, fileHashes);
    }

    @Override
    public WriteListResponse<String> associateIndicatorFiles(Integer uniqueId, List<String> fileHashes, String ownerName) throws IOException {
        return indAssocWriter.associateIndicatorFiles(uniqueId, fileHashes, ownerName);
    }

    @Override
    public ApiEntitySingleResponse associateIndicatorFile(Integer uniqueId, String fileHash) throws IOException, FailedResponseException {
        return indAssocWriter.associateIndicatorFile(uniqueId, fileHash);
    }

    @Override
    public ApiEntitySingleResponse associateIndicatorFile(Integer uniqueId, String fileHash, String ownerName) throws IOException, FailedResponseException {
        return indAssocWriter.associateIndicatorAddress(uniqueId, fileHash, ownerName);
    }

    @Override
    public WriteListResponse<String> associateIndicatorHosts(Integer uniqueId, List<String> hostNames) throws IOException {
        return indAssocWriter.associateIndicatorHosts(uniqueId, hostNames);
    }

    @Override
    public WriteListResponse<String> associateIndicatorHosts(Integer uniqueId, List<String> hostNames, String ownerName) throws IOException {
        return indAssocWriter.associateIndicatorHosts(uniqueId, hostNames, ownerName);
    }

    @Override
    public ApiEntitySingleResponse associateIndicatorHost(Integer uniqueId, String hostName) throws IOException, FailedResponseException {
        return indAssocWriter.associateIndicatorHost(uniqueId, hostName);
    }

    @Override
    public ApiEntitySingleResponse associateIndicatorHost(Integer uniqueId, String hostName, String ownerName) throws IOException, FailedResponseException {
        return indAssocWriter.associateIndicatorHost(uniqueId, hostName, ownerName);
    }

    @Override
    public WriteListResponse<String> associateIndicatorUrls(Integer uniqueId, List<String> urlTexts) throws IOException {
        return indAssocWriter.associateIndicatorUrls(uniqueId, urlTexts);
    }

    @Override
    public WriteListResponse<String> associateIndicatorUrls(Integer uniqueId, List<String> urlTexts, String ownerName) throws IOException {
        return indAssocWriter.associateIndicatorUrls(uniqueId, urlTexts, ownerName);
    }

    @Override
    public ApiEntitySingleResponse associateIndicatorUrl(Integer uniqueId, String urlText) throws IOException, FailedResponseException {
        return indAssocWriter.associateIndicatorUrl(uniqueId, urlText);
    }

    @Override
    public ApiEntitySingleResponse associateIndicatorUrl(Integer uniqueId, String urlText, String ownerName) throws IOException, FailedResponseException {
        return indAssocWriter.associateIndicatorUrl(uniqueId, urlText, ownerName);
    }

    @Override
    public WriteListResponse<Attribute> addAttributes(Integer uniqueId, List<Attribute> attributes) throws IOException {
        return attribWriter.addAttributes(uniqueId, attributes);
    }

    @Override
    public WriteListResponse<Attribute> addAttributes(Integer uniqueId, List<Attribute> attribute, String ownerName) throws IOException {
        return attribWriter.addAttributes(uniqueId, attribute, ownerName);
    }

    @Override
    public ApiEntitySingleResponse addAttribute(Integer uniqueId, Attribute attribute) throws IOException, FailedResponseException {
        return attribWriter.addAttribute(uniqueId, attribute);
    }

    @Override
    public ApiEntitySingleResponse addAttribute(Integer uniqueId, Attribute attribute, String ownerName) throws IOException, FailedResponseException {
        return attribWriter.addAttribute(uniqueId, attribute, ownerName);
    }

    @Override
    public WriteListResponse<String> addAttributeSecurityLabels(Integer uniqueId, Integer attributeId, List<String> securityLabels) throws IOException {
        return attribWriter.addAttributeSecurityLabels(uniqueId, attributeId, securityLabels);
    }

    @Override
    public WriteListResponse<String> addAttributeSecurityLabels(Integer uniqueId, Integer attributeId, List<String> securityLabels, String ownerName) throws IOException {
        return attribWriter.addAttributeSecurityLabels(uniqueId, attributeId, securityLabels, ownerName);
    }

    @Override
    public ApiEntitySingleResponse addAttributeSecurityLabel(Integer uniqueId, Integer attributeId, String securityLabel) throws IOException, FailedResponseException {
        return attribWriter.addAttributeSecurityLabel(uniqueId, attributeId, securityLabel);
    }

    @Override
    public ApiEntitySingleResponse addAttributeSecurityLabel(Integer uniqueId, Integer attributeId, String securityLabel, String ownerName) throws IOException, FailedResponseException {
        return attribWriter.addAttributeSecurityLabel(uniqueId, attributeId, securityLabel, ownerName);
    }

    @Override
    public WriteListResponse<Integer> associateVictimAssetEmailAddresses(Integer uniqueId, List<Integer> assetIds) throws IOException {
        return victimAssetAssocWriter.associateVictimAssetEmailAddresses(uniqueId, assetIds);
    }

    @Override
    public WriteListResponse<Integer> associateVictimAssetEmailAddresses(Integer uniqueId, List<Integer> assetIds, String ownerName) throws IOException {
        return victimAssetAssocWriter. associateVictimAssetEmailAddresses(uniqueId, assetIds, ownerName);
    }

    @Override
    public ApiEntitySingleResponse associateVictimAssetEmailAddress(Integer uniqueId, Integer assetId) throws IOException, FailedResponseException {
        return victimAssetAssocWriter.associateVictimAssetEmailAddress(uniqueId, assetId);
    }

    @Override
    public ApiEntitySingleResponse associateVictimAssetEmailAddress(Integer uniqueId, Integer assetId, String ownerName) throws IOException, FailedResponseException {
        return victimAssetAssocWriter.associateVictimAssetEmailAddress(uniqueId, assetId, ownerName);
    }

    @Override
    public WriteListResponse<Integer> associateVictimAssetNetworkAccounts(Integer uniqueId, List<Integer> assetIds) throws IOException {
        return victimAssetAssocWriter.associateVictimAssetNetworkAccounts(uniqueId, assetIds);
    }

    @Override
    public WriteListResponse<Integer> associateVictimAssetNetworkAccounts(Integer uniqueId, List<Integer> assetIds, String ownerName) throws IOException {
        return victimAssetAssocWriter.associateVictimAssetNetworkAccounts(uniqueId, assetIds, ownerName);
    }

    @Override
    public ApiEntitySingleResponse associateVictimAssetNetworkAccount(Integer uniqueId, Integer assetId) throws IOException, FailedResponseException {
        return victimAssetAssocWriter.associateVictimAssetNetworkAccount(uniqueId, assetId);
    }

    @Override
    public ApiEntitySingleResponse associateVictimAssetNetworkAccount(Integer uniqueId, Integer assetId, String ownerName) throws IOException, FailedResponseException {
        return victimAssetAssocWriter.associateVictimAssetNetworkAccount(uniqueId, assetId, ownerName);
    }

    @Override
    public WriteListResponse<Integer> associateVictimAssetPhoneNumbers(Integer uniqueId, List<Integer> assetIds) throws IOException {
        return victimAssetAssocWriter.associateVictimAssetPhoneNumbers(uniqueId, assetIds);
    }

    @Override
    public WriteListResponse<Integer> associateVictimAssetPhoneNumbers(Integer uniqueId, List<Integer> assetIds, String ownerName) throws IOException {
        return victimAssetAssocWriter.associateVictimAssetPhoneNumbers(uniqueId, assetIds, ownerName);
    }

    @Override
    public ApiEntitySingleResponse associateVictimAssetPhoneNumber(Integer uniqueId, Integer assetId) throws IOException, FailedResponseException {
        return victimAssetAssocWriter.associateVictimAssetPhoneNumber(uniqueId, assetId);
    }

    @Override
    public ApiEntitySingleResponse associateVictimAssetPhoneNumber(Integer uniqueId, Integer assetId, String ownerName) throws IOException, FailedResponseException {
        return victimAssetAssocWriter.associateVictimAssetPhoneNumber(uniqueId, assetId, ownerName);
    }

    @Override
    public WriteListResponse<Integer> associateVictimAssetSocialNetworks(Integer uniqueId, List<Integer> assetIds) throws IOException, FailedResponseException {
        return victimAssetAssocWriter.associateVictimAssetSocialNetworks(uniqueId, assetIds);
    }

    @Override
    public WriteListResponse<Integer> associateVictimAssetSocialNetworks(Integer uniqueId, List<Integer> assetIds, String ownerName) throws IOException {
        return victimAssetAssocWriter.associateVictimAssetSocialNetworks(uniqueId, assetIds, ownerName);
    }

    @Override
    public ApiEntitySingleResponse associateVictimAssetSocialNetwork(Integer uniqueId, Integer assetId) throws IOException, FailedResponseException {
        return victimAssetAssocWriter.associateVictimAssetSocialNetwork(uniqueId, assetId);
    }

    @Override
    public ApiEntitySingleResponse associateVictimAssetSocialNetwork(Integer uniqueId, Integer assetId, String ownerName) throws IOException, FailedResponseException {
        return victimAssetAssocWriter.associateVictimAssetSocialNetwork(uniqueId, assetId, ownerName);
    }

    @Override
    public WriteListResponse<Integer> associateVictimAssetWebsites(Integer uniqueId, List<Integer> assetIds) throws IOException {
        return victimAssetAssocWriter.associateVictimAssetWebsites(uniqueId, assetIds);
    }

    @Override
    public WriteListResponse<Integer> associateVictimAssetWebsites(Integer uniqueId, List<Integer> assetIds, String ownerName) throws IOException {
        return victimAssetAssocWriter.associateVictimAssetWebsites(uniqueId, assetIds, ownerName);
    }

    @Override
    public ApiEntitySingleResponse associateVictimAssetWebsite(Integer uniqueId, Integer assetId) throws IOException, FailedResponseException {
        return victimAssetAssocWriter.associateVictimAssetWebsite(uniqueId, assetId);
    }

    @Override
    public ApiEntitySingleResponse associateVictimAssetWebsite(Integer uniqueId, Integer assetId, String ownerName) throws IOException, FailedResponseException {
        return victimAssetAssocWriter.associateVictimAssetWebsite(uniqueId, assetId, ownerName);
    }

    @Override
    public WriteListResponse<String> associateTags(Integer uniqueId, List<String> tagNames) throws IOException {
        return tagAssocWriter.associateTags(uniqueId, tagNames);
    }

    @Override
    public WriteListResponse<String> associateTags(Integer uniqueId, List<String> tagNames, String ownerName) throws IOException {
        return tagAssocWriter.associateTags(uniqueId, tagNames, ownerName);
    }

    @Override
    public ApiEntitySingleResponse associateTag(Integer uniqueId, String tagName) throws IOException, FailedResponseException {
        return tagAssocWriter.associateTag(uniqueId, tagName);
    }

    @Override
    public ApiEntitySingleResponse associateTag(Integer uniqueId, String tagName, String ownerName) throws IOException, FailedResponseException {
        return tagAssocWriter.associateTag(uniqueId, tagName, ownerName);
    }

    @Override
    public WriteListResponse<String> associateSecurityLabels(Integer uniqueId, List<String> securityLabels) throws IOException {
        return secLabelAssocWriter.associateSecurityLabels(uniqueId, securityLabels);
    }

    @Override
    public WriteListResponse<String> associateSecurityLabels(Integer uniqueId, List<String> securityLabels, String ownerName) throws IOException {
        return secLabelAssocWriter.associateSecurityLabels(uniqueId, securityLabels, ownerName);
    }

    @Override
    public ApiEntitySingleResponse associateSecurityLabel(Integer uniqueId, String securityLabel) throws IOException, FailedResponseException {
        return secLabelAssocWriter.associateSecurityLabel(uniqueId, securityLabel);
    }

    @Override
    public ApiEntitySingleResponse associateSecurityLabel(Integer uniqueId, String securityLabel, String ownerName) throws IOException, FailedResponseException {
        return secLabelAssocWriter.associateSecurityLabel(uniqueId, securityLabel, ownerName);
    }

        @Override
    public WriteListResponse<Integer> dissociateGroupAdversaries(Integer uniqueId, List<Integer> adversaryIds) throws IOException {
        return groupAssocWriter.dissociateGroupAdversaries(uniqueId, adversaryIds);
    }

    @Override
    public WriteListResponse<Integer> dissociateGroupAdversaries(Integer uniqueId, List<Integer> adversaryIds, String ownerName) throws IOException {
        return groupAssocWriter.dissociateGroupAdversaries(uniqueId, adversaryIds, ownerName);
    }

    @Override
    public ApiEntitySingleResponse dissociateGroupAdversary(Integer uniqueId, Integer adversaryId) throws IOException, FailedResponseException {
        return groupAssocWriter.dissociateGroupAdversary(uniqueId, adversaryId);
    }

    @Override
    public ApiEntitySingleResponse dissociateGroupAdversary(Integer uniqueId, Integer adversaryId, String ownerName) throws IOException, FailedResponseException {
        return groupAssocWriter.dissociateGroupAdversary(uniqueId, adversaryId, ownerName);
    }

    @Override
    public WriteListResponse<Integer> dissociateGroupEmails(Integer uniqueId, List<Integer> emailIds) throws IOException {
        return groupAssocWriter.dissociateGroupEmails(uniqueId, emailIds);
    }

    @Override
    public WriteListResponse<Integer> dissociateGroupEmails(Integer uniqueId, List<Integer> emailIds, String ownerName) throws IOException {
        return groupAssocWriter.dissociateGroupEmails(uniqueId, emailIds, ownerName);
    }

    @Override
    public ApiEntitySingleResponse dissociateGroupEmail(Integer uniqueId, Integer emailId) throws IOException, FailedResponseException {
        return groupAssocWriter.dissociateGroupEmail(uniqueId, emailId);
    }

    @Override
    public ApiEntitySingleResponse dissociateGroupEmail(Integer uniqueId, Integer emailId, String ownerName) throws IOException, FailedResponseException {
        return groupAssocWriter.dissociateGroupEmail(uniqueId, emailId, ownerName);
    }

    @Override
    public WriteListResponse<Integer> dissociateGroupIncidents(Integer uniqueId, List<Integer> incidentIds) throws IOException {
        return groupAssocWriter.dissociateGroupIncidents(uniqueId, incidentIds);
    }

    @Override
    public WriteListResponse<Integer> dissociateGroupIncidents(Integer uniqueId, List<Integer> incidentIds, String ownerName) throws IOException {
        return groupAssocWriter.dissociateGroupIncidents(uniqueId, incidentIds, ownerName);
    }

    @Override
    public ApiEntitySingleResponse dissociateGroupIncident(Integer uniqueId, Integer incidentId) throws IOException, FailedResponseException {
        return groupAssocWriter.dissociateGroupIncident(uniqueId, incidentId);
    }

    @Override
    public ApiEntitySingleResponse dissociateGroupIncident(Integer uniqueId, Integer incidentId, String ownerName) throws IOException, FailedResponseException {
        return groupAssocWriter.dissociateGroupIncident(uniqueId, incidentId, ownerName);
    }

    @Override
    public WriteListResponse<Integer> dissociateGroupSignatures(Integer uniqueId, List<Integer> signatureIds) throws IOException {
        return groupAssocWriter.dissociateGroupSignatures(uniqueId, signatureIds);
    }

    @Override
    public WriteListResponse<Integer> dissociateGroupSignatures(Integer uniqueId, List<Integer> signatureIds, String ownerName) throws IOException {
        return groupAssocWriter.dissociateGroupSignatures(uniqueId, signatureIds, ownerName);
    }

    @Override
    public ApiEntitySingleResponse dissociateGroupSignature(Integer uniqueId, Integer signatureId) throws IOException, FailedResponseException {
        return groupAssocWriter.dissociateGroupSignature(uniqueId, signatureId);
    }

    @Override
    public ApiEntitySingleResponse dissociateGroupSignature(Integer uniqueId, Integer signatureId, String ownerName) throws IOException, FailedResponseException {
        return groupAssocWriter.dissociateGroupSignature(uniqueId, signatureId, ownerName);
    }

    @Override
    public WriteListResponse<Integer> dissociateGroupThreats(Integer uniqueId, List<Integer> threatIds) throws IOException {
        return groupAssocWriter.dissociateGroupThreats(uniqueId, threatIds);
    }

    @Override
    public WriteListResponse<Integer> dissociateGroupThreats(Integer uniqueId, List<Integer> threatIds, String ownerName) throws IOException {
        return groupAssocWriter.dissociateGroupThreats(uniqueId, threatIds, ownerName);
    }

    @Override
    public ApiEntitySingleResponse dissociateGroupThreat(Integer uniqueId, Integer threatId) throws IOException, FailedResponseException {
        return groupAssocWriter.dissociateGroupThreat(uniqueId, threatId);
    }

    @Override
    public ApiEntitySingleResponse dissociateGroupThreat(Integer uniqueId, Integer threatId, String ownerName) throws IOException, FailedResponseException {
        return groupAssocWriter.dissociateGroupThreat(uniqueId, threatId, ownerName);
    }

    @Override
    public WriteListResponse<String> dissociateIndicatorAddresses(Integer uniqueId, List<String> ipAddresses) throws IOException {
        return indAssocWriter.dissociateIndicatorAddresses(uniqueId, ipAddresses);
    }

    @Override
    public WriteListResponse<String> dissociateIndicatorAddresses(Integer uniqueId, List<String> ipAddresses, String ownerName) throws IOException {
        return indAssocWriter.dissociateIndicatorAddresses(uniqueId, ipAddresses, ownerName);
    }

    @Override
    public ApiEntitySingleResponse dissociateIndicatorAddress(Integer uniqueId, String ipAddress) throws IOException, FailedResponseException {
        return indAssocWriter.dissociateIndicatorAddress(uniqueId, ipAddress);
    }

    @Override
    public ApiEntitySingleResponse dissociateIndicatorAddress(Integer uniqueId, String ipAddress, String ownerName) throws IOException, FailedResponseException {
        return indAssocWriter.dissociateIndicatorAddress(uniqueId, ipAddress, ownerName);
    }

    @Override
    public WriteListResponse<String> dissociateIndicatorEmailAddresses(Integer uniqueId, List<String> emailAddresses) throws IOException {
        return indAssocWriter.dissociateIndicatorEmailAddresses(uniqueId, emailAddresses);
    }

    @Override
    public WriteListResponse<String> dissociateIndicatorEmailAddresses(Integer uniqueId, List<String> emailAddresses, String ownerName) throws IOException {
        return indAssocWriter.dissociateIndicatorEmailAddresses(uniqueId, emailAddresses, ownerName);
    }

    @Override
    public ApiEntitySingleResponse dissociateIndicatorEmailAddress(Integer uniqueId, String emailAddress) throws IOException, FailedResponseException {
        return indAssocWriter.dissociateIndicatorEmailAddress(uniqueId, emailAddress);
    }

    @Override
    public ApiEntitySingleResponse dissociateIndicatorEmailAddress(Integer uniqueId, String emailAddress, String ownerName) throws IOException, FailedResponseException {
        return indAssocWriter.dissociateIndicatorEmailAddress(uniqueId, emailAddress, ownerName);
    }

    @Override
    public WriteListResponse<String> dissociateIndicatorFiles(Integer uniqueId, List<String> fileHashes) throws IOException {
        return indAssocWriter.dissociateIndicatorFiles(uniqueId, fileHashes);
    }

    @Override
    public WriteListResponse<String> dissociateIndicatorFiles(Integer uniqueId, List<String> fileHashes, String ownerName) throws IOException {
        return indAssocWriter.dissociateIndicatorFiles(uniqueId, fileHashes, ownerName);
    }

    @Override
    public ApiEntitySingleResponse dissociateIndicatorFile(Integer uniqueId, String fileHash) throws IOException, FailedResponseException {
        return indAssocWriter.dissociateIndicatorFile(uniqueId, fileHash);
    }

    @Override
    public ApiEntitySingleResponse dissociateIndicatorFile(Integer uniqueId, String fileHash, String ownerName) throws IOException, FailedResponseException {
        return indAssocWriter.dissociateIndicatorFile(uniqueId, fileHash, ownerName);
    }

    @Override
    public WriteListResponse<String> dissociateIndicatorHosts(Integer uniqueId, List<String> hostNames) throws IOException {
        return indAssocWriter.dissociateIndicatorHosts(uniqueId, hostNames);
    }

    @Override
    public WriteListResponse<String> dissociateIndicatorHosts(Integer uniqueId, List<String> hostNames, String ownerName) throws IOException {
        return indAssocWriter.dissociateIndicatorHosts(uniqueId, hostNames, ownerName);
    }

    @Override
    public ApiEntitySingleResponse dissociateIndicatorHost(Integer uniqueId, String hostName) throws IOException, FailedResponseException {
        return indAssocWriter.dissociateIndicatorHost(uniqueId, hostName);
    }

    @Override
    public ApiEntitySingleResponse dissociateIndicatorHost(Integer uniqueId, String hostName, String ownerName) throws IOException, FailedResponseException {
        return indAssocWriter.dissociateIndicatorHost(uniqueId, hostName, ownerName);
    }

    @Override
    public WriteListResponse<String> dissociateIndicatorUrls(Integer uniqueId, List<String> urlTexts) throws IOException {
        return indAssocWriter.dissociateIndicatorUrls(uniqueId, urlTexts);
    }

    @Override
    public WriteListResponse<String> dissociateIndicatorUrls(Integer uniqueId, List<String> urlTexts, String ownerName) throws IOException {
        return indAssocWriter.dissociateIndicatorUrls(uniqueId, urlTexts, ownerName);
    }

    @Override
    public ApiEntitySingleResponse dissociateIndicatorUrl(Integer uniqueId, String urlText) throws IOException, FailedResponseException {
        return indAssocWriter.dissociateIndicatorUrl(uniqueId, urlText);
    }

    @Override
    public ApiEntitySingleResponse dissociateIndicatorUrl(Integer uniqueId, String urlText, String ownerName) throws IOException, FailedResponseException {
        return indAssocWriter.dissociateIndicatorUrl(uniqueId, urlText, ownerName);
    }

    @Override
    public WriteListResponse<Attribute> updateAttributes(Integer uniqueId, List<Attribute> attributes) throws IOException {
        return attribWriter.updateAttributes(uniqueId, attributes);
    }

    @Override
    public WriteListResponse<Attribute> updateAttributes(Integer uniqueId, List<Attribute> attribute, String ownerName) throws IOException {
        return attribWriter.updateAttributes(uniqueId, attribute, ownerName);
    }

    @Override
    public ApiEntitySingleResponse updateAttribute(Integer uniqueId, Attribute attribute) throws IOException, FailedResponseException {
        return attribWriter.updateAttribute(uniqueId, attribute);
    }

    @Override
    public ApiEntitySingleResponse updateAttribute(Integer uniqueId, Attribute attribute, String ownerName) throws IOException, FailedResponseException {
        return attribWriter.updateAttribute(uniqueId, attribute, ownerName);
    }

    @Override
    public WriteListResponse<Integer> deleteAttributes(Integer uniqueId, List<Integer> attributes) throws IOException {
        return attribWriter.deleteAttributes(uniqueId, attributes);
    }

    @Override
    public WriteListResponse<Integer> deleteAttributes(Integer uniqueId, List<Integer> attribute, String ownerName) throws IOException {
        return attribWriter.deleteAttributes(uniqueId, attribute, ownerName);
    }

    @Override
    public ApiEntitySingleResponse deleteAttribute(Integer uniqueId, Integer attribute) throws IOException, FailedResponseException {
        return attribWriter.deleteAttribute(uniqueId, attribute);
    }

    @Override
    public ApiEntitySingleResponse deleteAttribute(Integer uniqueId, Integer attribute, String ownerName) throws IOException, FailedResponseException {
        return attribWriter.deleteAttribute(uniqueId, attribute, ownerName);
    }

    @Override
    public WriteListResponse<String> deleteAttributeSecurityLabels(Integer uniqueId, Integer attributeId, List<String> securityLabels) throws IOException, FailedResponseException {
        return attribWriter.deleteAttributeSecurityLabels(uniqueId, attributeId, securityLabels);
    }

    @Override
    public WriteListResponse<String> deleteAttributeSecurityLabels(Integer uniqueId, Integer attributeId, List<String> securityLabels, String ownerName) throws IOException, FailedResponseException {
       return attribWriter.deleteAttributeSecurityLabels(uniqueId, attributeId, securityLabels, ownerName);
    }

    @Override
    public ApiEntitySingleResponse deleteAttributeSecurityLabel(Integer uniqueId, Integer attributeId, String securityLabel) throws IOException, FailedResponseException {
        return attribWriter.deleteAttributeSecurityLabel(uniqueId, attributeId, securityLabel);
    }

    @Override
    public ApiEntitySingleResponse deleteAttributeSecurityLabel(Integer uniqueId, Integer attributeId, String securityLabel, String ownerName) throws IOException, FailedResponseException {
        return attribWriter.deleteAttributeSecurityLabel(uniqueId, attributeId, securityLabel, ownerName);
    }

    @Override
    public WriteListResponse<Integer> dissociateVictimAssetEmailAddresses(Integer uniqueId, List<Integer> assetIds) throws IOException {
        return victimAssetAssocWriter.dissociateVictimAssetEmailAddresses(uniqueId, assetIds);
    }

    @Override
    public WriteListResponse<Integer> dissociateVictimAssetEmailAddresses(Integer uniqueId, List<Integer> assetIds, String ownerName) throws IOException {
        return victimAssetAssocWriter.dissociateVictimAssetEmailAddresses(uniqueId, assetIds, ownerName);
    }

    @Override
    public ApiEntitySingleResponse dissociateVictimAssetEmailAddress(Integer uniqueId, Integer assetId) throws IOException, FailedResponseException {
        return victimAssetAssocWriter.dissociateVictimAssetEmailAddress(uniqueId, assetId);
    }

    @Override
    public ApiEntitySingleResponse dissociateVictimAssetEmailAddress(Integer uniqueId, Integer assetId, String ownerName) throws IOException, FailedResponseException {
        return victimAssetAssocWriter.dissociateVictimAssetEmailAddress(uniqueId, assetId, ownerName);
    }

    @Override
    public WriteListResponse<Integer> dissociateVictimAssetNetworkAccounts(Integer uniqueId, List<Integer> assetIds) throws IOException {
        return victimAssetAssocWriter.dissociateVictimAssetNetworkAccounts(uniqueId, assetIds);
    }

    @Override
    public WriteListResponse<Integer> dissociateVictimAssetNetworkAccounts(Integer uniqueId, List<Integer> assetIds, String ownerName) throws IOException {
        return victimAssetAssocWriter.dissociateVictimAssetNetworkAccounts(uniqueId, assetIds, ownerName);
    }

    @Override
    public ApiEntitySingleResponse dissociateVictimAssetNetworkAccount(Integer uniqueId, Integer assetId) throws IOException, FailedResponseException {
        return victimAssetAssocWriter.associateVictimAssetNetworkAccount(uniqueId, assetId);
    }

    @Override
    public ApiEntitySingleResponse dissociateVictimAssetNetworkAccount(Integer uniqueId, Integer assetId, String ownerName) throws IOException, FailedResponseException {
        return victimAssetAssocWriter.dissociateVictimAssetNetworkAccount(uniqueId, assetId, ownerName);
    }

    @Override
    public WriteListResponse<Integer> dissociateVictimAssetPhoneNumbers(Integer uniqueId, List<Integer> assetIds) throws IOException {
        return victimAssetAssocWriter.dissociateVictimAssetPhoneNumbers(uniqueId, assetIds);
    }

    @Override
    public WriteListResponse<Integer> dissociateVictimAssetPhoneNumbers(Integer uniqueId, List<Integer> assetIds, String ownerName) throws IOException {
        return victimAssetAssocWriter.dissociateVictimAssetPhoneNumbers(uniqueId, assetIds, ownerName);
    }

    @Override
    public ApiEntitySingleResponse dissociateVictimAssetPhoneNumber(Integer uniqueId, Integer assetId) throws IOException, FailedResponseException {
        return victimAssetAssocWriter.dissociateVictimAssetPhoneNumber(uniqueId, assetId);
    }

    @Override
    public ApiEntitySingleResponse dissociateVictimAssetPhoneNumber(Integer uniqueId, Integer assetId, String ownerName) throws IOException, FailedResponseException {
        return victimAssetAssocWriter.dissociateVictimAssetPhoneNumber(uniqueId, assetId, ownerName);
    }

    @Override
    public WriteListResponse<Integer> dissociateVictimAssetSocialNetworks(Integer uniqueId, List<Integer> assetIds) throws IOException, FailedResponseException {
        return victimAssetAssocWriter.dissociateVictimAssetSocialNetworks(uniqueId, assetIds);
    }

    @Override
    public WriteListResponse<Integer> dissociateVictimAssetSocialNetworks(Integer uniqueId, List<Integer> assetIds, String ownerName) throws IOException {
        return victimAssetAssocWriter.dissociateVictimAssetSocialNetworks(uniqueId, assetIds, ownerName);
    }

    @Override
    public ApiEntitySingleResponse dissociateVictimAssetSocialNetwork(Integer uniqueId, Integer assetId) throws IOException, FailedResponseException {
        return victimAssetAssocWriter.dissociateVictimAssetSocialNetwork(uniqueId, assetId);
    }

    @Override
    public ApiEntitySingleResponse dissociateVictimAssetSocialNetwork(Integer uniqueId, Integer assetId, String ownerName) throws IOException, FailedResponseException {
        return victimAssetAssocWriter.dissociateVictimAssetSocialNetwork(uniqueId, assetId, ownerName);
    }

    @Override
    public WriteListResponse<Integer> dissociateVictimAssetWebsites(Integer uniqueId, List<Integer> assetIds) throws IOException {
        return victimAssetAssocWriter.dissociateVictimAssetWebsites(uniqueId, assetIds);
    }

    @Override
    public WriteListResponse<Integer> dissociateVictimAssetWebsites(Integer uniqueId, List<Integer> assetIds, String ownerName) throws IOException {
        return victimAssetAssocWriter.dissociateVictimAssetWebsites(uniqueId, assetIds, ownerName);
    }

    @Override
    public ApiEntitySingleResponse dissociateVictimAssetWebsite(Integer uniqueId, Integer assetId) throws IOException, FailedResponseException {
        return victimAssetAssocWriter.dissociateVictimAssetWebsite(uniqueId, assetId);
    }

    @Override
    public ApiEntitySingleResponse dissociateVictimAssetWebsite(Integer uniqueId, Integer assetId, String ownerName) throws IOException, FailedResponseException {
        return victimAssetAssocWriter.dissociateVictimAssetWebsite(uniqueId, assetId, ownerName);
    }

    @Override
    public WriteListResponse<String> dissociateTags(Integer uniqueId, List<String> tagNames) throws IOException {
        return tagAssocWriter.dissociateTags(uniqueId, tagNames);
    }

    @Override
    public WriteListResponse<String> dissociateTags(Integer uniqueId, List<String> tagNames, String ownerName) throws IOException {
        return tagAssocWriter.dissociateTags(uniqueId, tagNames, ownerName);
    }

    @Override
    public ApiEntitySingleResponse dissociateTag(Integer uniqueId, String tagName) throws IOException, FailedResponseException {
        return tagAssocWriter.dissociateTag(uniqueId, tagName);
    }

    @Override
    public ApiEntitySingleResponse dissociateTag(Integer uniqueId, String tagName, String ownerName) throws IOException, FailedResponseException {
        return tagAssocWriter.dissociateTag(uniqueId, tagName, ownerName);
    }

    @Override
    public WriteListResponse<String> dissociateSecurityLabel(Integer uniqueId, List<String> securityLabels) throws IOException {
        return secLabelAssocWriter.dissociateSecurityLabel(uniqueId, securityLabels);
    }

    @Override
    public WriteListResponse<String> dissociateSecurityLabel(Integer uniqueId, List<String> securityLabels, String ownerName) throws IOException {
        return secLabelAssocWriter.dissociateSecurityLabel(uniqueId, securityLabels, ownerName);
    }

    @Override
    public ApiEntitySingleResponse dissociateSecurityLabel(Integer uniqueId, String securityLabel) throws IOException, FailedResponseException {
        return secLabelAssocWriter.dissociateSecurityLabel(uniqueId, securityLabel);
    }

    @Override
    public ApiEntitySingleResponse dissociateSecurityLabel(Integer uniqueId, String securityLabel, String ownerName) throws IOException, FailedResponseException {
        return secLabelAssocWriter.dissociateSecurityLabel(uniqueId, securityLabel, ownerName);
    }

    @Override
    public WriteListResponse<Integer> associateVictims(Integer uniqueId, List<Integer> victimIds) throws IOException {
        return victimAssocWriter.associateVictims(uniqueId, victimIds);
    }

    @Override
    public WriteListResponse<Integer> associateVictims(Integer uniqueId, List<Integer> victimIds, String ownerName) throws IOException {
        return victimAssocWriter.associateVictims(uniqueId, victimIds, ownerName);
    }

    @Override
    public ApiEntitySingleResponse associateVictim(Integer uniqueId, Integer victimId) throws IOException, FailedResponseException {
        return victimAssocWriter.associateVictim(uniqueId, victimId);
    }

    @Override
    public ApiEntitySingleResponse associateVictim(Integer uniqueId, Integer victimId, String ownerName) throws IOException, FailedResponseException { return victimAssocWriter.associateVictim(uniqueId, victimId, ownerName);
    }

    @Override
    public WriteListResponse<Integer> dissociateVictims(Integer uniqueId, List<Integer> victimIds) throws IOException {
        return victimAssocWriter.dissociateVictims(uniqueId, victimIds);
    }

    @Override
    public WriteListResponse<Integer> dissociateVictims(Integer uniqueId, List<Integer> victimIds, String ownerName) throws IOException {
        return victimAssocWriter.dissociateVictims(uniqueId, victimIds, ownerName);
    }

    @Override
    public ApiEntitySingleResponse dissociateVictim(Integer uniqueId, Integer victimId) throws IOException, FailedResponseException {
        return victimAssocWriter.dissociateVictim(uniqueId, victimId);
    }

    @Override
    public ApiEntitySingleResponse dissociateVictim(Integer uniqueId, Integer victimId, String ownerName) throws IOException, FailedResponseException {
        return victimAssocWriter.dissociateVictim(uniqueId, victimId, ownerName);
    }

}
