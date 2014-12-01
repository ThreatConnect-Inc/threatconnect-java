/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyber2.api.lib.conn;

import com.cyber2.api.lib.util.StringUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author dtineo
 */
public class RequestExecutor {

    private final Logger logger = LogManager.getLogger(RequestExecutor.class);
    private final Connection conn;

    public static enum HttpMethod {
        GET, PUT, DELETE, POST
    };

    public RequestExecutor(Connection conn) {
        this.conn = conn;
    }

    public String executePut(String path, Object obj) throws IOException {
        return execute(path, HttpMethod.PUT, obj);
    }

    public String executePost(String path, Object obj) throws IOException {
        return execute(path, HttpMethod.POST, obj);
    }

    public String executeGet(String path) throws IOException {
        return execute(path, HttpMethod.GET, null); 
    }

    public String executeDelete(String path) throws IOException {
        return execute(path, HttpMethod.DELETE, null); 
    }

    private static HttpRequestBase getBase(String fullPath, HttpMethod type) {

        switch(type) {
            case GET:
                return new HttpGet(fullPath);
            case PUT:
                return new HttpPut(fullPath);
            case POST:
                return new HttpPost(fullPath);
            case DELETE:
                return new HttpDelete(fullPath);
        }

        return null;
    }

    private void applyEntityAsJSON(HttpRequestBase httpBase, Object obj) throws JsonProcessingException {
        String jsonData = StringUtil.toJSON(obj);
        logger.debug("EntityAsJSON: " + jsonData);
       ((HttpEntityEnclosingRequestBase)httpBase).setEntity(new StringEntity(jsonData, ContentType.APPLICATION_JSON));
    }

    public String execute(String path, HttpMethod type, Object obj) throws IOException {

        if (this.conn.getConfig() == null) {
            throw new IllegalStateException("Can't execute HTTP request when configuration is undefined.");
        }

        String fullPath = this.conn.getConfig().getTcApiUrl() + path;

        logger.debug("Calling " + type + ": " + fullPath);
        HttpRequestBase httpBase = getBase(fullPath, type);
        if ( obj != null )  applyEntityAsJSON( httpBase, obj );

        ConnectionUtil.applyHeaders(this.conn.getConfig(), httpBase, httpBase.getMethod(), path, null);

        logger.debug("Request: " + httpBase.getRequestLine());
        CloseableHttpResponse response = this.conn.getApiClient().execute(httpBase);
        String result = null;

        try {
            logger.debug(response.getStatusLine());
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                result = EntityUtils.toString(entity);
                logger.debug("Result:" + result);
                EntityUtils.consume(entity);
            }
        } finally {
            response.close();
        }

        return result;
    }
}
