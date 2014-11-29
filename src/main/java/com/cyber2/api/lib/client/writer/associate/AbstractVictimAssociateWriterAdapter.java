/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyber2.api.lib.client.writer.associate;

import com.cyber2.api.lib.client.response.WriteListResponse;
import com.cyber2.api.lib.client.writer.AbstractBaseWriterAdapter;
import com.cyber2.api.lib.conn.Connection;
import com.cyber2.api.lib.conn.RequestExecutor;
import com.cyber2.api.lib.exception.FailedResponseException;
import com.cyber2.api.lib.server.response.entity.ApiEntitySingleResponse;
import com.cyber2.api.lib.server.response.entity.VictimListResponse;
import com.cyber2.api.lib.server.response.entity.VictimResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author dtineo
 */
public abstract class AbstractVictimAssociateWriterAdapter<P> extends AbstractBaseWriterAdapter implements VictimAssociateWritable<P> {

    public AbstractVictimAssociateWriterAdapter(Connection conn, RequestExecutor executor, Class singleType, Class listType) {
        super(conn, executor, singleType );
    }

    @Override
    public WriteListResponse<Integer> associateVictims(P uniqueId, List<Integer> victimIds) throws IOException {
        return associateVictims(uniqueId,victimIds, null);
    }

    @Override
    public WriteListResponse<Integer> associateVictims(P uniqueId, List<Integer> victimIds, String ownerName) throws IOException {

        Map<String,Object> map = createParamMap("id", uniqueId);
        WriteListResponse<Integer> data = createListWithParam(getUrlBasePrefix() + ".byId.victims.byVictimId", VictimListResponse.class, ownerName, map, "victimId", victimIds);

        return data;
    }

    @Override
    public ApiEntitySingleResponse associateVictim(P uniqueId, Integer victimId) throws IOException, FailedResponseException {
        return associateVictim(uniqueId, victimId, null);
    }

    @Override
    public ApiEntitySingleResponse associateVictim(P uniqueId, Integer victimId, String ownerName) throws IOException, FailedResponseException {
        Map<String, Object> map = createParamMap("id", uniqueId, "victimId", victimId);
        VictimResponse data = createItem( getUrlBasePrefix() + ".byId.victims.byVictimId", VictimResponse.class, ownerName, map, null);

        return data;
    }

    @Override
    public WriteListResponse<Integer> deleteAssociatedVictims(P uniqueId, List<Integer> victimIds) throws IOException {
        return deleteAssociatedVictims(uniqueId, victimIds, null);
    }

    @Override
    public WriteListResponse<Integer> deleteAssociatedVictims(P uniqueId, List<Integer> victimIds, String ownerName) throws IOException {
      Map<String, Object> map = createParamMap("id", uniqueId);
        WriteListResponse<Integer> data = deleteList(getUrlBasePrefix() + ".type.byId.victims.byVictimId", VictimResponse.class, ownerName, map, "victimId", victimIds);

        return data;

    }

    @Override
    public ApiEntitySingleResponse deleteAssociatedVictim(P uniqueId, Integer victimId) throws IOException, FailedResponseException {
        return deleteAssociatedVictim(uniqueId, victimId, null);
    }


    @Override
   public ApiEntitySingleResponse deleteAssociatedVictim(P uniqueId, Integer victimId, String ownerName) throws IOException, FailedResponseException {
        Map<String, Object> map = createParamMap("id", uniqueId, "victimId", victimId);
        VictimResponse item = deleteItem(getUrlBasePrefix() + ".type.byId.victim..byVictimId", VictimResponse.class, ownerName, map);

        return item;
   }

}
