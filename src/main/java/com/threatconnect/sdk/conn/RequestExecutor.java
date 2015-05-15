/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.conn;

import com.threatconnect.sdk.util.StringUtil;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.*;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.FileEntity;
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

    /**
     * Execute an HTTP request and return the raw input stream.  <i>Caller is responsible for closing InputStream.</i>
     *
     * @param path url to issue request to
     * @return raw input stream from response
     * @throws IOException
     */
    public InputStream executeDownloadByteStream(String path) throws IOException {
        if (this.conn.getConfig() == null) {
            throw new IllegalStateException("Can't execute HTTP request when configuration is undefined.");
        }

        InputStream stream = null;

        String fullPath = this.conn.getConfig().getTcApiUrl() + path;

        logger.debug("Calling GET: " + fullPath);
        HttpRequestBase httpBase = getBase(fullPath, HttpMethod.GET);

        ConnectionUtil.applyHeaders(this.conn.getConfig(), httpBase, httpBase.getMethod(), path, null);
        httpBase.addHeader("Accept", "application/octet-stream");
        logger.debug("Request: " + httpBase.getRequestLine());
        CloseableHttpResponse response = this.conn.getApiClient().execute(httpBase);


        logger.debug(response.getStatusLine());
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            stream = entity.getContent();
            logger.debug(String.format("Result stream size: %d, encoding: %s",
                                                entity.getContentLength(), entity.getContentEncoding()));
        }
        return stream;
    }


    public InputStream executeUploadByteStream(String path, File file) throws IOException
    {
        if (this.conn.getConfig() == null) {
            throw new IllegalStateException("Can't execute HTTP request when configuration is undefined.");
        }

        InputStream stream = null;

        String fullPath = this.conn.getConfig().getTcApiUrl() + path;

        logger.debug("Calling POST: " + fullPath);
        HttpPost httpBase = new HttpPost(fullPath);
        httpBase.setEntity(new FileEntity(file));
        ConnectionUtil.applyHeaders(this.conn.getConfig(), httpBase, httpBase.getMethod(), path, null);
        httpBase.addHeader("Content-Type", "application/octet-stream");

        logger.debug("Request: " + httpBase.getRequestLine());


        CloseableHttpResponse response = this.conn.getApiClient().execute(httpBase);


        logger.debug(response.getStatusLine());
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            stream = entity.getContent();
            logger.debug(String.format("Result stream size: %d, encoding: %s",
                    entity.getContentLength(), entity.getContentEncoding()));
        }
        return stream;
    }
}
