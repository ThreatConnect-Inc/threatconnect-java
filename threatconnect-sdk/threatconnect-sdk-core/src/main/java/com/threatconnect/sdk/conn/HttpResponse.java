package com.threatconnect.sdk.conn;

import java.nio.charset.Charset;

public class HttpResponse
{
	private String statusLine;
	private int statusCode;
	private byte[] entity;
	
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
	
	public byte[] getEntity()
	{
		return entity;
	}
	
	public String getEntityAsString()
	{
		return new String(entity, Charset.forName("UTF-8"));
	}
	
	public void setEntity(final byte[] entity)
	{
		this.entity = entity;
	}
}
