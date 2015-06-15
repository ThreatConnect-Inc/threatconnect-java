package com.threatconnect.sdk.client.reader.associate;

import com.threatconnect.sdk.client.reader.IterableResponse;
import com.threatconnect.sdk.exception.FailedResponseException;
import com.threatconnect.sdk.server.entity.Attribute;
import com.threatconnect.sdk.server.entity.SecurityLabel;

import java.io.IOException;

public interface AttributeAssociateReadable<P> {

    public IterableResponse<Attribute> getAttributes(P uniqueId)
        throws IOException, FailedResponseException;

    public IterableResponse<Attribute> getAttributes(P uniqueId, String ownerName)
        throws IOException, FailedResponseException;

    public Attribute getAttribute(P uniqueId, Integer attributeId) throws IOException, FailedResponseException;

    public Attribute getAttribute(P uniqueId, Integer attributeId, String ownerName)
        throws IOException, FailedResponseException;

    public IterableResponse<SecurityLabel> getAttributeSecurityLabels(P uniqueId, Integer attributeId) throws IOException, FailedResponseException;

    public IterableResponse<SecurityLabel> getAttributeSecurityLabels(P uniqueId, Integer attributeId, String ownerName)
        throws IOException, FailedResponseException;

    public SecurityLabel getAttributeSecurityLabel(P uniqueId, Integer attributeId, String securityLabel) throws IOException, FailedResponseException;

    public SecurityLabel getAttributeSecurityLabel(P uniqueId, Integer attributeId, String securityLabel, String ownerName)
        throws IOException, FailedResponseException;
}
