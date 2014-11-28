/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyber2.api.lib.client.writer.associate;

import com.cyber2.api.lib.client.UrlTypeable;
import com.cyber2.api.lib.client.response.WriteListResponse;
import com.cyber2.api.lib.client.writer.AbstractBaseWriterAdapter;
import com.cyber2.api.lib.conn.Connection;
import com.cyber2.api.lib.conn.RequestExecutor;
import com.cyber2.api.lib.exception.FailedResponseException;
import com.cyber2.api.lib.server.response.entity.TagListResponse;
import com.cyber2.api.lib.server.response.entity.TagResponse;
import com.cyber2.api.lib.server.response.entity.VictimNetworkAccountResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author dtineo
 */
public abstract class AbstractTagAssociateWriterAdapter<T,P> extends AbstractBaseWriterAdapter<T,P> implements TagAssociateWritable<P>, UrlTypeable {

    public AbstractTagAssociateWriterAdapter(Connection conn, RequestExecutor executor, Class singleType) {
        super(conn, executor, singleType, /*createReturnsObject=*/false);
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
    public boolean associateTag(P uniqueId, String tagName) throws IOException, FailedResponseException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean associateTag(P uniqueId, String tagName, String ownerName) throws IOException, FailedResponseException {
        Map<String, Object> map = createParamMap("id", uniqueId, "tagName", tagName);
        TagResponse data = createItem( getUrlBasePrefix() + ".byId.tags.byName", TagResponse.class, ownerName, map, null);

        return data.isSuccess();
    }

    @Override
    public WriteListResponse<String> deleteAssociatedTags(P uniqueId, List<String> tagNames) throws IOException {
        return deleteAssociatedTags(uniqueId, tagNames, null);
    }

    @Override
    public WriteListResponse<String> deleteAssociatedTags(P uniqueId, List<String> tagNames, String ownerName) throws IOException {
        Map<String, Object> map = createParamMap("id", uniqueId);
        WriteListResponse<String> data = deleteList(getUrlBasePrefix() + ".type.byId.tags.byName", TagResponse.class, ownerName, map, "tagName", tagNames);

        return data;
    }

    @Override
    public boolean deleteAssociatedTag(P uniqueId, String tagName) throws IOException, FailedResponseException {
        return deleteAssociatedTag(uniqueId, tagName, null);
    }

    @Override
    public boolean deleteAssociatedTag(P uniqueId, String tagName, String ownerName) throws IOException, FailedResponseException {
        Map<String, Object> map = createParamMap("id", uniqueId, "tagName", tagName);
        TagResponse item = deleteItem(getUrlBasePrefix() + ".type.byId.tags.byName", TagResponse.class, ownerName, map);

        return item.isSuccess();
    }

}
