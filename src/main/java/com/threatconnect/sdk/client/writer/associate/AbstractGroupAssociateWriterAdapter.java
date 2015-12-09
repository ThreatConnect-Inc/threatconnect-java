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
    public WriteListResponse<Integer> associateGroupAdversaries(P uniqueId, List<Integer> adversaryIds) throws IOException {
        return associateGroupAdversaries(uniqueId, adversaryIds, null);
    }

    @Override
    public WriteListResponse<Integer> associateGroupAdversaries(P uniqueId, List<Integer> adversaryIds, String ownerName) throws IOException {

        Map<String, Object> map = AbstractClientAdapter.createParamMap("id", uniqueId);
        WriteListResponse<Integer> data = createListWithParam(getUrlBasePrefix() + ".byId.groups.adversaries.byGroupId", AdversaryResponse.class, ownerName, map, "groupId", adversaryIds);

        return data;
    }

    @Override
    public ApiEntitySingleResponse associateGroupAdversary(P uniqueId, Integer adversaryId) throws IOException, FailedResponseException {
        return associateGroupAdversary(uniqueId, adversaryId, null);
    }

    @Override
    public ApiEntitySingleResponse associateGroupAdversary(P uniqueId, Integer adversaryId, String ownerName) throws IOException, FailedResponseException {
        
        Map<String, Object> map = AbstractClientAdapter.createParamMap("id", uniqueId, "groupId", adversaryId);
        AdversaryResponse data = createItem(getUrlBasePrefix() + ".byId.groups.adversaries.byGroupId", AdversaryResponse.class, ownerName, map, null);

        return data;
    }

    @Override
    public WriteListResponse<Integer> associateGroupEmails(P uniqueId, List<Integer> emailIds) throws IOException {
        return associateGroupEmails(uniqueId, emailIds, null);
    }

    @Override
    public WriteListResponse<Integer> associateGroupEmails(P uniqueId, List<Integer> emailIds, String ownerName) throws IOException {

        Map<String, Object> map = AbstractClientAdapter.createParamMap("id", uniqueId);
        WriteListResponse<Integer> data = createListWithParam(getUrlBasePrefix() + ".byId.groups.emails.byGroupId", EmailResponse.class, ownerName, map, "groupId", emailIds);

        return data;
    }

    @Override
    public ApiEntitySingleResponse associateGroupEmail(P uniqueId, Integer emailId) throws IOException, FailedResponseException {
        return associateGroupEmail(uniqueId, emailId, null);
    }

    @Override
    public ApiEntitySingleResponse associateGroupEmail(P uniqueId, Integer emailId, String ownerName) throws IOException, FailedResponseException {
        Map<String, Object> map = AbstractClientAdapter.createParamMap("id", uniqueId, "groupId", emailId);
        EmailResponse data = createItem(getUrlBasePrefix() + ".byId.groups.emails.byGroupId", EmailResponse.class, ownerName, map, null);

        return data;
    }

    @Override
    public WriteListResponse<Integer> associateGroupIncidents(P uniqueId, List<Integer> incidentIds) throws IOException {
        return associateGroupIncidents(uniqueId, incidentIds, null);
    }

    @Override
    public WriteListResponse<Integer> associateGroupIncidents(P uniqueId, List<Integer> incidentIds, String ownerName) throws IOException {
        Map<String, Object> map = AbstractClientAdapter.createParamMap("id", uniqueId);
        WriteListResponse<Integer> data = createListWithParam(getUrlBasePrefix() + ".byId.groups.incidents.byGroupId", IncidentResponse.class, ownerName, map, "groupId", incidentIds);

        return data;
    }

    @Override
    public ApiEntitySingleResponse associateGroupIncident(P uniqueId, Integer incidentId) throws IOException, FailedResponseException {
        return associateGroupIncident(uniqueId, incidentId, null);
    }

    @Override
    public ApiEntitySingleResponse associateGroupIncident(P uniqueId, Integer incidentId, String ownerName) throws IOException, FailedResponseException {
        Map<String, Object> map = AbstractClientAdapter.createParamMap("id", uniqueId, "groupId", incidentId);
        IncidentResponse data = createItem(getUrlBasePrefix() + ".byId.groups.incidents.byGroupId", IncidentResponse.class, ownerName, map, null);

        return data;
    }

    @Override
    public WriteListResponse<Integer> associateGroupSignatures(P uniqueId, List<Integer> signatureIds) throws IOException {
        return associateGroupSignatures(uniqueId, signatureIds, null);
    }

    @Override
    public WriteListResponse<Integer> associateGroupSignatures(P uniqueId, List<Integer> signatureIds, String ownerName) throws IOException {
        Map<String, Object> map = AbstractClientAdapter.createParamMap("id", uniqueId);
        WriteListResponse<Integer> data = createListWithParam(getUrlBasePrefix() + ".byId.groups.signatures.byGroupId", SignatureResponse.class, ownerName, map, "groupId", signatureIds);

        return data;
    }

    @Override
    public ApiEntitySingleResponse associateGroupSignature(P uniqueId, Integer signatureId) throws IOException, FailedResponseException {
        return associateGroupSignature(uniqueId, signatureId, null);
    }

    @Override
    public ApiEntitySingleResponse associateGroupSignature(P uniqueId, Integer signatureId, String ownerName) throws IOException, FailedResponseException {
        Map<String, Object> map = AbstractClientAdapter.createParamMap("id", uniqueId, "groupId", signatureId);
        SignatureResponse data = createItem(getUrlBasePrefix() + ".byId.groups.signatures.byGroupId", SignatureResponse.class, ownerName, map, null);

        return data;
    }

    @Override
    public WriteListResponse<Integer> associateGroupThreats(P uniqueId, List<Integer> threatIds) throws IOException {
        return associateGroupThreats(uniqueId, threatIds, null);
    }

    @Override
    public WriteListResponse<Integer> associateGroupThreats(P uniqueId, List<Integer> threatIds, String ownerName) throws IOException {
        Map<String, Object> map = AbstractClientAdapter.createParamMap("id", uniqueId);
        WriteListResponse<Integer> data = createListWithParam(getUrlBasePrefix() + ".byId.groups.threats.byGroupId", ThreatResponse.class, ownerName, map, "groupId", threatIds);

        return data;
    }

    @Override
    public ApiEntitySingleResponse associateGroupThreat(P uniqueId, Integer threatId) throws IOException, FailedResponseException {
        return associateGroupThreat(uniqueId, threatId, null);
    }

    @Override
    public ApiEntitySingleResponse associateGroupThreat(P uniqueId, Integer threatId, String ownerName) throws IOException, FailedResponseException {
        Map<String, Object> map = AbstractClientAdapter.createParamMap("id", uniqueId, "groupId", threatId);
        ThreatResponse data = createItem(getUrlBasePrefix() + ".byId.groups.threats.byGroupId", ThreatResponse.class, ownerName, map, null);

        return data;
    }

    @Override
    public WriteListResponse<Integer> dissociateGroupAdversaries(P uniqueId, List<Integer> adversaryIds) throws IOException {
        return dissociateGroupAdversaries(uniqueId, adversaryIds, null);
    }

    @Override
    public WriteListResponse<Integer> dissociateGroupAdversaries(P uniqueId, List<Integer> adversaryIds, String ownerName) throws IOException {

        Map<String, Object> map = AbstractClientAdapter.createParamMap("id", uniqueId);
        WriteListResponse<Integer> data = deleteList(getUrlBasePrefix() + ".byId.groups.adversaries.byGroupId", AdversaryResponse.class, ownerName, map, "groupId", adversaryIds);

        return data;
    }

    @Override
    public ApiEntitySingleResponse dissociateGroupAdversary(P uniqueId, Integer adversaryId) throws IOException, FailedResponseException {
        return dissociateGroupAdversary(uniqueId, adversaryId, null);
    }

    @Override
    public ApiEntitySingleResponse dissociateGroupAdversary(P uniqueId, Integer adversaryId, String ownerName) throws IOException, FailedResponseException {
        
        Map<String, Object> map = AbstractClientAdapter.createParamMap("id", uniqueId, "groupId", adversaryId);
        AdversaryResponse data = deleteItem(getUrlBasePrefix() + ".byId.groups.adversaries.byGroupId", AdversaryResponse.class, ownerName, map);

        return data;
    }

    @Override
    public WriteListResponse<Integer> dissociateGroupEmails(P uniqueId, List<Integer> emailIds) throws IOException {
        return dissociateGroupEmails(uniqueId, emailIds, null);
    }

    @Override
    public WriteListResponse<Integer> dissociateGroupEmails(P uniqueId, List<Integer> emailIds, String ownerName) throws IOException {

        Map<String, Object> map = AbstractClientAdapter.createParamMap("id", uniqueId);
        WriteListResponse<Integer> data = deleteList(getUrlBasePrefix() + ".byId.groups.emails.byGroupId", EmailResponse.class, ownerName, map, "groupId", emailIds);

        return data;
    }

    @Override
    public ApiEntitySingleResponse dissociateGroupEmail(P uniqueId, Integer emailId) throws IOException, FailedResponseException {
        return dissociateGroupEmail(uniqueId, emailId, null);
    }

    @Override
    public ApiEntitySingleResponse dissociateGroupEmail(P uniqueId, Integer emailId, String ownerName) throws IOException, FailedResponseException {
        Map<String, Object> map = AbstractClientAdapter.createParamMap("id", uniqueId, "groupId", emailId);
        EmailResponse data = deleteItem(getUrlBasePrefix() + ".byId.groups.emails.byGroupId", EmailResponse.class, ownerName, map);

        return data;
    }

    @Override
    public WriteListResponse<Integer> dissociateGroupIncidents(P uniqueId, List<Integer> incidentIds) throws IOException {
        return dissociateGroupIncidents(uniqueId, incidentIds, null);
    }

    @Override
    public WriteListResponse<Integer> dissociateGroupIncidents(P uniqueId, List<Integer> incidentIds, String ownerName) throws IOException {
        Map<String, Object> map = AbstractClientAdapter.createParamMap("id", uniqueId);
        WriteListResponse<Integer> data = deleteList(getUrlBasePrefix() + ".byId.groups.incidents.byGroupId", IncidentResponse.class, ownerName, map, "groupId", incidentIds);

        return data;
    }

    @Override
    public ApiEntitySingleResponse dissociateGroupIncident(P uniqueId, Integer incidentId) throws IOException, FailedResponseException {
        return dissociateGroupIncident(uniqueId, incidentId, null);
    }

    @Override
    public ApiEntitySingleResponse dissociateGroupIncident(P uniqueId, Integer incidentId, String ownerName) throws IOException, FailedResponseException {
        Map<String, Object> map = AbstractClientAdapter.createParamMap("id", uniqueId, "groupId", incidentId);
        IncidentResponse data = deleteItem(getUrlBasePrefix() + ".byId.groups.incidents.byGroupId", IncidentResponse.class, ownerName, map);

        return data;
    }

    @Override
    public WriteListResponse<Integer> dissociateGroupSignatures(P uniqueId, List<Integer> signatureIds) throws IOException {
        return dissociateGroupSignatures(uniqueId, signatureIds, null);
    }

    @Override
    public WriteListResponse<Integer> dissociateGroupSignatures(P uniqueId, List<Integer> signatureIds, String ownerName) throws IOException {
        Map<String, Object> map = AbstractClientAdapter.createParamMap("id", uniqueId);
        WriteListResponse<Integer> data = deleteList(getUrlBasePrefix() + ".byId.groups.signatures.byGroupId", SignatureResponse.class, ownerName, map, "groupId", signatureIds);

        return data;
    }

    @Override
    public ApiEntitySingleResponse dissociateGroupSignature(P uniqueId, Integer signatureId) throws IOException, FailedResponseException {
        return dissociateGroupSignature(uniqueId, signatureId, null);
    }

    @Override
    public ApiEntitySingleResponse dissociateGroupSignature(P uniqueId, Integer signatureId, String ownerName) throws IOException, FailedResponseException {
        Map<String, Object> map = AbstractClientAdapter.createParamMap("id", uniqueId, "groupId", signatureId);
        SignatureResponse data = deleteItem(getUrlBasePrefix() + ".byId.groups.signatures.byGroupId", SignatureResponse.class, ownerName, map);

        return data;
    }

    @Override
    public WriteListResponse<Integer> dissociateGroupThreats(P uniqueId, List<Integer> threatIds) throws IOException {
        return dissociateGroupThreats(uniqueId, threatIds, null);
    }

    @Override
    public WriteListResponse<Integer> dissociateGroupThreats(P uniqueId, List<Integer> threatIds, String ownerName) throws IOException {
        Map<String, Object> map = AbstractClientAdapter.createParamMap("id", uniqueId);
        WriteListResponse<Integer> data = deleteList(getUrlBasePrefix() + ".byId.groups.threats.byGroupId", ThreatResponse.class, ownerName, map, "groupId", threatIds);

        return data;
    }

    @Override
    public ApiEntitySingleResponse dissociateGroupThreat(P uniqueId, Integer threatId) throws IOException, FailedResponseException {
        return dissociateGroupThreat(uniqueId, threatId, null);
    }

    @Override
    public ApiEntitySingleResponse dissociateGroupThreat(P uniqueId, Integer threatId, String ownerName) throws IOException, FailedResponseException {
        Map<String, Object> map = AbstractClientAdapter.createParamMap("id", uniqueId, "groupId", threatId);
        ThreatResponse data = deleteItem(getUrlBasePrefix() + ".byId.groups.threats.byGroupId", ThreatResponse.class, ownerName, map);

        return data;
    }

}
