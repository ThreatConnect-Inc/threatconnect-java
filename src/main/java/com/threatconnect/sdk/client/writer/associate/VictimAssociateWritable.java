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
import com.threatconnect.sdk.exception.FailedResponseException;
import com.threatconnect.sdk.server.response.entity.ApiEntitySingleResponse;

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
