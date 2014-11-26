/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyber2.api.lib.client.writer.associate;

import com.cyber2.api.lib.client.response.WriteListResponse;
import com.cyber2.api.lib.client.writer.AbstractBaseWriterAdapter;
import com.cyber2.api.lib.conn.Connection;
import com.cyber2.api.lib.conn.RequestExecutor;
import com.cyber2.api.lib.exception.FailedResponseException;
import com.cyber2.api.lib.server.entity.Attribute;
import com.cyber2.api.lib.server.response.entity.AttributeListResponse;
import com.cyber2.api.lib.server.response.entity.AttributeResponse;
import com.cyber2.api.lib.server.response.entity.SecurityLabelListResponse;
import com.cyber2.api.lib.server.response.entity.SecurityLabelResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author dtineo
 */
public abstract class AbstractAttributeAssociateWriterAdapter<T,P> extends AbstractBaseWriterAdapter<T,P> implements AttributeAssociateWritable<P> {

    public AbstractAttributeAssociateWriterAdapter(Connection conn, RequestExecutor executor, Class singleType, boolean createReturnsObject) {
        super(conn, executor, singleType, createReturnsObject);
    }

    @Override
    public WriteListResponse<Attribute> addAttributes(P uniqueId, List<Attribute> attributes) throws IOException, FailedResponseException {
        return addAttributes(uniqueId, attributes, null);
    }

    @Override
    public WriteListResponse<Attribute> addAttributes(P uniqueId, List<Attribute> attributes, String ownerName) throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", uniqueId);
        WriteListResponse data = createList(getUrlBasePrefix() + ".type.byId.attributes", AttributeListResponse.class, ownerName, map, attributes);

        return data;
    }

    @Override
    public Attribute addAttribute(P uniqueId, Attribute attribute) throws IOException, FailedResponseException {
        return addAttribute(uniqueId, attribute, null);
    }

    @Override
    public Attribute addAttribute(P uniqueId, Attribute attribute, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", uniqueId);
        AttributeResponse item = createItem(getUrlBasePrefix() + ".byId.attributes", AttributeResponse.class, ownerName, map, attribute);

        return (Attribute)item.getData().getData();
    }

    @Override
    public WriteListResponse<String> addAttributeSecurityLabels(P uniqueId, Integer attributeId, List<String> securityLabels ) throws IOException, FailedResponseException {
        return addAttributeSecurityLabels(uniqueId, attributeId, securityLabels, null);
    }

    @Override
    public WriteListResponse<String> addAttributeSecurityLabels(P uniqueId, Integer attributeId, List<String> securityLabels, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", uniqueId, "attributeId", attributeId);
        
        WriteListResponse<String> data = createListWithParam(getUrlBasePrefix() + ".byId.attributes.byId.securityLabels.byName", SecurityLabelListResponse.class, ownerName, map, "securityLabelName", securityLabels );

        return data;
    }

    @Override
    public boolean addAttributeSecurityLabel(P uniqueId, Integer attributeId, String securityLabel) throws IOException, FailedResponseException {
        return addAttributeSecurityLabel(uniqueId, attributeId, securityLabel, null);
    }

    @Override
    public boolean addAttributeSecurityLabel(P uniqueId, Integer attributeId, String securityLabel, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", uniqueId, "attributeId", attributeId, "securityLabelName", securityLabel);
        SecurityLabelResponse data = createItem(getUrlBasePrefix() + ".byId.attributes.byId.securityLabels.byName", SecurityLabelResponse.class, ownerName, map, null);

        return data.isSuccess();
    }

}
