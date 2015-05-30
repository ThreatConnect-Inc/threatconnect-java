/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.client.reader.associate;

import com.threatconnect.sdk.client.reader.AbstractBaseReaderAdapter;
import com.threatconnect.sdk.conn.Connection;
import com.threatconnect.sdk.conn.AbstractRequestExecutor;
import com.threatconnect.sdk.exception.FailedResponseException;
import com.threatconnect.sdk.server.entity.Victim;
import com.threatconnect.sdk.server.response.entity.VictimListResponse;
import com.threatconnect.sdk.server.response.entity.VictimResponse;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author dtineo
 */
public abstract class AbstractVictimAssociateReaderAdapter<P> 
    extends AbstractBaseReaderAdapter implements VictimAssociateReadable<P> {

    public AbstractVictimAssociateReaderAdapter(Connection conn, Class singleType, Class listType) {
        super(conn, singleType, listType);
    }

    @Override
    public List<Victim> getAssociatedVictims(P uniqueId) throws IOException, FailedResponseException {
        return getAssociatedVictims(uniqueId, null);
    }

    @Override
    public List<Victim> getAssociatedVictims(P uniqueId, String ownerName) throws IOException, FailedResponseException {
        Map<String,Object> map = createParamMap("id", uniqueId);
        VictimListResponse data = getList(getUrlBasePrefix() + ".byId.victims", VictimListResponse.class, ownerName, map);

        return (List<Victim>)data.getData().getData();
    }

    @Override
    public Victim getAssociatedVictim(P uniqueId, Integer victimId) throws IOException, FailedResponseException {
        return getAssociatedVictim(uniqueId, victimId, null);
    }

    @Override
    public Victim getAssociatedVictim(P uniqueId, Integer victimId, String ownerName) throws IOException, FailedResponseException {
        Map<String,Object> map = createParamMap("id", uniqueId, "victimId", victimId);
        VictimResponse data = getItem(getUrlBasePrefix() + ".byId.victims.byVictimId", VictimResponse.class, ownerName, map);

        return (Victim)data.getData().getData();
}

    
}
