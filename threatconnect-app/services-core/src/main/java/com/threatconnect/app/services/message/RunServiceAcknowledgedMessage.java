package com.threatconnect.app.services.message;

import java.util.List;

public class RunServiceAcknowledgedMessage extends AcknowledgedMessage
{
	private Integer statusCode;
	private boolean isBinary;
	private String bodyVariable;
	private List<NameValuePair<String, String>> headers;
	private String status;
	
	public RunServiceAcknowledgedMessage()
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
	
	public String getBodyVariable()
	{
		return bodyVariable;
	}
	
	public void setBodyVariable(final String bodyVariable)
	{
		this.bodyVariable = bodyVariable;
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
