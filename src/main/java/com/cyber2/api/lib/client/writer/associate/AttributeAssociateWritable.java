package com.cyber2.api.lib.client.writer.associate;

import com.cyber2.api.lib.client.response.WriteListResponse;
import com.cyber2.api.lib.exception.FailedResponseException;
import com.cyber2.api.lib.server.entity.Attribute;
import java.io.IOException;
import java.util.List;

public interface AttributeAssociateWritable<P> {

    public WriteListResponse<Attribute> addAttributes(P uniqueId, List<Attribute> attributes)
        throws IOException;

    public WriteListResponse<Attribute> addAttributes(P uniqueId, List<Attribute> attribute, String ownerName)
        throws IOException;

    public Attribute addAttribute(P uniqueId, Attribute attribute) throws IOException, FailedResponseException;

    public Attribute addAttribute(P uniqueId, Attribute attribute, String ownerName)
        throws IOException, FailedResponseException;

    public WriteListResponse<String> addAttributeSecurityLabels(P uniqueId, Integer attributeId, List<String> securityLabels) throws IOException;

    public WriteListResponse<String> addAttributeSecurityLabels(P uniqueId, Integer attributeId, List<String> securityLabels, String ownerName)
        throws IOException;

    public boolean addAttributeSecurityLabel(P uniqueId, Integer attributeId, String securityLabel) throws IOException, FailedResponseException;

    public boolean addAttributeSecurityLabel(P uniqueId, Integer attributeId, String securityLabel, String ownerName)
        throws IOException, FailedResponseException;

    public WriteListResponse<Attribute> updateAttributes(P uniqueId, List<Attribute> attributes)
        throws IOException;

    public WriteListResponse<Attribute> updateAttributes(P uniqueId, List<Attribute> attribute, String ownerName)
        throws IOException;

    public boolean updateAttribute(P uniqueId, Attribute attribute) throws IOException, FailedResponseException;

    public boolean updateAttribute(P uniqueId, Attribute attribute, String ownerName)
        throws IOException, FailedResponseException;

    public WriteListResponse<Integer> deleteAttributes(P uniqueId, List<Integer> attributes)
        throws IOException;

    public WriteListResponse<Integer> deleteAttributes(P uniqueId, List<Integer> attribute, String ownerName)
        throws IOException;

    public boolean deleteAttribute(P uniqueId, Integer attribute) throws IOException, FailedResponseException;

    public boolean deleteAttribute(P uniqueId, Integer attribute, String ownerName)
        throws IOException, FailedResponseException;

    public WriteListResponse<String> deleteAttributeSecurityLabels(P uniqueId, Integer attributeId, List<String> securityLabels) throws IOException, FailedResponseException;

    public WriteListResponse<String> deleteAttributeSecurityLabels(P uniqueId, Integer attributeId, List<String> securityLabels, String ownerName)
        throws IOException, FailedResponseException;

    public boolean deleteAttributeSecurityLabel(P uniqueId, Integer attributeId, String securityLabel) throws IOException, FailedResponseException;

    public boolean deleteAttributeSecurityLabel(P uniqueId, Integer attributeId, String securityLabel, String ownerName)
        throws IOException, FailedResponseException;

}
