package com.threatconnect.sdk.conn;

public class HttpResponse
{
	private String statusLine;
	private int statusCode;
	private String entity;
	
	public String getStatusLine()
	{
		return statusLine;
	}
	
	public void setStatusLine(final String statusLine)
	{
		this.statusLine = statusLine;
	}
	
	public int getStatusCode()
	{
		return statusCode;
	}
	
	public void setStatusCode(final int statusCode)
	{
		this.statusCode = statusCode;
	}
	
	public String getEntity()
	{
		return entity;
	}
	
	public void setEntity(final String entity)
	{
		this.entity = entity;
	}
}
