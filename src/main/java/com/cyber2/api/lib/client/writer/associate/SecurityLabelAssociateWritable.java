/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyber2.api.lib.client.writer.associate;

import com.cyber2.api.lib.client.response.WriteListResponse;
import com.cyber2.api.lib.exception.FailedResponseException;
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

   public boolean associateSecurityLabel(P uniqueId, String securityLabel) throws IOException, FailedResponseException;

   public boolean associateSecurityLabel(P uniqueId, String securityLabel, String ownerName) 
            throws IOException, FailedResponseException;

    public WriteListResponse<String> deleteAssociatedSecurityLabel(P uniqueId, List<String> securityLabels) 
        throws IOException;

    public WriteListResponse<String> deleteAssociatedSecurityLabel(P uniqueId, List<String> securityLabels, String ownerName) 
        throws IOException;

   public boolean deleteAssociatedSecurityLabel(P uniqueId, String securityLabel) throws IOException, FailedResponseException;

   public boolean deleteAssociatedSecurityLabel(P uniqueId, String securityLabel, String ownerName) 
            throws IOException, FailedResponseException;


} 