package com.threatconnect.sdk.app.exception;

public class AppInitializationException extends Exception
{
	public AppInitializationException(final String message)
	{
		super(message);
	}
	
	public AppInitializationException(final Throwable cause)
	{
		super(cause);
	}
}
