package com.cyber2.api.lib.client.writer;

import com.cyber2.api.lib.client.UrlTypeable;
import com.cyber2.api.lib.client.response.WriteListResponse;
import com.cyber2.api.lib.client.writer.associate.AbstractAttributeAssociateWriterAdapter;
import com.cyber2.api.lib.client.writer.associate.AbstractGroupAssociateWriterAdapter;
import com.cyber2.api.lib.client.writer.associate.AbstractIndicatorAssociateWriterAdapter;
import com.cyber2.api.lib.client.writer.associate.AbstractSecurityLabelAssociateWriterAdapter;
import com.cyber2.api.lib.client.writer.associate.AbstractTagAssociateWriterAdapter;
import com.cyber2.api.lib.client.writer.associate.AbstractVictimAssetAssociateWriterAdapter;
import com.cyber2.api.lib.client.writer.associate.AttributeAssociateWritable;
import com.cyber2.api.lib.client.writer.associate.GroupAssociateWritable;
import com.cyber2.api.lib.client.writer.associate.IndicatorAssociateWritable;
import com.cyber2.api.lib.client.writer.associate.SecurityLabelAssociateWritable;
import com.cyber2.api.lib.client.writer.associate.TagAssociateWritable;
import com.cyber2.api.lib.client.writer.associate.VictimAssetAssociateWritable;
import com.cyber2.api.lib.conn.Connection;
import com.cyber2.api.lib.conn.RequestExecutor;
import com.cyber2.api.lib.exception.FailedResponseException;
import com.cyber2.api.lib.server.entity.Attribute;
import com.cyber2.api.lib.server.entity.Group;
import com.cyber2.api.lib.server.response.entity.ApiEntitySingleResponse;
import java.io.IOException;
import java.util.List;

/**
 * EmailWriterAdapter is the primary client adapter for all Email group level objects.
 * It uses the {@link Connection} object to execute requests against the {@link RequestExecutor} object.
 * The responsibility of this class is to encapsulate all the low level ThreatConnect API calls
 * specifically targeted at data under the Email group type.
 * 
 *
 * @author dtineo
 * @param <T>
 */
