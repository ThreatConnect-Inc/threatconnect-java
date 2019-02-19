package com.threatconnect.sdk.client.writer;

import com.threatconnect.sdk.client.UrlTypeable;
import com.threatconnect.sdk.client.response.WriteListResponse;
import com.threatconnect.sdk.client.writer.associate.AbstractGroupAssociateWriterAdapter;
import com.threatconnect.sdk.client.writer.associate.AbstractIndicatorAssociateWriterAdapter;
import com.threatconnect.sdk.client.writer.associate.GroupAssociateWritable;
import com.threatconnect.sdk.client.writer.associate.IndicatorAssociateWritable;
import com.threatconnect.sdk.conn.Connection;
import com.threatconnect.sdk.conn.AbstractRequestExecutor;
import com.threatconnect.sdk.exception.FailedResponseException;
import com.threatconnect.sdk.server.entity.SecurityLabel;
import com.threatconnect.sdk.server.response.entity.ApiEntitySingleResponse;
import com.threatconnect.sdk.server.response.entity.SecurityLabelResponse;

import java.io.IOException;
import java.util.List;

/**
 * SecurityLabelWriterAdapter is the primary writer adapter for all SecurityLabel level objects.
 * It uses the {@link com.threatconnect.sdk.conn.Connection} object to execute requests against the {@link com.threatconnect.sdk.conn.AbstractRequestExecutor} object.
 * The responsibility of this class is to encapsulate all the low level ThreatConnect API calls
 * specifically targeted at data under the SecurityLabel type.
 * 
 *
 * @author dtineo
 */

public class SecurityLabelWriterAdapter
    extends AbstractBaseWriterAdapter<SecurityLabel,String>
    implements UrlTypeable, GroupAssociateWritable<String>, IndicatorAssociateWritable<String> {

    // composite pattern
    private AbstractGroupAssociateWriterAdapter<SecurityLabel,String> groupAssocWriter;
    private AbstractIndicatorAssociateWriterAdapter<SecurityLabel,String> indAssocWriter;

    /**
     * Package level constructor. Use the {@link WriterAdapterFactory} to access this object.
     * @param conn      Primary connection object to the ThreatConnect API
     *
     * @see WriterAdapterFactory
     */
    protected SecurityLabelWriterAdapter(Connection conn) {
        super(conn, SecurityLabelResponse.class);

        initComposite();
    }

    private void initComposite() {

        groupAssocWriter = new AbstractGroupAssociateWriterAdapter<SecurityLabel,String>(
                            SecurityLabelWriterAdapter.this.getConn()
                          , SecurityLabelWriterAdapter.this.singleType
            ) {
            @Override
            protected String getUrlBasePrefix() {
                return SecurityLabelWriterAdapter.this.getUrlBasePrefix();
            }

            @Override
            public String getId(SecurityLabel item) {
                return SecurityLabelWriterAdapter.this.getId(item);
            }

            @Override
            public String getUrlType()
            {
                return SecurityLabelWriterAdapter.this.getUrlType();
            }
        };

        indAssocWriter = new AbstractIndicatorAssociateWriterAdapter<SecurityLabel,String>(
                            SecurityLabelWriterAdapter.this.getConn()
                          , SecurityLabelWriterAdapter.this.singleType
            ) {
            @Override
            protected String getUrlBasePrefix() {
                return SecurityLabelWriterAdapter.this.getUrlBasePrefix();
            }

            @Override
            public String getUrlType() {
                return SecurityLabelWriterAdapter.this.getUrlType();
            }

            @Override
            public String getId(SecurityLabel item) {
                return SecurityLabelWriterAdapter.this.getId(item);
            }
        };

    }

    @Override
    protected String getUrlBasePrefix() {
        return "v2.securityLabels";
    }

    @Override
    public String getId(SecurityLabel item) {
        return item.getName();
    }

    @Override
    public String getUrlType() {
        return "securityLabels";
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
    public ApiEntitySingleResponse associateGroupAdversary(String uniqueId, Long adversaryId, String ownerName) throws IOException, FailedResponseException {
        return groupAssocWriter.associateGroupAdversary(uniqueId, adversaryId, ownerName);
    }
    
    @Override
    public ApiEntitySingleResponse associateGroupDocument(String uniqueId, Long documentId) throws IOException, FailedResponseException {
        return groupAssocWriter.associateGroupDocument(uniqueId, documentId);
    }

    @Override
    public ApiEntitySingleResponse associateGroupDocument(String uniqueId, Long documentId, String ownerName) throws IOException, FailedResponseException {
        return groupAssocWriter.associateGroupDocument(uniqueId, documentId, ownerName);
    }

    @Override
    public WriteListResponse<Long> associateGroupEmails(String uniqueId, List<Long> emailIds) throws IOException{
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
	public ApiEntitySingleResponse associateCustomIndicatorToIndicator(String uniqueId, String targetId,
			String assciateType, String targetType) throws IOException, FailedResponseException {
		// TODO Auto-generated method stub
		throw new RuntimeException("not implemented yet");
	}
    
}
