package com.threatconnect.sdk.app.exception;

/**
 * Whenever this exception is thrown, the app will return a partial failure and log the message to messagetc
 *
 * @author Greg Marut
 */
public class PartialFailureException extends RuntimeException
{
	private static final long serialVersionUID = 1L;
	
	public PartialFailureException(final String userFriendlyMessage)
	{
		super(userFriendlyMessage);
	}
	
	public PartialFailureException(final String userFriendlyMessage, final Exception cause)
	{
		super(userFriendlyMessage, cause);
	}
}