public abstract class AbstractGroupWriterAdapter<T extends Group> 
    extends AbstractBaseWriterAdapter<T,Integer> 
    implements UrlTypeable, GroupAssociateWritable<Integer>, IndicatorAssociateWritable<Integer>
             , AttributeAssociateWritable<Integer>, VictimAssetAssociateWritable<Integer>
             , TagAssociateWritable<Integer>, SecurityLabelAssociateWritable<Integer>  {

    // composite pattern
    private AbstractAttributeAssociateWriterAdapter<T,Integer> attribWriter;
    private AbstractGroupAssociateWriterAdapter<T,Integer> groupAssocWriter;
    private AbstractIndicatorAssociateWriterAdapter<T,Integer> indAssocWriter;
    private AbstractSecurityLabelAssociateWriterAdapter<T,Integer> secLabelAssocWriter;
    private AbstractTagAssociateWriterAdapter<T,Integer> tagAssocWriter;
    private AbstractVictimAssetAssociateWriterAdapter<T,Integer> victimAssetAssocWriter;

    /**
     * Package level constructor. Use the {@link WriterAdapterFactory} to access this object.
     * @param conn      Primary connection object to the ThreatConnect API
     * @param executor  Executor handling low level HTTPS calls to the ThreatConnect API
     * @param singleType
     * 
     * @see WriterAdapterFactory
     */
    protected AbstractGroupWriterAdapter(Connection conn, RequestExecutor executor
                , Class<? extends ApiEntitySingleResponse> singleType ) {
        super(conn, executor, singleType );

        initComposite();
    }

    private void initComposite() {
        attribWriter = new AbstractAttributeAssociateWriterAdapter<T,Integer>(
                            AbstractGroupWriterAdapter.this.getConn()
                          , AbstractGroupWriterAdapter.this.executor
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

        };

        groupAssocWriter = new AbstractGroupAssociateWriterAdapter<T,Integer>(
                            AbstractGroupWriterAdapter.this.getConn()
                          , AbstractGroupWriterAdapter.this.executor
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
        };

        indAssocWriter = new AbstractIndicatorAssociateWriterAdapter<T,Integer>(
                            AbstractGroupWriterAdapter.this.getConn()
                          , AbstractGroupWriterAdapter.this.executor
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
                          , AbstractGroupWriterAdapter.this.executor
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
                          , AbstractGroupWriterAdapter.this.executor
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
                          , AbstractGroupWriterAdapter.this.executor
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
    public Attribute addAttribute(Integer uniqueId, Attribute attribute) throws IOException, FailedResponseException {
        return attribWriter.addAttribute(uniqueId, attribute);
    }

    @Override
    public Attribute addAttribute(Integer uniqueId, Attribute attribute, String ownerName) throws IOException, FailedResponseException {
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
    public WriteListResponse<Integer> deleteAssociatedGroupAdversaries(Integer uniqueId, List<Integer> adversaryIds) throws IOException {
        return groupAssocWriter.deleteAssociatedGroupAdversaries(uniqueId, adversaryIds);
    }

    @Override
    public WriteListResponse<Integer> deleteAssociatedGroupAdversaries(Integer uniqueId, List<Integer> adversaryIds, String ownerName) throws IOException {
        return groupAssocWriter.deleteAssociatedGroupAdversaries(uniqueId, adversaryIds, ownerName);
    }

    @Override
    public ApiEntitySingleResponse deleteAssociatedGroupAdversary(Integer uniqueId, Integer adversaryId) throws IOException, FailedResponseException {
        return groupAssocWriter.deleteAssociatedGroupAdversary(uniqueId, adversaryId);
    }

    @Override
    public ApiEntitySingleResponse deleteAssociatedGroupAdversary(Integer uniqueId, Integer adversaryId, String ownerName) throws IOException, FailedResponseException {
        return groupAssocWriter.deleteAssociatedGroupAdversary(uniqueId, adversaryId, ownerName);
    }

    @Override
    public WriteListResponse<Integer> deleteAssociatedGroupEmails(Integer uniqueId, List<Integer> emailIds) throws IOException {
        return groupAssocWriter.deleteAssociatedGroupEmails(uniqueId, emailIds);
    }

    @Override
    public WriteListResponse<Integer> deleteAssociatedGroupEmails(Integer uniqueId, List<Integer> emailIds, String ownerName) throws IOException {
        return groupAssocWriter.deleteAssociatedGroupEmails(uniqueId, emailIds, ownerName);
    }

    @Override
    public ApiEntitySingleResponse deleteAssociatedGroupEmail(Integer uniqueId, Integer emailId) throws IOException, FailedResponseException {
        return groupAssocWriter.deleteAssociatedGroupEmail(uniqueId, emailId);
    }

    @Override
    public ApiEntitySingleResponse deleteAssociatedGroupEmail(Integer uniqueId, Integer emailId, String ownerName) throws IOException, FailedResponseException {
        return groupAssocWriter.deleteAssociatedGroupEmail(uniqueId, emailId, ownerName);
    }

    @Override
    public WriteListResponse<Integer> deleteAssociatedGroupIncidents(Integer uniqueId, List<Integer> incidentIds) throws IOException {
        return groupAssocWriter.deleteAssociatedGroupIncidents(uniqueId, incidentIds);
    }

    @Override
    public WriteListResponse<Integer> deleteAssociatedGroupIncidents(Integer uniqueId, List<Integer> incidentIds, String ownerName) throws IOException {
        return groupAssocWriter.deleteAssociatedGroupIncidents(uniqueId, incidentIds, ownerName);
    }

    @Override
    public ApiEntitySingleResponse deleteAssociatedGroupIncident(Integer uniqueId, Integer incidentId) throws IOException, FailedResponseException {
        return groupAssocWriter.deleteAssociatedGroupIncident(uniqueId, incidentId);
    }

    @Override
    public ApiEntitySingleResponse deleteAssociatedGroupIncident(Integer uniqueId, Integer incidentId, String ownerName) throws IOException, FailedResponseException {
        return groupAssocWriter.deleteAssociatedGroupIncident(uniqueId, incidentId, ownerName);
    }

    @Override
    public WriteListResponse<Integer> deleteAssociatedGroupSignatures(Integer uniqueId, List<Integer> signatureIds) throws IOException {
        return groupAssocWriter.deleteAssociatedGroupSignatures(uniqueId, signatureIds);
    }

    @Override
    public WriteListResponse<Integer> deleteAssociatedGroupSignatures(Integer uniqueId, List<Integer> signatureIds, String ownerName) throws IOException {
        return groupAssocWriter.deleteAssociatedGroupSignatures(uniqueId, signatureIds, ownerName);
    }

    @Override
    public ApiEntitySingleResponse deleteAssociatedGroupSignature(Integer uniqueId, Integer signatureId) throws IOException, FailedResponseException {
        return groupAssocWriter.deleteAssociatedGroupSignature(uniqueId, signatureId);
    }

    @Override
    public ApiEntitySingleResponse deleteAssociatedGroupSignature(Integer uniqueId, Integer signatureId, String ownerName) throws IOException, FailedResponseException {
        return groupAssocWriter.deleteAssociatedGroupSignature(uniqueId, signatureId, ownerName);
    }

    @Override
    public WriteListResponse<Integer> deleteAssociatedGroupThreats(Integer uniqueId, List<Integer> threatIds) throws IOException {
        return groupAssocWriter.deleteAssociatedGroupThreats(uniqueId, threatIds);
    }

    @Override
    public WriteListResponse<Integer> deleteAssociatedGroupThreats(Integer uniqueId, List<Integer> threatIds, String ownerName) throws IOException {
        return groupAssocWriter.deleteAssociatedGroupThreats(uniqueId, threatIds, ownerName);
    }

    @Override
    public ApiEntitySingleResponse deleteAssociatedGroupThreat(Integer uniqueId, Integer threatId) throws IOException, FailedResponseException {
        return groupAssocWriter.deleteAssociatedGroupThreat(uniqueId, threatId);
    }

    @Override
    public ApiEntitySingleResponse deleteAssociatedGroupThreat(Integer uniqueId, Integer threatId, String ownerName) throws IOException, FailedResponseException {
        return groupAssocWriter.deleteAssociatedGroupThreat(uniqueId, threatId, ownerName);
    }

    @Override
    public WriteListResponse<String> deleteAssociatedIndicatorAddresses(Integer uniqueId, List<String> ipAddresses) throws IOException {
        return indAssocWriter.deleteAssociatedIndicatorAddresses(uniqueId, ipAddresses);
    }

    @Override
    public WriteListResponse<String> deleteAssociatedIndicatorAddresses(Integer uniqueId, List<String> ipAddresses, String ownerName) throws IOException {
        return indAssocWriter.deleteAssociatedIndicatorAddresses(uniqueId, ipAddresses, ownerName);
    }

    @Override
    public ApiEntitySingleResponse deleteAssociatedIndicatorAddress(Integer uniqueId, String ipAddress) throws IOException, FailedResponseException {
        return indAssocWriter.deleteAssociatedIndicatorAddress(uniqueId, ipAddress);
    }

    @Override
    public ApiEntitySingleResponse deleteAssociatedIndicatorAddress(Integer uniqueId, String ipAddress, String ownerName) throws IOException, FailedResponseException {
        return indAssocWriter.deleteAssociatedIndicatorAddress(uniqueId, ipAddress, ownerName);
    }

    @Override
    public WriteListResponse<String> deleteAssociatedIndicatorEmailAddresses(Integer uniqueId, List<String> emailAddresses) throws IOException {
        return indAssocWriter.deleteAssociatedIndicatorEmailAddresses(uniqueId, emailAddresses);
    }

    @Override
    public WriteListResponse<String> deleteAssociatedIndicatorEmailAddresses(Integer uniqueId, List<String> emailAddresses, String ownerName) throws IOException {
        return indAssocWriter.deleteAssociatedIndicatorEmailAddresses(uniqueId, emailAddresses, ownerName);
    }

    @Override
    public ApiEntitySingleResponse deleteAssociatedIndicatorEmailAddress(Integer uniqueId, String emailAddress) throws IOException, FailedResponseException {
        return indAssocWriter.deleteAssociatedIndicatorEmailAddress(uniqueId, emailAddress);
    }

    @Override
    public ApiEntitySingleResponse deleteAssociatedIndicatorEmailAddress(Integer uniqueId, String emailAddress, String ownerName) throws IOException, FailedResponseException {
        return indAssocWriter.deleteAssociatedIndicatorEmailAddress(uniqueId, emailAddress, ownerName);
    }

    @Override
    public WriteListResponse<String> deleteAssociatedIndicatorFiles(Integer uniqueId, List<String> fileHashes) throws IOException {
        return indAssocWriter.deleteAssociatedIndicatorFiles(uniqueId, fileHashes);
    }

    @Override
    public WriteListResponse<String> deleteAssociatedIndicatorFiles(Integer uniqueId, List<String> fileHashes, String ownerName) throws IOException {
        return indAssocWriter.deleteAssociatedIndicatorFiles(uniqueId, fileHashes, ownerName);
    }

    @Override
    public ApiEntitySingleResponse deleteAssociatedIndicatorFile(Integer uniqueId, String fileHash) throws IOException, FailedResponseException {
        return indAssocWriter.deleteAssociatedIndicatorFile(uniqueId, fileHash);
    }

    @Override
    public ApiEntitySingleResponse deleteAssociatedIndicatorFile(Integer uniqueId, String fileHash, String ownerName) throws IOException, FailedResponseException {
        return indAssocWriter.deleteAssociatedIndicatorFile(uniqueId, fileHash, ownerName);
    }

    @Override
    public WriteListResponse<String> deleteAssociatedIndicatorHosts(Integer uniqueId, List<String> hostNames) throws IOException {
        return indAssocWriter.deleteAssociatedIndicatorHosts(uniqueId, hostNames);
    }

    @Override
    public WriteListResponse<String> deleteAssociatedIndicatorHosts(Integer uniqueId, List<String> hostNames, String ownerName) throws IOException {
        return indAssocWriter.deleteAssociatedIndicatorHosts(uniqueId, hostNames, ownerName);
    }

    @Override
    public ApiEntitySingleResponse deleteAssociatedIndicatorHost(Integer uniqueId, String hostName) throws IOException, FailedResponseException {
        return indAssocWriter.deleteAssociatedIndicatorHost(uniqueId, hostName);
    }

    @Override
    public ApiEntitySingleResponse deleteAssociatedIndicatorHost(Integer uniqueId, String hostName, String ownerName) throws IOException, FailedResponseException {
        return indAssocWriter.deleteAssociatedIndicatorHost(uniqueId, hostName, ownerName);
    }

    @Override
    public WriteListResponse<String> deleteAssociatedIndicatorUrls(Integer uniqueId, List<String> urlTexts) throws IOException {
        return indAssocWriter.deleteAssociatedIndicatorUrls(uniqueId, urlTexts);
    }

    @Override
    public WriteListResponse<String> deleteAssociatedIndicatorUrls(Integer uniqueId, List<String> urlTexts, String ownerName) throws IOException {
        return indAssocWriter.deleteAssociatedIndicatorUrls(uniqueId, urlTexts, ownerName);
    }

    @Override
    public ApiEntitySingleResponse deleteAssociatedIndicatorUrl(Integer uniqueId, String urlText) throws IOException, FailedResponseException {
        return indAssocWriter.deleteAssociatedIndicatorUrl(uniqueId, urlText);
    }

    @Override
    public ApiEntitySingleResponse deleteAssociatedIndicatorUrl(Integer uniqueId, String urlText, String ownerName) throws IOException, FailedResponseException {
        return indAssocWriter.deleteAssociatedIndicatorUrl(uniqueId, urlText, ownerName);
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
    public WriteListResponse<Integer> deleteAssociatedVictimAssetEmailAddresses(Integer uniqueId, List<Integer> assetIds) throws IOException {
        return victimAssetAssocWriter.deleteAssociatedVictimAssetEmailAddresses(uniqueId, assetIds);
    }

    @Override
    public WriteListResponse<Integer> deleteAssociatedVictimAssetEmailAddresses(Integer uniqueId, List<Integer> assetIds, String ownerName) throws IOException {
        return victimAssetAssocWriter.deleteAssociatedVictimAssetEmailAddresses(uniqueId, assetIds, ownerName);
    }

    @Override
    public ApiEntitySingleResponse deleteAssociatedVictimAssetEmailAddress(Integer uniqueId, Integer assetId) throws IOException, FailedResponseException {
        return victimAssetAssocWriter.deleteAssociatedVictimAssetEmailAddress(uniqueId, assetId);
    }

    @Override
    public ApiEntitySingleResponse deleteAssociatedVictimAssetEmailAddress(Integer uniqueId, Integer assetId, String ownerName) throws IOException, FailedResponseException {
        return victimAssetAssocWriter.deleteAssociatedVictimAssetEmailAddress(uniqueId, assetId, ownerName);
    }

    @Override
    public WriteListResponse<Integer> deleteAssociatedVictimAssetNetworkAccounts(Integer uniqueId, List<Integer> assetIds) throws IOException {
        return victimAssetAssocWriter.deleteAssociatedVictimAssetNetworkAccounts(uniqueId, assetIds);
    }

    @Override
    public WriteListResponse<Integer> deleteAssociatedVictimAssetNetworkAccounts(Integer uniqueId, List<Integer> assetIds, String ownerName) throws IOException {
        return victimAssetAssocWriter.deleteAssociatedVictimAssetNetworkAccounts(uniqueId, assetIds, ownerName);
    }

    @Override
    public ApiEntitySingleResponse deleteAssociatedVictimAssetNetworkAccount(Integer uniqueId, Integer assetId) throws IOException, FailedResponseException {
        return victimAssetAssocWriter.associateVictimAssetNetworkAccount(uniqueId, assetId);
    }

    @Override
    public ApiEntitySingleResponse deleteAssociatedVictimAssetNetworkAccount(Integer uniqueId, Integer assetId, String ownerName) throws IOException, FailedResponseException {
        return victimAssetAssocWriter.deleteAssociatedVictimAssetNetworkAccount(uniqueId, assetId, ownerName);
    }

    @Override
    public WriteListResponse<Integer> deleteAssociatedVictimAssetPhoneNumbers(Integer uniqueId, List<Integer> assetIds) throws IOException {
        return victimAssetAssocWriter.deleteAssociatedVictimAssetPhoneNumbers(uniqueId, assetIds);
    }

    @Override
    public WriteListResponse<Integer> deleteAssociatedVictimAssetPhoneNumbers(Integer uniqueId, List<Integer> assetIds, String ownerName) throws IOException {
        return victimAssetAssocWriter.deleteAssociatedVictimAssetPhoneNumbers(uniqueId, assetIds, ownerName);
    }

    @Override
    public ApiEntitySingleResponse deleteAssociatedVictimAssetPhoneNumber(Integer uniqueId, Integer assetId) throws IOException, FailedResponseException {
        return victimAssetAssocWriter.deleteAssociatedVictimAssetPhoneNumber(uniqueId, assetId);
    }

    @Override
    public ApiEntitySingleResponse deleteAssociatedVictimAssetPhoneNumber(Integer uniqueId, Integer assetId, String ownerName) throws IOException, FailedResponseException {
        return victimAssetAssocWriter.deleteAssociatedVictimAssetPhoneNumber(uniqueId, assetId, ownerName);
    }

    @Override
    public WriteListResponse<Integer> deleteAssociatedVictimAssetSocialNetworks(Integer uniqueId, List<Integer> assetIds) throws IOException, FailedResponseException {
        return victimAssetAssocWriter.deleteAssociatedVictimAssetSocialNetworks(uniqueId, assetIds);
    }

    @Override
    public WriteListResponse<Integer> deleteAssociatedVictimAssetSocialNetworks(Integer uniqueId, List<Integer> assetIds, String ownerName) throws IOException {
        return victimAssetAssocWriter.deleteAssociatedVictimAssetSocialNetworks(uniqueId, assetIds, ownerName);
    }

    @Override
    public ApiEntitySingleResponse deleteAssociatedVictimAssetSocialNetwork(Integer uniqueId, Integer assetId) throws IOException, FailedResponseException {
        return victimAssetAssocWriter.deleteAssociatedVictimAssetSocialNetwork(uniqueId, assetId);
    }

    @Override
    public ApiEntitySingleResponse deleteAssociatedVictimAssetSocialNetwork(Integer uniqueId, Integer assetId, String ownerName) throws IOException, FailedResponseException {
        return victimAssetAssocWriter.deleteAssociatedVictimAssetSocialNetwork(uniqueId, assetId, ownerName);
    }

    @Override
    public WriteListResponse<Integer> deleteAssociatedVictimAssetWebsites(Integer uniqueId, List<Integer> assetIds) throws IOException {
        return victimAssetAssocWriter.deleteAssociatedVictimAssetWebsites(uniqueId, assetIds);
    }

    @Override
    public WriteListResponse<Integer> deleteAssociatedVictimAssetWebsites(Integer uniqueId, List<Integer> assetIds, String ownerName) throws IOException {
        return victimAssetAssocWriter.deleteAssociatedVictimAssetWebsites(uniqueId, assetIds, ownerName);
    }

    @Override
    public ApiEntitySingleResponse deleteAssociatedVictimAssetWebsite(Integer uniqueId, Integer assetId) throws IOException, FailedResponseException {
        return victimAssetAssocWriter.deleteAssociatedVictimAssetWebsite(uniqueId, assetId);
    }

    @Override
    public ApiEntitySingleResponse deleteAssociatedVictimAssetWebsite(Integer uniqueId, Integer assetId, String ownerName) throws IOException, FailedResponseException {
        return victimAssetAssocWriter.deleteAssociatedVictimAssetWebsite(uniqueId, assetId, ownerName);
    }

    @Override
    public WriteListResponse<String> deleteAssociatedTags(Integer uniqueId, List<String> tagNames) throws IOException {
        return tagAssocWriter.deleteAssociatedTags(uniqueId, tagNames);
    }

    @Override
    public WriteListResponse<String> deleteAssociatedTags(Integer uniqueId, List<String> tagNames, String ownerName) throws IOException {
        return tagAssocWriter.deleteAssociatedTags(uniqueId, tagNames, ownerName);
    }

    @Override
    public ApiEntitySingleResponse deleteAssociatedTag(Integer uniqueId, String tagName) throws IOException, FailedResponseException {
        return tagAssocWriter.deleteAssociatedTag(uniqueId, tagName);
    }

    @Override
    public ApiEntitySingleResponse deleteAssociatedTag(Integer uniqueId, String tagName, String ownerName) throws IOException, FailedResponseException {
        return tagAssocWriter.deleteAssociatedTag(uniqueId, tagName, ownerName);
    }

    @Override
    public WriteListResponse<String> deleteAssociatedSecurityLabel(Integer uniqueId, List<String> securityLabels) throws IOException {
        return secLabelAssocWriter.deleteAssociatedSecurityLabel(uniqueId, securityLabels);
    }

    @Override
    public WriteListResponse<String> deleteAssociatedSecurityLabel(Integer uniqueId, List<String> securityLabels, String ownerName) throws IOException {
        return secLabelAssocWriter.deleteAssociatedSecurityLabel(uniqueId, securityLabels, ownerName);
    }

    @Override
    public ApiEntitySingleResponse deleteAssociatedSecurityLabel(Integer uniqueId, String securityLabel) throws IOException, FailedResponseException {
        return secLabelAssocWriter.deleteAssociatedSecurityLabel(uniqueId, securityLabel);
    }

    @Override
    public ApiEntitySingleResponse deleteAssociatedSecurityLabel(Integer uniqueId, String securityLabel, String ownerName) throws IOException, FailedResponseException {
        return secLabelAssocWriter.deleteAssociatedSecurityLabel(uniqueId, securityLabel, ownerName);
    }

}
