package com.threatconnect.sdk.client.writer;

import com.threatconnect.sdk.client.AbstractClientAdapter;
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
import com.threatconnect.sdk.server.entity.Indicator;
import com.threatconnect.sdk.server.response.entity.ApiEntitySingleResponse;
import com.threatconnect.sdk.server.response.entity.GenericIndicatorResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * AbstractIndicatorWriterAdapter is the primary writer adapter for all Indicator level objects.
 * It uses the {@link com.threatconnect.sdk.conn.Connection} object to execute requests against the {@link com.threatconnect.sdk.conn.AbstractRequestExecutor} object.
 * The responsibility of this class is to encapsulate all the low level ThreatConnect API calls
 * specifically targeted at data under the Indicator T type.
 *
 *
 * @author dtineo
 * @param <T> Parameter
 */
public abstract class AbstractIndicatorWriterAdapter<T extends Indicator>
    extends AbstractBaseWriterAdapter<T,String>
    implements UrlTypeable, GroupAssociateWritable<String>, IndicatorAssociateWritable<String>
             , AttributeAssociateWritable<String>, TagAssociateWritable<String>
             , SecurityLabelAssociateWritable<String>, VictimAssetAssociateWritable<String>
             , VictimAssociateWritable<String> {

    // composite pattern
    private AbstractAttributeAssociateWriterAdapter<T,String> attribWriter;
    private AbstractGroupAssociateWriterAdapter<T,String> groupAssocWriter;
    private AbstractIndicatorAssociateWriterAdapter<T,String> indAssocWriter;
    private AbstractSecurityLabelAssociateWriterAdapter<T,String> secLabelAssocWriter;
    private AbstractTagAssociateWriterAdapter<T,String> tagAssocWriter;
    private AbstractVictimAssetAssociateWriterAdapter<T,String> victimAssetAssocWriter;
    private AbstractVictimAssociateWriterAdapter<T,String> victimAssocWriter;

    /**
     * Package level constructor. Use the {@link WriterAdapterFactory} to access this object.
     * @param conn      Primary connection object to the ThreatConnect API
     * @param singleType Single type
     *
     * @see WriterAdapterFactory
     */
    protected AbstractIndicatorWriterAdapter(Connection conn
                , Class<? extends ApiEntitySingleResponse> singleType ) {
        super(conn, singleType );

        initComposite();
    }

    private void initComposite() {
        attribWriter = new AbstractAttributeAssociateWriterAdapter<T,String>(
                            AbstractIndicatorWriterAdapter.this.getConn()
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
            public String getUrlType()
            {
                return AbstractIndicatorWriterAdapter.this.getUrlType();
            }

        };

        groupAssocWriter = new AbstractGroupAssociateWriterAdapter<T,String>(
                            AbstractIndicatorWriterAdapter.this.getConn()
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
            public String getUrlType()
            {
                return AbstractIndicatorWriterAdapter.this.getUrlType();
            }
        };

        indAssocWriter = new AbstractIndicatorAssociateWriterAdapter<T,String>(
                            AbstractIndicatorWriterAdapter.this.getConn()
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
            	//TOD may need to update this for custom indicator, will see how it goes
                return AbstractIndicatorWriterAdapter.this.getId(item);
            }


        };

        secLabelAssocWriter = new AbstractSecurityLabelAssociateWriterAdapter<T,String>(
                            AbstractIndicatorWriterAdapter.this.getConn()
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

       victimAssocWriter = new AbstractVictimAssociateWriterAdapter<T,String>(
                            AbstractIndicatorWriterAdapter.this.getConn()
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
    public WriteListResponse<Long> associateGroupAdversaries(String uniqueId, List<Long> adversaryIds) throws IOException {
        return groupAssocWriter.associateGroupAdversaries(uniqueId, adversaryIds);
    }

    @Override
    public WriteListResponse<Long> associateGroupAdversaries(String uniqueId, List<Long> adversaryIds, String ownerName) throws IOException {
        return groupAssocWriter.associateGroupAdversaries(uniqueId, adversaryIds, ownerName);
    }

    @Override
    public ApiEntitySingleResponse associateGroupAdversary(String uniqueId, Long adversaryId) throws IOException, FailedResponseException {
        return groupAssocWriter.associateGroupAdversary(uniqueId, adversaryId);
    }

    @Override
    public ApiEntitySingleResponse associateGroupDocument(String uniqueId, Long adversaryId) throws IOException, FailedResponseException {
    	 return groupAssocWriter.associateGroupDocument(uniqueId, adversaryId);
    }

    @Override
    public ApiEntitySingleResponse associateGroupDocument(String uniqueId, Long adversaryId, String ownerName) throws IOException, FailedResponseException {
    	return groupAssocWriter.associateGroupDocument(uniqueId, adversaryId);
    }

    @Override
    public ApiEntitySingleResponse associateGroupAdversary(String uniqueId, Long adversaryId, String ownerName) throws IOException, FailedResponseException {
        return groupAssocWriter.associateGroupAdversary(uniqueId, adversaryId, ownerName);
    }

    @Override
    public WriteListResponse<Long> associateGroupEmails(String uniqueId, List<Long> emailIds) throws IOException {
        return groupAssocWriter.associateGroupEmails(uniqueId, emailIds);
    }

    @Override
    public WriteListResponse<Long> associateGroupEmails(String uniqueId, List<Long> emailIds, String ownerName) throws IOException {
        return groupAssocWriter.associateGroupEmails(uniqueId, emailIds, ownerName);
    }

    @Override
    public ApiEntitySingleResponse associateGroupEmail(String uniqueId, Long emailId) throws IOException, FailedResponseException {
        return groupAssocWriter.associateGroupEmail(uniqueId, emailId);
    }

    @Override
    public ApiEntitySingleResponse associateGroupEmail(String uniqueId, Long emailId, String ownerName) throws IOException, FailedResponseException {
        return groupAssocWriter.associateGroupEmail(uniqueId, emailId, ownerName);
    }

    @Override
    public WriteListResponse<Long> associateGroupIncidents(String uniqueId, List<Long> incidentIds) throws IOException {
        return groupAssocWriter.associateGroupIncidents(uniqueId, incidentIds);
    }

    @Override
    public WriteListResponse<Long> associateGroupIncidents(String uniqueId, List<Long> incidentIds, String ownerName) throws IOException {
        return groupAssocWriter.associateGroupIncidents(uniqueId, incidentIds, ownerName);
    }

    @Override
    public ApiEntitySingleResponse associateGroupIncident(String uniqueId, Long incidentId) throws IOException, FailedResponseException {
        return groupAssocWriter.associateGroupIncident(uniqueId, incidentId);
    }

    @Override
    public ApiEntitySingleResponse associateGroupIncident(String uniqueId, Long incidentId, String ownerName) throws IOException, FailedResponseException {
        return groupAssocWriter.associateGroupIncident(uniqueId, incidentId, ownerName);
    }

    @Override
    public WriteListResponse<Long> associateGroupSignatures(String uniqueId, List<Long> signatureIds) throws IOException {
        return groupAssocWriter.associateGroupSignatures(uniqueId, signatureIds);
    }

    @Override
    public WriteListResponse<Long> associateGroupSignatures(String uniqueId, List<Long> signatureIds, String ownerName) throws IOException {
        return groupAssocWriter.associateGroupSignatures(uniqueId, signatureIds, ownerName);
    }

    @Override
    public ApiEntitySingleResponse associateGroupSignature(String uniqueId, Long signatureId) throws IOException, FailedResponseException {
        return groupAssocWriter.associateGroupSignature(uniqueId, signatureId);
    }

    @Override
    public ApiEntitySingleResponse associateGroupSignature(String uniqueId, Long signatureId, String ownerName) throws IOException, FailedResponseException {
        return groupAssocWriter.associateGroupSignature(uniqueId, signatureId, ownerName);
    }

    @Override
    public WriteListResponse<Long> associateGroupThreats(String uniqueId, List<Long> threatIds) throws IOException {
        return groupAssocWriter.associateGroupThreats(uniqueId, threatIds);
    }

    @Override
    public WriteListResponse<Long> associateGroupThreats(String uniqueId, List<Long> threatIds, String ownerName) throws IOException {
        return groupAssocWriter.associateGroupThreats(uniqueId, threatIds, ownerName);
    }

    @Override
    public ApiEntitySingleResponse associateGroupThreat(String uniqueId, Long threatId) throws IOException, FailedResponseException {
        return groupAssocWriter.associateGroupThreat(uniqueId, threatId);
    }

    @Override
    public ApiEntitySingleResponse associateGroupThreat(String uniqueId, Long threatId, String ownerName) throws IOException, FailedResponseException {
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
    public ApiEntitySingleResponse addAttribute(String uniqueId, Attribute attribute) throws IOException, FailedResponseException {
        return attribWriter.addAttribute(uniqueId, attribute);
    }

    @Override
    public ApiEntitySingleResponse addAttribute(String uniqueId, Attribute attribute, String ownerName) throws IOException, FailedResponseException {
        return attribWriter.addAttribute(uniqueId, attribute, ownerName);
    }

    @Override
    public WriteListResponse<String> addAttributeSecurityLabels(String uniqueId, Long attributeId, List<String> securityLabels) throws IOException {
        return attribWriter.addAttributeSecurityLabels(uniqueId, attributeId, securityLabels);
    }

    @Override
    public WriteListResponse<String> addAttributeSecurityLabels(String uniqueId, Long attributeId, List<String> securityLabels, String ownerName) throws IOException {
        return attribWriter.addAttributeSecurityLabels(uniqueId, attributeId, securityLabels, ownerName);
    }

    @Override
    public ApiEntitySingleResponse addAttributeSecurityLabel(String uniqueId, Long attributeId, String securityLabel) throws IOException, FailedResponseException {
        return attribWriter.addAttributeSecurityLabel(uniqueId, attributeId, securityLabel);
    }

    @Override
    public ApiEntitySingleResponse addAttributeSecurityLabel(String uniqueId, Long attributeId, String securityLabel, String ownerName) throws IOException, FailedResponseException {
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
    public WriteListResponse<Long> dissociateGroupAdversaries(String uniqueId, List<Long> adversaryIds) throws IOException {
        return groupAssocWriter.dissociateGroupAdversaries(uniqueId, adversaryIds);
    }

    @Override
    public WriteListResponse<Long> dissociateGroupAdversaries(String uniqueId, List<Long> adversaryIds, String ownerName) throws IOException {
        return groupAssocWriter.dissociateGroupAdversaries(uniqueId, adversaryIds, ownerName);
    }

    @Override
    public ApiEntitySingleResponse dissociateGroupAdversary(String uniqueId, Long adversaryId) throws IOException, FailedResponseException {
        return groupAssocWriter.dissociateGroupAdversary(uniqueId, adversaryId);
    }

    @Override
    public ApiEntitySingleResponse dissociateGroupAdversary(String uniqueId, Long adversaryId, String ownerName) throws IOException, FailedResponseException {
        return groupAssocWriter.dissociateGroupAdversary(uniqueId, adversaryId, ownerName);
    }

    @Override
    public WriteListResponse<Long> dissociateGroupEmails(String uniqueId, List<Long> emailIds) throws IOException {
        return groupAssocWriter.dissociateGroupEmails(uniqueId, emailIds);
    }

    @Override
    public WriteListResponse<Long> dissociateGroupEmails(String uniqueId, List<Long> emailIds, String ownerName) throws IOException {
        return groupAssocWriter.dissociateGroupEmails(uniqueId, emailIds, ownerName);
    }

    @Override
    public ApiEntitySingleResponse dissociateGroupEmail(String uniqueId, Long emailId) throws IOException, FailedResponseException {
        return groupAssocWriter.dissociateGroupEmail(uniqueId, emailId);
    }

    @Override
    public ApiEntitySingleResponse dissociateGroupEmail(String uniqueId, Long emailId, String ownerName) throws IOException, FailedResponseException {
        return groupAssocWriter.dissociateGroupEmail(uniqueId, emailId, ownerName);
    }

    @Override
    public WriteListResponse<Long> dissociateGroupIncidents(String uniqueId, List<Long> incidentIds) throws IOException {
        return groupAssocWriter.dissociateGroupIncidents(uniqueId, incidentIds);
    }

    @Override
    public WriteListResponse<Long> dissociateGroupIncidents(String uniqueId, List<Long> incidentIds, String ownerName) throws IOException {
        return groupAssocWriter.dissociateGroupIncidents(uniqueId, incidentIds, ownerName);
    }

    @Override
    public ApiEntitySingleResponse dissociateGroupIncident(String uniqueId, Long incidentId) throws IOException, FailedResponseException {
        return groupAssocWriter.dissociateGroupIncident(uniqueId, incidentId);
    }

    @Override
    public ApiEntitySingleResponse dissociateGroupIncident(String uniqueId, Long incidentId, String ownerName) throws IOException, FailedResponseException {
        return groupAssocWriter.dissociateGroupIncident(uniqueId, incidentId, ownerName);
    }

    @Override
    public WriteListResponse<Long> dissociateGroupSignatures(String uniqueId, List<Long> signatureIds) throws IOException {
        return groupAssocWriter.dissociateGroupSignatures(uniqueId, signatureIds);
    }

    @Override
    public WriteListResponse<Long> dissociateGroupSignatures(String uniqueId, List<Long> signatureIds, String ownerName) throws IOException {
        return groupAssocWriter.dissociateGroupSignatures(uniqueId, signatureIds, ownerName);
    }

    @Override
    public ApiEntitySingleResponse dissociateGroupSignature(String uniqueId, Long signatureId) throws IOException, FailedResponseException {
        return groupAssocWriter.dissociateGroupSignature(uniqueId, signatureId);
    }

    @Override
    public ApiEntitySingleResponse dissociateGroupSignature(String uniqueId, Long signatureId, String ownerName) throws IOException, FailedResponseException {
        return groupAssocWriter.dissociateGroupSignature(uniqueId, signatureId, ownerName);
    }

    @Override
    public WriteListResponse<Long> dissociateGroupThreats(String uniqueId, List<Long> threatIds) throws IOException {
        return groupAssocWriter.dissociateGroupThreats(uniqueId, threatIds);
    }

    @Override
    public WriteListResponse<Long> dissociateGroupThreats(String uniqueId, List<Long> threatIds, String ownerName) throws IOException {
        return groupAssocWriter.dissociateGroupThreats(uniqueId, threatIds, ownerName);
    }

    @Override
    public ApiEntitySingleResponse dissociateGroupThreat(String uniqueId, Long threatId) throws IOException, FailedResponseException {
        return groupAssocWriter.dissociateGroupThreat(uniqueId, threatId);
    }

    @Override
    public ApiEntitySingleResponse dissociateGroupThreat(String uniqueId, Long threatId, String ownerName) throws IOException, FailedResponseException {
        return groupAssocWriter.dissociateGroupThreat(uniqueId, threatId, ownerName);
    }

    @Override
    public WriteListResponse<String> dissociateIndicatorAddresses(String uniqueId, List<String> ipAddresses) throws IOException {
        return indAssocWriter.dissociateIndicatorAddresses(uniqueId, ipAddresses);
    }

    @Override
    public WriteListResponse<String> dissociateIndicatorAddresses(String uniqueId, List<String> ipAddresses, String ownerName) throws IOException {
        return indAssocWriter.dissociateIndicatorAddresses(uniqueId, ipAddresses, ownerName);
    }

    @Override
    public ApiEntitySingleResponse dissociateIndicatorAddress(String uniqueId, String ipAddress) throws IOException, FailedResponseException {
        return indAssocWriter.dissociateIndicatorAddress(uniqueId, ipAddress);
    }

    @Override
    public ApiEntitySingleResponse dissociateIndicatorAddress(String uniqueId, String ipAddress, String ownerName) throws IOException, FailedResponseException {
        return indAssocWriter.dissociateIndicatorAddress(uniqueId, ipAddress, ownerName);
    }

    @Override
    public WriteListResponse<String> dissociateIndicatorEmailAddresses(String uniqueId, List<String> emailAddresses) throws IOException {
        return indAssocWriter.dissociateIndicatorEmailAddresses(uniqueId, emailAddresses);
    }

    @Override
    public WriteListResponse<String> dissociateIndicatorEmailAddresses(String uniqueId, List<String> emailAddresses, String ownerName) throws IOException {
        return indAssocWriter.dissociateIndicatorEmailAddresses(uniqueId, emailAddresses, ownerName);
    }

    @Override
    public ApiEntitySingleResponse dissociateIndicatorEmailAddress(String uniqueId, String emailAddress) throws IOException, FailedResponseException {
        return indAssocWriter.dissociateIndicatorEmailAddress(uniqueId, emailAddress);
    }

    @Override
    public ApiEntitySingleResponse dissociateIndicatorEmailAddress(String uniqueId, String emailAddress, String ownerName) throws IOException, FailedResponseException {
        return indAssocWriter.dissociateIndicatorEmailAddress(uniqueId, emailAddress, ownerName);
    }

    @Override
    public WriteListResponse<String> dissociateIndicatorFiles(String uniqueId, List<String> fileHashes) throws IOException {
        return indAssocWriter.dissociateIndicatorFiles(uniqueId, fileHashes);
    }

    @Override
    public WriteListResponse<String> dissociateIndicatorFiles(String uniqueId, List<String> fileHashes, String ownerName) throws IOException {
        return indAssocWriter.dissociateIndicatorFiles(uniqueId, fileHashes, ownerName);
    }

    @Override
    public ApiEntitySingleResponse dissociateIndicatorFile(String uniqueId, String fileHash) throws IOException, FailedResponseException {
        return indAssocWriter.dissociateIndicatorFile(uniqueId, fileHash);
    }

    @Override
    public ApiEntitySingleResponse dissociateIndicatorFile(String uniqueId, String fileHash, String ownerName) throws IOException, FailedResponseException {
        return indAssocWriter.dissociateIndicatorFile(uniqueId, fileHash, ownerName);
    }

    @Override
    public WriteListResponse<String> dissociateIndicatorHosts(String uniqueId, List<String> hostNames) throws IOException {
        return indAssocWriter.dissociateIndicatorHosts(uniqueId, hostNames);
    }

    @Override
    public WriteListResponse<String> dissociateIndicatorHosts(String uniqueId, List<String> hostNames, String ownerName) throws IOException {
        return indAssocWriter.dissociateIndicatorHosts(uniqueId, hostNames, ownerName);
    }

    @Override
    public ApiEntitySingleResponse dissociateIndicatorHost(String uniqueId, String hostName) throws IOException, FailedResponseException {
        return indAssocWriter.dissociateIndicatorHost(uniqueId, hostName);
    }

    @Override
    public ApiEntitySingleResponse dissociateIndicatorHost(String uniqueId, String hostName, String ownerName) throws IOException, FailedResponseException {
        return indAssocWriter.dissociateIndicatorHost(uniqueId, hostName, ownerName);
    }

    @Override
    public WriteListResponse<String> dissociateIndicatorUrls(String uniqueId, List<String> urlTexts) throws IOException {
        return indAssocWriter.dissociateIndicatorUrls(uniqueId, urlTexts);
    }

    @Override
    public WriteListResponse<String> dissociateIndicatorUrls(String uniqueId, List<String> urlTexts, String ownerName) throws IOException {
        return indAssocWriter.dissociateIndicatorUrls(uniqueId, urlTexts, ownerName);
    }

    @Override
    public ApiEntitySingleResponse dissociateIndicatorUrl(String uniqueId, String urlText) throws IOException, FailedResponseException {
        return indAssocWriter.dissociateIndicatorUrl(uniqueId, urlText);
    }

    @Override
    public ApiEntitySingleResponse dissociateIndicatorUrl(String uniqueId, String urlText, String ownerName) throws IOException, FailedResponseException {
        return indAssocWriter.dissociateIndicatorUrl(uniqueId, urlText, ownerName);
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
    public WriteListResponse<Long> deleteAttributes(String uniqueId, List<Long> attributes) throws IOException {
        return attribWriter.deleteAttributes(uniqueId, attributes);
    }

    @Override
    public WriteListResponse<Long> deleteAttributes(String uniqueId, List<Long> attribute, String ownerName) throws IOException {
        return attribWriter.deleteAttributes(uniqueId, attribute, ownerName);
    }

    @Override
    public ApiEntitySingleResponse deleteAttribute(String uniqueId, Long attribute) throws IOException, FailedResponseException {
        return attribWriter.deleteAttribute(uniqueId, attribute);
    }

    @Override
    public ApiEntitySingleResponse deleteAttribute(String uniqueId, Long attribute, String ownerName) throws IOException, FailedResponseException {
        return attribWriter.deleteAttribute(uniqueId, attribute, ownerName);
    }

    @Override
    public WriteListResponse<String> deleteAttributeSecurityLabels(String uniqueId, Long attributeId, List<String> securityLabels) throws IOException, FailedResponseException {
        return attribWriter.deleteAttributeSecurityLabels(uniqueId, attributeId, securityLabels);
    }

    @Override
    public WriteListResponse<String> deleteAttributeSecurityLabels(String uniqueId, Long attributeId, List<String> securityLabels, String ownerName) throws IOException, FailedResponseException {
       return attribWriter.deleteAttributeSecurityLabels(uniqueId, attributeId, securityLabels, ownerName);
    }

    @Override
    public ApiEntitySingleResponse deleteAttributeSecurityLabel(String uniqueId, Long attributeId, String securityLabel) throws IOException, FailedResponseException {
        return attribWriter.deleteAttributeSecurityLabel(uniqueId, attributeId, securityLabel);
    }

    @Override
    public ApiEntitySingleResponse deleteAttributeSecurityLabel(String uniqueId, Long attributeId, String securityLabel, String ownerName) throws IOException, FailedResponseException {
        return attribWriter.deleteAttributeSecurityLabel(uniqueId, attributeId, securityLabel, ownerName);
    }

    @Override
    public WriteListResponse<Long> dissociateVictimAssetEmailAddresses(String uniqueId, List<Long> assetIds) throws IOException {
        return victimAssetAssocWriter.dissociateVictimAssetEmailAddresses(uniqueId, assetIds);
    }

    @Override
    public WriteListResponse<Long> dissociateVictimAssetEmailAddresses(String uniqueId, List<Long> assetIds, String ownerName) throws IOException {
        return victimAssetAssocWriter.dissociateVictimAssetEmailAddresses(uniqueId, assetIds, ownerName);
    }

    @Override
    public ApiEntitySingleResponse dissociateVictimAssetEmailAddress(String uniqueId, Long assetId) throws IOException, FailedResponseException {
        return victimAssetAssocWriter.dissociateVictimAssetEmailAddress(uniqueId, assetId);
    }

    @Override
    public ApiEntitySingleResponse dissociateVictimAssetEmailAddress(String uniqueId, Long assetId, String ownerName) throws IOException, FailedResponseException {
        return victimAssetAssocWriter.dissociateVictimAssetEmailAddress(uniqueId, assetId, ownerName);
    }

    @Override
    public WriteListResponse<Long> dissociateVictimAssetNetworkAccounts(String uniqueId, List<Long> assetIds) throws IOException {
        return victimAssetAssocWriter.dissociateVictimAssetNetworkAccounts(uniqueId, assetIds);
    }

    @Override
    public WriteListResponse<Long> dissociateVictimAssetNetworkAccounts(String uniqueId, List<Long> assetIds, String ownerName) throws IOException {
        return victimAssetAssocWriter.dissociateVictimAssetNetworkAccounts(uniqueId, assetIds, ownerName);
    }

    @Override
    public ApiEntitySingleResponse dissociateVictimAssetNetworkAccount(String uniqueId, Long assetId) throws IOException, FailedResponseException {
        return victimAssetAssocWriter.associateVictimAssetNetworkAccount(uniqueId, assetId);
    }

    @Override
    public ApiEntitySingleResponse dissociateVictimAssetNetworkAccount(String uniqueId, Long assetId, String ownerName) throws IOException, FailedResponseException {
        return victimAssetAssocWriter.dissociateVictimAssetNetworkAccount(uniqueId, assetId, ownerName);
    }

    @Override
    public WriteListResponse<Long> dissociateVictimAssetPhoneNumbers(String uniqueId, List<Long> assetIds) throws IOException {
        return victimAssetAssocWriter.dissociateVictimAssetPhoneNumbers(uniqueId, assetIds);
    }

    @Override
    public WriteListResponse<Long> dissociateVictimAssetPhoneNumbers(String uniqueId, List<Long> assetIds, String ownerName) throws IOException {
        return victimAssetAssocWriter.dissociateVictimAssetPhoneNumbers(uniqueId, assetIds, ownerName);
    }

    @Override
    public ApiEntitySingleResponse dissociateVictimAssetPhoneNumber(String uniqueId, Long assetId) throws IOException, FailedResponseException {
        return victimAssetAssocWriter.dissociateVictimAssetPhoneNumber(uniqueId, assetId);
    }

    @Override
    public ApiEntitySingleResponse dissociateVictimAssetPhoneNumber(String uniqueId, Long assetId, String ownerName) throws IOException, FailedResponseException {
        return victimAssetAssocWriter.dissociateVictimAssetPhoneNumber(uniqueId, assetId, ownerName);
    }

    @Override
    public WriteListResponse<Long> dissociateVictimAssetSocialNetworks(String uniqueId, List<Long> assetIds) throws IOException, FailedResponseException {
        return victimAssetAssocWriter.dissociateVictimAssetSocialNetworks(uniqueId, assetIds);
    }

    @Override
    public WriteListResponse<Long> dissociateVictimAssetSocialNetworks(String uniqueId, List<Long> assetIds, String ownerName) throws IOException {
        return victimAssetAssocWriter.dissociateVictimAssetSocialNetworks(uniqueId, assetIds, ownerName);
    }

    @Override
    public ApiEntitySingleResponse dissociateVictimAssetSocialNetwork(String uniqueId, Long assetId) throws IOException, FailedResponseException {
        return victimAssetAssocWriter.dissociateVictimAssetSocialNetwork(uniqueId, assetId);
    }

    @Override
    public ApiEntitySingleResponse dissociateVictimAssetSocialNetwork(String uniqueId, Long assetId, String ownerName) throws IOException, FailedResponseException {
        return victimAssetAssocWriter.dissociateVictimAssetSocialNetwork(uniqueId, assetId, ownerName);
    }

    @Override
    public WriteListResponse<Long> dissociateVictimAssetWebsites(String uniqueId, List<Long> assetIds) throws IOException {
        return victimAssetAssocWriter.dissociateVictimAssetWebsites(uniqueId, assetIds);
    }

    @Override
    public WriteListResponse<Long> dissociateVictimAssetWebsites(String uniqueId, List<Long> assetIds, String ownerName) throws IOException {
        return victimAssetAssocWriter.dissociateVictimAssetWebsites(uniqueId, assetIds, ownerName);
    }

    @Override
    public ApiEntitySingleResponse dissociateVictimAssetWebsite(String uniqueId, Long assetId) throws IOException, FailedResponseException {
        return victimAssetAssocWriter.dissociateVictimAssetWebsite(uniqueId, assetId);
    }

    @Override
    public ApiEntitySingleResponse dissociateVictimAssetWebsite(String uniqueId, Long assetId, String ownerName) throws IOException, FailedResponseException {
        return victimAssetAssocWriter.dissociateVictimAssetWebsite(uniqueId, assetId, ownerName);
    }

    @Override
    public WriteListResponse<String> dissociateTags(String uniqueId, List<String> tagNames) throws IOException {
        return tagAssocWriter.dissociateTags(uniqueId, tagNames);
    }

    @Override
    public WriteListResponse<String> dissociateTags(String uniqueId, List<String> tagNames, String ownerName) throws IOException {
        return tagAssocWriter.dissociateTags(uniqueId, tagNames, ownerName);
    }

    @Override
    public ApiEntitySingleResponse dissociateTag(String uniqueId, String tagName) throws IOException, FailedResponseException {
        return tagAssocWriter.dissociateTag(uniqueId, tagName);
    }

    @Override
    public ApiEntitySingleResponse dissociateTag(String uniqueId, String tagName, String ownerName) throws IOException, FailedResponseException {
        return tagAssocWriter.dissociateTag(uniqueId, tagName, ownerName);
    }

    @Override
    public WriteListResponse<String> dissociateSecurityLabel(String uniqueId, List<String> securityLabels) throws IOException {
        return secLabelAssocWriter.dissociateSecurityLabel(uniqueId, securityLabels);
    }

    @Override
    public WriteListResponse<String> dissociateSecurityLabel(String uniqueId, List<String> securityLabels, String ownerName) throws IOException {
        return secLabelAssocWriter.dissociateSecurityLabel(uniqueId, securityLabels, ownerName);
    }

    @Override
    public ApiEntitySingleResponse dissociateSecurityLabel(String uniqueId, String securityLabel) throws IOException, FailedResponseException {
        return secLabelAssocWriter.dissociateSecurityLabel(uniqueId, securityLabel);
    }

    @Override
    public ApiEntitySingleResponse dissociateSecurityLabel(String uniqueId, String securityLabel, String ownerName) throws IOException, FailedResponseException {
        return secLabelAssocWriter.dissociateSecurityLabel(uniqueId, securityLabel, ownerName);
    }

        @Override
    public WriteListResponse<Long> associateVictimAssetEmailAddresses(String uniqueId, List<Long> assetIds) throws IOException {
        return victimAssetAssocWriter.associateVictimAssetEmailAddresses(uniqueId, assetIds);
    }

    @Override
    public WriteListResponse<Long> associateVictimAssetEmailAddresses(String uniqueId, List<Long> assetIds, String ownerName) throws IOException {
        return victimAssetAssocWriter. associateVictimAssetEmailAddresses(uniqueId, assetIds, ownerName);
    }

    @Override
    public ApiEntitySingleResponse associateVictimAssetEmailAddress(String uniqueId, Long assetId) throws IOException, FailedResponseException {
        return victimAssetAssocWriter.associateVictimAssetEmailAddress(uniqueId, assetId);
    }

    @Override
    public ApiEntitySingleResponse associateVictimAssetEmailAddress(String uniqueId, Long assetId, String ownerName) throws IOException, FailedResponseException {
        return victimAssetAssocWriter.associateVictimAssetEmailAddress(uniqueId, assetId, ownerName);
    }

    @Override
    public WriteListResponse<Long> associateVictimAssetNetworkAccounts(String uniqueId, List<Long> assetIds) throws IOException {
        return victimAssetAssocWriter.associateVictimAssetNetworkAccounts(uniqueId, assetIds);
    }

    @Override
    public WriteListResponse<Long> associateVictimAssetNetworkAccounts(String uniqueId, List<Long> assetIds, String ownerName) throws IOException {
        return victimAssetAssocWriter.associateVictimAssetNetworkAccounts(uniqueId, assetIds, ownerName);
    }

    @Override
    public ApiEntitySingleResponse associateVictimAssetNetworkAccount(String uniqueId, Long assetId) throws IOException, FailedResponseException {
        return victimAssetAssocWriter.associateVictimAssetNetworkAccount(uniqueId, assetId);
    }

    @Override
    public ApiEntitySingleResponse associateVictimAssetNetworkAccount(String uniqueId, Long assetId, String ownerName) throws IOException, FailedResponseException {
        return victimAssetAssocWriter.associateVictimAssetNetworkAccount(uniqueId, assetId, ownerName);
    }

    @Override
    public WriteListResponse<Long> associateVictimAssetPhoneNumbers(String uniqueId, List<Long> assetIds) throws IOException {
        return victimAssetAssocWriter.associateVictimAssetPhoneNumbers(uniqueId, assetIds);
    }

    @Override
    public WriteListResponse<Long> associateVictimAssetPhoneNumbers(String uniqueId, List<Long> assetIds, String ownerName) throws IOException {
        return victimAssetAssocWriter.associateVictimAssetPhoneNumbers(uniqueId, assetIds, ownerName);
    }

    @Override
    public ApiEntitySingleResponse associateVictimAssetPhoneNumber(String uniqueId, Long assetId) throws IOException, FailedResponseException {
        return victimAssetAssocWriter.associateVictimAssetPhoneNumber(uniqueId, assetId);
    }

    @Override
    public ApiEntitySingleResponse associateVictimAssetPhoneNumber(String uniqueId, Long assetId, String ownerName) throws IOException, FailedResponseException {
        return victimAssetAssocWriter.associateVictimAssetPhoneNumber(uniqueId, assetId, ownerName);
    }

    @Override
    public WriteListResponse<Long> associateVictimAssetSocialNetworks(String uniqueId, List<Long> assetIds) throws IOException, FailedResponseException {
        return victimAssetAssocWriter.associateVictimAssetSocialNetworks(uniqueId, assetIds);
    }

    @Override
    public WriteListResponse<Long> associateVictimAssetSocialNetworks(String uniqueId, List<Long> assetIds, String ownerName) throws IOException {
        return victimAssetAssocWriter.associateVictimAssetSocialNetworks(uniqueId, assetIds, ownerName);
    }

    @Override
    public ApiEntitySingleResponse associateVictimAssetSocialNetwork(String uniqueId, Long assetId) throws IOException, FailedResponseException {
        return victimAssetAssocWriter.associateVictimAssetSocialNetwork(uniqueId, assetId);
    }

    @Override
    public ApiEntitySingleResponse associateVictimAssetSocialNetwork(String uniqueId, Long assetId, String ownerName) throws IOException, FailedResponseException {
        return victimAssetAssocWriter.associateVictimAssetSocialNetwork(uniqueId, assetId, ownerName);
    }

    @Override
    public WriteListResponse<Long> associateVictimAssetWebsites(String uniqueId, List<Long> assetIds) throws IOException {
        return victimAssetAssocWriter.associateVictimAssetWebsites(uniqueId, assetIds);
    }

    @Override
    public WriteListResponse<Long> associateVictimAssetWebsites(String uniqueId, List<Long> assetIds, String ownerName) throws IOException {
        return victimAssetAssocWriter.associateVictimAssetWebsites(uniqueId, assetIds, ownerName);
    }

    @Override
    public ApiEntitySingleResponse associateVictimAssetWebsite(String uniqueId, Long assetId) throws IOException, FailedResponseException {
        return victimAssetAssocWriter.associateVictimAssetWebsite(uniqueId, assetId);
    }

    @Override
    public ApiEntitySingleResponse associateVictimAssetWebsite(String uniqueId, Long assetId, String ownerName) throws IOException, FailedResponseException {
        return victimAssetAssocWriter.associateVictimAssetWebsite(uniqueId, assetId, ownerName);
    }

    @Override
    public WriteListResponse<Long> associateVictims(String uniqueId, List<Long> victimIds) throws IOException {
        return victimAssocWriter.associateVictims(uniqueId, victimIds);
    }

    @Override
    public WriteListResponse<Long> associateVictims(String uniqueId, List<Long> victimIds, String ownerName) throws IOException {
        return victimAssocWriter.associateVictims(uniqueId, victimIds, ownerName);
    }

    @Override
    public ApiEntitySingleResponse associateVictim(String uniqueId, Long victimId) throws IOException, FailedResponseException {
        return victimAssocWriter.associateVictim(uniqueId, victimId);
    }

    @Override
    public ApiEntitySingleResponse associateVictim(String uniqueId, Long victimId, String ownerName) throws IOException, FailedResponseException { return victimAssocWriter.associateVictim(uniqueId, victimId, ownerName);
    }

    @Override
    public WriteListResponse<Long> dissociateVictims(String uniqueId, List<Long> victimIds) throws IOException {
        return victimAssocWriter.dissociateVictims(uniqueId, victimIds);
    }

    @Override
    public WriteListResponse<Long> dissociateVictims(String uniqueId, List<Long> victimIds, String ownerName) throws IOException {
        return victimAssocWriter.dissociateVictims(uniqueId, victimIds, ownerName);
    }

    @Override
    public ApiEntitySingleResponse dissociateVictim(String uniqueId, Long victimId) throws IOException, FailedResponseException {
        return victimAssocWriter.dissociateVictim(uniqueId, victimId);
    }

    @Override
    public ApiEntitySingleResponse dissociateVictim(String uniqueId, Long victimId, String ownerName) throws IOException, FailedResponseException {
        return victimAssocWriter.dissociateVictim(uniqueId, victimId, ownerName);
    }

    public ApiEntitySingleResponse createObservation(String uniqueId) throws IOException, FailedResponseException
    {
        return createObservation(uniqueId, null, 1);
    }

    public ApiEntitySingleResponse createObservation(String uniqueId, String ownerName, int count) throws IOException, FailedResponseException {
        Map<String, Object> map = AbstractClientAdapter.createParamMap("id", uniqueId);

        Map<String, Long> observationBody = new HashMap<>();
        observationBody.put("count", new Long(count));
        ApiEntitySingleResponse data =
                createItem( getUrlBasePrefix() + ".byId.observations", GenericIndicatorResponse.class,
                        ownerName, map, observationBody);

        return data;
    }

    public ApiEntitySingleResponse updateFalsePositive(String uniqueId) throws IOException, FailedResponseException
    {
        return updateFalsePositive(uniqueId, null);
    }

    public ApiEntitySingleResponse updateFalsePositive(String uniqueId, String ownerName) throws IOException, FailedResponseException {
        Map<String, Object> map = AbstractClientAdapter.createParamMap("id", uniqueId);
        ApiEntitySingleResponse data = createItem( getUrlBasePrefix() + ".byId.falsePositive", GenericIndicatorResponse.class, ownerName, map, null);

        return data;
    }

    @Override
	public ApiEntitySingleResponse associateCustomIndicatorToIndicator(String uniqueId, String targetId,
			String assciateType, String targetType) throws IOException, FailedResponseException {
    	return indAssocWriter.associateCustomIndicatorToIndicator(uniqueId, targetId, assciateType, targetType);
	}

}
