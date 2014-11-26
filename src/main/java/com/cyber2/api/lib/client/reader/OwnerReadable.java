/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyber2.api.lib.client.reader;

import com.cyber2.api.lib.exception.FailedResponseException;
import com.cyber2.api.lib.server.entity.Owner;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author dtineo
 */
public interface OwnerReadable<P> {
    public List<Owner> getOwners(P uniqueId) throws IOException, FailedResponseException;
}
