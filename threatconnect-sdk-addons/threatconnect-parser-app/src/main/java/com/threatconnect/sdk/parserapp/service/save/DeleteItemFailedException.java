package com.threatconnect.sdk.parserapp.service.save;

/**
 * Represents when the main item failed to delete
 * 
 * @author Greg Marut
 */
public class DeleteItemFailedException extends Exception
{
	private static final long serialVersionUID = 1L;
	
	public DeleteItemFailedException(final String message)
	{
		super(message);
	}
	
	public DeleteItemFailedException(final Exception cause)
	{
		super(cause);
	}
}
