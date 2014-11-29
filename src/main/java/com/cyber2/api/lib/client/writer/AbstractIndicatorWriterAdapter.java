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
import com.cyber2.api.lib.server.entity.Indicator;
import com.cyber2.api.lib.server.response.entity.ApiEntitySingleResponse;
import java.io.IOException;
import java.util.List;

/**
 * AbstractIndicatorWriterAdapter is the primary writer adapter for all Indicator level objects.
 * It uses the {@link Connection} object to execute requests against the {@link RequestExecutor} object.
 * The responsibility of this class is to encapsulate all the low level ThreatConnect API calls
 * specifically targeted at data under the Indicator T type.
 * 
 *
 * @author dtineo
 * @param <T>
 */
public abstract class AbstractIndicatorWriterAdapter<T extends Indicator> 
    extends AbstractBaseWriterAdapter<T,String> 
    implements UrlTypeable, GroupAssociateWritable<String>, IndicatorAssociateWritable<String>
             , AttributeAssociateWritable<String>, TagAssociateWritable<String>
             , SecurityLabelAssociateWritable<String>, VictimAssetAssociateWritable<String>  {

    // composite pattern
    private AbstractAttributeAssociateWriterAdapter<T,String> attribWriter;
    private AbstractGroupAssociateWriterAdapter<T,String> groupAssocWriter;
    private AbstractIndicatorAssociateWriterAdapter<T,String> indAssocWriter;
    private AbstractSecurityLabelAssociateWriterAdapter<T,String> secLabelAssocWriter;
    private AbstractTagAssociateWriterAdapter<T,String> tagAssocWriter;
    private AbstractVictimAssetAssociateWriterAdapter<T,String> victimAssetAssocWriter;

    /**
     * Package level constructor. Use the {@link WriterAdapterFactory} to access this object.
     * @param conn      Primary connection object to the ThreatConnect API
     * @param executor  Executor handling low level HTTPS calls to the ThreatConnect API
     * @param singleType
     * 
     * @see WriterAdapterFactory
     */
    protected AbstractIndicatorWriterAdapter(Connection conn, RequestExecutor executor
                , Class<? extends ApiEntitySingleResponse> singleType ) {
        super(conn, executor, singleType );

        initComposite();
    }

    private void initComposite() {
        attribWriter = new AbstractAttributeAssociateWriterAdapter<T,String>(
                            AbstractIndicatorWriterAdapter.this.getConn()
                          , AbstractIndicatorWriterAdapter.this.executor
                          , AbstractIndicatorWriterAdapter.this.singleType
            ) {
            @Override
            protected String getUrlBasePrefix() {
                return AbstractIndicatorWriterAdapter.this.getUrlBasePrefix();
            }

            @Override
            public String getId(T item) {
                return AbstractIndicatorWriterAdapter.this.getId(item);
            }

        };

        groupAssocWriter = new AbstractGroupAssociateWriterAdapter<T,String>(
                            AbstractIndicatorWriterAdapter.this.getConn()
                          , AbstractIndicatorWriterAdapter.this.executor
                          , AbstractIndicatorWriterAdapter.this.singleType
            ) {
            @Override
            protected String getUrlBasePrefix() {
                return AbstractIndicatorWriterAdapter.this.getUrlBasePrefix();
            }

            @Override
            public String getId(T item) {
                return AbstractIndicatorWriterAdapter.this.getId(item);
            }
        };

        indAssocWriter = new AbstractIndicatorAssociateWriterAdapter<T,String>(
                            AbstractIndicatorWriterAdapter.this.getConn()
                          , AbstractIndicatorWriterAdapter.this.executor
                          , AbstractIndicatorWriterAdapter.this.singleType
            ) {
            @Override
            protected String getUrlBasePrefix() {
                return AbstractIndicatorWriterAdapter.this.getUrlBasePrefix();
            }

            @Override
            public String getUrlType() {
                return AbstractIndicatorWriterAdapter.this.getUrlType();
            }

            @Override
            public String getId(T item) {
                return AbstractIndicatorWriterAdapter.this.getId(item);
            }
        };

        secLabelAssocWriter = new AbstractSecurityLabelAssociateWriterAdapter<T,String>(
                            AbstractIndicatorWriterAdapter.this.getConn()
                          , AbstractIndicatorWriterAdapter.this.executor
                          , AbstractIndicatorWriterAdapter.this.singleType
            ) {
            @Override
            protected String getUrlBasePrefix() {
                return AbstractIndicatorWriterAdapter.this.getUrlBasePrefix();
            }

            @Override
            public String getId(T item) {
                return AbstractIndicatorWriterAdapter.this.getId(item);
            }

            @Override
            public String getUrlType() {
                return AbstractIndicatorWriterAdapter.this.getUrlType();
            }
        };

        tagAssocWriter = new AbstractTagAssociateWriterAdapter<T,String>(
                            AbstractIndicatorWriterAdapter.this.getConn()
                          , AbstractIndicatorWriterAdapter.this.executor
                          , AbstractIndicatorWriterAdapter.this.singleType
            ) {
            @Override
            protected String getUrlBasePrefix() {
                return AbstractIndicatorWriterAdapter.this.getUrlBasePrefix();
            }

            @Override
            public String getUrlType() {
                return AbstractIndicatorWriterAdapter.this.getUrlType();
            }

            @Override
            public String getId(T item) {
                return AbstractIndicatorWriterAdapter.this.getId(item);
            }
        };

        victimAssetAssocWriter = new AbstractVictimAssetAssociateWriterAdapter<T,String>(
                            AbstractIndicatorWriterAdapter.this.getConn()
                          , AbstractIndicatorWriterAdapter.this.executor
                          , AbstractIndicatorWriterAdapter.this.singleType
            ) {
            @Override
            protected String getUrlBasePrefix() {
                return AbstractIndicatorWriterAdapter.this.getUrlBasePrefix();
            }

            @Override
            public String getId(T item) {
                return AbstractIndicatorWriterAdapter.this.getId(item);
            }

            @Override
            public String getUrlType() {
                return AbstractIndicatorWriterAdapter.this.getUrlType();
            }
        };

    }

    @Override
    protected String getUrlBasePrefix() {
        return "v2.indicators.type";
    }

   @Override
    public WriteListResponse<Integer> associateGroupAdversaries(String uniqueId, List<Integer> adversaryIds) throws IOException {
        return groupAssocWriter.associateGroupAdversaries(uniqueId, adversaryIds);
    }

    @Override
    public WriteListResponse<Integer> associateGroupAdversaries(String uniqueId, List<Integer> adversaryIds, String ownerName) throws IOException {
        return groupAssocWriter.associateGroupAdversaries(uniqueId, adversaryIds, ownerName);
    }

    @Override
    public ApiEntitySingleResponse associateGroupAdversary(String uniqueId, Integer adversaryId) throws IOException, FailedResponseException {
        return groupAssocWriter.associateGroupAdversary(uniqueId, adversaryId);
    }

    @Override
    public ApiEntitySingleResponse associateGroupAdversary(String uniqueId, Integer adversaryId, String ownerName) throws IOException, FailedResponseException {
        return groupAssocWriter.associateGroupAdversary(uniqueId, adversaryId, ownerName);
    }

    @Override
    public WriteListResponse<Integer> associateGroupEmails(String uniqueId, List<Integer> emailIds) throws IOException {
        return groupAssocWriter.associateGroupEmails(uniqueId, emailIds);
    }

    @Override
    public WriteListResponse<Integer> associateGroupEmails(String uniqueId, List<Integer> emailIds, String ownerName) throws IOException {
        return groupAssocWriter.associateGroupEmails(uniqueId, emailIds, ownerName);
    }

    @Override
    public ApiEntitySingleResponse associateGroupEmail(String uniqueId, Integer emailId) throws IOException, FailedResponseException {
        return groupAssocWriter.associateGroupEmail(uniqueId, emailId);
    }

    @Override
    public ApiEntitySingleResponse associateGroupEmail(String uniqueId, Integer emailId, String ownerName) throws IOException, FailedResponseException {
        return groupAssocWriter.associateGroupEmail(uniqueId, emailId, ownerName);
    }

    @Override
    public WriteListResponse<Integer> associateGroupIncidents(String uniqueId, List<Integer> incidentIds) throws IOException {
        return groupAssocWriter.associateGroupIncidents(uniqueId, incidentIds);
    }

    @Override
    public WriteListResponse<Integer> associateGroupIncidents(String uniqueId, List<Integer> incidentIds, String ownerName) throws IOException {
        return groupAssocWriter.associateGroupIncidents(uniqueId, incidentIds, ownerName);
    }

    @Override
    public ApiEntitySingleResponse associateGroupIncident(String uniqueId, Integer incidentId) throws IOException, FailedResponseException {
        return groupAssocWriter.associateGroupIncident(uniqueId, incidentId);
    }

    @Override
    public ApiEntitySingleResponse associateGroupIncident(String uniqueId, Integer incidentId, String ownerName) throws IOException, FailedResponseException {
        return groupAssocWriter.associateGroupIncident(uniqueId, incidentId, ownerName);
    }

    @Override
    public WriteListResponse<Integer> associateGroupSignatures(String uniqueId, List<Integer> signatureIds) throws IOException {
        return groupAssocWriter.associateGroupSignatures(uniqueId, signatureIds);
    }

    @Override
    public WriteListResponse<Integer> associateGroupSignatures(String uniqueId, List<Integer> signatureIds, String ownerName) throws IOException {
        return groupAssocWriter.associateGroupSignatures(uniqueId, signatureIds, ownerName);
    }

    @Override
    public ApiEntitySingleResponse associateGroupSignature(String uniqueId, Integer signatureId) throws IOException, FailedResponseException {
        return groupAssocWriter.associateGroupSignature(uniqueId, signatureId);
    }

    @Override
    public ApiEntitySingleResponse associateGroupSignature(String uniqueId, Integer signatureId, String ownerName) throws IOException, FailedResponseException {
        return groupAssocWriter.associateGroupSignature(uniqueId, signatureId, ownerName);
    }

    @Override
    public WriteListResponse<Integer> associateGroupThreats(String uniqueId, List<Integer> threatIds) throws IOException {
        return groupAssocWriter.associateGroupThreats(uniqueId, threatIds);
    }

    @Override
    public WriteListResponse<Integer> associateGroupThreats(String uniqueId, List<Integer> threatIds, String ownerName) throws IOException {
        return groupAssocWriter.associateGroupThreats(uniqueId, threatIds, ownerName);
    }

    @Override
    public ApiEntitySingleResponse associateGroupThreat(String uniqueId, Integer threatId) throws IOException, FailedResponseException {
        return groupAssocWriter.associateGroupThreat(uniqueId, threatId);
    }

    @Override
    public ApiEntitySingleResponse associateGroupThreat(String uniqueId, Integer threatId, String ownerName) throws IOException, FailedResponseException {
        return groupAssocWriter.associateGroupThreat(uniqueId, threatId, ownerName);
    }

    @Override
    public WriteListResponse<String> associateIndicatorAddresses(String uniqueId, List<String> ipAddresses) throws IOException {
        return indAssocWriter.associateIndicatorAddresses(uniqueId, ipAddresses);
    }

    @Override
    public WriteListResponse<String> associateIndicatorAddresses(String uniqueId, List<String> ipAddresses, String ownerName) throws IOException {
        return indAssocWriter.associateIndicatorAddresses(uniqueId, ipAddresses, ownerName);
    }

    @Override
    public ApiEntitySingleResponse associateIndicatorAddress(String uniqueId, String ipAddress) throws IOException, FailedResponseException {
        return indAssocWriter.associateIndicatorAddress(uniqueId, ipAddress);
    }

    @Override
    public ApiEntitySingleResponse associateIndicatorAddress(String uniqueId, String ipAddress, String ownerName) throws IOException, FailedResponseException {
        return indAssocWriter.associateIndicatorAddress(uniqueId, ipAddress, ownerName);
    }

    @Override
    public WriteListResponse<String> associateIndicatorEmailAddresses(String uniqueId, List<String> emailAddresses) throws IOException {
        return indAssocWriter.associateIndicatorEmailAddresses(uniqueId, emailAddresses);
    }

    @Override
    public WriteListResponse<String> associateIndicatorEmailAddresses(String uniqueId, List<String> emailAddresses, String ownerName) throws IOException {
        return indAssocWriter.associateIndicatorEmailAddresses(uniqueId, emailAddresses, ownerName);
    }

    @Override
    public ApiEntitySingleResponse associateIndicatorEmailAddress(String uniqueId, String emailAddress) throws IOException, FailedResponseException {
        return indAssocWriter.associateIndicatorEmailAddress(uniqueId, emailAddress);
    }

    @Override
    public ApiEntitySingleResponse associateIndicatorEmailAddress(String uniqueId, String emailAddress, String ownerName) throws IOException, FailedResponseException {
        return indAssocWriter.associateIndicatorAddress(uniqueId, emailAddress, ownerName);
    }

    @Override
    public WriteListResponse<String> associateIndicatorFiles(String uniqueId, List<String> fileHashes) throws IOException {
        return indAssocWriter.associateIndicatorFiles(uniqueId, fileHashes);
    }

    @Override
    public WriteListResponse<String> associateIndicatorFiles(String uniqueId, List<String> fileHashes, String ownerName) throws IOException {
        return indAssocWriter.associateIndicatorFiles(uniqueId, fileHashes, ownerName);
    }

    @Override
    public ApiEntitySingleResponse associateIndicatorFile(String uniqueId, String fileHash) throws IOException, FailedResponseException {
        return indAssocWriter.associateIndicatorFile(uniqueId, fileHash);
    }

    @Override
    public ApiEntitySingleResponse associateIndicatorFile(String uniqueId, String fileHash, String ownerName) throws IOException, FailedResponseException {
        return indAssocWriter.associateIndicatorAddress(uniqueId, fileHash, ownerName);
    }

    @Override
    public WriteListResponse<String> associateIndicatorHosts(String uniqueId, List<String> hostNames) throws IOException {
        return indAssocWriter.associateIndicatorHosts(uniqueId, hostNames);
    }

    @Override
    public WriteListResponse<String> associateIndicatorHosts(String uniqueId, List<String> hostNames, String ownerName) throws IOException {
        return indAssocWriter.associateIndicatorHosts(uniqueId, hostNames, ownerName);
    }

    @Override
    public ApiEntitySingleResponse associateIndicatorHost(String uniqueId, String hostName) throws IOException, FailedResponseException {
        return indAssocWriter.associateIndicatorHost(uniqueId, hostName);
    }

    @Override
    public ApiEntitySingleResponse associateIndicatorHost(String uniqueId, String hostName, String ownerName) throws IOException, FailedResponseException {
        return indAssocWriter.associateIndicatorHost(uniqueId, hostName, ownerName);
    }

    @Override
    public WriteListResponse<String> associateIndicatorUrls(String uniqueId, List<String> urlTexts) throws IOException {
        return indAssocWriter.associateIndicatorUrls(uniqueId, urlTexts);
    }

    @Override
    public WriteListResponse<String> associateIndicatorUrls(String uniqueId, List<String> urlTexts, String ownerName) throws IOException {
        return indAssocWriter.associateIndicatorUrls(uniqueId, urlTexts, ownerName);
    }

    @Override
    public ApiEntitySingleResponse associateIndicatorUrl(String uniqueId, String urlText) throws IOException, FailedResponseException {
        return indAssocWriter.associateIndicatorUrl(uniqueId, urlText);
    }

    @Override
    public ApiEntitySingleResponse associateIndicatorUrl(String uniqueId, String urlText, String ownerName) throws IOException, FailedResponseException {
        return indAssocWriter.associateIndicatorUrl(uniqueId, urlText, ownerName);
    }

    @Override
    public WriteListResponse<Attribute> addAttributes(String uniqueId, List<Attribute> attributes) throws IOException {
        return attribWriter.addAttributes(uniqueId, attributes);
    }

    @Override
    public WriteListResponse<Attribute> addAttributes(String uniqueId, List<Attribute> attribute, String ownerName) throws IOException {
        return attribWriter.addAttributes(uniqueId, attribute, ownerName);
    }

    @Override
    public Attribute addAttribute(String uniqueId, Attribute attribute) throws IOException, FailedResponseException {
        return attribWriter.addAttribute(uniqueId, attribute);
    }

    @Override
    public Attribute addAttribute(String uniqueId, Attribute attribute, String ownerName) throws IOException, FailedResponseException {
        return attribWriter.addAttribute(uniqueId, attribute, ownerName);
    }

    @Override
    public WriteListResponse<String> addAttributeSecurityLabels(String uniqueId, Integer attributeId, List<String> securityLabels) throws IOException {
        return attribWriter.addAttributeSecurityLabels(uniqueId, attributeId, securityLabels);
    }

    @Override
    public WriteListResponse<String> addAttributeSecurityLabels(String uniqueId, Integer attributeId, List<String> securityLabels, String ownerName) throws IOException {
        return attribWriter.addAttributeSecurityLabels(uniqueId, attributeId, securityLabels, ownerName);
    }

    @Override
    public ApiEntitySingleResponse addAttributeSecurityLabel(String uniqueId, Integer attributeId, String securityLabel) throws IOException, FailedResponseException {
        return attribWriter.addAttributeSecurityLabel(uniqueId, attributeId, securityLabel);
    }

    @Override
    public ApiEntitySingleResponse addAttributeSecurityLabel(String uniqueId, Integer attributeId, String securityLabel, String ownerName) throws IOException, FailedResponseException {
        return attribWriter.addAttributeSecurityLabel(uniqueId, attributeId, securityLabel, ownerName);
    }

    @Override
    public WriteListResponse<String> associateTags(String uniqueId, List<String> tagNames) throws IOException {
        return tagAssocWriter.associateTags(uniqueId, tagNames);
    }

    @Override
    public WriteListResponse<String> associateTags(String uniqueId, List<String> tagNames, String ownerName) throws IOException {
        return tagAssocWriter.associateTags(uniqueId, tagNames, ownerName);
    }

    @Override
    public ApiEntitySingleResponse associateTag(String uniqueId, String tagName) throws IOException, FailedResponseException {
        return tagAssocWriter.associateTag(uniqueId, tagName);
    }

    @Override
    public ApiEntitySingleResponse associateTag(String uniqueId, String tagName, String ownerName) throws IOException, FailedResponseException {
        return tagAssocWriter.associateTag(uniqueId, tagName, ownerName);
    }

    @Override
    public WriteListResponse<String> associateSecurityLabels(String uniqueId, List<String> securityLabels) throws IOException {
        return secLabelAssocWriter.associateSecurityLabels(uniqueId, securityLabels);
    }

    @Override
    public WriteListResponse<String> associateSecurityLabels(String uniqueId, List<String> securityLabels, String ownerName) throws IOException {
        return secLabelAssocWriter.associateSecurityLabels(uniqueId, securityLabels, ownerName);
    }

    @Override
    public ApiEntitySingleResponse associateSecurityLabel(String uniqueId, String securityLabel) throws IOException, FailedResponseException {
        return secLabelAssocWriter.associateSecurityLabel(uniqueId, securityLabel);
    }

    @Override
    public ApiEntitySingleResponse associateSecurityLabel(String uniqueId, String securityLabel, String ownerName) throws IOException, FailedResponseException {
        return secLabelAssocWriter.associateSecurityLabel(uniqueId, securityLabel, ownerName);
    }

    @Override
    public WriteListResponse<Integer> deleteAssociatedGroupAdversaries(String uniqueId, List<Integer> adversaryIds) throws IOException {
        return groupAssocWriter.deleteAssociatedGroupAdversaries(uniqueId, adversaryIds);
    }

    @Override
    public WriteListResponse<Integer> deleteAssociatedGroupAdversaries(String uniqueId, List<Integer> adversaryIds, String ownerName) throws IOException {
        return groupAssocWriter.deleteAssociatedGroupAdversaries(uniqueId, adversaryIds, ownerName);
    }

    @Override
    public ApiEntitySingleResponse deleteAssociatedGroupAdversary(String uniqueId, Integer adversaryId) throws IOException, FailedResponseException {
        return groupAssocWriter.deleteAssociatedGroupAdversary(uniqueId, adversaryId);
    }

    @Override
    public ApiEntitySingleResponse deleteAssociatedGroupAdversary(String uniqueId, Integer adversaryId, String ownerName) throws IOException, FailedResponseException {
        return groupAssocWriter.deleteAssociatedGroupAdversary(uniqueId, adversaryId, ownerName);
    }

    @Override
    public WriteListResponse<Integer> deleteAssociatedGroupEmails(String uniqueId, List<Integer> emailIds) throws IOException {
        return groupAssocWriter.deleteAssociatedGroupEmails(uniqueId, emailIds);
    }

    @Override
    public WriteListResponse<Integer> deleteAssociatedGroupEmails(String uniqueId, List<Integer> emailIds, String ownerName) throws IOException {
        return groupAssocWriter.deleteAssociatedGroupEmails(uniqueId, emailIds, ownerName);
    }

    @Override
    public ApiEntitySingleResponse deleteAssociatedGroupEmail(String uniqueId, Integer emailId) throws IOException, FailedResponseException {
        return groupAssocWriter.deleteAssociatedGroupEmail(uniqueId, emailId);
    }

    @Override
    public ApiEntitySingleResponse deleteAssociatedGroupEmail(String uniqueId, Integer emailId, String ownerName) throws IOException, FailedResponseException {
        return groupAssocWriter.deleteAssociatedGroupEmail(uniqueId, emailId, ownerName);
    }

    @Override
    public WriteListResponse<Integer> deleteAssociatedGroupIncidents(String uniqueId, List<Integer> incidentIds) throws IOException {
        return groupAssocWriter.deleteAssociatedGroupIncidents(uniqueId, incidentIds);
    }

    @Override
    public WriteListResponse<Integer> deleteAssociatedGroupIncidents(String uniqueId, List<Integer> incidentIds, String ownerName) throws IOException {
        return groupAssocWriter.deleteAssociatedGroupIncidents(uniqueId, incidentIds, ownerName);
    }

    @Override
    public ApiEntitySingleResponse deleteAssociatedGroupIncident(String uniqueId, Integer incidentId) throws IOException, FailedResponseException {
        return groupAssocWriter.deleteAssociatedGroupIncident(uniqueId, incidentId);
    }

    @Override
    public ApiEntitySingleResponse deleteAssociatedGroupIncident(String uniqueId, Integer incidentId, String ownerName) throws IOException, FailedResponseException {
        return groupAssocWriter.deleteAssociatedGroupIncident(uniqueId, incidentId, ownerName);
    }

    @Override
    public WriteListResponse<Integer> deleteAssociatedGroupSignatures(String uniqueId, List<Integer> signatureIds) throws IOException {
        return groupAssocWriter.deleteAssociatedGroupSignatures(uniqueId, signatureIds);
    }

    @Override
    public WriteListResponse<Integer> deleteAssociatedGroupSignatures(String uniqueId, List<Integer> signatureIds, String ownerName) throws IOException {
        return groupAssocWriter.deleteAssociatedGroupSignatures(uniqueId, signatureIds, ownerName);
    }

    @Override
    public ApiEntitySingleResponse deleteAssociatedGroupSignature(String uniqueId, Integer signatureId) throws IOException, FailedResponseException {
        return groupAssocWriter.deleteAssociatedGroupSignature(uniqueId, signatureId);
    }

    @Override
    public ApiEntitySingleResponse deleteAssociatedGroupSignature(String uniqueId, Integer signatureId, String ownerName) throws IOException, FailedResponseException {
        return groupAssocWriter.deleteAssociatedGroupSignature(uniqueId, signatureId, ownerName);
    }

    @Override
    public WriteListResponse<Integer> deleteAssociatedGroupThreats(String uniqueId, List<Integer> threatIds) throws IOException {
        return groupAssocWriter.deleteAssociatedGroupThreats(uniqueId, threatIds);
    }

    @Override
    public WriteListResponse<Integer> deleteAssociatedGroupThreats(String uniqueId, List<Integer> threatIds, String ownerName) throws IOException {
        return groupAssocWriter.deleteAssociatedGroupThreats(uniqueId, threatIds, ownerName);
    }

    @Override
    public ApiEntitySingleResponse deleteAssociatedGroupThreat(String uniqueId, Integer threatId) throws IOException, FailedResponseException {
        return groupAssocWriter.deleteAssociatedGroupThreat(uniqueId, threatId);
    }

    @Override
    public ApiEntitySingleResponse deleteAssociatedGroupThreat(String uniqueId, Integer threatId, String ownerName) throws IOException, FailedResponseException {
        return groupAssocWriter.deleteAssociatedGroupThreat(uniqueId, threatId, ownerName);
    }

    @Override
    public WriteListResponse<String> deleteAssociatedIndicatorAddresses(String uniqueId, List<String> ipAddresses) throws IOException {
        return indAssocWriter.deleteAssociatedIndicatorAddresses(uniqueId, ipAddresses);
    }

    @Override
    public WriteListResponse<String> deleteAssociatedIndicatorAddresses(String uniqueId, List<String> ipAddresses, String ownerName) throws IOException {
        return indAssocWriter.deleteAssociatedIndicatorAddresses(uniqueId, ipAddresses, ownerName);
    }

    @Override
    public ApiEntitySingleResponse deleteAssociatedIndicatorAddress(String uniqueId, String ipAddress) throws IOException, FailedResponseException {
        return indAssocWriter.deleteAssociatedIndicatorAddress(uniqueId, ipAddress);
    }

    @Override
    public ApiEntitySingleResponse deleteAssociatedIndicatorAddress(String uniqueId, String ipAddress, String ownerName) throws IOException, FailedResponseException {
        return indAssocWriter.deleteAssociatedIndicatorAddress(uniqueId, ipAddress, ownerName);
    }

    @Override
    public WriteListResponse<String> deleteAssociatedIndicatorEmailAddresses(String uniqueId, List<String> emailAddresses) throws IOException {
        return indAssocWriter.deleteAssociatedIndicatorEmailAddresses(uniqueId, emailAddresses);
    }

    @Override
    public WriteListResponse<String> deleteAssociatedIndicatorEmailAddresses(String uniqueId, List<String> emailAddresses, String ownerName) throws IOException {
        return indAssocWriter.deleteAssociatedIndicatorEmailAddresses(uniqueId, emailAddresses, ownerName);
    }

    @Override
    public ApiEntitySingleResponse deleteAssociatedIndicatorEmailAddress(String uniqueId, String emailAddress) throws IOException, FailedResponseException {
        return indAssocWriter.deleteAssociatedIndicatorEmailAddress(uniqueId, emailAddress);
    }

    @Override
    public ApiEntitySingleResponse deleteAssociatedIndicatorEmailAddress(String uniqueId, String emailAddress, String ownerName) throws IOException, FailedResponseException {
        return indAssocWriter.deleteAssociatedIndicatorEmailAddress(uniqueId, emailAddress, ownerName);
    }

    @Override
    public WriteListResponse<String> deleteAssociatedIndicatorFiles(String uniqueId, List<String> fileHashes) throws IOException {
        return indAssocWriter.deleteAssociatedIndicatorFiles(uniqueId, fileHashes);
    }

    @Override
    public WriteListResponse<String> deleteAssociatedIndicatorFiles(String uniqueId, List<String> fileHashes, String ownerName) throws IOException {
        return indAssocWriter.deleteAssociatedIndicatorFiles(uniqueId, fileHashes, ownerName);
    }

    @Override
    public ApiEntitySingleResponse deleteAssociatedIndicatorFile(String uniqueId, String fileHash) throws IOException, FailedResponseException {
        return indAssocWriter.deleteAssociatedIndicatorFile(uniqueId, fileHash);
    }

    @Override
    public ApiEntitySingleResponse deleteAssociatedIndicatorFile(String uniqueId, String fileHash, String ownerName) throws IOException, FailedResponseException {
        return indAssocWriter.deleteAssociatedIndicatorFile(uniqueId, fileHash, ownerName);
    }

    @Override
    public WriteListResponse<String> deleteAssociatedIndicatorHosts(String uniqueId, List<String> hostNames) throws IOException {
        return indAssocWriter.deleteAssociatedIndicatorHosts(uniqueId, hostNames);
    }

    @Override
    public WriteListResponse<String> deleteAssociatedIndicatorHosts(String uniqueId, List<String> hostNames, String ownerName) throws IOException {
        return indAssocWriter.deleteAssociatedIndicatorHosts(uniqueId, hostNames, ownerName);
    }

    @Override
    public ApiEntitySingleResponse deleteAssociatedIndicatorHost(String uniqueId, String hostName) throws IOException, FailedResponseException {
        return indAssocWriter.deleteAssociatedIndicatorHost(uniqueId, hostName);
    }

    @Override
    public ApiEntitySingleResponse deleteAssociatedIndicatorHost(String uniqueId, String hostName, String ownerName) throws IOException, FailedResponseException {
        return indAssocWriter.deleteAssociatedIndicatorHost(uniqueId, hostName, ownerName);
    }

    @Override
    public WriteListResponse<String> deleteAssociatedIndicatorUrls(String uniqueId, List<String> urlTexts) throws IOException {
        return indAssocWriter.deleteAssociatedIndicatorUrls(uniqueId, urlTexts);
    }

    @Override
    public WriteListResponse<String> deleteAssociatedIndicatorUrls(String uniqueId, List<String> urlTexts, String ownerName) throws IOException {
        return indAssocWriter.deleteAssociatedIndicatorUrls(uniqueId, urlTexts, ownerName);
    }

    @Override
    public ApiEntitySingleResponse deleteAssociatedIndicatorUrl(String uniqueId, String urlText) throws IOException, FailedResponseException {
        return indAssocWriter.deleteAssociatedIndicatorUrl(uniqueId, urlText);
    }

    @Override
    public ApiEntitySingleResponse deleteAssociatedIndicatorUrl(String uniqueId, String urlText, String ownerName) throws IOException, FailedResponseException {
        return indAssocWriter.deleteAssociatedIndicatorUrl(uniqueId, urlText, ownerName);
    }

    @Override
    public WriteListResponse<Attribute> updateAttributes(String uniqueId, List<Attribute> attributes) throws IOException {
        return attribWriter.updateAttributes(uniqueId, attributes);
    }

    @Override
    public WriteListResponse<Attribute> updateAttributes(String uniqueId, List<Attribute> attribute, String ownerName) throws IOException {
        return attribWriter.updateAttributes(uniqueId, attribute, ownerName);
    }

    @Override
    public ApiEntitySingleResponse updateAttribute(String uniqueId, Attribute attribute) throws IOException, FailedResponseException {
        return attribWriter.updateAttribute(uniqueId, attribute);
    }

    @Override
    public ApiEntitySingleResponse updateAttribute(String uniqueId, Attribute attribute, String ownerName) throws IOException, FailedResponseException {
        return attribWriter.updateAttribute(uniqueId, attribute, ownerName);
    }

    @Override
    public WriteListResponse<Integer> deleteAttributes(String uniqueId, List<Integer> attributes) throws IOException {
        return attribWriter.deleteAttributes(uniqueId, attributes);
    }

    @Override
    public WriteListResponse<Integer> deleteAttributes(String uniqueId, List<Integer> attribute, String ownerName) throws IOException {
        return attribWriter.deleteAttributes(uniqueId, attribute, ownerName);
    }

    @Override
    public ApiEntitySingleResponse deleteAttribute(String uniqueId, Integer attribute) throws IOException, FailedResponseException {
        return attribWriter.deleteAttribute(uniqueId, attribute);
    }

    @Override
    public ApiEntitySingleResponse deleteAttribute(String uniqueId, Integer attribute, String ownerName) throws IOException, FailedResponseException {
        return attribWriter.deleteAttribute(uniqueId, attribute, ownerName);
    }

    @Override
    public WriteListResponse<String> deleteAttributeSecurityLabels(String uniqueId, Integer attributeId, List<String> securityLabels) throws IOException, FailedResponseException {
        return attribWriter.deleteAttributeSecurityLabels(uniqueId, attributeId, securityLabels);
    }

    @Override
    public WriteListResponse<String> deleteAttributeSecurityLabels(String uniqueId, Integer attributeId, List<String> securityLabels, String ownerName) throws IOException, FailedResponseException {
       return attribWriter.deleteAttributeSecurityLabels(uniqueId, attributeId, securityLabels, ownerName);
    }

    @Override
    public ApiEntitySingleResponse deleteAttributeSecurityLabel(String uniqueId, Integer attributeId, String securityLabel) throws IOException, FailedResponseException {
        return attribWriter.deleteAttributeSecurityLabel(uniqueId, attributeId, securityLabel);
    }

    @Override
    public ApiEntitySingleResponse deleteAttributeSecurityLabel(String uniqueId, Integer attributeId, String securityLabel, String ownerName) throws IOException, FailedResponseException {
        return attribWriter.deleteAttributeSecurityLabel(uniqueId, attributeId, securityLabel, ownerName);
    }

    @Override
    public WriteListResponse<Integer> deleteAssociatedVictimAssetEmailAddresses(String uniqueId, List<Integer> assetIds) throws IOException {
        return victimAssetAssocWriter.deleteAssociatedVictimAssetEmailAddresses(uniqueId, assetIds);
    }

    @Override
    public WriteListResponse<Integer> deleteAssociatedVictimAssetEmailAddresses(String uniqueId, List<Integer> assetIds, String ownerName) throws IOException {
        return victimAssetAssocWriter.deleteAssociatedVictimAssetEmailAddresses(uniqueId, assetIds, ownerName);
    }

    @Override
    public ApiEntitySingleResponse deleteAssociatedVictimAssetEmailAddress(String uniqueId, Integer assetId) throws IOException, FailedResponseException {
        return victimAssetAssocWriter.deleteAssociatedVictimAssetEmailAddress(uniqueId, assetId);
    }

    @Override
    public ApiEntitySingleResponse deleteAssociatedVictimAssetEmailAddress(String uniqueId, Integer assetId, String ownerName) throws IOException, FailedResponseException {
        return victimAssetAssocWriter.deleteAssociatedVictimAssetEmailAddress(uniqueId, assetId, ownerName);
    }

    @Override
    public WriteListResponse<Integer> deleteAssociatedVictimAssetNetworkAccounts(String uniqueId, List<Integer> assetIds) throws IOException {
        return victimAssetAssocWriter.deleteAssociatedVictimAssetNetworkAccounts(uniqueId, assetIds);
    }

    @Override
    public WriteListResponse<Integer> deleteAssociatedVictimAssetNetworkAccounts(String uniqueId, List<Integer> assetIds, String ownerName) throws IOException {
        return victimAssetAssocWriter.deleteAssociatedVictimAssetNetworkAccounts(uniqueId, assetIds, ownerName);
    }

    @Override
    public ApiEntitySingleResponse deleteAssociatedVictimAssetNetworkAccount(String uniqueId, Integer assetId) throws IOException, FailedResponseException {
        return victimAssetAssocWriter.associateVictimAssetNetworkAccount(uniqueId, assetId);
    }

    @Override
    public ApiEntitySingleResponse deleteAssociatedVictimAssetNetworkAccount(String uniqueId, Integer assetId, String ownerName) throws IOException, FailedResponseException {
        return victimAssetAssocWriter.deleteAssociatedVictimAssetNetworkAccount(uniqueId, assetId, ownerName);
    }

    @Override
    public WriteListResponse<Integer> deleteAssociatedVictimAssetPhoneNumbers(String uniqueId, List<Integer> assetIds) throws IOException {
        return victimAssetAssocWriter.deleteAssociatedVictimAssetPhoneNumbers(uniqueId, assetIds);
    }

    @Override
    public WriteListResponse<Integer> deleteAssociatedVictimAssetPhoneNumbers(String uniqueId, List<Integer> assetIds, String ownerName) throws IOException {
        return victimAssetAssocWriter.deleteAssociatedVictimAssetPhoneNumbers(uniqueId, assetIds, ownerName);
    }

    @Override
    public ApiEntitySingleResponse deleteAssociatedVictimAssetPhoneNumber(String uniqueId, Integer assetId) throws IOException, FailedResponseException {
        return victimAssetAssocWriter.deleteAssociatedVictimAssetPhoneNumber(uniqueId, assetId);
    }

    @Override
    public ApiEntitySingleResponse deleteAssociatedVictimAssetPhoneNumber(String uniqueId, Integer assetId, String ownerName) throws IOException, FailedResponseException {
        return victimAssetAssocWriter.deleteAssociatedVictimAssetPhoneNumber(uniqueId, assetId, ownerName);
    }

    @Override
    public WriteListResponse<Integer> deleteAssociatedVictimAssetSocialNetworks(String uniqueId, List<Integer> assetIds) throws IOException, FailedResponseException {
        return victimAssetAssocWriter.deleteAssociatedVictimAssetSocialNetworks(uniqueId, assetIds);
    }

    @Override
    public WriteListResponse<Integer> deleteAssociatedVictimAssetSocialNetworks(String uniqueId, List<Integer> assetIds, String ownerName) throws IOException {
        return victimAssetAssocWriter.deleteAssociatedVictimAssetSocialNetworks(uniqueId, assetIds, ownerName);
    }

    @Override
    public ApiEntitySingleResponse deleteAssociatedVictimAssetSocialNetwork(String uniqueId, Integer assetId) throws IOException, FailedResponseException {
        return victimAssetAssocWriter.deleteAssociatedVictimAssetSocialNetwork(uniqueId, assetId);
    }

    @Override
    public ApiEntitySingleResponse deleteAssociatedVictimAssetSocialNetwork(String uniqueId, Integer assetId, String ownerName) throws IOException, FailedResponseException {
        return victimAssetAssocWriter.deleteAssociatedVictimAssetSocialNetwork(uniqueId, assetId, ownerName);
    }

    @Override
    public WriteListResponse<Integer> deleteAssociatedVictimAssetWebsites(String uniqueId, List<Integer> assetIds) throws IOException {
        return victimAssetAssocWriter.deleteAssociatedVictimAssetWebsites(uniqueId, assetIds);
    }

    @Override
    public WriteListResponse<Integer> deleteAssociatedVictimAssetWebsites(String uniqueId, List<Integer> assetIds, String ownerName) throws IOException {
        return victimAssetAssocWriter.deleteAssociatedVictimAssetWebsites(uniqueId, assetIds, ownerName);
    }

    @Override
    public ApiEntitySingleResponse deleteAssociatedVictimAssetWebsite(String uniqueId, Integer assetId) throws IOException, FailedResponseException {
        return victimAssetAssocWriter.deleteAssociatedVictimAssetWebsite(uniqueId, assetId);
    }

    @Override
    public ApiEntitySingleResponse deleteAssociatedVictimAssetWebsite(String uniqueId, Integer assetId, String ownerName) throws IOException, FailedResponseException {
        return victimAssetAssocWriter.deleteAssociatedVictimAssetWebsite(uniqueId, assetId, ownerName);
    }

    @Override
    public WriteListResponse<String> deleteAssociatedTags(String uniqueId, List<String> tagNames) throws IOException {
        return tagAssocWriter.deleteAssociatedTags(uniqueId, tagNames);
    }

    @Override
    public WriteListResponse<String> deleteAssociatedTags(String uniqueId, List<String> tagNames, String ownerName) throws IOException {
        return tagAssocWriter.deleteAssociatedTags(uniqueId, tagNames, ownerName);
    }

    @Override
    public ApiEntitySingleResponse deleteAssociatedTag(String uniqueId, String tagName) throws IOException, FailedResponseException {
        return tagAssocWriter.deleteAssociatedTag(uniqueId, tagName);
    }

    @Override
    public ApiEntitySingleResponse deleteAssociatedTag(String uniqueId, String tagName, String ownerName) throws IOException, FailedResponseException {
        return tagAssocWriter.deleteAssociatedTag(uniqueId, tagName, ownerName);
    }

    @Override
    public WriteListResponse<String> deleteAssociatedSecurityLabel(String uniqueId, List<String> securityLabels) throws IOException {
        return secLabelAssocWriter.deleteAssociatedSecurityLabel(uniqueId, securityLabels);
    }

    @Override
    public WriteListResponse<String> deleteAssociatedSecurityLabel(String uniqueId, List<String> securityLabels, String ownerName) throws IOException {
        return secLabelAssocWriter.deleteAssociatedSecurityLabel(uniqueId, securityLabels, ownerName);
    }

    @Override
    public ApiEntitySingleResponse deleteAssociatedSecurityLabel(String uniqueId, String securityLabel) throws IOException, FailedResponseException {
        return secLabelAssocWriter.deleteAssociatedSecurityLabel(uniqueId, securityLabel);
    }

    @Override
    public ApiEntitySingleResponse deleteAssociatedSecurityLabel(String uniqueId, String securityLabel, String ownerName) throws IOException, FailedResponseException {
        return secLabelAssocWriter.deleteAssociatedSecurityLabel(uniqueId, securityLabel, ownerName);
    }

        @Override
    public WriteListResponse<Integer> associateVictimAssetEmailAddresses(String uniqueId, List<Integer> assetIds) throws IOException {
        return victimAssetAssocWriter.associateVictimAssetEmailAddresses(uniqueId, assetIds);
    }

    @Override
    public WriteListResponse<Integer> associateVictimAssetEmailAddresses(String uniqueId, List<Integer> assetIds, String ownerName) throws IOException {
        return victimAssetAssocWriter. associateVictimAssetEmailAddresses(uniqueId, assetIds, ownerName);
    }

    @Override
    public ApiEntitySingleResponse associateVictimAssetEmailAddress(String uniqueId, Integer assetId) throws IOException, FailedResponseException {
        return victimAssetAssocWriter.associateVictimAssetEmailAddress(uniqueId, assetId);
    }

    @Override
    public ApiEntitySingleResponse associateVictimAssetEmailAddress(String uniqueId, Integer assetId, String ownerName) throws IOException, FailedResponseException {
        return victimAssetAssocWriter.associateVictimAssetEmailAddress(uniqueId, assetId, ownerName);
    }

    @Override
    public WriteListResponse<Integer> associateVictimAssetNetworkAccounts(String uniqueId, List<Integer> assetIds) throws IOException {
        return victimAssetAssocWriter.associateVictimAssetNetworkAccounts(uniqueId, assetIds);
    }

    @Override
    public WriteListResponse<Integer> associateVictimAssetNetworkAccounts(String uniqueId, List<Integer> assetIds, String ownerName) throws IOException {
        return victimAssetAssocWriter.associateVictimAssetNetworkAccounts(uniqueId, assetIds, ownerName);
    }

    @Override
    public ApiEntitySingleResponse associateVictimAssetNetworkAccount(String uniqueId, Integer assetId) throws IOException, FailedResponseException {
        return victimAssetAssocWriter.associateVictimAssetNetworkAccount(uniqueId, assetId);
    }

    @Override
    public ApiEntitySingleResponse associateVictimAssetNetworkAccount(String uniqueId, Integer assetId, String ownerName) throws IOException, FailedResponseException {
        return victimAssetAssocWriter.associateVictimAssetNetworkAccount(uniqueId, assetId, ownerName);
    }

    @Override
    public WriteListResponse<Integer> associateVictimAssetPhoneNumbers(String uniqueId, List<Integer> assetIds) throws IOException {
        return victimAssetAssocWriter.associateVictimAssetPhoneNumbers(uniqueId, assetIds);
    }

    @Override
    public WriteListResponse<Integer> associateVictimAssetPhoneNumbers(String uniqueId, List<Integer> assetIds, String ownerName) throws IOException {
        return victimAssetAssocWriter.associateVictimAssetPhoneNumbers(uniqueId, assetIds, ownerName);
    }

    @Override
    public ApiEntitySingleResponse associateVictimAssetPhoneNumber(String uniqueId, Integer assetId) throws IOException, FailedResponseException {
        return victimAssetAssocWriter.associateVictimAssetPhoneNumber(uniqueId, assetId);
    }

    @Override
    public ApiEntitySingleResponse associateVictimAssetPhoneNumber(String uniqueId, Integer assetId, String ownerName) throws IOException, FailedResponseException {
        return victimAssetAssocWriter.associateVictimAssetPhoneNumber(uniqueId, assetId, ownerName);
    }

    @Override
    public WriteListResponse<Integer> associateVictimAssetSocialNetworks(String uniqueId, List<Integer> assetIds) throws IOException, FailedResponseException {
        return victimAssetAssocWriter.associateVictimAssetSocialNetworks(uniqueId, assetIds);
    }

    @Override
    public WriteListResponse<Integer> associateVictimAssetSocialNetworks(String uniqueId, List<Integer> assetIds, String ownerName) throws IOException {
        return victimAssetAssocWriter.associateVictimAssetSocialNetworks(uniqueId, assetIds, ownerName);
    }

    @Override
    public ApiEntitySingleResponse associateVictimAssetSocialNetwork(String uniqueId, Integer assetId) throws IOException, FailedResponseException {
        return victimAssetAssocWriter.associateVictimAssetSocialNetwork(uniqueId, assetId);
    }

    @Override
    public ApiEntitySingleResponse associateVictimAssetSocialNetwork(String uniqueId, Integer assetId, String ownerName) throws IOException, FailedResponseException {
        return victimAssetAssocWriter.associateVictimAssetSocialNetwork(uniqueId, assetId, ownerName);
    }

    @Override
    public WriteListResponse<Integer> associateVictimAssetWebsites(String uniqueId, List<Integer> assetIds) throws IOException {
        return victimAssetAssocWriter.associateVictimAssetWebsites(uniqueId, assetIds);
    }

    @Override
    public WriteListResponse<Integer> associateVictimAssetWebsites(String uniqueId, List<Integer> assetIds, String ownerName) throws IOException {
        return victimAssetAssocWriter.associateVictimAssetWebsites(uniqueId, assetIds, ownerName);
    }

    @Override
    public ApiEntitySingleResponse associateVictimAssetWebsite(String uniqueId, Integer assetId) throws IOException, FailedResponseException {
        return victimAssetAssocWriter.associateVictimAssetWebsite(uniqueId, assetId);
    }

    @Override
    public ApiEntitySingleResponse associateVictimAssetWebsite(String uniqueId, Integer assetId, String ownerName) throws IOException, FailedResponseException {
        return victimAssetAssocWriter.associateVictimAssetWebsite(uniqueId, assetId, ownerName);
    }

}
