package com.cyber2.api.lib.client.writer.associate;

import com.cyber2.api.lib.client.response.WriteListResponse;
import com.cyber2.api.lib.exception.FailedResponseException;
import com.cyber2.api.lib.server.entity.Attribute;
import java.io.IOException;
import java.util.List;

public interface AttributeAssociateWritable<P> {

    public WriteListResponse<Attribute> addAttributes(P uniqueId, List<Attribute> attributes)
        throws IOException, FailedResponseException;

    public WriteListResponse<Attribute> addAttributes(P uniqueId, List<Attribute> attribute, String ownerName)
        throws IOException, FailedResponseException;

    public Attribute addAttribute(P uniqueId, Attribute attribute) throws IOException, FailedResponseException;

    public Attribute addAttribute(P uniqueId, Attribute attribute, String ownerName)
        throws IOException, FailedResponseException;

    public WriteListResponse<String> addAttributeSecurityLabels(P uniqueId, Integer attributeId, List<String> securityLabels) throws IOException, FailedResponseException;

    public WriteListResponse<String> addAttributeSecurityLabels(P uniqueId, Integer attributeId, List<String> securityLabels, String ownerName)
        throws IOException, FailedResponseException;

    public boolean addAttributeSecurityLabel(P uniqueId, Integer attributeId, String securityLabelName) throws IOException, FailedResponseException;

    public boolean addAttributeSecurityLabel(P uniqueId, Integer attributeId, String securityLabelName, String ownerName)
        throws IOException, FailedResponseException;
}
