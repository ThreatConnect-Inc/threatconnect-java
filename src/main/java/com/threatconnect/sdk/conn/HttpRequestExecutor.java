/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.conn;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.threatconnect.sdk.util.StringUtil;
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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;

/**
 *
 * @author dtineo
 */
public class HttpRequestExecutor extends AbstractRequestExecutor
{

    public HttpRequestExecutor(Connection conn)
    {
        super(conn);
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
        logger.log(Level.INFO, "entity : " + jsonData);
       ((HttpEntityEnclosingRequestBase)httpBase).setEntity(new StringEntity(jsonData, ContentType.APPLICATION_JSON));
    }

    @Override
    public String execute(String path, HttpMethod type, Object obj) throws IOException {

        // TODO: pull elements from connection/config
        if ( type == HttpMethod.GET )
        {
            path += "&resultStart=0&resultLimit=500";
        }
        path += "&createActivityLog=false";

        String fullPath = this.conn.getConfig().getTcApiUrl() + path.replace("/api/","/");



        logger.log(Level.INFO, "Calling " + type + ": " + fullPath);
        HttpRequestBase httpBase = getBase(fullPath, type);
        if ( obj != null )  applyEntityAsJSON( httpBase, obj );

        ConnectionUtil.applyHeaders(this.conn.getConfig(), httpBase, type.toString(), path);
        logger.log(Level.INFO, "Request: " + httpBase.getRequestLine());
        CloseableHttpResponse response = this.conn.getApiClient().execute(httpBase);
        String result = null;

        try {
            logger.log(Level.INFO, response.getStatusLine().toString() );
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                result = EntityUtils.toString(entity);
                logger.log(Level.INFO, "Result:" + result);
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
    @Override
    public InputStream executeDownloadByteStream(String path) throws IOException {
        if (this.conn.getConfig() == null) {
            throw new IllegalStateException("Can't execute HTTP request when configuration is undefined.");
        }

        InputStream stream = null;

        String fullPath = this.conn.getConfig().getTcApiUrl() + path.replace("/api/","/");

        logger.log(Level.INFO, "Calling GET: " + fullPath);
        HttpRequestBase httpBase = getBase(fullPath, HttpMethod.GET);

        ConnectionUtil.applyHeaders(this.conn.getConfig(), httpBase, httpBase.getMethod(), path, ContentType.APPLICATION_OCTET_STREAM.toString());
        logger.log(Level.INFO, "Request: " + httpBase.getRequestLine());
        CloseableHttpResponse response = this.conn.getApiClient().execute(httpBase);


        logger.log(Level.INFO, response.getStatusLine().toString());
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            stream = entity.getContent();
            logger.log(Level.INFO, String.format("Result stream size: %d, encoding: %s",
                                                entity.getContentLength(), entity.getContentEncoding()));
        }
        return stream;
    }


    @Override
    public InputStream executeUploadByteStream(String path, File file) throws IOException
    {
        if (this.conn.getConfig() == null) {
            throw new IllegalStateException("Can't execute HTTP request when configuration is undefined.");
        }

        InputStream stream = null;

        String fullPath = this.conn.getConfig().getTcApiUrl() + path.replace("/api/","/");

        logger.log(Level.INFO, "Calling POST: " + fullPath);
        HttpPost httpBase = new HttpPost(fullPath);
        httpBase.setEntity(new FileEntity(file));
        ConnectionUtil.applyHeaders(this.conn.getConfig(), httpBase, httpBase.getMethod(), path, ContentType.APPLICATION_OCTET_STREAM.toString());

        logger.log(Level.INFO, "Request: " + httpBase.getRequestLine());

        CloseableHttpResponse response = this.conn.getApiClient().execute(httpBase);


        logger.log(Level.INFO, response.getStatusLine().toString());
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            stream = entity.getContent();
            logger.log(Level.INFO, String.format("Result stream size: %d, encoding: %s",
                    entity.getContentLength(), entity.getContentEncoding()));
        }
        return stream;
    }
}
