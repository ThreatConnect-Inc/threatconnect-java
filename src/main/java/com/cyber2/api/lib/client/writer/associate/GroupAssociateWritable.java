/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyber2.api.lib.client.writer.associate;

import com.cyber2.api.lib.client.response.WriteListResponse;
import com.cyber2.api.lib.exception.FailedResponseException;
import com.cyber2.api.lib.server.response.entity.ApiEntitySingleResponse;
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

    public ApiEntitySingleResponse associateGroupAdversary(P uniqueId, Integer adversaryId) 
        throws IOException, FailedResponseException;
    
    public ApiEntitySingleResponse associateGroupAdversary(P uniqueId, Integer adversaryId, String ownerName) 
            throws IOException, FailedResponseException;

    public WriteListResponse<Integer> associateGroupEmails(P uniqueId, List<Integer> emailIds) 
        throws IOException;

    public WriteListResponse<Integer> associateGroupEmails(P uniqueId, List<Integer> emailIds, String ownerName) 
            throws IOException;

    public ApiEntitySingleResponse associateGroupEmail(P uniqueId, Integer emailId) 
        throws IOException, FailedResponseException;
    
    public ApiEntitySingleResponse associateGroupEmail(P uniqueId, Integer emailId, String ownerName) 
            throws IOException, FailedResponseException;

    public WriteListResponse<Integer> associateGroupIncidents(P uniqueId, List<Integer> incidentIds) 
        throws IOException;

    public WriteListResponse<Integer> associateGroupIncidents(P uniqueId, List<Integer> incidentIds, String ownerName) 
            throws IOException;

    public ApiEntitySingleResponse associateGroupIncident(P uniqueId, Integer incidentId) 
        throws IOException, FailedResponseException;
    
    public ApiEntitySingleResponse associateGroupIncident(P uniqueId, Integer incidentId, String ownerName) 
            throws IOException, FailedResponseException;

    public WriteListResponse<Integer> associateGroupSignatures(P uniqueId, List<Integer> signatureIds) 
        throws IOException;

    public WriteListResponse<Integer> associateGroupSignatures(P uniqueId, List<Integer> signatureIds, String ownerName) 
            throws IOException;

    public ApiEntitySingleResponse associateGroupSignature(P uniqueId, Integer signatureId) 
        throws IOException, FailedResponseException;
    
    public ApiEntitySingleResponse associateGroupSignature(P uniqueId, Integer signatureId, String ownerName) 
            throws IOException, FailedResponseException;

    public WriteListResponse<Integer> associateGroupThreats(P uniqueId, List<Integer> threatIds) 
        throws IOException;

    public WriteListResponse<Integer> associateGroupThreats(P uniqueId, List<Integer> threatIds, String ownerName) 
            throws IOException;

    public ApiEntitySingleResponse associateGroupThreat(P uniqueId, Integer threatId) 
        throws IOException, FailedResponseException;
    
    public ApiEntitySingleResponse associateGroupThreat(P uniqueId, Integer threatId, String ownerName) 
            throws IOException, FailedResponseException;

    public WriteListResponse<Integer> dissociateGroupAdversaries(P uniqueId, List<Integer> adversaryIds)
        throws IOException;

    public WriteListResponse<Integer> dissociateGroupAdversaries(P uniqueId, List<Integer> adversaryIds, String ownerName)
        throws IOException;

    public ApiEntitySingleResponse dissociateGroupAdversary(P uniqueId, Integer adversaryId)
        throws IOException, FailedResponseException;
    
    public ApiEntitySingleResponse dissociateGroupAdversary(P uniqueId, Integer adversaryId, String ownerName)
            throws IOException, FailedResponseException;

    public WriteListResponse<Integer> dissociateGroupEmails(P uniqueId, List<Integer> emailIds)
        throws IOException;

    public WriteListResponse<Integer> dissociateGroupEmails(P uniqueId, List<Integer> emailIds, String ownerName)
            throws IOException;

    public ApiEntitySingleResponse dissociateGroupEmail(P uniqueId, Integer emailId)
        throws IOException, FailedResponseException;
    
    public ApiEntitySingleResponse dissociateGroupEmail(P uniqueId, Integer emailId, String ownerName)
            throws IOException, FailedResponseException;

    public WriteListResponse<Integer> dissociateGroupIncidents(P uniqueId, List<Integer> incidentIds)
        throws IOException;

    public WriteListResponse<Integer> dissociateGroupIncidents(P uniqueId, List<Integer> incidentIds, String ownerName)
            throws IOException;

    public ApiEntitySingleResponse dissociateGroupIncident(P uniqueId, Integer incidentId)
        throws IOException, FailedResponseException;
    
    public ApiEntitySingleResponse dissociateGroupIncident(P uniqueId, Integer incidentId, String ownerName)
            throws IOException, FailedResponseException;

    public WriteListResponse<Integer> dissociateGroupSignatures(P uniqueId, List<Integer> signatureIds)
        throws IOException;

    public WriteListResponse<Integer> dissociateGroupSignatures(P uniqueId, List<Integer> signatureIds, String ownerName)
            throws IOException;

    public ApiEntitySingleResponse dissociateGroupSignature(P uniqueId, Integer signatureId)
        throws IOException, FailedResponseException;
    
    public ApiEntitySingleResponse dissociateGroupSignature(P uniqueId, Integer signatureId, String ownerName)
            throws IOException, FailedResponseException;

    public WriteListResponse<Integer> dissociateGroupThreats(P uniqueId, List<Integer> threatIds)
        throws IOException;

    public WriteListResponse<Integer> dissociateGroupThreats(P uniqueId, List<Integer> threatIds, String ownerName)
            throws IOException;

    public ApiEntitySingleResponse dissociateGroupThreat(P uniqueId, Integer threatId)
        throws IOException, FailedResponseException;
    
    public ApiEntitySingleResponse dissociateGroupThreat(P uniqueId, Integer threatId, String ownerName)
            throws IOException, FailedResponseException;

}
