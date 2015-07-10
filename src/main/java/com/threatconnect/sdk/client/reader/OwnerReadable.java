/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.client.reader;

import com.threatconnect.sdk.client.response.IterableResponse;
import com.threatconnect.sdk.exception.FailedResponseException;
import com.threatconnect.sdk.server.entity.Owner;

import java.io.IOException;

/**
 *
 * @author dtineo
 */
public interface OwnerReadable<P> {
    public IterableResponse<Owner> getOwners(P uniqueId) throws IOException, FailedResponseException;
}
