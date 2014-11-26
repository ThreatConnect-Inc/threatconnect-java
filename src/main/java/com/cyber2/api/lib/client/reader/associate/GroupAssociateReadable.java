/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyber2.api.lib.client.reader.associate;

import com.cyber2.api.lib.exception.FailedResponseException;
import com.cyber2.api.lib.server.entity.Adversary;
import com.cyber2.api.lib.server.entity.Email;
import com.cyber2.api.lib.server.entity.Group;
import com.cyber2.api.lib.server.entity.Incident;
import com.cyber2.api.lib.server.entity.Signature;
import com.cyber2.api.lib.server.entity.Threat;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author dtineo
 */
public interface GroupAssociateReadable<P> {
    
    public List<Group> getAssociatedGroups(P uniqueId) throws IOException, FailedResponseException;

    public List<Group> getAssociatedGroups(P uniqueId, String ownerName) 
            throws IOException, FailedResponseException;

    public List<Adversary> getAssociatedGroupAdversaries(P uniqueId) throws IOException, FailedResponseException;

    public List<Adversary> getAssociatedGroupAdversaries(P uniqueId, String ownerName) 
            throws IOException, FailedResponseException;

    public Adversary getAssociatedGroupAdversary(P uniqueId, Integer adversaryId) throws IOException, FailedResponseException;
    
    public Adversary getAssociatedGroupAdversary(P uniqueId, Integer adversaryId, String ownerName) 
            throws IOException, FailedResponseException;

    public List<Email> getAssociatedGroupEmails(P uniqueId) throws IOException, FailedResponseException;

    public List<Email> getAssociatedGroupEmails(P uniqueId, String ownerName) 
            throws IOException, FailedResponseException;

     public Email getAssociatedGroupEmail(P uniqueId, Integer emailId) throws IOException, FailedResponseException;

    public Email getAssociatedGroupEmail(P uniqueId, Integer emailId, String ownerName)
            throws IOException, FailedResponseException;

    public List<Incident> getAssociatedGroupIncidents(P uniqueId) throws IOException, FailedResponseException;

    public List<Incident> getAssociatedGroupIncidents(P uniqueId, String ownerName)
            throws IOException, FailedResponseException;

     public Incident getAssociatedGroupIncident(P uniqueId, Integer incidentId) throws IOException, FailedResponseException;

    public Incident getAssociatedGroupIncident(P uniqueId, Integer incidentId, String ownerName)
            throws IOException, FailedResponseException;

    public List<Signature> getAssociatedGroupSignatures(P uniqueId) throws IOException, FailedResponseException;

    public List<Signature> getAssociatedGroupSignatures(P uniqueId, String ownerName)
            throws IOException, FailedResponseException;

     public Signature getAssociatedGroupSignature(P uniqueId, Integer signatureId) throws IOException, FailedResponseException;

    public Signature getAssociatedGroupSignature(P uniqueId, Integer signatureId, String ownerName)
            throws IOException, FailedResponseException;

    public List<Threat> getAssociatedGroupThreats(P uniqueId) throws IOException, FailedResponseException;

    public List<Threat> getAssociatedGroupThreats(P uniqueId, String ownerName) 
            throws IOException, FailedResponseException;

     public Threat getAssociatedGroupThreat(P uniqueId, Integer threatId) throws IOException, FailedResponseException;

    public Threat getAssociatedGroupThreat(P uniqueId, Integer threatId, String ownerName) 
            throws IOException, FailedResponseException;

}
