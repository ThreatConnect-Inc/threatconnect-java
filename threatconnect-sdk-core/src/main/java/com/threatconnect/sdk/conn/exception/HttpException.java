package com.threatconnect.sdk.conn.exception;

public class HttpException extends RuntimeException
{
	private static final long serialVersionUID = 1L;
	
	public HttpException()
	{
	}
	
	public HttpException(final String message)
	{
		super(message);
	}
	
	public
	HttpException(final Throwable cause)
	{
		super(cause);
	}
}
