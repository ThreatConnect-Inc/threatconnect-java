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
import com.threatconnect.sdk.server.response.entity.ApiEntitySingleResponse;
import com.threatconnect.sdk.server.response.entity.SecurityLabelListResponse;
import com.threatconnect.sdk.server.response.entity.SecurityLabelResponse;
import com.threatconnect.sdk.client.AbstractClientAdapter;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author dtineo
 */
public abstract class AbstractSecurityLabelAssociateWriterAdapter<T,P> extends AbstractBaseWriterAdapter<T,P> implements SecurityLabelAssociateWritable<P>, UrlTypeable
{

    public AbstractSecurityLabelAssociateWriterAdapter(Connection conn, Class singleType) {
        super(conn, singleType );
    }

    @Override
    public WriteListResponse<String> associateSecurityLabels(P uniqueId, List<String> securityLabels) throws IOException {
        return associateSecurityLabels(uniqueId,securityLabels, null);
    }

    @Override
    public WriteListResponse<String> associateSecurityLabels(P uniqueId, List<String> securityLabels, String ownerName) throws IOException {

        Map<String,Object> map = AbstractClientAdapter.createParamMap("id", uniqueId);
        WriteListResponse<String> data = createListWithParam(getUrlBasePrefix() + ".byId.securityLabels.byName", SecurityLabelListResponse.class, ownerName, map, "securityLabel", securityLabels);

        return data;
    }

    @Override
    public ApiEntitySingleResponse associateSecurityLabel(P uniqueId, String securityLabel) throws IOException, FailedResponseException {
        return associateSecurityLabel(uniqueId, securityLabel, null);
    }

    @Override
    public ApiEntitySingleResponse associateSecurityLabel(P uniqueId, String securityLabel, String ownerName) throws IOException, FailedResponseException {
        Map<String, Object> map = AbstractClientAdapter.createParamMap("id", uniqueId, "securityLabel", securityLabel);
        SecurityLabelResponse data = createItem( getUrlBasePrefix() + ".byId.securityLabels.byName", SecurityLabelResponse.class, ownerName, map, null);

        return data;
    }

    @Override
    public WriteListResponse<String> dissociateSecurityLabel(P uniqueId, List<String> securityLabels) throws IOException {
        return dissociateSecurityLabel(uniqueId, securityLabels, null);
    }

    @Override
    public WriteListResponse<String> dissociateSecurityLabel(P uniqueId, List<String> securityLabels, String ownerName) throws IOException {
        Map<String, Object> map = AbstractClientAdapter.createParamMap("id", uniqueId);
        WriteListResponse<String> data = deleteList(getUrlBasePrefix() + ".byId.securityLabels.byName", SecurityLabelResponse.class, ownerName, map, "securityLabel", securityLabels);

        return data;

    }

    @Override
    public ApiEntitySingleResponse dissociateSecurityLabel(P uniqueId, String securityLabel) throws IOException, FailedResponseException {
        return dissociateSecurityLabel(uniqueId, securityLabel, null);
    }

    @Override
    public ApiEntitySingleResponse dissociateSecurityLabel(P uniqueId, String securityLabel, String ownerName) throws IOException, FailedResponseException {
        Map<String, Object> map = AbstractClientAdapter.createParamMap("id", uniqueId, "securityLabel", securityLabel);
        SecurityLabelResponse item = deleteItem(getUrlBasePrefix() + ".byId.tags.byName", SecurityLabelResponse.class, ownerName, map);

        return item;
    }


}
