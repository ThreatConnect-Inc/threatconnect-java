package com.threatconnect.app.apps.service.message;

import java.util.List;

public class RunServiceAcknowledgeMessage extends AcknowledgeMessage
{
	private Integer statusCode;
	private boolean isBinary;
	private String body;
	private String bodyBinarySessionId;
	private List<NameValuePair<String, String>> headers;
	private String status;
	
	public RunServiceAcknowledgeMessage()
	{
		super(CommandType.RunService);
	}
	
	public Integer getStatusCode()
	{
		return statusCode;
	}
	
	public void setStatusCode(Integer statusCode)
	{
		this.statusCode = statusCode;
	}
	
	public List<NameValuePair<String, String>> getHeaders()
	{
		return headers;
	}
	
	public void setHeaders(List<NameValuePair<String, String>> headers)
	{
		this.headers = headers;
	}
	
	public String getBody()
	{
		return body;
	}
	
	public void setBody(String body)
	{
		this.body = body;
	}
	
	public String getBodyBinarySessionId()
	{
		return bodyBinarySessionId;
	}
	
	public void setBodyBinarySessionId(String bodyBinarySessionId)
	{
		this.bodyBinarySessionId = bodyBinarySessionId;
	}
	
	public boolean isBinary()
	{
		return isBinary;
	}
	
	public void setBinary(boolean binary)
	{
		isBinary = binary;
	}
	
	public String getStatus()
	{
		return status;
	}
	
	public void setStatus(final String status)
	{
		this.status = status;
	}
}
