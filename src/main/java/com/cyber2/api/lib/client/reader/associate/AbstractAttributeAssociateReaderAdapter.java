/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyber2.api.lib.client.reader.associate;

import com.cyber2.api.lib.client.reader.AbstractBaseReaderAdapter;
import com.cyber2.api.lib.client.reader.associate.AttributeAssociateReadable;
import com.cyber2.api.lib.conn.Connection;
import com.cyber2.api.lib.conn.RequestExecutor;
import com.cyber2.api.lib.exception.FailedResponseException;
import com.cyber2.api.lib.server.entity.Attribute;
import com.cyber2.api.lib.server.entity.SecurityLabel;
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
public abstract class AbstractAttributeAssociateReaderAdapter<P> extends AbstractBaseReaderAdapter implements AttributeAssociateReadable<P> {

    public AbstractAttributeAssociateReaderAdapter(Connection conn, RequestExecutor executor, Class singleType, Class listType) {
        super(conn, executor, singleType, listType);
    }

    @Override
    public List<Attribute> getAttributes(P uniqueId)
        throws IOException, FailedResponseException {
        return getAttributes(uniqueId, null);
    }

    @Override
    public List<Attribute> getAttributes(P uniqueId, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", uniqueId);
        AttributeListResponse item = getList(getUrlBasePrefix() + ".byId.attributes", AttributeListResponse.class, ownerName, map);

        return (List<Attribute>) item.getData().getData();
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
    public List<SecurityLabel> getAttributeSecurityLabels(P uniqueId, Integer attributeId) throws IOException, FailedResponseException {
        return getAttributeSecurityLabels(uniqueId, attributeId, null);
    }

    @Override
    public List<SecurityLabel> getAttributeSecurityLabels(P uniqueId, Integer attributeId, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", uniqueId, "attributeId", attributeId);
        SecurityLabelListResponse data = getList(getUrlBasePrefix() + ".byId.attributes.byId.securityLabels", SecurityLabelListResponse.class, ownerName, map);

        return (List<SecurityLabel>) data.getData().getData();
    }

    @Override
    public SecurityLabel getAttributeSecurityLabel(P uniqueId, Integer attributeId, String securityLabelName) throws IOException, FailedResponseException {
        return getAttributeSecurityLabel(uniqueId, attributeId, securityLabelName, null);
    }

    @Override
    public SecurityLabel getAttributeSecurityLabel(P uniqueId, Integer attributeId, String securityLabelName, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", uniqueId, "attributeId", attributeId, "securityLabelName", securityLabelName);
        SecurityLabelResponse data = getItem(getUrlBasePrefix() + ".byId.attributes.byId.securityLabels.byName", SecurityLabelResponse.class, ownerName, map);

        return (SecurityLabel) data.getData().getData();
    }

}
