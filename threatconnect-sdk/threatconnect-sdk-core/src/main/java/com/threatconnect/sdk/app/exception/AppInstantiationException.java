package com.threatconnect.sdk.app.exception;

public class AppInstantiationException extends Exception
{
	private static final long serialVersionUID = 1L;
	
	public AppInstantiationException(final Exception cause, final Class<?> clazz)
	{
		super(buildErrorMessage(clazz), cause);
	}
	
	private static String buildErrorMessage(final Class<?> clazz)
	{
		return "Unable to instantiate " + clazz.getName() + ". Does it contain a public no-argument constructor?";
	}
}
