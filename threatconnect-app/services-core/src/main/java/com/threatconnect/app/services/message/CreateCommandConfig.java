package com.threatconnect.app.services.message;

import java.util.HashMap;
import java.util.Map;

public class CreateCommandConfig extends AbstractCommandConfig
{
	private final Map<String, Object> config;
	private String apiToken;
	private String retryToken;
	private long expireSeconds;
	private String url;
	
	public CreateCommandConfig()
	{
		super(CommandType.CreateConfig);
		this.config = new HashMap<String, Object>();
	}
	
	public Map<String, Object> getConfig()
	{
		return config;
	}
	
	public String getApiToken()
	{
		return apiToken;
	}
	
	public void setApiToken(final String apiToken)
	{
		this.apiToken = apiToken;
	}
	
	public String getRetryToken()
	{
		return retryToken;
	}
	
	public void setRetryToken(final String retryToken)
	{
		this.retryToken = retryToken;
	}
	
	public long getExpireSeconds()
	{
		return expireSeconds;
	}
	
	public void setExpireSeconds(final long expireSeconds)
	{
		this.expireSeconds = expireSeconds;
	}
	
	public String getUrl()
	{
		return url;
	}
	
	public void setUrl(final String url)
	{
		this.url = url;
	}
}
