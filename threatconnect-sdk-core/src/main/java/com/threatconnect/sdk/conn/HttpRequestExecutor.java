/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.conn;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.threatconnect.sdk.conn.exception.HttpResourceNotFoundException;
import com.threatconnect.sdk.util.StringUtil;
import com.threatconnect.sdk.util.UploadMethodType;
import com.google.gson.JsonObject; 
import com.google.gson.JsonParser;
import com.threatconnect.sdk.app.AppConfig;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import org.apache.http.impl.client.CloseableHttpClient;

/**
 * @author dtineo
 */
public class HttpRequestExecutor extends AbstractRequestExecutor        
{
	private static final String APP_AUTH_URL_SERVLET_PART = "/appAuth/?expiredToken=";
        private static final String MSG = "message";
        private static final String NEW_TOKEN = "apiToken";
        private static final String NEW_TOKEN_EXPIRES = "apiTokenExpires";
        private static final String SUCCESS_IND = "success";
        
	public HttpRequestExecutor(Connection conn)
	{
		super(conn);
	}
	
	private HttpRequestBase getBase(String fullPath, HttpMethod type)
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
	
	private void applyEntityAsJSON(HttpRequestBase httpBase, Object obj) throws JsonProcessingException
	{
		String jsonData = StringUtil.toJSON(obj);
		logger.trace("entity : " + jsonData);
		((HttpEntityEnclosingRequestBase) httpBase).setEntity(new StringEntity(jsonData, ContentType.APPLICATION_JSON));
	}
        
    private String makeApiCall(HttpMethod type, String fullPath, HttpRequestBase httpBase) throws IOException
    {
        logger.trace("Before call to api server");
        long startMs = System.currentTimeMillis();
        CloseableHttpResponse response = this.conn.getApiClient().execute(httpBase);

        //update listeners based on api call whether it worked or not
        notifyListeners(type, fullPath, (System.currentTimeMillis() - startMs));

        String result = null;
        try
        {
            logger.trace(response.getStatusLine().toString());
            HttpEntity entity = response.getEntity();
            if (entity != null)
            {
                logger.trace("Response Headers: " + Arrays.toString(response.getAllHeaders()));
                logger.trace("Content Encoding: " + entity.getContentEncoding());
                result = EntityUtils.toString(entity, StandardCharsets.UTF_8);
                logger.trace("Result:" + result);
                EntityUtils.consume(entity);
            }
        } finally
        {
            response.close();
        }

        return result;
    }

    private String getNewToken(HttpRequestBase httpBase) throws UnsupportedEncodingException, IOException
    {
        if (this.conn.getConfig().getTcToken() != null)
	{
            //get the expire time
            long expireTime = Long.valueOf(this.conn.getConfig().getTcTokenExpires());

            //compare to now + 15
            long timeNow = System.currentTimeMillis() / 1000L;
            if (expireTime < (timeNow+15l))
            {
                //its expired...lets get a new one
                try
                {
                    //in this case, we want to get a new token if the token being used has not been reused before.
                    String retryToken = this.conn.getConfig().getTcToken();
                    logger.trace("Secure Token to be looked at: " + retryToken);
                    String retryUrl = httpBase.getURI().getScheme()
                            + "://"
                            + httpBase.getURI().getHost()
                            + ":"
                            + httpBase.getURI().getPort()
                            + APP_AUTH_URL_SERVLET_PART
                            + URLEncoder.encode(retryToken, "UTF-8");

                    //Default TC SSL cert is not trusted so need to add the TC cert to local jvm cacerts if
                    //running app locally
                    CloseableHttpClient httpClient = this.conn.getApiClient();
                    HttpGet httpGet = new HttpGet(retryUrl);
                    CloseableHttpResponse retryTokenCloseableResponse = httpClient.execute(httpGet);
                    HttpEntity entity = retryTokenCloseableResponse.getEntity();
                    String retryTokenResponse = EntityUtils.toString(entity, StandardCharsets.UTF_8);

                    //parse line to see what happened
                    JsonParser jsonParser = new JsonParser();
                    JsonObject retryResponse = jsonParser.parse(retryTokenResponse).getAsJsonObject();
                    boolean retrySuccess = retryResponse.get(SUCCESS_IND).getAsBoolean();
                    if (!retrySuccess)
                    {
                        //need to translate the retry token error into the format
                        //expected in 'result' object
                        String errMsg = retryResponse.get(MSG).getAsString();
                        logger.trace("errmsg from servlet: " + errMsg);
                        return "{\"status\":\"Failure\",\"message\":\""+errMsg+"\"}";
                    }

                    //no error, got a new token..need to set it so the caller will use it
                    String newToken = retryResponse.get(NEW_TOKEN).getAsString();
                    String newTokenExpires = retryResponse.get(NEW_TOKEN_EXPIRES).getAsString();
                    logger.trace("New token returned from servlet: " + newToken);

                    //update the config object being used for this api call
                    this.conn.getConfig().setTcToken(newToken);
                    this.conn.getConfig().setTcTokenExpires(newTokenExpires);

                    //Then update the AppConfig singleton object too for future calls/future configs
                    AppConfig.getInstance().set(AppConfig.TC_TOKEN, newToken);
                    AppConfig.getInstance().set(AppConfig.TC_TOKEN_EXPIRES, newTokenExpires);

                } catch (MalformedURLException ex)
                {
                    return "{\"status\":\"Failure\",\"message\":\""+ex.getMessage()+"\"}";
                }
            }
	}
        return null;
    }

