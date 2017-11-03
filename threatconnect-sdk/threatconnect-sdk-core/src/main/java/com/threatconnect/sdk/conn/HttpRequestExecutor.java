package com.threatconnect.sdk.conn;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.threatconnect.app.apps.AppConfig;
import com.threatconnect.sdk.app.SdkAppConfig;
import com.threatconnect.sdk.conn.exception.HttpException;
import com.threatconnect.sdk.conn.exception.HttpResourceNotFoundException;
import com.threatconnect.sdk.conn.exception.TokenRenewException;
import com.threatconnect.sdk.util.StringUtil;
import com.threatconnect.sdk.util.UploadMethodType;
import org.apache.commons.io.IOUtils;
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
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;

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
		String jsonData = obj instanceof String ? (String) obj : StringUtil.toJSON(obj);
		logger.trace("entity : " + jsonData);
		((HttpEntityEnclosingRequestBase) httpBase).setEntity(new StringEntity(jsonData, ContentType.APPLICATION_JSON));
	}

	private String makeApiCall(HttpMethod type, String fullPath, HttpRequestBase httpBase) throws IOException
	{
		logger.trace("Before call to api server");
		long startMs = System.currentTimeMillis();
		
		try(CloseableHttpClient httpClient = this.conn.getApiClient())
		{
			try(CloseableHttpResponse response = httpClient.execute(httpBase))
			{
				//update listeners based on api call whether it worked or not
				notifyListeners(type, fullPath, (System.currentTimeMillis() - startMs));
				
				String result = null;
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
				
				return result;
			}
		}
	}

	private void getNewToken() throws IOException, TokenRenewException
	{
		if (this.conn.getConfig().getTcToken() != null)
		{
			//get the expire time
			long expireTime = Long.valueOf(this.conn.getConfig().getTcTokenExpires());

			//compare to now + 15
			long timeNow = System.currentTimeMillis() / 1000L;
			if (expireTime < (timeNow + 15l))
			{
				//its expired...lets get a new one
				try
				{
					//in this case, we want to get a new token if the token being used has not been reused before.
					String retryToken = this.conn.getConfig().getTcToken();
					logger.trace("Secure Token to be looked at: " + retryToken);

                                        //We moved the retry servlet from the web module to the api
                                        //module so need to use the api path:
                                        String apiPath = SdkAppConfig.getInstance().getTcApiPath();
                                        logger.trace("apiPath: " + apiPath);
					String retryUrl = apiPath
						+ APP_AUTH_URL_SERVLET_PART
						+ URLEncoder.encode(retryToken, "UTF-8");

					//Default TC SSL cert is not trusted so need to add the TC cert to local jvm cacerts if
					//running app locally
					try(CloseableHttpClient httpClient = this.conn.getApiClient())
					{
						HttpGet httpGet = new HttpGet(retryUrl);
						try (CloseableHttpResponse retryTokenCloseableResponse = httpClient.execute(httpGet))
						{
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
								throw new TokenRenewException(errMsg);
							}
							
							//no error, got a new token..need to set it so the caller will use it
							String newToken = retryResponse.get(NEW_TOKEN).getAsString();
							String newTokenExpires = retryResponse.get(NEW_TOKEN_EXPIRES).getAsString();
							logger.trace("New token returned from servlet: " + newToken);
							
							//update the config object being used for this api call
							this.conn.getConfig().setTcToken(newToken);
							this.conn.getConfig().setTcTokenExpires(newTokenExpires);
							
							//Then update the AppConfig singleton object too for future calls/future configs
							SdkAppConfig.getInstance().set(AppConfig.TC_TOKEN, newToken);
							SdkAppConfig.getInstance().set(AppConfig.TC_TOKEN_EXPIRES, newTokenExpires);
						}
					}
				}
				catch (MalformedURLException ex)
				{
					throw new TokenRenewException(ex);
				}
			}
		}
	}

	@Override
	public HttpResponse execute(String path, HttpMethod type, Object obj) throws IOException
	{
		return execute(path, type, null /*headers*/, obj);
	}

	@Override
	public HttpResponse execute(String path, HttpMethod type, Map<String, String> headers, Object obj)
		throws IOException
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

		try
		{
			//Check for token to expire and get a new one if it is
			getNewToken();

			//this call adds in the auth token from the connection config if one exists
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
			
			try(CloseableHttpClient httpClient = this.conn.getApiClient())
			{
				try(CloseableHttpResponse response = httpClient.execute(httpBase))
				{
					notifyListeners(type, fullPath, (System.currentTimeMillis() - startMs));
					
					HttpResponse httpResponse = new HttpResponse();
					httpResponse.setStatusCode(response.getStatusLine().getStatusCode());
					httpResponse.setStatusLine(response.getStatusLine().toString());
					
					logger.trace(httpResponse.getStatusLine());
					HttpEntity entity = response.getEntity();
					if (entity != null)
					{
						logger.trace("Response Headers: " + Arrays.toString(response.getAllHeaders()));
						logger.trace("Content Encoding: " + entity.getContentEncoding());
						
						httpResponse.setEntity(IOUtils.toByteArray(entity.getContent()));
						logger.trace("Result:" + httpResponse.getEntityAsString());
						EntityUtils.consume(entity);
					}
					
					return httpResponse;
				}
			}
		}
		catch (TokenRenewException e)
		{
			throw new HttpException(e);
		}
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
	 * @param path url to issue request to
	 * @return raw input stream from response
	 * @throws IOException On error
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
		
		try(CloseableHttpClient httpClient = this.conn.getApiClient())
		{
			try(CloseableHttpResponse response = httpClient.execute(httpBase))
			{
				logger.trace(response.getStatusLine().toString());
				
				// check to see if there was an error executing this request
				switch (response.getStatusLine().getStatusCode())
				{
					case HttpStatus.SC_NOT_FOUND:
						throw new HttpResourceNotFoundException("Server responded with a 404: " + fullPath);
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
		}
	}
	
	@Override
	public String executeUploadByteStream(String path, InputStream inputStream, UploadMethodType uploadMethodType)
		throws IOException
	{
		return executeUpload(path, new InputStreamEntity(inputStream), uploadMethodType);
	}
	
	@Override
	public String executeUpload(String path, HttpEntity requestEntity, UploadMethodType uploadMethodType)
		throws IOException
	{
		return executeUpload(path, requestEntity, uploadMethodType, ContentType.APPLICATION_OCTET_STREAM.toString());
	}
	
	@Override
	public String executeUpload(String path, HttpEntity requestEntity, UploadMethodType uploadMethodType, String contentType)
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

		httpBase.setEntity(requestEntity);
		String headerPath = httpBase.getURI().getRawPath() + "?" + httpBase.getURI().getRawQuery();
		ConnectionUtil.applyHeaders(this.conn.getConfig(), httpBase, httpBase.getMethod(), headerPath, contentType);

		logger.trace("Request: " + httpBase.getRequestLine());
		
		try(CloseableHttpClient httpClient = this.conn.getApiClient())
		{
			try(CloseableHttpResponse response = httpClient.execute(httpBase))
			{
				String result = null;
				
				logger.trace(response.getStatusLine().toString());
				HttpEntity responseEntity = response.getEntity();
				if (responseEntity != null)
				{
					try
					{
						result = EntityUtils.toString(responseEntity, "iso-8859-1");
						logger.trace("Result:" + result);
						EntityUtils.consume(responseEntity);
					}
					finally
					{
						response.close();
					}
					
				}
				
				return result;
			}
		}
	}
}
