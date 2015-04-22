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
public interface VictimAssociateWritable<P> {

   public WriteListResponse<Integer> associateVictims(P uniqueId, List<Integer> victimIds) throws IOException;

   public WriteListResponse<Integer> associateVictims(P uniqueId, List<Integer> victimIds, String ownerName) 
            throws IOException;

   public ApiEntitySingleResponse associateVictim(P uniqueId, Integer victimId) throws IOException, FailedResponseException;

   public ApiEntitySingleResponse associateVictim(P uniqueId, Integer victimId, String ownerName) 
            throws IOException, FailedResponseException;
    
   public WriteListResponse<Integer> dissociateVictims(P uniqueId, List<Integer> victimIds) throws IOException;

   public WriteListResponse<Integer> dissociateVictims(P uniqueId, List<Integer> victimIds, String ownerName)
            throws IOException;

   public ApiEntitySingleResponse dissociateVictim(P uniqueId, Integer victimId) throws IOException, FailedResponseException;

   public ApiEntitySingleResponse dissociateVictim(P uniqueId, Integer victimId, String ownerName) throws IOException, FailedResponseException;
}
