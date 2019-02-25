package com.threatconnect.sdk.client.writer.associate;

import com.threatconnect.sdk.client.response.WriteListResponse;
import com.threatconnect.sdk.exception.FailedResponseException;
import com.threatconnect.sdk.server.entity.Attribute;
import com.threatconnect.sdk.server.response.entity.ApiEntitySingleResponse;
import com.threatconnect.sdk.client.response.WriteListResponse;
import com.threatconnect.sdk.server.entity.Attribute;

import java.io.IOException;
import java.util.List;

public interface AttributeAssociateWritable<P> {

    public WriteListResponse<Attribute> addAttributes(P uniqueId, List<Attribute> attributes)
        throws IOException;

    public WriteListResponse<Attribute> addAttributes(P uniqueId, List<Attribute> attribute, String ownerName)
        throws IOException;

    public ApiEntitySingleResponse addAttribute(P uniqueId, Attribute attribute) throws IOException, FailedResponseException;

    public ApiEntitySingleResponse addAttribute(P uniqueId, Attribute attribute, String ownerName)
        throws IOException, FailedResponseException;

    public WriteListResponse<String> addAttributeSecurityLabels(P uniqueId, Long attributeId, List<String> securityLabels) throws IOException;

    public WriteListResponse<String> addAttributeSecurityLabels(P uniqueId, Long attributeId, List<String> securityLabels, String ownerName)
        throws IOException;

    public ApiEntitySingleResponse addAttributeSecurityLabel(P uniqueId, Long attributeId, String securityLabel) throws IOException, FailedResponseException;

    public ApiEntitySingleResponse addAttributeSecurityLabel(P uniqueId, Long attributeId, String securityLabel, String ownerName)
        throws IOException, FailedResponseException;

    public WriteListResponse<Attribute> updateAttributes(P uniqueId, List<Attribute> attributes)
        throws IOException;

    public WriteListResponse<Attribute> updateAttributes(P uniqueId, List<Attribute> attribute, String ownerName)
        throws IOException;

    public ApiEntitySingleResponse updateAttribute(P uniqueId, Attribute attribute) throws IOException, FailedResponseException;

    public ApiEntitySingleResponse updateAttribute(P uniqueId, Attribute attribute, String ownerName)
        throws IOException, FailedResponseException;

    public WriteListResponse<Long> deleteAttributes(P uniqueId, List<Long> attributes)
        throws IOException;

    public WriteListResponse<Long> deleteAttributes(P uniqueId, List<Long> attribute, String ownerName)
        throws IOException;

    public ApiEntitySingleResponse deleteAttribute(P uniqueId, Long attribute) throws IOException, FailedResponseException;

    public ApiEntitySingleResponse deleteAttribute(P uniqueId, Long attribute, String ownerName)
        throws IOException, FailedResponseException;

    public WriteListResponse<String> deleteAttributeSecurityLabels(P uniqueId, Long attributeId, List<String> securityLabels) throws IOException, FailedResponseException;

    public WriteListResponse<String> deleteAttributeSecurityLabels(P uniqueId, Long attributeId, List<String> securityLabels, String ownerName)
        throws IOException, FailedResponseException;

    public ApiEntitySingleResponse deleteAttributeSecurityLabel(P uniqueId, Long attributeId, String securityLabel) throws IOException, FailedResponseException;

    public ApiEntitySingleResponse deleteAttributeSecurityLabel(P uniqueId, Long attributeId, String securityLabel, String ownerName)
        throws IOException, FailedResponseException;

}
