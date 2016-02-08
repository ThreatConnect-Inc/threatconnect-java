/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.client.reader.associate;

import com.threatconnect.sdk.client.response.IterableResponse;
import com.threatconnect.sdk.exception.FailedResponseException;
import com.threatconnect.sdk.server.entity.SecurityLabel;
import java.io.IOException;

/**
 *
 * @author dtineo
 */
public interface SecurityLabelAssociateReadable<P> {

    public IterableResponse<SecurityLabel> getAssociatedSecurityLabels(P uniqueId) throws IOException, FailedResponseException;

    public IterableResponse<SecurityLabel> getAssociatedSecurityLabels(P uniqueId, String ownerName)
        throws IOException, FailedResponseException;

    public SecurityLabel getAssociatedSecurityLabel(P uniqueId, String securityLabel) throws IOException, FailedResponseException;

    public SecurityLabel getAssociatedSecurityLabel(P uniqueId, String securityLabel, String ownerName)
        throws IOException, FailedResponseException;
}
