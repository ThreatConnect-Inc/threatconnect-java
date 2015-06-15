/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.client.reader.associate;

import com.threatconnect.sdk.client.UrlTypeable;
import com.threatconnect.sdk.client.reader.AbstractBaseReaderAdapter;
import com.threatconnect.sdk.client.reader.IterableResponse;
import com.threatconnect.sdk.conn.Connection;
import com.threatconnect.sdk.exception.FailedResponseException;
import com.threatconnect.sdk.server.entity.Attribute;
import com.threatconnect.sdk.server.entity.SecurityLabel;
import com.threatconnect.sdk.server.response.entity.AttributeListResponse;
import com.threatconnect.sdk.server.response.entity.AttributeResponse;
import com.threatconnect.sdk.server.response.entity.SecurityLabelListResponse;
import com.threatconnect.sdk.server.response.entity.SecurityLabelResponse;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author dtineo
 */
public abstract class AbstractAttributeAssociateReaderAdapter<P> extends AbstractBaseReaderAdapter implements AttributeAssociateReadable<P>, UrlTypeable
{

    public AbstractAttributeAssociateReaderAdapter(Connection conn, Class singleItemType, Class singleType, Class listType) {
        super(conn, singleType, singleItemType, listType);
    }

    @Override
    public IterableResponse<Attribute> getAttributes(P uniqueId)
        throws IOException, FailedResponseException {
        return getAttributes(uniqueId, null);
    }

    @Override
    public IterableResponse<Attribute> getAttributes(P uniqueId, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", uniqueId);
        return getItems(getUrlBasePrefix() + ".byId.attributes", AttributeListResponse.class, Attribute.class, ownerName, map);
    }

    @Override
    public Attribute getAttribute(P uniqueId, Integer attributeId) throws IOException, FailedResponseException {
        return getAttribute(uniqueId, attributeId, null);
    }

    @Override
    public Attribute getAttribute(P uniqueId, Integer attributeId, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", uniqueId, "attributeId", attributeId);
        AttributeResponse item = getItem(getUrlBasePrefix() + ".byId.attributes.byId", AttributeResponse.class, ownerName, map);

        return (Attribute) item.getData().getData();
    }

    @Override
    public IterableResponse<SecurityLabel> getAttributeSecurityLabels(P uniqueId, Integer attributeId) throws IOException, FailedResponseException {
        return getAttributeSecurityLabels(uniqueId, attributeId, null);
    }

    @Override
    public IterableResponse<SecurityLabel> getAttributeSecurityLabels(P uniqueId, Integer attributeId, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", uniqueId, "attributeId", attributeId);
        return getItems(getUrlBasePrefix() + ".byId.attributes.byId.securityLabels", SecurityLabelListResponse.class, SecurityLabel.class, ownerName, map);
    }

    @Override
    public SecurityLabel getAttributeSecurityLabel(P uniqueId, Integer attributeId, String securityLabel) throws IOException, FailedResponseException {
        return getAttributeSecurityLabel(uniqueId, attributeId, securityLabel, null);
    }

    @Override
    public SecurityLabel getAttributeSecurityLabel(P uniqueId, Integer attributeId, String securityLabel, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", uniqueId, "attributeId", attributeId, "securityLabel", securityLabel);
        SecurityLabelResponse data = getItem(getUrlBasePrefix() + ".byId.attributes.byId.securityLabels.byName", SecurityLabelResponse.class, ownerName, map);

        return (SecurityLabel) data.getData().getData();
    }

}
