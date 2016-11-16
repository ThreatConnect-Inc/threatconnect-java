package com.threatconnect.apps.app.test.orc;

/**
 * @author Greg Marut
 */
public class AppRunnerException extends RuntimeException
{
	public AppRunnerException(final Throwable cause)
	{
		super(cause);
	}
	
	public AppRunnerException(final String message)
	{
		super(message);
	}
}
