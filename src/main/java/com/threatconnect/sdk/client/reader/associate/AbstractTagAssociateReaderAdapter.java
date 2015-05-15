/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.client.reader.associate;

import com.threatconnect.sdk.client.UrlTypeable;
import com.threatconnect.sdk.client.reader.AbstractBaseReaderAdapter;
import com.threatconnect.sdk.conn.Connection;
import com.threatconnect.sdk.conn.RequestExecutor;
import com.threatconnect.sdk.exception.FailedResponseException;
import com.threatconnect.sdk.server.entity.Tag;
import com.threatconnect.sdk.server.response.entity.TagListResponse;
import com.threatconnect.sdk.server.response.entity.TagResponse;
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
