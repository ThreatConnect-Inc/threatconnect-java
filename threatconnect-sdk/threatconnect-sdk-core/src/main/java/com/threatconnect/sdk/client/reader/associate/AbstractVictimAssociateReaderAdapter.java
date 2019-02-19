/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.client.reader.associate;

import com.threatconnect.sdk.client.UrlTypeable;
import com.threatconnect.sdk.client.reader.AbstractBaseReaderAdapter;
import com.threatconnect.sdk.client.response.IterableResponse;
import com.threatconnect.sdk.conn.Connection;
import com.threatconnect.sdk.exception.FailedResponseException;
import com.threatconnect.sdk.server.entity.Victim;
import com.threatconnect.sdk.server.response.entity.VictimListResponse;
import com.threatconnect.sdk.server.response.entity.VictimResponse;

import java.io.IOException;
import java.util.Map;

/**
 *
 * @author dtineo
 */
public abstract class AbstractVictimAssociateReaderAdapter<P> 
    extends AbstractBaseReaderAdapter implements VictimAssociateReadable<P>, UrlTypeable
{

    public AbstractVictimAssociateReaderAdapter(Connection conn, Class singleType, Class singleItemType, Class listType) {
        super(conn, singleType, singleItemType, listType);
    }

    @Override
    public IterableResponse<Victim> getAssociatedVictims(P uniqueId) throws IOException, FailedResponseException {
        return getAssociatedVictims(uniqueId, null);
    }

    @Override
    public IterableResponse<Victim> getAssociatedVictims(P uniqueId, String ownerName) throws IOException, FailedResponseException {
        Map<String,Object> map = createParamMap("id", uniqueId, "type", getUrlType());
        return getItems(getUrlBasePrefix() + ".byId.victims", VictimListResponse.class, Victim.class, ownerName, map);
    }

    @Override
    public Victim getAssociatedVictim(P uniqueId, Long victimId) throws IOException, FailedResponseException {
        return getAssociatedVictim(uniqueId, victimId, null);
    }

    @Override
    public Victim getAssociatedVictim(P uniqueId, Long victimId, String ownerName) throws IOException, FailedResponseException {
        Map<String,Object> map = createParamMap("id", uniqueId, "victimId", victimId, "type", getUrlType());
        VictimResponse data = getItem(getUrlBasePrefix() + ".byId.victims.byVictimId", VictimResponse.class, ownerName, map);

        return (Victim)data.getData().getData();
}

    
}
