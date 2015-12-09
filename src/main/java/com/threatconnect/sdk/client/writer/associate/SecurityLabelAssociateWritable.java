/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.client.writer.associate;

import com.threatconnect.sdk.client.response.WriteListResponse;
import com.threatconnect.sdk.exception.FailedResponseException;
import com.threatconnect.sdk.server.response.entity.ApiEntitySingleResponse;
import com.threatconnect.sdk.client.response.WriteListResponse;
import com.threatconnect.sdk.server.response.entity.ApiEntitySingleResponse;

import java.io.IOException;
import java.util.List;

/**
 *
 * @author dtineo
 */
public interface SecurityLabelAssociateWritable<P> {

   public WriteListResponse<String> associateSecurityLabels(P uniqueId, List<String> securityLabels) throws IOException;

   public WriteListResponse<String> associateSecurityLabels(P uniqueId, List<String> securityLabels, String ownerName) 
            throws IOException;

   public ApiEntitySingleResponse associateSecurityLabel(P uniqueId, String securityLabel) throws IOException, FailedResponseException;

   public ApiEntitySingleResponse associateSecurityLabel(P uniqueId, String securityLabel, String ownerName) 
            throws IOException, FailedResponseException;

    public WriteListResponse<String> dissociateSecurityLabel(P uniqueId, List<String> securityLabels)
        throws IOException;

    public WriteListResponse<String> dissociateSecurityLabel(P uniqueId, List<String> securityLabels, String ownerName)
        throws IOException;

   public ApiEntitySingleResponse dissociateSecurityLabel(P uniqueId, String securityLabel) throws IOException, FailedResponseException;

   public ApiEntitySingleResponse dissociateSecurityLabel(P uniqueId, String securityLabel, String ownerName)
            throws IOException, FailedResponseException;


} 
