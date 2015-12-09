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
import com.threatconnect.sdk.server.entity.Owner;
import com.threatconnect.sdk.server.response.entity.OwnerListResponse;
import java.io.IOException;
import java.util.Map;

/**
 *
 * @author dtineo
 */
public abstract class AbstractOwnerAssociateReaderAdapter<P> extends AbstractBaseReaderAdapter implements OwnerAssociateReadable<P> {

    public AbstractOwnerAssociateReaderAdapter(Connection conn, Class singleType, Class singleItemType, Class listType) {
        super(conn, singleType, singleItemType, listType);
    }

    @Override
    public IterableResponse<Owner> getAssociatedOwners(P uniqueId) throws IOException, FailedResponseException {
        return getAssociatedOwners(uniqueId, null);
    }

    @Override
    public IterableResponse<Owner> getAssociatedOwners(P uniqueId, String ownerName) throws IOException, FailedResponseException {
       Map<String,Object> map = createParamMap("id", uniqueId);
        return getItems(getUrlBasePrefix() + ".byId.owners", OwnerListResponse.class, Owner.class, ownerName, map);

    }
    
}
