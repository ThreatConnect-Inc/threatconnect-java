/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyber2.api.lib.client.writer.associate;

import com.cyber2.api.lib.client.UrlTypeable;
import com.cyber2.api.lib.client.response.WriteListResponse;
import com.cyber2.api.lib.client.writer.AbstractBaseWriterAdapter;
import com.cyber2.api.lib.conn.Connection;
import com.cyber2.api.lib.conn.RequestExecutor;
import com.cyber2.api.lib.exception.FailedResponseException;
import com.cyber2.api.lib.server.response.entity.SecurityLabelListResponse;
import com.cyber2.api.lib.server.response.entity.SecurityLabelResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author dtineo
 */
public abstract class AbstractSecurityLabelAssociateWriterAdapter<T,P> extends AbstractBaseWriterAdapter<T,P> implements SecurityLabelAssociateWritable<P>, UrlTypeable {

    public AbstractSecurityLabelAssociateWriterAdapter(Connection conn, RequestExecutor executor, Class singleType) {
        super(conn, executor, singleType, /*createReturnsObject=*/false);
    }

    @Override
    public WriteListResponse<String> associateSecurityLabels(P uniqueId, List<String> securityLabels) throws IOException {
        return associateSecurityLabels(uniqueId,securityLabels, null);
    }

    @Override
    public WriteListResponse<String> associateSecurityLabels(P uniqueId, List<String> securityLabels, String ownerName) throws IOException {

        Map<String,Object> map = createParamMap("id", uniqueId);
        WriteListResponse<String> data = createListWithParam(getUrlBasePrefix() + ".byId.securityLabels.byName", SecurityLabelListResponse.class, ownerName, map, "securityLabel", securityLabels);

        return data;
    }

    @Override
    public boolean associateSecurityLabel(P uniqueId, String securityLabel) throws IOException, FailedResponseException {
        return associateSecurityLabel(uniqueId, securityLabel, null);
    }

    @Override
    public boolean associateSecurityLabel(P uniqueId, String securityLabel, String ownerName) throws IOException, FailedResponseException {
        Map<String, Object> map = createParamMap("id", uniqueId, "securityLabel", securityLabel);
        SecurityLabelResponse data = createItem( getUrlBasePrefix() + ".byId.securityLabels.byName", SecurityLabelResponse.class, ownerName, map, null);

        return data.isSuccess();
    }

    @Override
    public WriteListResponse<String> deleteAssociatedSecurityLabel(P uniqueId, List<String> securityLabels) throws IOException {
        return deleteAssociatedSecurityLabel(uniqueId, securityLabels, null);
    }

    @Override
    public WriteListResponse<String> deleteAssociatedSecurityLabel(P uniqueId, List<String> securityLabels, String ownerName) throws IOException {
        Map<String, Object> map = createParamMap("id", uniqueId);
        WriteListResponse<String> data = deleteList(getUrlBasePrefix() + ".type.byId.securityLabels.byName", SecurityLabelResponse.class, ownerName, map, "securityLabel", securityLabels);

        return data;

    }

    @Override
    public boolean deleteAssociatedSecurityLabel(P uniqueId, String securityLabel) throws IOException, FailedResponseException {
        return deleteAssociatedSecurityLabel(uniqueId, securityLabel, null);
    }

    @Override
    public boolean deleteAssociatedSecurityLabel(P uniqueId, String securityLabel, String ownerName) throws IOException, FailedResponseException {
        Map<String, Object> map = createParamMap("id", uniqueId, "securityLabel", securityLabel);
        SecurityLabelResponse item = deleteItem(getUrlBasePrefix() + ".type.byId.tags.byName", SecurityLabelResponse.class, ownerName, map);

        return item.isSuccess();
    }


}
