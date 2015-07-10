/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.client.reader.associate;

import com.threatconnect.sdk.client.UrlTypeable;
import com.threatconnect.sdk.client.reader.AbstractBaseReaderAdapter;
import com.threatconnect.sdk.client.response.IterableResponse;
import com.threatconnect.sdk.conn.Connection;
import com.threatconnect.sdk.exception.FailedResponseException;
import com.threatconnect.sdk.server.entity.Adversary;
import com.threatconnect.sdk.server.entity.Email;
import com.threatconnect.sdk.server.entity.Group;
import com.threatconnect.sdk.server.entity.Incident;
import com.threatconnect.sdk.server.entity.Signature;
import com.threatconnect.sdk.server.entity.Threat;
import com.threatconnect.sdk.server.response.entity.AdversaryListResponse;
import com.threatconnect.sdk.server.response.entity.AdversaryResponse;
import com.threatconnect.sdk.server.response.entity.EmailListResponse;
import com.threatconnect.sdk.server.response.entity.EmailResponse;
import com.threatconnect.sdk.server.response.entity.GroupListResponse;
import com.threatconnect.sdk.server.response.entity.IncidentListResponse;
import com.threatconnect.sdk.server.response.entity.IncidentResponse;
import com.threatconnect.sdk.server.response.entity.SignatureListResponse;
import com.threatconnect.sdk.server.response.entity.SignatureResponse;
import com.threatconnect.sdk.server.response.entity.ThreatListResponse;
import com.threatconnect.sdk.server.response.entity.ThreatResponse;

import java.io.IOException;
import java.util.Map;

/**
 *
 * @author dtineo
 */
public abstract class AbstractGroupAssociateReaderAdapter<P> extends AbstractBaseReaderAdapter implements GroupAssociateReadable<P>, UrlTypeable
{

    public AbstractGroupAssociateReaderAdapter(Connection conn, Class singleType, Class singleItemType, Class listType) {
        super(conn, singleType, singleItemType, listType);
    }

    @Override
        public IterableResponse<Group> getAssociatedGroups(P uniqueId) throws IOException, FailedResponseException {
        return getAssociatedGroups(uniqueId, null); 
    }

    @Override
    public IterableResponse<Group> getAssociatedGroups(P uniqueId, String ownerName)
            throws IOException, FailedResponseException {

        Map<String,Object> map = createParamMap("id", uniqueId);
        return getItems(getUrlBasePrefix() + ".byId.groups", GroupListResponse.class, Group.class, ownerName, map);
    }

    @Override
    public IterableResponse<Adversary> getAssociatedGroupAdversaries(P uniqueId) throws IOException, FailedResponseException {
        return getAssociatedGroupAdversaries(uniqueId, null); 
    }

    @Override
    public IterableResponse<Adversary> getAssociatedGroupAdversaries(P uniqueId, String ownerName)
            throws IOException, FailedResponseException {

        Map<String,Object> map = createParamMap("id", uniqueId);
        return getItems(getUrlBasePrefix() + ".byId.groups.adversaries", AdversaryListResponse.class, Adversary.class, ownerName, map);
    }

    @Override
    public Adversary getAssociatedGroupAdversary(P uniqueId, Integer adversaryId) throws IOException, FailedResponseException {
        return getAssociatedGroupAdversary(uniqueId, adversaryId, null); 
    }

    @Override
    public Adversary getAssociatedGroupAdversary(P uniqueId, Integer adversaryId, String ownerName) 
            throws IOException, FailedResponseException {

        Map<String,Object> map = createParamMap("id", uniqueId, "groupId", adversaryId);
        AdversaryResponse data = getItem(getUrlBasePrefix() + ".byId.groups.adversaries.byGroupId", AdversaryResponse.class, ownerName, map);

        return (Adversary)data.getData().getData();
    }

    @Override
    public IterableResponse<Incident> getAssociatedGroupIncidents(P uniqueId) throws IOException, FailedResponseException {
        return getAssociatedGroupIncidents(uniqueId, null); 
    }

    @Override
    public IterableResponse<Incident> getAssociatedGroupIncidents(P uniqueId, String ownerName)
            throws IOException, FailedResponseException {

        Map<String,Object> map = createParamMap("id", uniqueId);
        return getItems(getUrlBasePrefix() + ".byId.groups.incidents", IncidentListResponse.class, Incident.class, ownerName, map);
    }

