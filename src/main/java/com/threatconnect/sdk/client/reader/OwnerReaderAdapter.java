/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.client.reader;

import com.threatconnect.sdk.client.response.IterableResponse;
import com.threatconnect.sdk.conn.Connection;
import com.threatconnect.sdk.exception.FailedResponseException;
import com.threatconnect.sdk.server.entity.Owner;
import com.threatconnect.sdk.server.response.entity.OwnerListResponse;
import com.threatconnect.sdk.server.response.entity.OwnerResponse;

import java.io.IOException;

/**
 *
 * @author dtineo
 */
public class OwnerReaderAdapter extends AbstractReaderAdapter {

    protected OwnerReaderAdapter(Connection conn) {
        super(conn);
    }

    public String getOwnerAsText() throws IOException {
        return getAsText("v2.owners.list");
    }

    public IterableResponse<Owner> getOwners() throws IOException, FailedResponseException
    {
        return getItems("v2.owners.list", OwnerListResponse.class, Owner.class);
    }

    public Owner getOwnerMine() throws IOException, FailedResponseException {
        OwnerResponse data = getItem("v2.owners.mine", OwnerResponse.class);

        return (Owner) data.getData().getData();
    }

}
