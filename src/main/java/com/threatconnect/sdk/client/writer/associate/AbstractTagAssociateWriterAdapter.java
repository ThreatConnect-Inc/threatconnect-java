/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.client.writer.associate;

import com.threatconnect.sdk.client.UrlTypeable;
import com.threatconnect.sdk.client.response.WriteListResponse;
import com.threatconnect.sdk.client.writer.AbstractBaseWriterAdapter;
import com.threatconnect.sdk.conn.Connection;
import com.threatconnect.sdk.conn.AbstractRequestExecutor;
import com.threatconnect.sdk.exception.FailedResponseException;
import com.threatconnect.sdk.server.response.entity.ApiEntitySingleResponse;
import com.threatconnect.sdk.server.response.entity.TagListResponse;
import com.threatconnect.sdk.server.response.entity.TagResponse;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author dtineo
 */
public abstract class AbstractTagAssociateWriterAdapter<T,P> extends AbstractBaseWriterAdapter<T,P> implements TagAssociateWritable<P>, UrlTypeable
{

    public AbstractTagAssociateWriterAdapter(Connection conn, Class singleType) {
        super(conn, singleType);
    }

    @Override
    public WriteListResponse<String> associateTags(P uniqueId, List<String> tagNames) throws IOException {
        return associateTags(uniqueId, tagNames, null);
    }

    @Override
    public WriteListResponse<String> associateTags(P uniqueId, List<String> tagNames, String ownerName) throws IOException {
        Map<String,Object> map = createParamMap("id", uniqueId);
        WriteListResponse<String> data = createListWithParam(getUrlBasePrefix() + ".byId.tags.byName", TagListResponse.class, ownerName, map, "tagName", tagNames);

        return data;
    }

    @Override
    public ApiEntitySingleResponse associateTag(P uniqueId, String tagName) throws IOException, FailedResponseException {
        return associateTag(uniqueId, tagName, null);
    }

    @Override
    public ApiEntitySingleResponse associateTag(P uniqueId, String tagName, String ownerName) throws IOException, FailedResponseException {
        Map<String, Object> map = createParamMap("id", uniqueId, "tagName", tagName);
        TagResponse data = createItem( getUrlBasePrefix() + ".byId.tags.byName", TagResponse.class, ownerName, map, null);

        return data;
    }

    @Override
    public WriteListResponse<String> dissociateTags(P uniqueId, List<String> tagNames) throws IOException {
        return dissociateTags(uniqueId, tagNames, null);
    }

    @Override
    public WriteListResponse<String> dissociateTags(P uniqueId, List<String> tagNames, String ownerName) throws IOException {
        Map<String, Object> map = createParamMap("id", uniqueId);
        WriteListResponse<String> data = deleteList(getUrlBasePrefix() + ".byId.tags.byName", TagResponse.class, ownerName, map, "tagName", tagNames);

        return data;
    }

    @Override
    public ApiEntitySingleResponse dissociateTag(P uniqueId, String tagName) throws IOException, FailedResponseException {
        return dissociateTag(uniqueId, tagName, null);
    }

    @Override
    public ApiEntitySingleResponse dissociateTag(P uniqueId, String tagName, String ownerName) throws IOException, FailedResponseException {
        Map<String, Object> map = createParamMap("id", uniqueId, "tagName", tagName);
        TagResponse item = deleteItem(getUrlBasePrefix() + ".byId.tags.byName", TagResponse.class, ownerName, map);

        return item;
    }

}
