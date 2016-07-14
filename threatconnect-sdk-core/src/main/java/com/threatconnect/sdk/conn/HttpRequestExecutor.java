/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.conn;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.threatconnect.sdk.conn.exception.HttpResourceNotFoundException;
import com.threatconnect.sdk.util.StringUtil;
import com.threatconnect.sdk.util.UploadMethodType;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author dtineo
 */
public class HttpRequestExecutor extends AbstractRequestExecutor
{
	
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
		String jsonData = obj instanceof String ? (String)obj : StringUtil.toJSON(obj);
		logger.trace("entity : " + jsonData);
		((HttpEntityEnclosingRequestBase) httpBase).setEntity(new StringEntity(jsonData, ContentType.APPLICATION_JSON));
	}

	@Override
	public String execute(String path, HttpMethod type, Object obj) throws IOException
	{
		return execute(path, type, null /*headers*/, obj);
	}

	public String execute(String path, HttpMethod type, Map<String, String> headers, Object obj) throws IOException
	{
		
		path += (path.contains("?") ? "&" : "?");
		path += "createActivityLog=" + this.conn.getConfig().isActivityLogEnabled();
		
		logger.trace("Path: " + path);
		String fullPath = this.conn.getConfig().getTcApiUrl() + path.replace("/api/", "/");
		
		logger.trace("Full: " + type + ": " + fullPath);
		HttpRequestBase httpBase = getBase(fullPath, type);
		if (obj != null)
			applyEntityAsJSON(httpBase, obj);
			
		logger.trace("RawPath: " + httpBase.getURI().getPath());
		logger.trace("Query: " + httpBase.getURI().getRawQuery());
		logger.trace("Path: " + path);
		
		String headerPath = httpBase.getURI().getRawPath() + "?" + httpBase.getURI().getRawQuery();
		logger.trace("HeaderPath: " + headerPath);
		ConnectionUtil.applyHeaders(this.conn.getConfig(), httpBase, type.toString(), headerPath);
		if (headers != null)
		{
			for (Entry<String, String> header : headers.entrySet())
			{
				logger.trace(String.format("Applying header: %s: %s", header.getKey(), header.getValue()));
				httpBase.addHeader(header.getKey(), header.getValue());
			}
		}
		logger.trace("Request: " + httpBase.getRequestLine());
		long startMs = System.currentTimeMillis();
		CloseableHttpResponse response = this.conn.getApiClient().execute(httpBase);
		notifyListeners(type, fullPath, (System.currentTimeMillis() - startMs));
		String result = null;
		
		try
		{
			logger.trace(response.getStatusLine().toString());
			HttpEntity entity = response.getEntity();
			logger.trace("Response Headers: " + Arrays.toString(response.getAllHeaders()));
			logger.trace("Content Encoding: " + entity.getContentEncoding());
			if (entity != null)
			{
				result = EntityUtils.toString(entity, StandardCharsets.UTF_8);
				logger.trace("Result:" + result);
				EntityUtils.consume(entity);
			}
		}
		finally
		{
			response.close();
		}
		
		return result;
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
