package com.threatconnect.app.services.message;

import java.util.HashMap;
import java.util.Map;

public abstract class UpdateCommandConfig extends AbstractCommandConfig
{
	private final Map<String, Object> config;
	private String apiToken;
	private long expireSeconds;
	
	public UpdateCommandConfig()
	{
		super(CommandType.UpdateConfig);
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
	
	public long getExpireSeconds()
	{
		return expireSeconds;
	}
	
	public void setExpireSeconds(final long expireSeconds)
	{
		this.expireSeconds = expireSeconds;
	}
}

