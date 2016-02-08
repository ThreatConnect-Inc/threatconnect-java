package com.threatconnect.sdk.app.util;

public class InvalidURLException extends Exception
{
	private static final long serialVersionUID = 1L;
	
	public InvalidURLException(final Exception cause)
	{
		super(cause);
	}
	
	public InvalidURLException(final String message)
	{
		super(message);
	}
}
