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
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.jboss.resteasy.client.core.executors.InMemoryClientExecutor;
import org.jboss.resteasy.util.MediaTypeHelper;

import javax.ws.rs.core.MediaType;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;

/**
 *
 * @author dtineo
 */
public class InMemoryRequestExecutor extends AbstractRequestExecutor
{

    public InMemoryRequestExecutor(Connection conn)
    {
        super(conn);
    }

    private static HttpRequestBase getBase(String fullPath, HttpMethod type)
    {

        switch (type)
        {
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

    private void applyEntityAsJSON(ClientRequest request, Object obj) throws JsonProcessingException
    {
        String jsonData = StringUtil.toJSON(obj);
        logger.log(Level.INFO, "entity : " + jsonData);
        request.body(MediaType.APPLICATION_JSON_TYPE, jsonData);
    }

    @Override
    public String execute(String path, HttpMethod type, Object obj) throws IOException
    {

        if (this.conn.getConfig() == null)
        {
            throw new IllegalStateException("Can't execute HTTP request when configuration is undefined.");
        }

        InMemoryClientExecutor executor = new InMemoryClientExecutor();
        ClientRequest request = new ClientRequest(path, executor);
        request.setHttpMethod(type.toString());
        if ( obj != null )  applyEntityAsJSON( request, obj );

        ConnectionUtil.applyHeaders(this.conn.getConfig(), request, type.toString(), path);

        logger.log(Level.INFO, "Calling " + type + ": " + path);

        ClientResponse<String> result = null;
        try
        {
            result = request.execute();
        } catch (Exception e)
        {
            throw new IOException(e);   // re-throw to more specific exception
        }
        System.err.println("result = " + result);

        return result.getEntity();


        /*

        ConnectionUtil.applyHeaders(this.conn.getConfig(), httpBase, httpBase.getMethod(), path);
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
        */
    }

    /**
     * Execute an HTTP request and return the raw input stream.  <i>Caller is responsible for closing InputStream.</i>
     *
     * @param path url to issue request to
     * @return raw input stream from response
     * @throws java.io.IOException
     */
    @Override
    public InputStream executeDownloadByteStream(String path) throws IOException
    {
        if (this.conn.getConfig() == null)
        {
            throw new IllegalStateException("Can't execute HTTP request when configuration is undefined.");
        }

        return null;    // TODO
    }


    @Override
    public InputStream executeUploadByteStream(String path, File file) throws IOException
    {
        if (this.conn.getConfig() == null)
        {
            throw new IllegalStateException("Can't execute HTTP request when configuration is undefined.");
        }

        return null;    // TODO
    }

}
