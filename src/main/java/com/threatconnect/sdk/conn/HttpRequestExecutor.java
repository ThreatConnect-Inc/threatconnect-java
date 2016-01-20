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
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
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

    private HttpRequestBase getBase(String fullPath, HttpMethod type) {

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
        logger.log(Level.FINEST, "entity : " + jsonData);
       ((HttpEntityEnclosingRequestBase)httpBase).setEntity(new StringEntity(jsonData, ContentType.APPLICATION_JSON));
    }

    @Override
    public String execute(String path, HttpMethod type, Object obj) throws IOException {

        path +=  ( path.contains("?") ? "&" : "?" );
        path += "createActivityLog=" + this.conn.getConfig().isActivityLogEnabled();

        logger.log(Level.FINEST, "Path: " + path);
        String fullPath = this.conn.getConfig().getTcApiUrl() + path.replace("/api/","/");

        logger.log(Level.FINEST, "Full: " + type + ": " + fullPath);
        HttpRequestBase httpBase = getBase(fullPath, type);
        if ( obj != null )  applyEntityAsJSON( httpBase, obj );

        logger.log(Level.FINEST, "RawPath: " + httpBase.getURI().getPath());
        logger.log(Level.FINEST, "Query: " + httpBase.getURI().getRawQuery());
        logger.log(Level.FINEST, "Path: " + path);

        String headerPath = httpBase.getURI().getRawPath() + "?" + httpBase.getURI().getRawQuery();
        logger.log(Level.FINEST, "HeaderPath: " + headerPath);
        ConnectionUtil.applyHeaders(this.conn.getConfig(), httpBase, type.toString(), headerPath);
        logger.log(Level.FINEST, "Request: " + httpBase.getRequestLine());
        long startMs = System.currentTimeMillis();
        CloseableHttpResponse response = this.conn.getApiClient().execute(httpBase);
        notifyListeners( type, fullPath, (System.currentTimeMillis()-startMs));
        String result = null;

        try {
            logger.log(Level.FINEST, response.getStatusLine().toString() );
            HttpEntity entity = response.getEntity();
            logger.log(Level.FINEST, "Response Headers: " + Arrays.toString(response.getAllHeaders()));
            logger.log(Level.FINEST, "Content Encoding: " + entity.getContentEncoding());
            if (entity != null) {
                result = EntityUtils.toString(entity, StandardCharsets.UTF_8);
                logger.log(Level.FINEST, "Result:" + result);
                EntityUtils.consume(entity);
            }
        } finally {
            response.close();
        }

        return result;
    }

    private void notifyListeners(HttpMethod type, String fullPath, long ms)
    {
        for(ApiCallListener l : apiCallListeners)
        {
            l.apiCall(type.toString(), fullPath, ms);
        }
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

        logger.log(Level.FINEST, "Calling GET: " + fullPath);
        HttpRequestBase httpBase = getBase(fullPath, HttpMethod.GET);

        String headerPath = httpBase.getURI().getRawPath() + "?" + httpBase.getURI().getRawQuery();
        ConnectionUtil.applyHeaders(this.conn.getConfig(), httpBase, httpBase.getMethod(), headerPath, conn.getConfig().getContentType()
            , ContentType.APPLICATION_OCTET_STREAM.toString());
        logger.log(Level.FINEST, "Request: " + httpBase.getRequestLine());
        logger.log(Level.FINEST, "Headers: " + Arrays.toString(httpBase.getAllHeaders()));
        CloseableHttpResponse response = this.conn.getApiClient().execute(httpBase);


        logger.log(Level.FINEST, response.getStatusLine().toString());
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            stream = entity.getContent();
            logger.log(Level.FINEST, String.format("Result stream size: %d, encoding: %s",
                                                entity.getContentLength(), entity.getContentEncoding()));
        }
        return stream;
    }


    @Override
    public String executeUploadByteStream(String path, File file) throws IOException
    {
        if (this.conn.getConfig() == null) {
            throw new IllegalStateException("Can't execute HTTP request when configuration is undefined.");
        }

        String fullPath = this.conn.getConfig().getTcApiUrl() + path.replace("/api/","/");

        logger.log(Level.FINEST, "Calling POST: " + fullPath);
        HttpPost httpBase = new HttpPost(fullPath);
        httpBase.setEntity(new FileEntity(file));
        String headerPath = httpBase.getURI().getRawPath() + "?" + httpBase.getURI().getRawQuery();
        ConnectionUtil.applyHeaders(this.conn.getConfig(), httpBase, httpBase.getMethod(), headerPath, ContentType.APPLICATION_OCTET_STREAM.toString());

        logger.log(Level.FINEST, "Request: " + httpBase.getRequestLine());

        CloseableHttpResponse response = this.conn.getApiClient().execute(httpBase);
        String result = null;


        logger.log(Level.FINEST, response.getStatusLine().toString());
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            try {
                result = EntityUtils.toString(entity, "iso-8859-1");
                logger.log(Level.FINEST, "Result:" + result);
                EntityUtils.consume(entity);
            } finally {
                response.close();
            }

        }

        return result;
    }
}
