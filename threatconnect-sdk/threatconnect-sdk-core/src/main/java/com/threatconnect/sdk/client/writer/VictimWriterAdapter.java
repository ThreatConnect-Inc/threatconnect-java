package com.threatconnect.sdk.client.writer;

import com.threatconnect.sdk.client.UrlTypeable;
import com.threatconnect.sdk.client.response.WriteListResponse;
import com.threatconnect.sdk.client.writer.associate.AbstractGroupAssociateWriterAdapter;
import com.threatconnect.sdk.client.writer.associate.AbstractIndicatorAssociateWriterAdapter;
import com.threatconnect.sdk.client.writer.associate.AbstractVictimAssetAssociateWriterAdapter;
import com.threatconnect.sdk.client.writer.associate.GroupAssociateWritable;
import com.threatconnect.sdk.client.writer.associate.IndicatorAssociateWritable;
import com.threatconnect.sdk.client.writer.associate.VictimAssetAssociateWritable;
import com.threatconnect.sdk.conn.Connection;
import com.threatconnect.sdk.conn.AbstractRequestExecutor;
import com.threatconnect.sdk.exception.FailedResponseException;
import com.threatconnect.sdk.server.entity.Victim;
import com.threatconnect.sdk.server.response.entity.ApiEntitySingleResponse;
import com.threatconnect.sdk.server.response.entity.VictimResponse;
import java.io.IOException;
import java.util.List;

/**
 * VictimWriterAdapter is the primary writer adapter for all Victim level objects.
 * It uses the {@link Connection} object to execute requests against the {@link com.threatconnect.sdk.conn.AbstractRequestExecutor} object.
 * The responsibility of this class is to encapsulate all the low level ThreatConnect API calls
 * specifically targeted at data under the Victim type.
 * 
 *
 * @author dtineo
 */

public class VictimWriterAdapter
    extends AbstractBaseWriterAdapter<Victim,Long>
    implements UrlTypeable, GroupAssociateWritable<Long>
    , IndicatorAssociateWritable<Long>, VictimAssetAssociateWritable<Long> {

    // composite pattern
    private AbstractGroupAssociateWriterAdapter<Victim,Long> groupAssocWriter;
    private AbstractIndicatorAssociateWriterAdapter<Victim,Long> indAssocWriter;
    private AbstractVictimAssetAssociateWriterAdapter<Victim,Long> victimAssetAssocWriter;

    /**
     * Package level constructor. Use the {@link WriterAdapterFactory} to access this object.
     * @param conn      Primary connection object to the ThreatConnect API
     *
     * @see WriterAdapterFactory
     */
    protected VictimWriterAdapter(Connection conn) {
        super(conn, VictimResponse.class );

        initComposite();
    }

    private void initComposite() {

        groupAssocWriter = new AbstractGroupAssociateWriterAdapter<Victim,Long>(
                            VictimWriterAdapter.this.getConn()
                          , VictimWriterAdapter.this.singleType
            ) {
            @Override
            protected String getUrlBasePrefix() {
                return VictimWriterAdapter.this.getUrlBasePrefix();
            }

            @Override
            public Long getId(Victim item) {
                return VictimWriterAdapter.this.getId(item);
            }

            @Override
            public String getUrlType()
            {
                return VictimWriterAdapter.this.getUrlType();
            }
        };

        indAssocWriter = new AbstractIndicatorAssociateWriterAdapter<Victim,Long>(
                            VictimWriterAdapter.this.getConn()
                          , VictimWriterAdapter.this.singleType
            ) {
            @Override
            protected String getUrlBasePrefix() {
                return VictimWriterAdapter.this.getUrlBasePrefix();
            }

            @Override
            public String getUrlType() {
                return VictimWriterAdapter.this.getUrlType();
            }

            @Override
            public Long getId(Victim item) {
                return VictimWriterAdapter.this.getId(item);
            }
        };

        victimAssetAssocWriter = new AbstractVictimAssetAssociateWriterAdapter<Victim,Long>(
                            VictimWriterAdapter.this.getConn()
                          , VictimWriterAdapter.this.singleType
            ) {
            @Override
            protected String getUrlBasePrefix() {
                return VictimWriterAdapter.this.getUrlBasePrefix();
            }

            @Override
            public Long getId(Victim item) {
                return VictimWriterAdapter.this.getId(item);
            }

            @Override
            public String getUrlType() {
                return VictimWriterAdapter.this.getUrlType();
            }

        };

    }

    @Override
    protected String getUrlBasePrefix() {
        return "v2.victims";
    }

    @Override
    public Long getId(Victim item) {
        return item.getId();
    }

    @Override
    public String getUrlType() {
        return "victims";
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
	public ApiEntitySingleResponse associateGroupDocument(Long uniqueId, Long documentId) throws IOException, FailedResponseException	{
    	return groupAssocWriter.associateGroupDocument(uniqueId, documentId);
	}

	@Override
	public ApiEntitySingleResponse associateGroupDocument(Long uniqueId, Long documentId, String ownerName) throws IOException, FailedResponseException {
		return groupAssocWriter.associateGroupDocument(uniqueId, documentId, ownerName);
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
        return indAssocWriter.associateIndicatorAddress(uniqueId, emailAddress, ownerName);
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
        return indAssocWriter.associateIndicatorAddress(uniqueId, fileHash, ownerName);
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
    public WriteListResponse<Long> dissociateGroupAdversaries(Long uniqueId, List<Long> adversaryIds) throws IOException {
        return groupAssocWriter.dissociateGroupAdversaries(uniqueId, adversaryIds);
    }

    @Override
    public WriteListResponse<Long> dissociateGroupAdversaries(Long uniqueId, List<Long> adversaryIds, String ownerName) throws IOException {
        return groupAssocWriter.associateGroupAdversaries(uniqueId, adversaryIds, ownerName);
    }

    @Override
    public ApiEntitySingleResponse dissociateGroupAdversary(Long uniqueId, Long adversaryId) throws IOException, FailedResponseException {
        return groupAssocWriter.associateGroupAdversary(uniqueId, adversaryId);
    }

    @Override
    public ApiEntitySingleResponse dissociateGroupAdversary(Long uniqueId, Long adversaryId, String ownerName) throws IOException, FailedResponseException {
        return groupAssocWriter.associateGroupAdversary(uniqueId, adversaryId, ownerName);
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
        return groupAssocWriter.associateGroupSignature(uniqueId, signatureId);
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
	public ApiEntitySingleResponse associateCustomIndicatorToIndicator(Long uniqueId, String targetId,
			String assciateType, String targetType) throws IOException, FailedResponseException {
		// TODO Auto-generated method stub
		throw new RuntimeException("not implemented yet");
	}
}
