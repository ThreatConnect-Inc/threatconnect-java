/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.client.reader;

import com.threatconnect.sdk.client.AbstractClientAdapter;
import com.threatconnect.sdk.client.UrlTypeable;
import com.threatconnect.sdk.client.response.IterableResponse;
import com.threatconnect.sdk.conn.AbstractRequestExecutor;
import com.threatconnect.sdk.conn.Connection;
import com.threatconnect.sdk.exception.FailedResponseException;
import com.threatconnect.sdk.server.response.entity.ApiEntitySingleResponse;
import com.threatconnect.sdk.util.ApiFilterParser;
import com.threatconnect.sdk.util.ApiFilterType;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * <p>
 * Base client class used by {@link com.threatconnect.sdk.client.reader} and
 * {@link com.threatconnect.sdk.client.writer}. Conceptually works as an adapter
 * with a {@link com.threatconnect.sdk.conn.Connection} and a
 * {@link com.threatconnect.sdk.conn.AbstractRequestExecutor}.
 * </p>
 *
 * <p>
 * Implementing classes should abstract away low level API calls to the
 * {@link com.threatconnect.sdk.conn.AbstractRequestExecutor} and return high-level
 * {@link com.threatconnect.sdk.server.entity} style classes.
 * </p>
 *
 *
 */
public abstract class AbstractReaderAdapter extends AbstractClientAdapter
{
    private final Logger logger = LoggerFactory.getLogger(getClass().getSimpleName());


    public AbstractReaderAdapter(Connection conn) {
        super(conn);
    }

    protected String getAsText(String propName) throws IOException {
        String url = getConn().getUrlConfig().getUrl(propName);
                
        
        if (this instanceof UrlTypeable) {
            url = url.replace("{type}", ((UrlTypeable) this).getUrlType());
        }

        logger.trace("calling url={}", url);
        String content = executor.execute(AbstractRequestExecutor.HttpMethod.GET, url).getEntity();
        logger.trace("returning content={}", content);

        return content;
    }

    protected InputStream getFile(String propName, String ownerName, Map<String,Object> paramMap) throws IOException
    {
        return getFile(propName, ownerName, paramMap, false, ContentType.APPLICATION_OCTET_STREAM);
    }
    
    protected InputStream getFile(String propName, String ownerName, Map<String,Object> paramMap, ContentType contentType) throws IOException
    {
        return getFile(propName, ownerName, paramMap, false, contentType);
    }

    protected InputStream getFile(String propName, String ownerName, Map<String,Object> paramMap, boolean bypassOwnerCheck, ContentType contentType) throws IOException
    {
        String url = getUrl(propName, ownerName, bypassOwnerCheck);

        if (paramMap != null) {
            for(Entry<String,Object> entry : paramMap.entrySet()) {
                String value = URLEncoder.encode( entry.getValue().toString(), "UTF-8").replace("+", "%20");
                url = url.replace(String.format("{%s}", entry.getKey()), value );
            }
        }

        logger.trace("Calling url={}", url);
        InputStream content = executor.executeDownloadByteStream(url, contentType);
        logger.trace("returning content={}", content);

        return content;
    }

    protected <T extends ApiEntitySingleResponse> T getItem(String propName, Class<T> type)
        throws IOException, FailedResponseException {
        return getItem(propName, type, null, null);
    }

    protected <T extends ApiEntitySingleResponse> T getItem(String propName, Class<T> type, String ownerName, Map<String,Object> paramMap)
        throws IOException, FailedResponseException {
        return getItem(propName, type, ownerName, paramMap, false);
    }

    protected <T extends ApiEntitySingleResponse> T getItem(String propName, Class<T> type, String ownerName, Map<String,Object> paramMap,
                                                            boolean bypassOwnerCheck)
        throws IOException, FailedResponseException {

        String url = getUrl(propName, ownerName, bypassOwnerCheck);

        if (this instanceof UrlTypeable) {
            url = url.replace("{type}", ((UrlTypeable) this).getUrlType());
        }

        if (paramMap != null) {
            for(Entry<String,Object> entry : paramMap.entrySet()) {
                String value = URLEncoder.encode( entry.getValue().toString(), "UTF-8").replace("+", "%20");
                url = url.replace(String.format("{%s}", entry.getKey()), value);
            }
        }


        logger.trace("Calling url={}", url);
        System.out.println("Calling url={}"+url);
        String content = executor.execute(AbstractRequestExecutor.HttpMethod.GET, url).getEntity();
        logger.trace("returning content={}", content);
        System.out.println("returning content={}"+content);

        T result = mapper.readValue(content, type);
        if (!result.isSuccess()) {
            throw new FailedResponseException(result.getMessage());
        }
        return result;
    }

    protected IterableResponse getItems(String propName, Class responseType, Class itemType)
        throws IOException, FailedResponseException {
        return getItems(propName, responseType, itemType, null, null);
    }

    protected IterableResponse getItems(String propName, Class responseType, Class itemType, String ownerName, Map<String, Object> paramMap)
            throws IOException, FailedResponseException {
        return getItems(propName, responseType, itemType, ownerName, paramMap, null, false);
    }


    protected IterableResponse getItems(String propName, Class responseType, Class itemType, String ownerName, Map<String, Object> paramMap, ApiFilterType[] filters, boolean orParams)
        throws IOException, FailedResponseException {

        Integer resultLimit = getConn().getConfig().getResultLimit();
        String url = getUrl(propName, ownerName);

        if (this instanceof UrlTypeable) {
            url = url.replace("{type}", ((UrlTypeable) this).getUrlType());
        }

        logger.trace("Calling url={}", url);
        System.out.println("Calling url={}"+url);
        if (paramMap != null) {
            logger.trace("paramMap={}", paramMap);
            for(Entry<String,Object> entry : paramMap.entrySet()) {
                String value = URLEncoder.encode( entry.getValue().toString(), "UTF-8").replace("+", "%20");
                url = url.replace(String.format("{%s}", entry.getKey()), value);
            }
        }
        String filtersString = null;

        if (filters != null && filters.length > 0)
        {
            filtersString = ApiFilterParser.ParseApiFilters(filters);
        }

        return new IterableResponse(executor, responseType, itemType, url, resultLimit, filtersString, orParams);
    }

}
