/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.client.reader.associate;

import com.threatconnect.sdk.exception.FailedResponseException;
import com.threatconnect.sdk.server.entity.Owner;
import com.threatconnect.sdk.server.entity.Owner;

import java.io.IOException;
import java.util.List;

/**
 *
 * @author dtineo
 */
public interface OwnerAssociateReadable<P> {
    public List<Owner> getAssociatedOwners(P uniqueId) throws IOException, FailedResponseException;
    public List<Owner> getAssociatedOwners(P uniqueId, String ownerName) throws IOException, FailedResponseException;
}
