/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.client.reader.associate;

import com.threatconnect.sdk.exception.FailedResponseException;
import com.threatconnect.sdk.server.entity.Victim;
import com.threatconnect.sdk.server.entity.Victim;

import java.io.IOException;
import java.util.List;

/**
 *
 * @author dtineo
 */
public interface VictimAssociateReadable<P> {

   public List<Victim> getAssociatedVictims(P uniqueId) throws IOException, FailedResponseException;

   public List<Victim> getAssociatedVictims(P uniqueId, String ownerName) 
            throws IOException, FailedResponseException;

   public Victim getAssociatedVictim(P uniqueId, Integer victimId) throws IOException, FailedResponseException;

   public Victim getAssociatedVictim(P uniqueId, Integer victimId, String ownerName) 
            throws IOException, FailedResponseException;
    
}
