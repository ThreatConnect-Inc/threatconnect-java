package com.cyber2.api.lib.client.reader.associate;

import com.cyber2.api.lib.exception.FailedResponseException;
import com.cyber2.api.lib.server.entity.Attribute;
import com.cyber2.api.lib.server.entity.SecurityLabel;
import java.io.IOException;
import java.util.List;

public interface AttributeAssociateReadable<P> {

    public List<Attribute> getAttributes(P uniqueId)
        throws IOException, FailedResponseException;

    public List<Attribute> getAttributes(P uniqueId, String ownerName)
        throws IOException, FailedResponseException;

    public Attribute getAttribute(P uniqueId, Integer attributeId) throws IOException, FailedResponseException;

    public Attribute getAttribute(P uniqueId, Integer attributeId, String ownerName)
        throws IOException, FailedResponseException;

    public List<SecurityLabel> getAttributeSecurityLabels(P uniqueId, Integer attributeId) throws IOException, FailedResponseException;

    public List<SecurityLabel> getAttributeSecurityLabels(P uniqueId, Integer attributeId, String ownerName)
        throws IOException, FailedResponseException;

    public SecurityLabel getAttributeSecurityLabel(P uniqueId, Integer attributeId, String securityLabel) throws IOException, FailedResponseException;

    public SecurityLabel getAttributeSecurityLabel(P uniqueId, Integer attributeId, String securityLabel, String ownerName)
        throws IOException, FailedResponseException;
}
