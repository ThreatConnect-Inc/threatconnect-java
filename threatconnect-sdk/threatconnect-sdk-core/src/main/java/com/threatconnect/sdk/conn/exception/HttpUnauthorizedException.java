package com.threatconnect.sdk.conn.exception;

public class HttpUnauthorizedException extends HttpException
{
	private static final long serialVersionUID = 1L;
	
	public HttpUnauthorizedException(final String message)
	{
		super(message);
	}
}
