/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.client.reader.associate;

import com.threatconnect.sdk.client.UrlTypeable;
import com.threatconnect.sdk.client.reader.AbstractBaseReaderAdapter;
import com.threatconnect.sdk.conn.Connection;
import com.threatconnect.sdk.conn.AbstractRequestExecutor;
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
import java.util.List;
import java.util.Map;

/**
 *
 * @author dtineo
 */
public abstract class AbstractGroupAssociateReaderAdapter<P> extends AbstractBaseReaderAdapter implements GroupAssociateReadable<P>, UrlTypeable
{

    public AbstractGroupAssociateReaderAdapter(Connection conn, Class singleType, Class listType) {
        super(conn, singleType, listType);
    }

    @Override
        public List<Group> getAssociatedGroups(P uniqueId) throws IOException, FailedResponseException {
        return getAssociatedGroups(uniqueId, null); 
    }

    @Override
    public List<Group> getAssociatedGroups(P uniqueId, String ownerName) 
            throws IOException, FailedResponseException {

        Map<String,Object> map = createParamMap("id", uniqueId);
        GroupListResponse data = getList(getUrlBasePrefix() + ".byId.groups", GroupListResponse.class, ownerName, map);

        return (List<Group>)data.getData().getData();
    }

    @Override
    public List<Adversary> getAssociatedGroupAdversaries(P uniqueId) throws IOException, FailedResponseException {
        return getAssociatedGroupAdversaries(uniqueId, null); 
    }

    @Override
    public List<Adversary> getAssociatedGroupAdversaries(P uniqueId, String ownerName) 
            throws IOException, FailedResponseException {

        Map<String,Object> map = createParamMap("id", uniqueId);
        AdversaryListResponse data = getList(getUrlBasePrefix() + ".byId.groups.adversaries", AdversaryListResponse.class, ownerName, map);

        return (List<Adversary>)data.getData().getData();
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
    public List<Incident> getAssociatedGroupIncidents(P uniqueId) throws IOException, FailedResponseException {
        return getAssociatedGroupIncidents(uniqueId, null); 
    }

    @Override
    public List<Incident> getAssociatedGroupIncidents(P uniqueId, String ownerName)
            throws IOException, FailedResponseException {

        Map<String,Object> map = createParamMap("id", uniqueId);
        IncidentListResponse data = getList(getUrlBasePrefix() + ".byId.groups.incidents", IncidentListResponse.class, ownerName, map);

        return (List<Incident>)data.getData().getData();
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
    public List<Signature> getAssociatedGroupSignatures(P uniqueId) throws IOException, FailedResponseException {
        return getAssociatedGroupSignatures(uniqueId, null);
    }

    @Override
    public List<Signature> getAssociatedGroupSignatures(P uniqueId, String ownerName)
            throws IOException, FailedResponseException {

        Map<String,Object> map = createParamMap("id", uniqueId);
        SignatureListResponse data = getList(getUrlBasePrefix() + ".byId.groups.signatures", SignatureListResponse.class, ownerName, map);

        return (List<Signature>)data.getData().getData();
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
    public List<Threat> getAssociatedGroupThreats(P uniqueId) throws IOException, FailedResponseException {
        return getAssociatedGroupThreats(uniqueId, null); 
    }

    @Override
    public List<Threat> getAssociatedGroupThreats(P uniqueId, String ownerName) 
            throws IOException, FailedResponseException {

        Map<String,Object> map = createParamMap("id", uniqueId);
        ThreatListResponse data = getList(getUrlBasePrefix() + ".byId.groups.threats", ThreatListResponse.class, ownerName, map);

        return (List<Threat>)data.getData().getData();
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
    public List<Email> getAssociatedGroupEmails(P uniqueId) throws IOException, FailedResponseException {
        return getAssociatedGroupEmails(uniqueId,null);
    }

    @Override
    public List<Email> getAssociatedGroupEmails(P uniqueId, String ownerName) throws IOException, FailedResponseException {
        Map<String,Object> map = createParamMap("id", uniqueId);
        EmailListResponse data = getList(getUrlBasePrefix() + ".byId.groups.emails", EmailListResponse.class, ownerName, map);

        return (List<Email>)data.getData().getData();
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
