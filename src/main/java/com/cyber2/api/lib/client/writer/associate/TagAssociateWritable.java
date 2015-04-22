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
public interface TagAssociateWritable<P> {

   public WriteListResponse<String> associateTags(P uniqueId, List<String> tagNames) throws IOException;

   public WriteListResponse<String> associateTags(P uniqueId, List<String> tagNames, String ownerName) 
            throws IOException;

   public ApiEntitySingleResponse associateTag(P uniqueId, String tagName) throws IOException, FailedResponseException;

   public ApiEntitySingleResponse associateTag(P uniqueId, String tagName, String ownerName) 
            throws IOException, FailedResponseException;
    
   public WriteListResponse<String> dissociateTags(P uniqueId, List<String> tagNames) throws IOException;

   public WriteListResponse<String> dissociateTags(P uniqueId, List<String> tagNames, String ownerName)
            throws IOException;

   public ApiEntitySingleResponse dissociateTag(P uniqueId, String tagName) throws IOException, FailedResponseException;

   public ApiEntitySingleResponse dissociateTag(P uniqueId, String tagName, String ownerName)
            throws IOException, FailedResponseException;

}