    @Override
    public String execute(String path, HttpMethod type, Object obj) throws IOException
    {
        path += (path.contains("?") ? "&" : "?");
        path += "createActivityLog=" + this.conn.getConfig().isActivityLogEnabled();

        logger.trace("Path: " + path);
        String fullPath = this.conn.getConfig().getTcApiUrl() + path.replace("/api/", "/");

        logger.trace("Full: " + type + ": " + fullPath);
        HttpRequestBase httpBase = getBase(fullPath, type);
        if (obj != null)
        {
            applyEntityAsJSON(httpBase, obj);
        }

        logger.trace("RawPath: " + httpBase.getURI().getPath());
        logger.trace("Query: " + httpBase.getURI().getRawQuery());
        logger.trace("Path: " + path);

        String headerPath = httpBase.getURI().getRawPath() + "?" + httpBase.getURI().getRawQuery();
        logger.trace("HeaderPath: " + headerPath);

        //Check for token to expire and get a new one if it is
        String error = getNewToken(httpBase);
        if (error != null)
        {
            return error;
        }

        //this call adds in the auth token from the connection config if one exists
        ConnectionUtil.applyHeaders(this.conn.getConfig(), httpBase, type.toString(), headerPath);

        logger.trace("Request: " + httpBase.getRequestLine());
        return makeApiCall(type, fullPath, httpBase);
    }
	
	private void notifyListeners(HttpMethod type, String fullPath, long ms)
	{
		for (ApiCallListener l : apiCallListeners)
		{
			l.apiCall(type.toString(), fullPath, ms);
		}
	}
	
	/**
	 * Execute an HTTP request and return the raw input stream. <i>Caller is responsible for closing
	 * InputStream.</i>
	 *
	 * @param path
	 * url to issue request to
	 * @return raw input stream from response
	 * @throws IOException
	 * On error
	 */
	@Override
	public InputStream executeDownloadByteStream(String path, ContentType contentType) throws IOException
	{
		if (this.conn.getConfig() == null)
		{
			throw new IllegalStateException("Can't execute HTTP request when configuration is undefined.");
		}
		
		InputStream stream = null;
		
		String fullPath = this.conn.getConfig().getTcApiUrl() + path.replace("/api/", "/");
		
		logger.trace("Calling GET: " + fullPath);
		HttpRequestBase httpBase = getBase(fullPath, HttpMethod.GET);
		
		String headerPath = httpBase.getURI().getRawPath() + "?" + httpBase.getURI().getRawQuery();
		ConnectionUtil.applyHeaders(this.conn.getConfig(), httpBase, httpBase.getMethod(), headerPath,
			conn.getConfig().getContentType(), contentType.toString());
		logger.trace("Request: " + httpBase.getRequestLine());
		logger.trace("Headers: " + Arrays.toString(httpBase.getAllHeaders()));
		CloseableHttpResponse response = this.conn.getApiClient().execute(httpBase);
		
		logger.trace(response.getStatusLine().toString());
		
		// check to see if there was an error executing this request
		switch (response.getStatusLine().getStatusCode())
		{
			case HttpStatus.SC_NOT_FOUND:
				throw new HttpResourceNotFoundException();
		}
		
		HttpEntity entity = response.getEntity();
		if (entity != null)
		{
			stream = entity.getContent();
			logger.trace(String.format("Result stream size: %d, encoding: %s",
				entity.getContentLength(), entity.getContentEncoding()));
		}
		return stream;
	}
	
	@Override
	public String executeUploadByteStream(String path, InputStream inputStream, UploadMethodType uploadMethodType)
		throws IOException
	{
		if (this.conn.getConfig() == null)
		{
			throw new IllegalStateException("Can't execute HTTP request when configuration is undefined.");
		}
		
		String fullPath = this.conn.getConfig().getTcApiUrl() + path.replace("/api/", "/");
		
		final HttpEntityEnclosingRequestBase httpBase;
		
		if (uploadMethodType.equals(UploadMethodType.POST))
		{
			logger.trace("Calling POST: " + fullPath);
			httpBase = new HttpPost(fullPath);
		}
		else
		{
			logger.trace("Calling PUT: " + fullPath);
			httpBase = new HttpPut(fullPath);
		}
		
		httpBase.setEntity(new InputStreamEntity(inputStream));
		String headerPath = httpBase.getURI().getRawPath() + "?" + httpBase.getURI().getRawQuery();
		ConnectionUtil.applyHeaders(this.conn.getConfig(), httpBase, httpBase.getMethod(), headerPath,
			ContentType.APPLICATION_OCTET_STREAM.toString());
			
		logger.trace("Request: " + httpBase.getRequestLine());
		
		CloseableHttpResponse response = this.conn.getApiClient().execute(httpBase);
		String result = null;
		
		logger.trace(response.getStatusLine().toString());
		HttpEntity entity = response.getEntity();
		if (entity != null)
		{
			try
			{
				result = EntityUtils.toString(entity, "iso-8859-1");
				logger.trace("Result:" + result);
				EntityUtils.consume(entity);
			}
			finally
			{
				response.close();
			}
			
		}
		
		return result;
	}
}
