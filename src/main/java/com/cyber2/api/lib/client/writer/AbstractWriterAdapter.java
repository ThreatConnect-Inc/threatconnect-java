/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyber2.api.lib.client.writer;

import com.cyber2.api.lib.client.response.WriteListResponse;
import com.cyber2.api.lib.client.AbstractClientAdapter;
import com.cyber2.api.lib.client.UrlTypeable;
import com.cyber2.api.lib.conn.Connection;
import com.cyber2.api.lib.conn.RequestExecutor;
import com.cyber2.api.lib.conn.RequestExecutor.HttpMethod;
import com.cyber2.api.lib.exception.FailedResponseException;
import com.cyber2.api.lib.server.response.entity.ApiEntitySingleResponse;
import java.io.EOFException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * <p>
 * Base client class used by {@link com.cyber2.api.lib.client.reader} and
 * {@link com.cyber2.api.lib.client.writer}. Conceptually works as an adapter
 * with a {@link com.cyber2.api.lib.conn.Connection} and a
 * {@link com.cyber2.api.lib.conn.RequestExecutor}.
 * </p>
 *
 * <p>
 * Implementing classes should abstract away low level API calls to the
 * {@link com.cyber2.api.lib.conn.RequestExecutor} and return high-level
 * {@link com.cyber2.api.lib.entities} style classes.
 * </p>
 *
 *
 */
public abstract class AbstractWriterAdapter extends AbstractClientAdapter {

    private final Logger logger = LogManager.getLogger(AbstractWriterAdapter.class);
    protected boolean createReturnsObject;

    public AbstractWriterAdapter(Connection conn, RequestExecutor executor, boolean createReturnsObject) {
        super(conn, executor);

        this.createReturnsObject = createReturnsObject;
    }

   protected WriteListResponse createListWithParam(String propName, Class type, String paramName, List paramValue)
        throws IOException {
        
        return createListWithParam(propName, type, null, null, paramName, paramValue);
    }

    protected WriteListResponse createListWithParam(String propName, Class type, String ownerName, Map<String, Object> paramMap, String paramName, List paramValue) 
        throws IOException {

        WriteListResponse list = new WriteListResponse();
        for (Object o : paramValue) {
            try {
                Map<String,Object> pMap = new HashMap<>(paramMap);
                pMap.putAll( createParamMap(paramName, o));

                ApiEntitySingleResponse item = createItem(propName, type, ownerName, pMap, null);
                if ( createReturnsObject ) {
                    list.getSuccessList().add(item.getData().getData());
                } else {
                    list.getSuccessList().add(o);
                }
            } catch (FailedResponseException ex) {
                list.getFailureList().add(o);
                list.getFailureMessageList().add(ex.getMessage());
            }
        }

        return list;
    }

   protected WriteListResponse updateListWithParam(String propName, Class type, String paramName, List paramValue)
        throws IOException {
        
        return updateListWithParam(propName, type, null, null, paramName, paramValue);
    }

    protected WriteListResponse updateListWithParam(String propName, Class type, String ownerName, Map<String, Object> paramMap, String paramName, List paramValue) 
        throws IOException {

        WriteListResponse list = new WriteListResponse();
        for (Object o : paramValue) {
            try {
                Map<String,Object> pMap = paramMap == null ? new HashMap<String,Object>() : new HashMap<>(paramMap);
                pMap.putAll( createParamMap(paramName, o));

                ApiEntitySingleResponse item = modifyItem(propName, type, ownerName, pMap, null, HttpMethod.PUT);
                if ( createReturnsObject ) {
                    list.getSuccessList().add(item.getData().getData());
                } else {
                    list.getSuccessList().add( o );
                }
            } catch (FailedResponseException | EOFException ex) {
                list.getFailureList().add(o);
                list.getFailureMessageList().add(ex.getMessage());
            }
        }

        return list;
    }

    protected WriteListResponse updateListWithParam(String propName, Class type, String ownerName, Map<String, Object> paramMap, String paramName, List paramValue, List updateObjList) 
        throws IOException {

        WriteListResponse list = new WriteListResponse();
        int i=0;
        for (Object o : paramValue) {
            try {
                Object updateObj = updateObjList.get(i++);
                Map<String,Object> pMap = new HashMap<>(paramMap);
                pMap.putAll( createParamMap(paramName, o));

                ApiEntitySingleResponse item = modifyItem(propName, type, ownerName, pMap, updateObj, HttpMethod.PUT);
                if ( createReturnsObject ) {
                    list.getSuccessList().add(item.getData().getData());
                } else {
                    list.getSuccessList().add( o );
                }
            } catch (FailedResponseException | EOFException ex) {
                list.getFailureList().add(o);
                list.getFailureMessageList().add(ex.getMessage());
            }
        }

        return list;
    }

