/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.client.reader.associate;

import com.threatconnect.sdk.client.response.IterableResponse;
import com.threatconnect.sdk.exception.FailedResponseException;
import com.threatconnect.sdk.server.entity.*;

import java.io.IOException;

/**
 *
 * @author dtineo
 */
public interface GroupAssociateReadable<P> {
    
    public IterableResponse<Group> getAssociatedGroups(P uniqueId) throws IOException, FailedResponseException;

    public IterableResponse<Group> getAssociatedGroups(P uniqueId, String ownerName)
            throws IOException, FailedResponseException;

    public IterableResponse<Adversary> getAssociatedGroupAdversaries(P uniqueId) throws IOException, FailedResponseException;

    public IterableResponse<Adversary> getAssociatedGroupAdversaries(P uniqueId, String ownerName)
            throws IOException, FailedResponseException;

    public Adversary getAssociatedGroupAdversary(P uniqueId, Long adversaryId) throws IOException, FailedResponseException;
    
    public Adversary getAssociatedGroupAdversary(P uniqueId, Long adversaryId, String ownerName) 
            throws IOException, FailedResponseException;

    public IterableResponse<Email> getAssociatedGroupEmails(P uniqueId) throws IOException, FailedResponseException;

    public IterableResponse<Email> getAssociatedGroupEmails(P uniqueId, String ownerName)
            throws IOException, FailedResponseException;

     public Email getAssociatedGroupEmail(P uniqueId, Long emailId) throws IOException, FailedResponseException;

    public Email getAssociatedGroupEmail(P uniqueId, Long emailId, String ownerName)
            throws IOException, FailedResponseException;

    public IterableResponse<Incident> getAssociatedGroupIncidents(P uniqueId) throws IOException, FailedResponseException;

    public IterableResponse<Incident> getAssociatedGroupIncidents(P uniqueId, String ownerName)
            throws IOException, FailedResponseException;

     public Incident getAssociatedGroupIncident(P uniqueId, Long incidentId) throws IOException, FailedResponseException;

    public Incident getAssociatedGroupIncident(P uniqueId, Long incidentId, String ownerName)
            throws IOException, FailedResponseException;

    public IterableResponse<Document> getAssociatedGroupDocuments(P uniqueId) throws IOException, FailedResponseException;

    public IterableResponse<Document> getAssociatedGroupDocuments(P uniqueId, String ownerName)
            throws IOException, FailedResponseException;

    public Document getAssociatedGroupDocument(P uniqueId, Long incidentId) throws IOException, FailedResponseException;

    public Document getAssociatedGroupDocument(P uniqueId, Long incidentId, String ownerName)
            throws IOException, FailedResponseException;

    public IterableResponse<Signature> getAssociatedGroupSignatures(P uniqueId) throws IOException, FailedResponseException;

    public IterableResponse<Signature> getAssociatedGroupSignatures(P uniqueId, String ownerName)
            throws IOException, FailedResponseException;

     public Signature getAssociatedGroupSignature(P uniqueId, Long signatureId) throws IOException, FailedResponseException;

    public Signature getAssociatedGroupSignature(P uniqueId, Long signatureId, String ownerName)
            throws IOException, FailedResponseException;

    public IterableResponse<Threat> getAssociatedGroupThreats(P uniqueId) throws IOException, FailedResponseException;

    public IterableResponse<Threat> getAssociatedGroupThreats(P uniqueId, String ownerName)
            throws IOException, FailedResponseException;

     public Threat getAssociatedGroupThreat(P uniqueId, Long threatId) throws IOException, FailedResponseException;

    public Threat getAssociatedGroupThreat(P uniqueId, Long threatId, String ownerName) 
            throws IOException, FailedResponseException;
    
    public IterableResponse<Campaign> getAssociatedGroupCampaigns(P uniqueId) throws IOException, FailedResponseException;
    
    public IterableResponse<Campaign> getAssociatedGroupCampaigns(P uniqueId, String ownerName)
        throws IOException, FailedResponseException;
    
    public Campaign getAssociatedGroupCampaign(P uniqueId, Long campaignId) throws IOException, FailedResponseException;
    
    public Campaign getAssociatedGroupCampaign(P uniqueId, Long campaignId, String ownerName)
        throws IOException, FailedResponseException;
}