    @Override
     public Incident getAssociatedGroupIncident(P uniqueId, Integer incidentId) throws IOException, FailedResponseException {
        return getAssociatedGroupIncident(uniqueId, incidentId, null);
    }

    @Override
    public Incident getAssociatedGroupIncident(P uniqueId, Integer incidentId, String ownerName)
            throws IOException, FailedResponseException {

        Map<String,Object> map = createParamMap("id", uniqueId, "groupId", incidentId);
        IncidentResponse data = getItem(getUrlBasePrefix() + ".byId.groups.incidents.byGroupId", IncidentResponse.class, ownerName, map);

        return (Incident)data.getData().getData();
    }

    @Override
    public IterableResponse<Signature> getAssociatedGroupSignatures(P uniqueId) throws IOException, FailedResponseException {
        return getAssociatedGroupSignatures(uniqueId, null);
    }

    @Override
    public IterableResponse<Signature> getAssociatedGroupSignatures(P uniqueId, String ownerName)
            throws IOException, FailedResponseException {

        Map<String,Object> map = createParamMap("id", uniqueId);
        return getItems(getUrlBasePrefix() + ".byId.groups.signatures", SignatureListResponse.class, Signature.class, ownerName, map);
    }

    @Override
     public Signature getAssociatedGroupSignature(P uniqueId, Integer signatureId) throws IOException, FailedResponseException {
        return getAssociatedGroupSignature(uniqueId, signatureId, null);
    }

    @Override
    public Signature getAssociatedGroupSignature(P uniqueId, Integer signatureId, String ownerName)
            throws IOException, FailedResponseException {

        Map<String,Object> map = createParamMap("id", uniqueId, "groupId", signatureId);
        SignatureResponse data = getItem(getUrlBasePrefix() + ".byId.groups.signatures.byGroupId", SignatureResponse.class, ownerName, map);

        return (Signature)data.getData().getData();
    }

    @Override
    public IterableResponse<Threat> getAssociatedGroupThreats(P uniqueId) throws IOException, FailedResponseException {
        return getAssociatedGroupThreats(uniqueId, null); 
    }

    @Override
    public IterableResponse<Threat> getAssociatedGroupThreats(P uniqueId, String ownerName)
            throws IOException, FailedResponseException {

        Map<String,Object> map = createParamMap("id", uniqueId);
        return getItems(getUrlBasePrefix() + ".byId.groups.threats", ThreatListResponse.class, Threat.class, ownerName, map);
    }

    @Override
     public Threat getAssociatedGroupThreat(P uniqueId, Integer threatId) throws IOException, FailedResponseException {
        return getAssociatedGroupThreat(uniqueId, threatId, null); 
    }

    @Override
    public Threat getAssociatedGroupThreat(P uniqueId, Integer threatId, String ownerName) 
            throws IOException, FailedResponseException {

        Map<String,Object> map = createParamMap("id", uniqueId, "groupId", threatId);
        ThreatResponse data = getItem(getUrlBasePrefix() + ".byId.groups.threats.byGroupId", ThreatResponse.class, ownerName, map);

        return (Threat)data.getData().getData();
    }

    @Override
    public IterableResponse<Email> getAssociatedGroupEmails(P uniqueId) throws IOException, FailedResponseException {
        return getAssociatedGroupEmails(uniqueId,null);
    }

    @Override
    public IterableResponse<Email> getAssociatedGroupEmails(P uniqueId, String ownerName) throws IOException, FailedResponseException {
        Map<String,Object> map = createParamMap("id", uniqueId);
        return getItems(getUrlBasePrefix() + ".byId.groups.emails", EmailListResponse.class, Email.class, ownerName, map);
    }

    @Override
    public Email getAssociatedGroupEmail(P uniqueId, Integer emailId) throws IOException, FailedResponseException {
        return getAssociatedGroupEmail(uniqueId, emailId, null);
    }

    @Override
    public Email getAssociatedGroupEmail(P uniqueId, Integer emailId, String ownerName) throws IOException, FailedResponseException {
        Map<String,Object> map = createParamMap("id", uniqueId, "groupId", emailId);
        EmailResponse data = getItem(getUrlBasePrefix() + ".byId.groups.emails.byGroupId", EmailResponse.class, ownerName, map);

        return (Email)data.getData().getData();
    }

}
