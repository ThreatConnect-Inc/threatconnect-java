package com.threatconnect.sdk.app.exception;

/**
 * Whenever this exception is thrown in an app, the userFriendlyMessage will be written to
 * message.tc
 * 
 * @author Greg Marut
 */
public class TCMessageException extends RuntimeException
{
	private static final long serialVersionUID = 1L;
	
	public TCMessageException(final String userFriendlyMessage)
	{
		super(userFriendlyMessage);
	}
	
	public TCMessageException(final String userFriendlyMessage, final Exception cause)
	{
		super(userFriendlyMessage, cause);
	}
}
