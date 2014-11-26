/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyber2.api.lib.client.writer.associate;

import com.cyber2.api.lib.client.response.WriteListResponse;
import com.cyber2.api.lib.exception.FailedResponseException;
import com.cyber2.api.lib.server.entity.Adversary;
import com.cyber2.api.lib.server.entity.Email;
import com.cyber2.api.lib.server.entity.Incident;
import com.cyber2.api.lib.server.entity.Signature;
import com.cyber2.api.lib.server.entity.Threat;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author dtineo
 */
public interface GroupAssociateWritable<P> {
    
    public WriteListResponse<Integer> associateGroupAdversaries(P uniqueId, List<Integer> adversaryIds) 
        throws IOException;

    public WriteListResponse<Integer> associateGroupAdversaries(P uniqueId, List<Integer> adversaryIds, String ownerName) 
        throws IOException;

    public boolean associateGroupAdversary(P uniqueId, Integer adversaryId) 
        throws IOException, FailedResponseException;
    
    public boolean associateGroupAdversary(P uniqueId, Integer adversaryId, String ownerName) 
            throws IOException, FailedResponseException;

    public WriteListResponse<Integer> associateGroupEmails(P uniqueId, List<Integer> emailIds) 
        throws IOException, FailedResponseException;

    public WriteListResponse<Integer> associateGroupEmails(P uniqueId, List<Integer> emailIds, String ownerName) 
            throws IOException, FailedResponseException;

    public boolean associateGroupEmail(P uniqueId, Integer emailId) 
        throws IOException, FailedResponseException;
    
    public boolean associateGroupEmail(P uniqueId, Integer emailId, String ownerName) 
            throws IOException, FailedResponseException;

    public WriteListResponse<Integer> associateGroupIncidents(P uniqueId, List<Integer> incidentIds) 
        throws IOException, FailedResponseException;

    public WriteListResponse<Integer> associateGroupIncidents(P uniqueId, List<Integer> incidentIds, String ownerName) 
            throws IOException, FailedResponseException;

    public boolean associateGroupIncident(P uniqueId, Integer incidentId) 
        throws IOException, FailedResponseException;
    
    public boolean associateGroupIncident(P uniqueId, Integer incidentId, String ownerName) 
            throws IOException, FailedResponseException;

    public WriteListResponse<Integer> associateGroupSignatures(P uniqueId, List<Integer> signatureIds) 
        throws IOException, FailedResponseException;

    public WriteListResponse<Integer> associateGroupSignatures(P uniqueId, List<Integer> signatureIds, String ownerName) 
            throws IOException, FailedResponseException;

    public boolean associateGroupSignature(P uniqueId, Integer signatureId) 
        throws IOException, FailedResponseException;
    
    public boolean associateGroupSignature(P uniqueId, Integer signatureId, String ownerName) 
            throws IOException, FailedResponseException;

    public WriteListResponse<Integer> associateGroupThreats(P uniqueId, List<Integer> threatIds) 
        throws IOException, FailedResponseException;

    public WriteListResponse<Integer> associateGroupThreats(P uniqueId, List<Integer> threatIds, String ownerName) 
            throws IOException, FailedResponseException;

    public boolean associateGroupThreat(P uniqueId, Integer threatId) 
        throws IOException, FailedResponseException;
    
    public boolean associateGroupThreat(P uniqueId, Integer threatId, String ownerName) 
            throws IOException, FailedResponseException;

}
