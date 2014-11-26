/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyber2.api.lib.client.reader;

import com.cyber2.api.lib.conn.Connection;
import com.cyber2.api.lib.conn.RequestExecutor;
import com.cyber2.api.lib.exception.FailedResponseException;
import com.cyber2.api.lib.server.entity.Owner;
import com.cyber2.api.lib.server.response.entity.OwnerListResponse;
import com.cyber2.api.lib.server.response.entity.OwnerResponse;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author dtineo
 */
public class OwnerReaderAdapter extends AbstractReaderAdapter {

    protected OwnerReaderAdapter(Connection conn, RequestExecutor executor) {
        super(conn, executor);
    }

    public String getOwnerAsText() throws IOException {
        return getAsText("v2.owners.list");
    }

    public List<Owner> getOwners() throws IOException, FailedResponseException {
        OwnerListResponse data = getList("v2.owners.list", OwnerListResponse.class);

        return (List<Owner>) data.getData().getData();
    }

    public Owner getOwnerMine() throws IOException, FailedResponseException {
        OwnerResponse data = getItem("v2.owners.mine", OwnerResponse.class);

        return (Owner) data.getData().getData();
    }

}
