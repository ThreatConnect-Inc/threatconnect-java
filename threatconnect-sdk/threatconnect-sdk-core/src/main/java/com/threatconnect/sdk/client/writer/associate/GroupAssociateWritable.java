/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.client.writer.associate;

import com.threatconnect.sdk.client.response.WriteListResponse;
import com.threatconnect.sdk.exception.FailedResponseException;
import com.threatconnect.sdk.server.response.entity.ApiEntitySingleResponse;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author dtineo
 */
public interface GroupAssociateWritable<P> {
    
    public WriteListResponse<Long> associateGroupAdversaries(P uniqueId, List<Long> adversaryIds) 
        throws IOException;

    public WriteListResponse<Long> associateGroupAdversaries(P uniqueId, List<Long> adversaryIds, String ownerName) 
        throws IOException;

    public ApiEntitySingleResponse associateGroupAdversary(P uniqueId, Long adversaryId) 
        throws IOException, FailedResponseException;
    
    public ApiEntitySingleResponse associateGroupAdversary(P uniqueId, Long adversaryId, String ownerName) 
            throws IOException, FailedResponseException;
    
    public ApiEntitySingleResponse associateGroupDocument(P uniqueId, Long documentId) 
        throws IOException, FailedResponseException;
    
    public ApiEntitySingleResponse associateGroupDocument(P uniqueId, Long documentId, String ownerName) 
        throws IOException, FailedResponseException;

    public WriteListResponse<Long> associateGroupEmails(P uniqueId, List<Long> emailIds) 
        throws IOException;

    public WriteListResponse<Long> associateGroupEmails(P uniqueId, List<Long> emailIds, String ownerName) 
            throws IOException;

    public ApiEntitySingleResponse associateGroupEmail(P uniqueId, Long emailId) 
        throws IOException, FailedResponseException;
    
    public ApiEntitySingleResponse associateGroupEmail(P uniqueId, Long emailId, String ownerName) 
            throws IOException, FailedResponseException;

    public WriteListResponse<Long> associateGroupIncidents(P uniqueId, List<Long> incidentIds) 
        throws IOException;

    public WriteListResponse<Long> associateGroupIncidents(P uniqueId, List<Long> incidentIds, String ownerName) 
            throws IOException;

    public ApiEntitySingleResponse associateGroupIncident(P uniqueId, Long incidentId) 
        throws IOException, FailedResponseException;
    
    public ApiEntitySingleResponse associateGroupIncident(P uniqueId, Long incidentId, String ownerName) 
            throws IOException, FailedResponseException;

    public WriteListResponse<Long> associateGroupSignatures(P uniqueId, List<Long> signatureIds) 
        throws IOException;

    public WriteListResponse<Long> associateGroupSignatures(P uniqueId, List<Long> signatureIds, String ownerName) 
            throws IOException;

    public ApiEntitySingleResponse associateGroupSignature(P uniqueId, Long signatureId) 
        throws IOException, FailedResponseException;
    
    public ApiEntitySingleResponse associateGroupSignature(P uniqueId, Long signatureId, String ownerName) 
            throws IOException, FailedResponseException;

    public WriteListResponse<Long> associateGroupThreats(P uniqueId, List<Long> threatIds) 
        throws IOException;

    public WriteListResponse<Long> associateGroupThreats(P uniqueId, List<Long> threatIds, String ownerName) 
            throws IOException;

    public ApiEntitySingleResponse associateGroupThreat(P uniqueId, Long threatId) 
        throws IOException, FailedResponseException;
    
    public ApiEntitySingleResponse associateGroupThreat(P uniqueId, Long threatId, String ownerName) 
            throws IOException, FailedResponseException;

    public WriteListResponse<Long> dissociateGroupAdversaries(P uniqueId, List<Long> adversaryIds)
        throws IOException;

    public WriteListResponse<Long> dissociateGroupAdversaries(P uniqueId, List<Long> adversaryIds, String ownerName)
        throws IOException;

    public ApiEntitySingleResponse dissociateGroupAdversary(P uniqueId, Long adversaryId)
        throws IOException, FailedResponseException;
    
    public ApiEntitySingleResponse dissociateGroupAdversary(P uniqueId, Long adversaryId, String ownerName)
            throws IOException, FailedResponseException;

    public WriteListResponse<Long> dissociateGroupEmails(P uniqueId, List<Long> emailIds)
        throws IOException;

    public WriteListResponse<Long> dissociateGroupEmails(P uniqueId, List<Long> emailIds, String ownerName)
            throws IOException;

    public ApiEntitySingleResponse dissociateGroupEmail(P uniqueId, Long emailId)
        throws IOException, FailedResponseException;
    
    public ApiEntitySingleResponse dissociateGroupEmail(P uniqueId, Long emailId, String ownerName)
            throws IOException, FailedResponseException;

    public WriteListResponse<Long> dissociateGroupIncidents(P uniqueId, List<Long> incidentIds)
        throws IOException;

    public WriteListResponse<Long> dissociateGroupIncidents(P uniqueId, List<Long> incidentIds, String ownerName)
            throws IOException;

    public ApiEntitySingleResponse dissociateGroupIncident(P uniqueId, Long incidentId)
        throws IOException, FailedResponseException;
    
    public ApiEntitySingleResponse dissociateGroupIncident(P uniqueId, Long incidentId, String ownerName)
            throws IOException, FailedResponseException;

    public WriteListResponse<Long> dissociateGroupSignatures(P uniqueId, List<Long> signatureIds)
        throws IOException;

    public WriteListResponse<Long> dissociateGroupSignatures(P uniqueId, List<Long> signatureIds, String ownerName)
            throws IOException;

    public ApiEntitySingleResponse dissociateGroupSignature(P uniqueId, Long signatureId)
        throws IOException, FailedResponseException;
    
    public ApiEntitySingleResponse dissociateGroupSignature(P uniqueId, Long signatureId, String ownerName)
            throws IOException, FailedResponseException;

    public WriteListResponse<Long> dissociateGroupThreats(P uniqueId, List<Long> threatIds)
        throws IOException;

    public WriteListResponse<Long> dissociateGroupThreats(P uniqueId, List<Long> threatIds, String ownerName)
            throws IOException;

    public ApiEntitySingleResponse dissociateGroupThreat(P uniqueId, Long threatId)
        throws IOException, FailedResponseException;
    
    public ApiEntitySingleResponse dissociateGroupThreat(P uniqueId, Long threatId, String ownerName)
            throws IOException, FailedResponseException;

}
