package com.threatconnect.app.playbooks.db.tcapi;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.threatconnect.app.apps.AppConfig;
import com.threatconnect.app.playbooks.app.PlaybooksAppConfig;
import com.threatconnect.app.playbooks.db.DBReadException;
import com.threatconnect.app.playbooks.db.DBService;
import com.threatconnect.app.playbooks.db.DBWriteException;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * @author Greg Marut
 */
public class TCApiDBService implements DBService
{
	private static final Logger logger = LoggerFactory.getLogger(TCApiDBService.class);
	
	private static final String APP_AUTH_URL_SERVLET_PART = "/appAuth/?expiredToken=";
	private static final String MSG = "message";
	private static final String NEW_TOKEN = "apiToken";
	private static final String NEW_TOKEN_EXPIRES = "apiTokenExpires";
	private static final String SUCCESS_IND = "success";
	
	private final PlaybooksAppConfig playbooksAppConfig;
	private final AppConfig appConfig;
	
	public TCApiDBService(final PlaybooksAppConfig playbooksAppConfig)
	{
		this.playbooksAppConfig = playbooksAppConfig;
		this.appConfig = playbooksAppConfig.getAppConfig();
	}
	
	@Override
	public void saveValue(final String key, final byte[] value) throws DBWriteException
	{
		try (CloseableHttpClient httpClient = ConnectionUtil.createClient(true))
		{
			//create the put request
			HttpPut httpPut = new HttpPut(buildUrl(key));
			httpPut.setEntity(new ByteArrayEntity(value));
			try (CloseableHttpResponse response = httpClient.execute(httpPut))
			{
				//check to make sure the response code is invalid
				final int statusCode = response.getStatusLine().getStatusCode();
				if (statusCode < 200 || statusCode >= 400)
				{
					throw new DBWriteException("Server returned status code " + statusCode);
				}
			}
		}
		catch (IOException e)
		{
			throw new DBWriteException(e);
		}
	}
	
	@Override
	public byte[] getValue(final String key) throws DBReadException
	{
		try (CloseableHttpClient httpClient = ConnectionUtil.createClient(true))
		{
			//create the get request
			HttpGet httpGet = new HttpGet(buildUrl(key));
			try (CloseableHttpResponse response = httpClient.execute(httpGet))
			{
				//check to make sure the response code is invalid
				final int statusCode = response.getStatusLine().getStatusCode();
				if (statusCode < 200 || statusCode >= 400)
				{
					throw new DBReadException("Server returned status code " + statusCode);
				}
				else
				{
					return IOUtils.toByteArray(response.getEntity().getContent());
				}
			}
		}
		catch (IOException e)
		{
			throw new DBReadException(e);
		}
	}
	
	private String buildUrl(final String key) throws UnsupportedEncodingException
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append(playbooksAppConfig.getDBPath());
		
		if (!sb.toString().endsWith("/"))
		{
			sb.append("/");
		}
		
		sb.append(URLEncoder.encode(key, "UTF-8"));
		
		return sb.toString();
	}
	
	//Copied from the SDK to prevent importing the entire dependency
	//:TODO: this code should be shared somewhere
	private void getNewToken(HttpRequestBase httpBase) throws IOException, TokenRenewException
	{
		if (null != appConfig.getTcToken() && null != appConfig.getTcTokenExpires())
		{
			//get the expire time
			long expireTime = Long.valueOf(appConfig.getTcTokenExpires());
			
			//compare to now + 15
			long timeNow = System.currentTimeMillis() / 1000L;
			if (expireTime < (timeNow + 15l))
			{
				//its expired...lets get a new one
				try
				{
					//in this case, we want to get a new token if the token being used has not been reused before.
					String retryToken = appConfig.getTcToken();
					logger.trace("Secure Token to be looked at: " + retryToken);
					
					//We moved the retry servlet from the web module to the api
					//module so need to use the api path:
					String apiPath = appConfig.getTcApiPath();
					logger.trace("apiPath: " + apiPath);
					String retryUrl = apiPath
						+ APP_AUTH_URL_SERVLET_PART
						+ URLEncoder.encode(retryToken, "UTF-8");
					
					//Default TC SSL cert is not trusted so need to add the TC cert to local jvm cacerts if
					//running app locally
					try (CloseableHttpClient httpClient = ConnectionUtil.createClient(true))
					{
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
							throw new TokenRenewException(errMsg);
						}
						
						//no error, got a new token..need to set it so the caller will use it
						String newToken = retryResponse.get(NEW_TOKEN).getAsString();
						String newTokenExpires = retryResponse.get(NEW_TOKEN_EXPIRES).getAsString();
						logger.trace("New token returned from servlet: " + newToken);
						
						//Then update the AppConfig singleton object too for future calls/future configs
						appConfig.set(AppConfig.TC_TOKEN, newToken);
						appConfig.set(AppConfig.TC_TOKEN_EXPIRES, newTokenExpires);
					}
					
				}
				catch (MalformedURLException ex)
				{
					throw new TokenRenewException(ex);
				}
			}
		}
	}
}
