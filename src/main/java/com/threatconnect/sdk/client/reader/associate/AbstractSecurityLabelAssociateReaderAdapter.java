/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.client.reader.associate;

import com.threatconnect.sdk.client.reader.AbstractBaseReaderAdapter;
import com.threatconnect.sdk.client.response.IterableResponse;
import com.threatconnect.sdk.conn.Connection;
import com.threatconnect.sdk.exception.FailedResponseException;
import com.threatconnect.sdk.server.entity.SecurityLabel;
import com.threatconnect.sdk.server.response.entity.SecurityLabelListResponse;
import com.threatconnect.sdk.server.response.entity.SecurityLabelResponse;
import java.io.IOException;
import java.util.Map;

/**
 *
 * @author dtineo
 */
public abstract class AbstractSecurityLabelAssociateReaderAdapter<P> extends AbstractBaseReaderAdapter implements SecurityLabelAssociateReadable<P> {

    public AbstractSecurityLabelAssociateReaderAdapter(Connection conn, Class singleType, Class singleItemType, Class listType) {
        super(conn, singleType, singleItemType, listType);
    }

    @Override
    public IterableResponse<SecurityLabel> getAssociatedSecurityLabels(P uniqueId) throws IOException, FailedResponseException {
        return getAssociatedSecurityLabels(uniqueId, null);
    }

    @Override
    public IterableResponse<SecurityLabel> getAssociatedSecurityLabels(P uniqueId, String ownerName) throws IOException, FailedResponseException {
        Map<String,Object> map = createParamMap("id", uniqueId);
        return getItems(getUrlBasePrefix() + ".byId.securityLabels", SecurityLabelListResponse.class, SecurityLabel.class, ownerName, map);

    }

    @Override
    public SecurityLabel getAssociatedSecurityLabel(P uniqueId, String securityLabel) throws IOException, FailedResponseException {
        return getAssociatedSecurityLabel(uniqueId, securityLabel, null);
    }

    @Override
    public SecurityLabel getAssociatedSecurityLabel(P uniqueId, String securityLabel, String ownerName) throws IOException, FailedResponseException {
       Map<String,Object> map = createParamMap("id", uniqueId, "securityLabel", securityLabel);
        SecurityLabelResponse data = getItem(getUrlBasePrefix() + ".byId.securityLabels.byName", SecurityLabelResponse.class, ownerName, map);

        return (SecurityLabel)data.getData().getData();

    }


}
