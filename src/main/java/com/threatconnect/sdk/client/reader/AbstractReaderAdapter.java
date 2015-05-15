/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.client.reader;

import com.threatconnect.sdk.client.AbstractClientAdapter;
import com.threatconnect.sdk.client.UrlTypeable;
import com.threatconnect.sdk.conn.Connection;
import com.threatconnect.sdk.conn.RequestExecutor;
import com.threatconnect.sdk.exception.FailedResponseException;
import com.threatconnect.sdk.server.response.entity.ApiEntityListResponse;
import com.threatconnect.sdk.server.response.entity.ApiEntitySingleResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * <p>
 * Base client class used by {@link com.cyber2.api.lib.client.reader} and
 * {@link com.cyber2.api.lib.client.writer}. Conceptually works as an adapter
 * with a {@link com.threatconnect.sdk.conn.Connection} and a
 * {@link com.threatconnect.sdk.conn.RequestExecutor}.
 * </p>
 *
 * <p>
 * Implementing classes should abstract away low level API calls to the
 * {@link com.threatconnect.sdk.conn.RequestExecutor} and return high-level
 * {@link com.cyber2.api.lib.entities} style classes.
 * </p>
 *
 *
 */
public abstract class AbstractReaderAdapter extends AbstractClientAdapter
{

    private final Logger logger = LogManager.getLogger(AbstractReaderAdapter.class);

    public AbstractReaderAdapter(Connection conn, RequestExecutor executor) {
        super(conn, executor);
    }

    protected String getAsText(String propName) throws IOException {
        String url = getConn().getUrlConfig().getUrl(propName);
        
        if (this instanceof UrlTypeable) {
            url = url.replace("{type}", ((UrlTypeable) this).getUrlType());
        }

        logger.debug("calling url=" + url);
        String content = executor.executeGet(url);
        logger.debug("returning content=" + content);

        return content;
    }

    protected InputStream getFile(String propName, String ownerName, Map<String,Object> paramMap) throws IOException
    {
        String url = getConn().getUrlConfig().getUrl(propName);

        if (ownerName != null) {
            url += "?owner=" + ownerName;
        }

        if (paramMap != null) {
            for(Entry<String,Object> entry : paramMap.entrySet()) {
                url = url.replace(String.format("{%s}", entry.getKey()), entry.getValue().toString() );
            }
        }

        logger.debug("Calling url=" + url);
        InputStream content = executor.executeDownloadByteStream(url);
        logger.debug("returning content=" + content);

        return content;
    }

    protected <T extends ApiEntitySingleResponse> T getItem(String propName, Class<T> type)
        throws IOException, FailedResponseException {
        return getItem(propName, type, null, null);
    }

    protected <T extends ApiEntitySingleResponse> T getItem(String propName, Class<T> type, String ownerName, Map<String,Object> paramMap)
        throws IOException, FailedResponseException {

        String url = getConn().getUrlConfig().getUrl(propName);

        if (ownerName != null) {
            url += "?owner=" + ownerName;
        }

        if (this instanceof UrlTypeable) {
            url = url.replace("{type}", ((UrlTypeable) this).getUrlType());
        }

        if (paramMap != null) {
            for(Entry<String,Object> entry : paramMap.entrySet()) {
                url = url.replace(String.format("{%s}", entry.getKey()), entry.getValue().toString() );
            }
        }


        logger.debug("Calling url=" + url);
        String content = executor.executeGet(url);
        logger.debug("returning content=" + content);

        T result = (T) mapper.readValue(content, type);
        if (!result.isSuccess()) {
            throw new FailedResponseException(result.getMessage());
        }
        return result;
    }

    protected <T extends ApiEntityListResponse> T getList(String propName, Class<T> type)
        throws IOException, FailedResponseException {
        return getList(propName, type, null, null);
    }

    protected <T extends ApiEntityListResponse> T getList(String propName, Class<T> type, String ownerName, Map<String,Object> paramMap)
        throws IOException, FailedResponseException {

        String url = getConn().getUrlConfig().getUrl(propName);

        if (ownerName != null) {
            url += "?owner=" + ownerName;
        }

        if (this instanceof UrlTypeable) {
            url = url.replace("{type}", ((UrlTypeable) this).getUrlType());
        }

        if (paramMap != null) {
            for(Entry<String,Object> entry : paramMap.entrySet()) {
                url = url.replace(String.format("{%s}", entry.getKey()), entry.getValue().toString() );
            }
        }

        logger.debug("Calling url=" + url);
        String content = executor.executeGet(url);
        logger.debug("returning content=" + content);

        T result = (T) mapper.readValue(content, type);
        if (!result.isSuccess()) {
            throw new FailedResponseException(result.getMessage());
        }
        return result;
    }

}
