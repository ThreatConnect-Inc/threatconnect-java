/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.conn;

/*
import com.fasterxml.jackson.core.JsonProcessingException;
import com.threatconnect.sdk.server.response.service.ApiDataServiceResponse;
import com.threatconnect.sdk.util.StringUtil;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.jboss.resteasy.client.core.executors.InMemoryClientExecutor;
import org.jboss.resteasy.core.Dispatcher;
import org.jboss.resteasy.spi.Registry;
import org.jboss.resteasy.spi.ResteasyProviderFactory;

import javax.ws.rs.core.MediaType;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
*/

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.entity.ContentType;

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

    @Override
    public String execute(String path, HttpMethod type, Object obj) throws IOException
    {
        return null;
    }

    @Override
    public String executeUploadByteStream(String path, InputStream inputStream) throws IOException
    {
        return null;
    }

    @Override
    public InputStream executeDownloadByteStream(String path, ContentType contentType) throws IOException
    {
        return null;
    }
/*


    private ResteasyProviderFactory resteasyProviderFactory;
    private Registry registry;

    private InMemoryClientExecutor executor;
    private Dispatcher dispatcher;

    public InMemoryRequestExecutor(Connection conn)
    {
        super(conn);

    }

    private void applyEntityAsJSON(ClientRequest request, Object obj) throws JsonProcessingException
    {
        String jsonData = StringUtil.toJSON(obj);
        logger.log(Level.FINEST, "entity: " + jsonData);
        request.body(MediaType.APPLICATION_JSON_TYPE, jsonData);
    }

    @Override
    public String execute(String path, HttpMethod type, Object obj) throws IOException
    {

        // TODO: pull elements from connection/config
        if ( type == HttpMethod.GET )
        {
            path += "&resultStart=0&resultLimit=500";
        }
        path += "&createActivityLog=false";

        String basePath = path.replace("/api/","/");


        ClientRequest request = new ClientRequest( basePath, this.executor);
        request.setHttpMethod(type.toString());
        if ( obj != null )  applyEntityAsJSON( request, obj );

        // NOTE: Using the API server with embedded dispatcher means the signature fails due
        //       to resource path vs. url path differences (i.e. "/api" domain suffix)
        //       To resolve this difference, use the basePath for signature in header
        ConnectionUtil.applyHeaders(this.conn.getConfig(), request, type.toString(), basePath);

        logger.log(Level.FINEST, "Calling " + type + ": " + basePath);

        ClientResponse result = null;
        try
        {
            switch(type)
            {
                case GET:
                    result = request.get();
                    break;
                case POST:
                    result = request.post();
                    break;
                case DELETE:
                    result = request.delete();
                    break;
                case PUT:
                    result = request.put();
                    break;
            }
        } catch (Exception e)
        {
            logger.log(Level.SEVERE, "Request Error", e);
            throw new IOException(e);   // re-throw to more specific exception
        }
        //System.err.println("status=" + result.getStatus());
        //System.err.println("reason=" + result.getResponseStatus().getReasonPhrase() );
        //System.err.println("result = " + result);

        return (String)result.getEntity(String.class);

    }

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

    public void setResteasyProviderFactory(ResteasyProviderFactory resteasyProviderFactory)
    {
        this.resteasyProviderFactory = resteasyProviderFactory;
    }

    public void setRegistry(Registry registry)
    {
        this.registry = registry;
    }

    public void setDispatcher(Dispatcher dispatcher)
    {
        this.dispatcher = dispatcher;
        this.executor = new InMemoryClientExecutor(dispatcher);
    }
*/
}
