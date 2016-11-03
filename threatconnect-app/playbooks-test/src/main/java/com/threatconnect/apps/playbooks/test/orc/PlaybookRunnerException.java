package com.threatconnect.apps.playbooks.test.orc;

/**
 * @author Greg Marut
 */
public class PlaybookRunnerException extends RuntimeException
{
	public PlaybookRunnerException(final Throwable cause)
	{
		super(cause);
	}
	
	public PlaybookRunnerException(final String message)
	{
		super(message);
	}
}
