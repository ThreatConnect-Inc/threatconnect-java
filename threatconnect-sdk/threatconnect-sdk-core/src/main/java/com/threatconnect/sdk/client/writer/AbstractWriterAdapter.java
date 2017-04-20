/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.client.writer;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.threatconnect.sdk.client.AbstractClientAdapter;
import com.threatconnect.sdk.client.UrlTypeable;
import com.threatconnect.sdk.client.response.WriteListResponse;
import com.threatconnect.sdk.conn.AbstractRequestExecutor.HttpMethod;
import com.threatconnect.sdk.conn.Connection;
import com.threatconnect.sdk.exception.FailedResponseException;
import com.threatconnect.sdk.server.entity.CustomIndicator;
import com.threatconnect.sdk.server.response.entity.ApiEntitySingleResponse;
import com.threatconnect.sdk.util.UploadMethodType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
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
public abstract class AbstractWriterAdapter extends AbstractClientAdapter {

    protected final Logger logger = LoggerFactory.getLogger(getClass().getSimpleName());

    public AbstractWriterAdapter(Connection conn) {
        super(conn);

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
                list.getSuccessList().add( item );
            } catch (FailedResponseException ex) {
                list.getFailureList().add( wrapFailedResponse(ex, o) );
            }
        }

        return list;
    }

   protected WriteListResponse updateListWithParam(String propName, Class type, String paramName, List paramValue)
        throws IOException {
        
        return updateListWithParam(propName, type, null, null, paramName, paramValue);
    }

    protected WriteListResponse<ApiEntitySingleResponse> updateListWithParam(String propName, Class type, String ownerName, Map<String, Object> paramMap, String paramName, List paramValue) 
        throws IOException {

        WriteListResponse list = new WriteListResponse();
        for (Object o : paramValue) {
            try {
                Map<String,Object> pMap = paramMap == null ? new HashMap<String,Object>() : new HashMap<>(paramMap);
                pMap.putAll( createParamMap(paramName, o));

                ApiEntitySingleResponse item = modifyItem(propName, type, ownerName, pMap, null, HttpMethod.PUT);
                list.getSuccessList().add(item);
            } catch (FailedResponseException | EOFException ex) {
                list.getFailureList().add( wrapFailedResponse(ex, o) );
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
                list.getSuccessList().add( item );
            } catch (FailedResponseException | EOFException ex) {
                list.getFailureList().add( wrapFailedResponse(ex, o) );
            }
        }

        return list;
    }

    private static ApiEntitySingleResponse wrapFailedResponse(final Exception ex, final Object attemptedItem) {
        return new ApiEntitySingleResponse () {
             @Override
             public String getMessage() { 
                 return ex.getMessage(); 
             }

             @Override
             public boolean isSuccess() { 
                 return false; 
             }

             @Override
             public Object getItem() {
                 return attemptedItem;
             }

         };
    }

    private static ApiEntitySingleResponse wrapSuccessResponse(final Object attemptedItem) {
        return new ApiEntitySingleResponse () {
             @Override
             public String getMessage() { 
                 return null;
             }

             @Override
             public boolean isSuccess() { 
                 return true; 
             }

             @Override
             public Object getItem() {
                 return attemptedItem;
             }

         };
    }


    protected WriteListResponse deleteList(String propName, Class type, String ownerName, Map<String, Object> paramMap, String paramName, List paramValue) throws IOException {

        WriteListResponse list = new WriteListResponse();
        for (Object o : paramValue) {
            try {
                Map<String,Object> pMap = paramMap == null ? new HashMap<String,Object>() : new HashMap<>(paramMap);
                pMap.putAll( createParamMap(paramName, o));

                modifyItem(propName, type, ownerName, pMap, null, HttpMethod.DELETE);
                list.getSuccessList().add( wrapSuccessResponse(o) );  // DELETE's don't return body, wrap original 
            } catch (FailedResponseException | EOFException ex) {
                list.getFailureList().add( wrapFailedResponse(ex, o) );
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
                list.getSuccessList().add( item );
            } catch (FailedResponseException | EOFException ex) {
                list.getFailureList().add( wrapFailedResponse(ex, o));
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
    
    protected <T extends ApiEntitySingleResponse> T createItemWithGet(String propName, Class<T> type, String ownerName, Map<String, Object> paramMap, Object saveObject)
            throws IOException, FailedResponseException {
            return modifyItem(propName, type, ownerName, paramMap, saveObject, HttpMethod.GET);
        }

    protected <T extends ApiEntitySingleResponse> T uploadFile(String propName, Class<T> type, InputStream inputStream, Map<String, Object> paramMap, UploadMethodType uploadMethodType) throws FailedResponseException, UnsupportedEncodingException
    {
        return uploadFile(propName, type, null, inputStream, paramMap, uploadMethodType);
    }

    protected <T extends ApiEntitySingleResponse> T uploadFile(String propName, Class<T> type, String ownerName, InputStream inputStream, Map<String, Object> paramMap, UploadMethodType uploadMethodType) throws FailedResponseException, UnsupportedEncodingException
    {
        logger.trace("Getting URL: {}", propName);
        String url = getUrl(propName, ownerName);
        logger.trace("\tURL: {}", url);

        T result;
        try
        {
            if (paramMap != null) {
                for (Entry<String, Object> entry : paramMap.entrySet()) {
                    String value = URLEncoder.encode( entry.getValue().toString(), "UTF-8").replace("+", "%20");
                    url = url.replace(String.format("{%s}", entry.getKey()), value );
                }
            }
            logger.trace("Calling url={}", url);
            String content = executor.executeUploadByteStream(url, inputStream, uploadMethodType);
            logger.trace("returning content={}", content);
            result = mapper.readValue(content, type);
        } catch (IOException e)
        {
            throw new FailedResponseException("Failed to upload file: " + e.getMessage());
        }

        return result;
    }

    protected <T extends ApiEntitySingleResponse> T modifyItem(String propName, Class<T> type, String ownerName, Map<String, Object> paramMap, Object saveObject, HttpMethod requestType)
        throws IOException, FailedResponseException {
    	logger.trace("Getting URL: {}", propName);
        String url = getUrl(propName, ownerName);

        //overwrite if customIndicator
        if(saveObject instanceof CustomIndicator) {
        	CustomIndicator cIn = (CustomIndicator)saveObject;
        	if(cIn.getIndicatorType() != null)
        	url = url.replace("{type}", cIn.getIndicatorType());
        }
        else if (this instanceof UrlTypeable) {
            url = url.replace("{type}", ((UrlTypeable) this).getUrlType());
        }
        

        if (paramMap != null) {
            for (Entry<String, Object> entry : paramMap.entrySet()) {
                if (entry.getValue() != null)
                {
                    String value = URLEncoder.encode(entry.getValue().toString(), "UTF-8").replace("+", "%20");
                    url = url.replace(String.format("{%s}", entry.getKey()), value);
                }
            }
        }

        T result;
		try
		{
			logger.trace("Calling url={}", url);
			String content = executor.execute(url, requestType, saveObject).getEntityAsString();
			logger.trace("returning content={}", content);
			result = mapper.readValue(content, type);
		}
		catch (JsonMappingException | EOFException ex)
		{
			logger.error(requestType + " Error ", ex);
			throw new FailedResponseException(ex);
		}

        return result;
    }

}
