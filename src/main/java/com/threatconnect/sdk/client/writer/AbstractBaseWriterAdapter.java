/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.client.writer;

import com.threatconnect.sdk.client.Identifiable;
import com.threatconnect.sdk.client.response.WriteListResponse;
import com.threatconnect.sdk.conn.Connection;
import com.threatconnect.sdk.conn.AbstractRequestExecutor;
import com.threatconnect.sdk.exception.FailedResponseException;
import com.threatconnect.sdk.server.response.entity.ApiEntitySingleResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author dtineo
 */
public abstract class AbstractBaseWriterAdapter<T,P> extends AbstractWriterAdapter implements Identifiable<T,P> {
    protected final Class<? extends ApiEntitySingleResponse> singleType;

    protected AbstractBaseWriterAdapter(Connection conn
                , Class<? extends ApiEntitySingleResponse> singleType) { 
        super(conn);

        this.singleType = singleType;
    }

    protected abstract String getUrlBasePrefix();

   public WriteListResponse<T> create(List<T> itemList) throws IOException {
        return createList(getUrlBasePrefix(), singleType, itemList);
    }

    public WriteListResponse<T> update(List<T> itemList) throws IOException {
        return update(itemList, null);
    }

    public WriteListResponse<T> update(List<T> itemList, String ownerName) throws IOException {
        List<P> idList = new ArrayList<>();
        for(T it : itemList)    idList.add( getId(it) );
        WriteListResponse<T> data = updateListWithParam(getUrlBasePrefix() + ".byId", singleType, ownerName, null, "id", idList, itemList);

        return data;

    }

    public WriteListResponse<P> delete(List<P> itemIds) throws IOException {
        return delete(itemIds, null);
    }

    public WriteListResponse<P> delete(List<P> itemIds, String ownerName) throws IOException {
        WriteListResponse<P> data = deleteList(getUrlBasePrefix() + ".byId", singleType, ownerName, null, "id", itemIds);

        return data;
    }

    /**
     *
     * API call to create single T.
     * <p>
     * Per the ThreatConnect User Documentation:
     * </p>
     * <div style="margin-left: 2em; border-left: solid 2px gray; padding-left: 2em;"><i>
     * By default, all requests that do not include an Owner are assumed to be
     * for the API User’s Organization.
     * </i></div>
     *
     * @param item The type T object to create 
     * @return T object when it exists in ThreatConnect
     * @throws IOException When the HTTPS API request fails due to IO issues
     * @throws com.threatconnect.sdk.exception.FailedResponseException When the API
     * responds with an error and the request is unsuccessful
     */
    public ApiEntitySingleResponse create(T item) throws IOException, FailedResponseException {
        return create(item, null);
    }

    public ApiEntitySingleResponse create(T item, String ownerName) throws IOException, FailedResponseException {

        ApiEntitySingleResponse data = createItem(getUrlBasePrefix(), singleType, ownerName, null, item);

        return data;
    }

    public ApiEntitySingleResponse update(T item) throws IOException, FailedResponseException {
        return update(item, null);
    }

    public ApiEntitySingleResponse update(T item, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", getId(item) );
        ApiEntitySingleResponse data = updateItem(getUrlBasePrefix() + ".byId", singleType, ownerName, map, item);

        return data;
    }

    // delete on non-existent itemId returns 404
   public ApiEntitySingleResponse delete(P itemId) throws IOException, FailedResponseException {
        return delete(itemId, null);
    }

    public ApiEntitySingleResponse delete(P itemId, String ownerName) throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", itemId);
        ApiEntitySingleResponse data = deleteItem(getUrlBasePrefix() + ".byId", singleType, ownerName, map);

        return data;
    }


}
