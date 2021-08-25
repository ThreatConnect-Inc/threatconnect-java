package com.threatconnect.app.services.message;

import java.util.List;

public class WebhookMarshallEvent extends AbstractCommandConfig
{
	private List<NameValuePair<String, String>> headers;
	private String requestUrl;
	private Integer statusCode;
	
	private String bodyVariable;
	
	public WebhookMarshallEvent()
	{
		super(CommandType.WebhookMarshallEvent);
	}
	
	public List<NameValuePair<String, String>> getHeaders()
	{
		return headers;
	}
	
	public void setHeaders(final List<NameValuePair<String, String>> headers)
	{
		this.headers = headers;
	}
	
	public String getRequestUrl()
	{
		return requestUrl;
	}
	
	public void setRequestUrl(final String requestUrl)
	{
		this.requestUrl = requestUrl;
	}
	
	public Integer getStatusCode()
	{
		return statusCode;
	}
	
	public void setStatusCode(final Integer statusCode)
	{
		this.statusCode = statusCode;
	}
	
	public String getBodyVariable()
	{
		return bodyVariable;
	}
	
	public void setBodyVariable(final String bodyVariable)
	{
		this.bodyVariable = bodyVariable;
	}
}

