package com.cyber2.api.lib.client.writer;

import com.cyber2.api.lib.client.UrlTypeable;
import com.cyber2.api.lib.client.response.WriteListResponse;
import com.cyber2.api.lib.client.writer.associate.AbstractGroupAssociateWriterAdapter;
import com.cyber2.api.lib.client.writer.associate.AbstractIndicatorAssociateWriterAdapter;
import com.cyber2.api.lib.client.writer.associate.GroupAssociateWritable;
import com.cyber2.api.lib.client.writer.associate.IndicatorAssociateWritable;
import com.cyber2.api.lib.conn.Connection;
import com.cyber2.api.lib.conn.RequestExecutor;
import com.cyber2.api.lib.exception.FailedResponseException;
import com.cyber2.api.lib.server.entity.Tag;
import com.cyber2.api.lib.server.response.entity.ApiEntitySingleResponse;
import com.cyber2.api.lib.server.response.entity.TagResponse;
import java.io.IOException;
import java.util.List;

/**
 * TagWriterAdapter is the primary writer adapter for all Tag level objects.
 * It uses the {@link Connection} object to execute requests against the {@link RequestExecutor} object.
 * The responsibility of this class is to encapsulate all the low level ThreatConnect API calls
 * specifically targeted at data under the Tag type.
 * 
 *
 * @author dtineo
 */

public class TagWriterAdapter
    extends AbstractBaseWriterAdapter<Tag,String>
    implements UrlTypeable, GroupAssociateWritable<String>, IndicatorAssociateWritable<String> {

    // composite pattern
    private AbstractGroupAssociateWriterAdapter<Tag,String> groupAssocWriter;
    private AbstractIndicatorAssociateWriterAdapter<Tag,String> indAssocWriter;

    /**
     * Package level constructor. Use the {@link WriterAdapterFactory} to access this object.
     * @param conn      Primary connection object to the ThreatConnect API
     * @param executor  Executor handling low level HTTPS calls to the ThreatConnect API
     * 
     * @see WriterAdapterFactory
     */
    protected TagWriterAdapter(Connection conn, RequestExecutor executor) {
        super(conn, executor, TagResponse.class);

        initComposite();
    }

    private void initComposite() {

        groupAssocWriter = new AbstractGroupAssociateWriterAdapter<Tag,String>(
                            TagWriterAdapter.this.getConn()
                          , TagWriterAdapter.this.executor
                          , TagWriterAdapter.this.singleType
            ) {
            @Override
            protected String getUrlBasePrefix() {
                return TagWriterAdapter.this.getUrlBasePrefix();
            }

            @Override
            public String getId(Tag item) {
                return TagWriterAdapter.this.getId(item);
            }
        };

        indAssocWriter = new AbstractIndicatorAssociateWriterAdapter<Tag,String>(
                            TagWriterAdapter.this.getConn()
                          , TagWriterAdapter.this.executor
                          , TagWriterAdapter.this.singleType
            ) {
            @Override
            protected String getUrlBasePrefix() {
                return TagWriterAdapter.this.getUrlBasePrefix();
            }

            @Override
            public String getUrlType() {
                return TagWriterAdapter.this.getUrlType();
            }

            @Override
            public String getId(Tag item) {
                return TagWriterAdapter.this.getId(item);
            }
        };

    }

    @Override
    protected String getUrlBasePrefix() {
        return "v2.tags";
    }

    @Override
    public String getId(Tag item) {
        return item.getName();
    }

    @Override
    public String getUrlType() {
        return "tags";
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
}
