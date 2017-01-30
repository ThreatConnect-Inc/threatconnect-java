package com.threatconnect.apps.playbooks.test.config;

/**
 * @author Greg Marut
 */
public class PlaybooksConfigurationException extends RuntimeException
{
	public PlaybooksConfigurationException(final String message)
	{
		super(message);
	}
	
	public PlaybooksConfigurationException(final Throwable cause)
	{
		super(cause);
	}
}