    protected WriteListResponse deleteList(String propName, Class type, String ownerName, Map<String, Object> paramMap, String paramName, List paramValue) throws IOException {

        WriteListResponse list = new WriteListResponse();
        for (Object o : paramValue) {
            try {
                Map<String,Object> pMap = paramMap == null ? new HashMap<String,Object>() : new HashMap<>(paramMap);
                pMap.putAll( createParamMap(paramName, o));

                modifyItem(propName, type, ownerName, pMap, null, HttpMethod.DELETE);
                list.getSuccessList().add(o);
            } catch (FailedResponseException | EOFException ex) {
                list.getFailureList().add(o);
                list.getFailureMessageList().add(ex.getMessage());
            }
        }

        return list;
    }

    protected WriteListResponse updateList(String propName, Class type, List saveObjectList)
        throws IOException {
        
        return updateList(propName, type, null, null, saveObjectList);
    }

    protected WriteListResponse updateList(String propName, Class type, String ownerName, Map<String, Object> paramMap, List saveObjectList)
        throws IOException {

        return modifyList(propName, type, ownerName, paramMap, saveObjectList, HttpMethod.PUT);
    }

    protected WriteListResponse createList(String propName, Class type, List saveObjectList)
        throws IOException {
        
        return createList(propName, type, null, null, saveObjectList);
    }

    protected WriteListResponse createList(String propName, Class type, String ownerName, Map<String, Object> paramMap, List saveObjectList)
        throws IOException {

        return modifyList(propName, type, ownerName, paramMap, saveObjectList, HttpMethod.POST);
    }

    protected WriteListResponse modifyList(String propName, Class type, String ownerName, Map<String, Object> paramMap, List saveObjectList, HttpMethod requestType)
        throws IOException {
        WriteListResponse list = new WriteListResponse();
        for (Object o : saveObjectList) {
            try {
                ApiEntitySingleResponse item = modifyItem(propName, type, ownerName, paramMap, o, requestType);
                if ( createReturnsObject ) {
                    list.getSuccessList().add(item.getData().getData());
                } else {
                    list.getSuccessList().add(o);
                }
            } catch (FailedResponseException | EOFException ex) {
                list.getFailureList().add(o);
                list.getFailureMessageList().add(ex.getMessage());
            }
        }

        return list;
    }

    protected <T extends ApiEntitySingleResponse> T updateItem(String propName, Class<T> type, Object saveObject)
        throws IOException, FailedResponseException {
        return updateItem(propName, type, null, null, saveObject);
    }

    protected <T extends ApiEntitySingleResponse> T updateItem(String propName, Class<T> type, String ownerName, Map<String, Object> paramMap, Object saveObject)
        throws IOException, FailedResponseException {

        return modifyItem(propName, type, ownerName, paramMap, saveObject, HttpMethod.PUT);
    }

    protected <T extends ApiEntitySingleResponse> T deleteItem(String propName, Class<T> type, String ownerName, Map<String, Object> paramMap)
        throws IOException, FailedResponseException {

        return modifyItem(propName, type, ownerName, paramMap, null, HttpMethod.DELETE);
    }

    protected <T extends ApiEntitySingleResponse> T createItem(String propName, Class<T> type, Object saveObject)
        throws IOException, FailedResponseException {
        return createItem(propName, type, null, null, saveObject);
    }

    protected <T extends ApiEntitySingleResponse> T createItem(String propName, Class<T> type, String ownerName, Map<String, Object> paramMap, Object saveObject)
        throws IOException, FailedResponseException {

        return modifyItem(propName, type, ownerName, paramMap, saveObject, HttpMethod.POST);
    }

    private <T extends ApiEntitySingleResponse> T modifyItem(String propName, Class<T> type, String ownerName, Map<String, Object> paramMap, Object saveObject, HttpMethod requestType)
        throws IOException, FailedResponseException {

        String url = getConn().getUrlConfig().getUrl(propName);

        if (ownerName != null) {
            url += "?owner=" + ownerName;
        }

        if (this instanceof UrlTypeable) {
            url = url.replace("{type}", ((UrlTypeable) this).getUrlType());
        }

        if (paramMap != null) {
            for (Entry<String, Object> entry : paramMap.entrySet()) {
                url = url.replace(String.format("{%s}", entry.getKey()), entry.getValue().toString());
            }
        }

        T result = null;
        try {
            logger.debug("Calling url=" + url);
            String content = executor.execute(url, requestType, saveObject);
            logger.debug("returning content=" + content);
            result = (T) mapper.readValue(content, type);
        } catch ( EOFException ex ) {
            logger.error( requestType + " Error ", ex);
            throw new FailedResponseException( ex.toString() ); // rethrow using local exception
        }

        if (!result.isSuccess()) {
            throw new FailedResponseException(result.getMessage());
        }
        return result;
    }

}
