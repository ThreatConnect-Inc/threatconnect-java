package com.threatconnect.app.services.message;

import java.util.List;

public class RunService extends AbstractCommandConfig
{
	private String method;
	private List<NameValuePair<String, String>> headers;
	private List<NameValuePair<String, String>> queryParams;
	private String path;
	private String apiToken;
	private long expireSeconds;
	
	private String bodyVariable;
	
	public RunService()
	{
		super(CommandType.RunService);
	}
	
	public RunService(CommandType command)
	{
		super(command);
	}
	
	public String getMethod()
	{
		return method;
	}
	
	public void setMethod(String method)
	{
		this.method = method;
	}
	
	public String getBodyVariable()
	{
		return bodyVariable;
	}
	
	public void setBodyVariable(final String bodyVariable)
	{
		this.bodyVariable = bodyVariable;
	}
	
	public String getPath()
	{
		return path;
	}
	
	public void setPath(String path)
	{
		this.path = path;
	}
	
	public void setApiToken(final String apiToken)
	{
		this.apiToken = apiToken;
	}
	
	public String getApiToken()
	{
		return apiToken;
	}
	
	public List<NameValuePair<String, String>> getHeaders()
	{
		return headers;
	}
	
	public void setHeaders(List<NameValuePair<String, String>> headers)
	{
		this.headers = headers;
	}
	
	public List<NameValuePair<String, String>> getQueryParams()
	{
		return queryParams;
	}
	
	public void setQueryParams(List<NameValuePair<String, String>> queryParams)
	{
		this.queryParams = queryParams;
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

