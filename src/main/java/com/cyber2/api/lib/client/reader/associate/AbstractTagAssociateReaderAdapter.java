/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyber2.api.lib.client.reader.associate;

import com.cyber2.api.lib.client.UrlTypeable;
import com.cyber2.api.lib.client.reader.AbstractBaseReaderAdapter;
import com.cyber2.api.lib.conn.Connection;
import com.cyber2.api.lib.conn.RequestExecutor;
import com.cyber2.api.lib.exception.FailedResponseException;
import com.cyber2.api.lib.server.entity.Tag;
import com.cyber2.api.lib.server.response.entity.TagListResponse;
import com.cyber2.api.lib.server.response.entity.TagResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author dtineo
 */
public abstract class AbstractTagAssociateReaderAdapter<P> extends AbstractBaseReaderAdapter implements TagAssociateReadable<P>, UrlTypeable {

    public AbstractTagAssociateReaderAdapter(Connection conn, RequestExecutor executor, Class singleType, Class listType) {
        super(conn, executor, singleType, listType);
    }

    @Override
    public List<Tag> getAssociatedTags(P uniqueId) throws IOException, FailedResponseException {
        return getAssociatedTags(uniqueId, null);
    }

    @Override
    public List<Tag> getAssociatedTags(P uniqueId, String ownerName) throws IOException, FailedResponseException {
        Map<String,Object> map = createParamMap("id", uniqueId);
        TagListResponse data = getList(getUrlBasePrefix() + ".byId.tags", TagListResponse.class, ownerName, map);

        return (List<Tag>)data.getData().getData();

    }

    @Override
    public Tag getAssociatedTag(P uniqueId, String tagName) throws IOException, FailedResponseException {
        return getAssociatedTag(uniqueId, tagName, null);
    }

    @Override
    public Tag getAssociatedTag(P uniqueId, String tagName, String ownerName) throws IOException, FailedResponseException {
       Map<String,Object> map = createParamMap("id", uniqueId, "tagName", tagName);
        TagResponse data = getItem(getUrlBasePrefix() + ".byId.tags.byName", TagResponse.class, ownerName, map);

        return (Tag)data.getData().getData();

    }


}
