/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.client.writer.associate;

import com.threatconnect.sdk.client.UrlTypeable;
import com.threatconnect.sdk.client.response.WriteListResponse;
import com.threatconnect.sdk.client.writer.AbstractBaseWriterAdapter;
import com.threatconnect.sdk.conn.Connection;
import com.threatconnect.sdk.conn.AbstractRequestExecutor;
import com.threatconnect.sdk.exception.FailedResponseException;
import com.threatconnect.sdk.server.entity.Attribute;
import com.threatconnect.sdk.server.response.entity.ApiEntitySingleResponse;
import com.threatconnect.sdk.server.response.entity.AttributeListResponse;
import com.threatconnect.sdk.server.response.entity.AttributeResponse;
import com.threatconnect.sdk.server.response.entity.SecurityLabelListResponse;
import com.threatconnect.sdk.server.response.entity.SecurityLabelResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author dtineo
 */
public abstract class AbstractAttributeAssociateWriterAdapter<T,P> extends AbstractBaseWriterAdapter<T,P> 
    implements AttributeAssociateWritable<P>, UrlTypeable {

    public AbstractAttributeAssociateWriterAdapter(Connection conn, Class singleType) {
        super(conn, singleType );
    }

    @Override
    public WriteListResponse<Attribute> addAttributes(P uniqueId, List<Attribute> attributes) throws IOException {
        return addAttributes(uniqueId, attributes, null);
    }

    @Override
    public WriteListResponse<Attribute> addAttributes(P uniqueId, List<Attribute> attributes, String ownerName) throws IOException {

        Map<String, Object> map = createParamMap("id", uniqueId);
        WriteListResponse data = createList(getUrlBasePrefix() + ".byId.attributes", AttributeListResponse.class, ownerName, map, attributes);

        return data;
    }

    @Override
    public ApiEntitySingleResponse addAttribute(P uniqueId, Attribute attribute) throws IOException, FailedResponseException {
        return addAttribute(uniqueId, attribute, null);
    }

    @Override
    public ApiEntitySingleResponse addAttribute(P uniqueId, Attribute attribute, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", uniqueId);
        AttributeResponse item = createItem(getUrlBasePrefix() + ".byId.attributes", AttributeResponse.class, ownerName, map, attribute);

        return item;
    }

    @Override
    public WriteListResponse<String> addAttributeSecurityLabels(P uniqueId, Long attributeId, List<String> securityLabels ) throws IOException {
        return addAttributeSecurityLabels(uniqueId, attributeId, securityLabels, null);
    }

    @Override
    public WriteListResponse<String> addAttributeSecurityLabels(P uniqueId, Long attributeId, List<String> securityLabels, String ownerName)
        throws IOException {

        Map<String, Object> map = createParamMap("id", uniqueId, "attributeId", attributeId);
        
        WriteListResponse<String> data = createListWithParam(getUrlBasePrefix() + ".byId.attributes.byId.securityLabels.byName", SecurityLabelListResponse.class, ownerName, map, "securityLabel", securityLabels );

        return data;
    }

    @Override
    public ApiEntitySingleResponse addAttributeSecurityLabel(P uniqueId, Long attributeId, String securityLabel) throws IOException, FailedResponseException {
        return addAttributeSecurityLabel(uniqueId, attributeId, securityLabel, null);
    }

    @Override
    public ApiEntitySingleResponse addAttributeSecurityLabel(P uniqueId, Long attributeId, String securityLabel, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", uniqueId, "attributeId", attributeId, "securityLabel", securityLabel);
        SecurityLabelResponse data = createItem(getUrlBasePrefix() + ".byId.attributes.byId.securityLabels.byName", SecurityLabelResponse.class, ownerName, map, null);

        return data;
    }

    @Override
    public WriteListResponse<Attribute> updateAttributes(P uniqueId, List<Attribute> attributes) throws IOException {
        return updateAttributes(uniqueId, attributes, null);
    }


    @Override
    public WriteListResponse<Attribute> updateAttributes(P uniqueId, List<Attribute> attributes, String ownerName) throws IOException {

        Map<String, Object> map = createParamMap("id", uniqueId);
        List<Long> idList = new ArrayList<>();
        for( Attribute a : attributes )     idList.add( a.getId() );
        WriteListResponse data = updateListWithParam(getUrlBasePrefix() + ".byId.attributes.byId", AttributeListResponse.class, ownerName, map, "attributeId", idList, attributes);

        return data;
    }

    @Override
    public ApiEntitySingleResponse updateAttribute(P uniqueId, Attribute attribute) throws IOException, FailedResponseException {
        return updateAttribute(uniqueId, attribute, null);
    }

    @Override
    public ApiEntitySingleResponse updateAttribute(P uniqueId, Attribute attribute, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", uniqueId, "attributeId", attribute.getId() );
        AttributeResponse item = updateItem(getUrlBasePrefix() + ".byId.attributes.byId", AttributeResponse.class, ownerName, map, attribute);

        return item;
    }

    @Override
    public WriteListResponse<Long> deleteAttributes(P uniqueId, List<Long> attributes) throws IOException {
        return deleteAttributes(uniqueId, attributes, null);
    }

    @Override
    public WriteListResponse<Long> deleteAttributes(P uniqueId, List<Long> attribute, String ownerName) throws IOException {
        Map<String, Object> map = createParamMap("id", uniqueId);
        List<Long> idList = new ArrayList<>();
        for(Long it : attribute)    idList.add( it );
        WriteListResponse<Long> data = deleteList(getUrlBasePrefix() + ".byId.attributes.byId"
                , AttributeResponse.class, ownerName, map, "attributeId", idList);

        return data;
    }

    @Override
    public ApiEntitySingleResponse deleteAttribute(P uniqueId, Long attribute) throws IOException, FailedResponseException {
        return deleteAttribute(uniqueId, attribute, null);
    }

    @Override
    public ApiEntitySingleResponse deleteAttribute(P uniqueId, Long attribute, String ownerName) throws IOException, FailedResponseException {
        Map<String, Object> map = createParamMap("id", uniqueId, "attributeId", attribute);
        ApiEntitySingleResponse item = deleteItem(getUrlBasePrefix() + ".byId.attributes.byId", AttributeResponse.class, ownerName, map);

        return item;
    }

    @Override
    public WriteListResponse<String> deleteAttributeSecurityLabels(P uniqueId, Long attributeId, List<String> securityLabels) throws IOException {
        return deleteAttributeSecurityLabels(uniqueId, attributeId, securityLabels, null);
    }

    @Override
    public WriteListResponse<String> deleteAttributeSecurityLabels(P uniqueId, Long attributeId, List<String> securityLabels, String ownerName) throws IOException {
       Map<String, Object> map = createParamMap("id", uniqueId);
       WriteListResponse<String> data = deleteList(getUrlBasePrefix() + ".byId.attributes.byId.securityLabels.byName"
                , SecurityLabelResponse.class, ownerName, map, "securityLabel", securityLabels);

        return data;
    }

    @Override
    public ApiEntitySingleResponse deleteAttributeSecurityLabel(P uniqueId, Long attributeId, String securityLabel) throws IOException, FailedResponseException {
        return deleteAttributeSecurityLabel(uniqueId, attributeId, securityLabel, null);
    }

    @Override
    public ApiEntitySingleResponse deleteAttributeSecurityLabel(P uniqueId, Long attributeId, String securityLabel, String ownerName) throws IOException, FailedResponseException {
        Map<String, Object> map = createParamMap("id", uniqueId, "securityLabel", securityLabel);
        ApiEntitySingleResponse item = deleteItem(getUrlBasePrefix() + ".byId.attributes.byId.securityLabels.byName", SecurityLabelResponse.class, ownerName, map);

        return item;

    }

}
