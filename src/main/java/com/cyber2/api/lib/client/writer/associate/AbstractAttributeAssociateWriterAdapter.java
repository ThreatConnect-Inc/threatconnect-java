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
import com.cyber2.api.lib.server.response.entity.ApiEntitySingleResponse;
import com.cyber2.api.lib.server.response.entity.AttributeListResponse;
import com.cyber2.api.lib.server.response.entity.AttributeResponse;
import com.cyber2.api.lib.server.response.entity.SecurityLabelListResponse;
import com.cyber2.api.lib.server.response.entity.SecurityLabelResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author dtineo
 */
public abstract class AbstractAttributeAssociateWriterAdapter<T,P> extends AbstractBaseWriterAdapter<T,P> implements AttributeAssociateWritable<P> {

    public AbstractAttributeAssociateWriterAdapter(Connection conn, RequestExecutor executor, Class singleType) {
        super(conn, executor, singleType, /*createReturnsObject=*/false);
    }

    @Override
    public WriteListResponse<Attribute> addAttributes(P uniqueId, List<Attribute> attributes) throws IOException {
        return addAttributes(uniqueId, attributes, null);
    }

    @Override
    public WriteListResponse<Attribute> addAttributes(P uniqueId, List<Attribute> attributes, String ownerName) throws IOException {

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
    public WriteListResponse<String> addAttributeSecurityLabels(P uniqueId, Integer attributeId, List<String> securityLabels ) throws IOException {
        return addAttributeSecurityLabels(uniqueId, attributeId, securityLabels, null);
    }

    @Override
    public WriteListResponse<String> addAttributeSecurityLabels(P uniqueId, Integer attributeId, List<String> securityLabels, String ownerName)
        throws IOException {

        Map<String, Object> map = createParamMap("id", uniqueId, "attributeId", attributeId);
        
        WriteListResponse<String> data = createListWithParam(getUrlBasePrefix() + ".byId.attributes.byId.securityLabels.byName", SecurityLabelListResponse.class, ownerName, map, "securityLabel", securityLabels );

        return data;
    }

    @Override
    public boolean addAttributeSecurityLabel(P uniqueId, Integer attributeId, String securityLabel) throws IOException, FailedResponseException {
        return addAttributeSecurityLabel(uniqueId, attributeId, securityLabel, null);
    }

    @Override
    public boolean addAttributeSecurityLabel(P uniqueId, Integer attributeId, String securityLabel, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", uniqueId, "attributeId", attributeId, "securityLabel", securityLabel);
        SecurityLabelResponse data = createItem(getUrlBasePrefix() + ".byId.attributes.byId.securityLabels.byName", SecurityLabelResponse.class, ownerName, map, null);

        return data.isSuccess();
    }

    @Override
    public WriteListResponse<Attribute> updateAttributes(P uniqueId, List<Attribute> attributes) throws IOException {
        return updateAttributes(uniqueId, attributes, null);
    }


    @Override
    public WriteListResponse<Attribute> updateAttributes(P uniqueId, List<Attribute> attributes, String ownerName) throws IOException {

        Map<String, Object> map = createParamMap("id", uniqueId);
        List<Integer> idList = new ArrayList<>();
        for( Attribute a : attributes )     idList.add( a.getId() );
        WriteListResponse data = updateListWithParam(getUrlBasePrefix() + ".type.byId.attributes.byId", AttributeListResponse.class, ownerName, map, "attributeId", idList, attributes);

        return data;
    }

    @Override
    public boolean updateAttribute(P uniqueId, Attribute attribute) throws IOException, FailedResponseException {
        return updateAttribute(uniqueId, attribute, null);
    }

    @Override
    public boolean updateAttribute(P uniqueId, Attribute attribute, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", uniqueId, "attributeId", attribute.getId() );
        AttributeResponse item = updateItem(getUrlBasePrefix() + "type.byId.attributes.byId", AttributeResponse.class, ownerName, map, attribute);

        return item.isSuccess();
    }

    @Override
    public WriteListResponse<Integer> deleteAttributes(P uniqueId, List<Integer> attributes) throws IOException {
        return deleteAttributes(uniqueId, attributes, null);
    }

    @Override
    public WriteListResponse<Integer> deleteAttributes(P uniqueId, List<Integer> attribute, String ownerName) throws IOException {
        Map<String, Object> map = createParamMap("id", uniqueId);
        List<Integer> idList = new ArrayList<>();
        for(Integer it : attribute)    idList.add( it );
        WriteListResponse<Integer> data = deleteList(getUrlBasePrefix() + ".type.byId.attributes.byId"
                , AttributeResponse.class, ownerName, map, "attributeId", idList);

        return data;
    }

    @Override
    public boolean deleteAttribute(P uniqueId, Integer attribute) throws IOException, FailedResponseException {
        return deleteAttribute(uniqueId, attribute, null);
    }

    @Override
    public boolean deleteAttribute(P uniqueId, Integer attribute, String ownerName) throws IOException, FailedResponseException {
        Map<String, Object> map = createParamMap("id", uniqueId, "attributeId", attribute);
        ApiEntitySingleResponse item = deleteItem(getUrlBasePrefix() + ".type.byId.attributes.byId", AttributeResponse.class, ownerName, map);

        return item.isSuccess();
    }

    @Override
    public WriteListResponse<String> deleteAttributeSecurityLabels(P uniqueId, Integer attributeId, List<String> securityLabels) throws IOException {
        return deleteAttributeSecurityLabels(uniqueId, attributeId, securityLabels, null);
    }

    @Override
    public WriteListResponse<String> deleteAttributeSecurityLabels(P uniqueId, Integer attributeId, List<String> securityLabels, String ownerName) throws IOException {
       Map<String, Object> map = createParamMap("id", uniqueId);
       WriteListResponse<String> data = deleteList(getUrlBasePrefix() + ".type.byId.attributes.byId.securityLabels.byName"
                , SecurityLabelResponse.class, ownerName, map, "securityLabel", securityLabels);

        return data;
    }

    @Override
    public boolean deleteAttributeSecurityLabel(P uniqueId, Integer attributeId, String securityLabel) throws IOException, FailedResponseException {
        return deleteAttributeSecurityLabel(uniqueId, attributeId, securityLabel, null);
    }

    @Override
    public boolean deleteAttributeSecurityLabel(P uniqueId, Integer attributeId, String securityLabel, String ownerName) throws IOException, FailedResponseException {
        Map<String, Object> map = createParamMap("id", uniqueId, "securityLabel", securityLabel);
        ApiEntitySingleResponse item = deleteItem(getUrlBasePrefix() + ".type.byId.attributes.byId.securityLabels.byName", SecurityLabelResponse.class, ownerName, map);

        return item.isSuccess();

    }

}
