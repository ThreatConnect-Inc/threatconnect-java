package com.threatconnect.app.apps.service.message;

import java.util.List;

public class RunService extends AbstractCommandConfig
{
	private String method;
	private List<NameValuePair<String, String>> headers;
	private List<NameValuePair<String, String>> queryParams;
	private String path;
	
	private boolean isBinary;
	private String body;
	private String bodyBinarySessionId;
	private String responseBinarySessionId;
	
	public RunService()
	{
		super(CommandType.RunService);
	}
	
	public RunService(CommandType command)
	{
		super(command);
	}
	
	public String getBodyBinarySessionId()
	{
		return bodyBinarySessionId;
	}
	
	public void setBodyBinarySessionId(String bodyBinarySessionId)
	{
		this.bodyBinarySessionId = bodyBinarySessionId;
	}
	
	public String getResponseBinarySessionId()
	{
		return responseBinarySessionId;
	}
	
	public void setResponseBinarySessionId(String responseBinarySessionId)
	{
		this.responseBinarySessionId = responseBinarySessionId;
	}
	
	public String getMethod()
	{
		return method;
	}
	
	public void setMethod(String method)
	{
		this.method = method;
	}
	
	public boolean isBinary()
	{
		return isBinary;
	}
	
	public void setBinary(boolean binary)
	{
		isBinary = binary;
	}
	
	public String getBody()
	{
		return body;
	}
	
	public void setBody(String body)
	{
		this.body = body;
	}
	
	public String getPath()
	{
		return path;
	}
	
	public void setPath(String path)
	{
		this.path = path;
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
	
}

