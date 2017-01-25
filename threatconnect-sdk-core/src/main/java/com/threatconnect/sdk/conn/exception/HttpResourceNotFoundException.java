package com.threatconnect.sdk.conn.exception;

public class HttpResourceNotFoundException extends HttpException
{
	private static final long serialVersionUID = 1L;
	
	public HttpResourceNotFoundException(final String message)
	{
		super(message);
	}
}
