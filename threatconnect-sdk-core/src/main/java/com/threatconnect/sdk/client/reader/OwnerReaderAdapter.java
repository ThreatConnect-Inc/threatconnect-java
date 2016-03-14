/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.client.reader;

import com.threatconnect.sdk.client.AbstractClientAdapter;
import com.threatconnect.sdk.client.response.IterableResponse;
import com.threatconnect.sdk.conn.Connection;
import com.threatconnect.sdk.exception.FailedResponseException;
import com.threatconnect.sdk.server.entity.Owner;
import com.threatconnect.sdk.server.entity.OwnerMetric;
import com.threatconnect.sdk.server.response.entity.ApiEntitySingleResponse;
import com.threatconnect.sdk.server.response.entity.OwnerListResponse;
import com.threatconnect.sdk.server.response.entity.OwnerMetricListResponse;
import com.threatconnect.sdk.server.response.entity.OwnerMetricResponse;
import com.threatconnect.sdk.server.response.entity.OwnerResponse;

import java.io.IOException;
import java.util.Map;

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

    public IterableResponse<OwnerMetric> getOwnerMetrics() throws IOException, FailedResponseException
    {
        return getItems("v2.owners.metrics", OwnerMetricListResponse.class, OwnerMetric.class);
    }

    public IterableResponse<OwnerMetric> getOwnerMetrics(Integer id) throws IOException, FailedResponseException
    {
        Map<String, Object> map = AbstractClientAdapter.createParamMap("id", id);

        return getItems("v2.owners.byId.metrics", OwnerMetricListResponse.class, OwnerMetric.class, null, map);
    }

}
