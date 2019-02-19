/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.client.writer.associate;

import com.threatconnect.sdk.client.UrlTypeable;
import com.threatconnect.sdk.client.response.WriteListResponse;
import com.threatconnect.sdk.client.writer.AbstractBaseWriterAdapter;
import com.threatconnect.sdk.conn.Connection;
import com.threatconnect.sdk.conn.AbstractRequestExecutor;
import com.threatconnect.sdk.exception.FailedResponseException;
import com.threatconnect.sdk.server.response.entity.AdversaryResponse;
import com.threatconnect.sdk.server.response.entity.ApiEntitySingleResponse;
import com.threatconnect.sdk.server.response.entity.DocumentResponse;
import com.threatconnect.sdk.server.response.entity.EmailResponse;
import com.threatconnect.sdk.server.response.entity.IncidentResponse;
import com.threatconnect.sdk.server.response.entity.SignatureResponse;
import com.threatconnect.sdk.server.response.entity.ThreatResponse;
import com.threatconnect.sdk.client.AbstractClientAdapter;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author dtineo
 */
public abstract class AbstractGroupAssociateWriterAdapter<T,P> extends AbstractBaseWriterAdapter<T,P>
    implements GroupAssociateWritable<P>, UrlTypeable
{

    public AbstractGroupAssociateWriterAdapter(Connection conn, Class singleType) {
        super(conn, singleType);
    }

    @Override
    public WriteListResponse<Long> associateGroupAdversaries(P uniqueId, List<Long> adversaryIds) throws IOException {
        return associateGroupAdversaries(uniqueId, adversaryIds, null);
    }

    @Override
    public WriteListResponse<Long> associateGroupAdversaries(P uniqueId, List<Long> adversaryIds, String ownerName) throws IOException {

        Map<String, Object> map = AbstractClientAdapter.createParamMap("id", uniqueId);
        WriteListResponse<Long> data = createListWithParam(getUrlBasePrefix() + ".byId.groups.adversaries.byGroupId", AdversaryResponse.class, ownerName, map, "groupId", adversaryIds);

        return data;
    }

    @Override
    public ApiEntitySingleResponse associateGroupAdversary(P uniqueId, Long adversaryId) throws IOException, FailedResponseException {
        return associateGroupAdversary(uniqueId, adversaryId, null);
    }

    @Override
    public ApiEntitySingleResponse associateGroupAdversary(P uniqueId, Long adversaryId, String ownerName) throws IOException, FailedResponseException {
        
        Map<String, Object> map = AbstractClientAdapter.createParamMap("id", uniqueId, "groupId", adversaryId);
        AdversaryResponse data = createItem(getUrlBasePrefix() + ".byId.groups.adversaries.byGroupId", AdversaryResponse.class, ownerName, map, null);

        return data;
    }
    
    @Override
    public ApiEntitySingleResponse associateGroupDocument(P uniqueId, Long documentId)
    	throws IOException, FailedResponseException
    {
    	return associateGroupDocument(uniqueId, documentId, null);
    }
    
    @Override
    public ApiEntitySingleResponse associateGroupDocument(P uniqueId, Long documentId, String ownerName)
    	throws IOException, FailedResponseException
    {
    	Map<String, Object> map = AbstractClientAdapter.createParamMap("id", uniqueId, "groupId", documentId);
    	DocumentResponse data = createItem(getUrlBasePrefix() + ".byId.groups.documents.byGroupId", DocumentResponse.class, ownerName, map, null);

        return data;
    }

    @Override
    public WriteListResponse<Long> associateGroupEmails(P uniqueId, List<Long> emailIds) throws IOException {
        return associateGroupEmails(uniqueId, emailIds, null);
    }

    @Override
    public WriteListResponse<Long> associateGroupEmails(P uniqueId, List<Long> emailIds, String ownerName) throws IOException {

        Map<String, Object> map = AbstractClientAdapter.createParamMap("id", uniqueId);
        WriteListResponse<Long> data = createListWithParam(getUrlBasePrefix() + ".byId.groups.emails.byGroupId", EmailResponse.class, ownerName, map, "groupId", emailIds);

        return data;
    }

    @Override
    public ApiEntitySingleResponse associateGroupEmail(P uniqueId, Long emailId) throws IOException, FailedResponseException {
        return associateGroupEmail(uniqueId, emailId, null);
    }

    @Override
    public ApiEntitySingleResponse associateGroupEmail(P uniqueId, Long emailId, String ownerName) throws IOException, FailedResponseException {
        Map<String, Object> map = AbstractClientAdapter.createParamMap("id", uniqueId, "groupId", emailId);
        EmailResponse data = createItem(getUrlBasePrefix() + ".byId.groups.emails.byGroupId", EmailResponse.class, ownerName, map, null);

        return data;
    }

    @Override
    public WriteListResponse<Long> associateGroupIncidents(P uniqueId, List<Long> incidentIds) throws IOException {
        return associateGroupIncidents(uniqueId, incidentIds, null);
    }

    @Override
    public WriteListResponse<Long> associateGroupIncidents(P uniqueId, List<Long> incidentIds, String ownerName) throws IOException {
        Map<String, Object> map = AbstractClientAdapter.createParamMap("id", uniqueId);
        WriteListResponse<Long> data = createListWithParam(getUrlBasePrefix() + ".byId.groups.incidents.byGroupId", IncidentResponse.class, ownerName, map, "groupId", incidentIds);

        return data;
    }

    @Override
    public ApiEntitySingleResponse associateGroupIncident(P uniqueId, Long incidentId) throws IOException, FailedResponseException {
        return associateGroupIncident(uniqueId, incidentId, null);
    }

    @Override
    public ApiEntitySingleResponse associateGroupIncident(P uniqueId, Long incidentId, String ownerName) throws IOException, FailedResponseException {
        Map<String, Object> map = AbstractClientAdapter.createParamMap("id", uniqueId, "groupId", incidentId);
        IncidentResponse data = createItem(getUrlBasePrefix() + ".byId.groups.incidents.byGroupId", IncidentResponse.class, ownerName, map, null);

        return data;
    }

    @Override
    public WriteListResponse<Long> associateGroupSignatures(P uniqueId, List<Long> signatureIds) throws IOException {
        return associateGroupSignatures(uniqueId, signatureIds, null);
    }

    @Override
    public WriteListResponse<Long> associateGroupSignatures(P uniqueId, List<Long> signatureIds, String ownerName) throws IOException {
        Map<String, Object> map = AbstractClientAdapter.createParamMap("id", uniqueId);
        WriteListResponse<Long> data = createListWithParam(getUrlBasePrefix() + ".byId.groups.signatures.byGroupId", SignatureResponse.class, ownerName, map, "groupId", signatureIds);

        return data;
    }

    @Override
    public ApiEntitySingleResponse associateGroupSignature(P uniqueId, Long signatureId) throws IOException, FailedResponseException {
        return associateGroupSignature(uniqueId, signatureId, null);
    }

    @Override
    public ApiEntitySingleResponse associateGroupSignature(P uniqueId, Long signatureId, String ownerName) throws IOException, FailedResponseException {
        Map<String, Object> map = AbstractClientAdapter.createParamMap("id", uniqueId, "groupId", signatureId);
        SignatureResponse data = createItem(getUrlBasePrefix() + ".byId.groups.signatures.byGroupId", SignatureResponse.class, ownerName, map, null);

        return data;
    }

    @Override
    public WriteListResponse<Long> associateGroupThreats(P uniqueId, List<Long> threatIds) throws IOException {
        return associateGroupThreats(uniqueId, threatIds, null);
    }

    @Override
    public WriteListResponse<Long> associateGroupThreats(P uniqueId, List<Long> threatIds, String ownerName) throws IOException {
        Map<String, Object> map = AbstractClientAdapter.createParamMap("id", uniqueId);
        WriteListResponse<Long> data = createListWithParam(getUrlBasePrefix() + ".byId.groups.threats.byGroupId", ThreatResponse.class, ownerName, map, "groupId", threatIds);

        return data;
    }

    @Override
    public ApiEntitySingleResponse associateGroupThreat(P uniqueId, Long threatId) throws IOException, FailedResponseException {
        return associateGroupThreat(uniqueId, threatId, null);
    }

    @Override
    public ApiEntitySingleResponse associateGroupThreat(P uniqueId, Long threatId, String ownerName) throws IOException, FailedResponseException {
        Map<String, Object> map = AbstractClientAdapter.createParamMap("id", uniqueId, "groupId", threatId);
        ThreatResponse data = createItem(getUrlBasePrefix() + ".byId.groups.threats.byGroupId", ThreatResponse.class, ownerName, map, null);

        return data;
    }

    @Override
    public WriteListResponse<Long> dissociateGroupAdversaries(P uniqueId, List<Long> adversaryIds) throws IOException {
        return dissociateGroupAdversaries(uniqueId, adversaryIds, null);
    }

    @Override
    public WriteListResponse<Long> dissociateGroupAdversaries(P uniqueId, List<Long> adversaryIds, String ownerName) throws IOException {

        Map<String, Object> map = AbstractClientAdapter.createParamMap("id", uniqueId);
        WriteListResponse<Long> data = deleteList(getUrlBasePrefix() + ".byId.groups.adversaries.byGroupId", AdversaryResponse.class, ownerName, map, "groupId", adversaryIds);

        return data;
    }

    @Override
    public ApiEntitySingleResponse dissociateGroupAdversary(P uniqueId, Long adversaryId) throws IOException, FailedResponseException {
        return dissociateGroupAdversary(uniqueId, adversaryId, null);
    }

    @Override
    public ApiEntitySingleResponse dissociateGroupAdversary(P uniqueId, Long adversaryId, String ownerName) throws IOException, FailedResponseException {
        
        Map<String, Object> map = AbstractClientAdapter.createParamMap("id", uniqueId, "groupId", adversaryId);
        AdversaryResponse data = deleteItem(getUrlBasePrefix() + ".byId.groups.adversaries.byGroupId", AdversaryResponse.class, ownerName, map);

        return data;
    }

    @Override
    public WriteListResponse<Long> dissociateGroupEmails(P uniqueId, List<Long> emailIds) throws IOException {
        return dissociateGroupEmails(uniqueId, emailIds, null);
    }

    @Override
    public WriteListResponse<Long> dissociateGroupEmails(P uniqueId, List<Long> emailIds, String ownerName) throws IOException {

        Map<String, Object> map = AbstractClientAdapter.createParamMap("id", uniqueId);
        WriteListResponse<Long> data = deleteList(getUrlBasePrefix() + ".byId.groups.emails.byGroupId", EmailResponse.class, ownerName, map, "groupId", emailIds);

        return data;
    }

    @Override
    public ApiEntitySingleResponse dissociateGroupEmail(P uniqueId, Long emailId) throws IOException, FailedResponseException {
        return dissociateGroupEmail(uniqueId, emailId, null);
    }

    @Override
    public ApiEntitySingleResponse dissociateGroupEmail(P uniqueId, Long emailId, String ownerName) throws IOException, FailedResponseException {
        Map<String, Object> map = AbstractClientAdapter.createParamMap("id", uniqueId, "groupId", emailId);
        EmailResponse data = deleteItem(getUrlBasePrefix() + ".byId.groups.emails.byGroupId", EmailResponse.class, ownerName, map);

        return data;
    }

    @Override
    public WriteListResponse<Long> dissociateGroupIncidents(P uniqueId, List<Long> incidentIds) throws IOException {
        return dissociateGroupIncidents(uniqueId, incidentIds, null);
    }

    @Override
    public WriteListResponse<Long> dissociateGroupIncidents(P uniqueId, List<Long> incidentIds, String ownerName) throws IOException {
        Map<String, Object> map = AbstractClientAdapter.createParamMap("id", uniqueId);
        WriteListResponse<Long> data = deleteList(getUrlBasePrefix() + ".byId.groups.incidents.byGroupId", IncidentResponse.class, ownerName, map, "groupId", incidentIds);

        return data;
    }

    @Override
    public ApiEntitySingleResponse dissociateGroupIncident(P uniqueId, Long incidentId) throws IOException, FailedResponseException {
        return dissociateGroupIncident(uniqueId, incidentId, null);
    }

    @Override
    public ApiEntitySingleResponse dissociateGroupIncident(P uniqueId, Long incidentId, String ownerName) throws IOException, FailedResponseException {
        Map<String, Object> map = AbstractClientAdapter.createParamMap("id", uniqueId, "groupId", incidentId);
        IncidentResponse data = deleteItem(getUrlBasePrefix() + ".byId.groups.incidents.byGroupId", IncidentResponse.class, ownerName, map);

        return data;
    }

    @Override
    public WriteListResponse<Long> dissociateGroupSignatures(P uniqueId, List<Long> signatureIds) throws IOException {
        return dissociateGroupSignatures(uniqueId, signatureIds, null);
    }

    @Override
    public WriteListResponse<Long> dissociateGroupSignatures(P uniqueId, List<Long> signatureIds, String ownerName) throws IOException {
        Map<String, Object> map = AbstractClientAdapter.createParamMap("id", uniqueId);
        WriteListResponse<Long> data = deleteList(getUrlBasePrefix() + ".byId.groups.signatures.byGroupId", SignatureResponse.class, ownerName, map, "groupId", signatureIds);

        return data;
    }

    @Override
    public ApiEntitySingleResponse dissociateGroupSignature(P uniqueId, Long signatureId) throws IOException, FailedResponseException {
        return dissociateGroupSignature(uniqueId, signatureId, null);
    }

    @Override
    public ApiEntitySingleResponse dissociateGroupSignature(P uniqueId, Long signatureId, String ownerName) throws IOException, FailedResponseException {
        Map<String, Object> map = AbstractClientAdapter.createParamMap("id", uniqueId, "groupId", signatureId);
        SignatureResponse data = deleteItem(getUrlBasePrefix() + ".byId.groups.signatures.byGroupId", SignatureResponse.class, ownerName, map);

        return data;
    }

    @Override
    public WriteListResponse<Long> dissociateGroupThreats(P uniqueId, List<Long> threatIds) throws IOException {
        return dissociateGroupThreats(uniqueId, threatIds, null);
    }

    @Override
    public WriteListResponse<Long> dissociateGroupThreats(P uniqueId, List<Long> threatIds, String ownerName) throws IOException {
        Map<String, Object> map = AbstractClientAdapter.createParamMap("id", uniqueId);
        WriteListResponse<Long> data = deleteList(getUrlBasePrefix() + ".byId.groups.threats.byGroupId", ThreatResponse.class, ownerName, map, "groupId", threatIds);

        return data;
    }

    @Override
    public ApiEntitySingleResponse dissociateGroupThreat(P uniqueId, Long threatId) throws IOException, FailedResponseException {
        return dissociateGroupThreat(uniqueId, threatId, null);
    }

    @Override
    public ApiEntitySingleResponse dissociateGroupThreat(P uniqueId, Long threatId, String ownerName) throws IOException, FailedResponseException {
        Map<String, Object> map = AbstractClientAdapter.createParamMap("id", uniqueId, "groupId", threatId);
        ThreatResponse data = deleteItem(getUrlBasePrefix() + ".byId.groups.threats.byGroupId", ThreatResponse.class, ownerName, map);

        return data;
    }

}
