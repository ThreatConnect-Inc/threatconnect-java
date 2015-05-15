/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.client.reader;

import com.threatconnect.sdk.conn.Connection;
import com.threatconnect.sdk.conn.RequestExecutor;
import com.threatconnect.sdk.exception.FailedResponseException;
import com.threatconnect.sdk.server.entity.Owner;
import com.threatconnect.sdk.server.response.entity.OwnerListResponse;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author dtineo
 */
public abstract class AbstractOwnerReaderAdapter<T> extends AbstractBaseReaderAdapter implements OwnerReadable<String> {

    public AbstractOwnerReaderAdapter(Connection conn, RequestExecutor executor, Class singleType, Class listType) {
        super(conn, executor, singleType, listType);
    }

    @Override
    public List<Owner> getOwners(String uniqueId) throws IOException, FailedResponseException {
        Map<String, Object> map = createParamMap("id", uniqueId);
        OwnerListResponse data = getList(getUrlBasePrefix() + ".byId.owners", OwnerListResponse.class, null, map);

        return (List<Owner>) data.getData().getData();
    }

}
