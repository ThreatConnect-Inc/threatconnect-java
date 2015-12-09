/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.client.reader.associate;

import com.threatconnect.sdk.client.response.IterableResponse;
import com.threatconnect.sdk.exception.FailedResponseException;
import com.threatconnect.sdk.server.entity.Adversary;
import com.threatconnect.sdk.server.entity.Email;
import com.threatconnect.sdk.server.entity.Group;
import com.threatconnect.sdk.server.entity.Incident;
import com.threatconnect.sdk.server.entity.Signature;
import com.threatconnect.sdk.server.entity.Threat;

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

    public Adversary getAssociatedGroupAdversary(P uniqueId, Integer adversaryId) throws IOException, FailedResponseException;
    
    public Adversary getAssociatedGroupAdversary(P uniqueId, Integer adversaryId, String ownerName) 
            throws IOException, FailedResponseException;

    public IterableResponse<Email> getAssociatedGroupEmails(P uniqueId) throws IOException, FailedResponseException;

    public IterableResponse<Email> getAssociatedGroupEmails(P uniqueId, String ownerName)
            throws IOException, FailedResponseException;

     public Email getAssociatedGroupEmail(P uniqueId, Integer emailId) throws IOException, FailedResponseException;

    public Email getAssociatedGroupEmail(P uniqueId, Integer emailId, String ownerName)
            throws IOException, FailedResponseException;

    public IterableResponse<Incident> getAssociatedGroupIncidents(P uniqueId) throws IOException, FailedResponseException;

    public IterableResponse<Incident> getAssociatedGroupIncidents(P uniqueId, String ownerName)
            throws IOException, FailedResponseException;

     public Incident getAssociatedGroupIncident(P uniqueId, Integer incidentId) throws IOException, FailedResponseException;

    public Incident getAssociatedGroupIncident(P uniqueId, Integer incidentId, String ownerName)
            throws IOException, FailedResponseException;

    public IterableResponse<Signature> getAssociatedGroupSignatures(P uniqueId) throws IOException, FailedResponseException;

    public IterableResponse<Signature> getAssociatedGroupSignatures(P uniqueId, String ownerName)
            throws IOException, FailedResponseException;

     public Signature getAssociatedGroupSignature(P uniqueId, Integer signatureId) throws IOException, FailedResponseException;

    public Signature getAssociatedGroupSignature(P uniqueId, Integer signatureId, String ownerName)
            throws IOException, FailedResponseException;

    public IterableResponse<Threat> getAssociatedGroupThreats(P uniqueId) throws IOException, FailedResponseException;

    public IterableResponse<Threat> getAssociatedGroupThreats(P uniqueId, String ownerName)
            throws IOException, FailedResponseException;

     public Threat getAssociatedGroupThreat(P uniqueId, Integer threatId) throws IOException, FailedResponseException;

    public Threat getAssociatedGroupThreat(P uniqueId, Integer threatId, String ownerName) 
            throws IOException, FailedResponseException;

}
