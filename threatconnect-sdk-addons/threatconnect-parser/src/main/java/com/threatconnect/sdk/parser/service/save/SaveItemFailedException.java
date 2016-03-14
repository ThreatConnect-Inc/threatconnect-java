package com.threatconnect.sdk.parser.service.save;

/**
 * Represents when the main item failed to save
 * 
 * @author Greg Marut
 */
public class SaveItemFailedException extends Exception
{
	private static final long serialVersionUID = 1L;
	
	public SaveItemFailedException(final String message)
	{
		super(message);
	}
	
	public SaveItemFailedException(final Exception cause)
	{
		super(cause);
	}
}
