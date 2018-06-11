package com.threatconnect.app.addons.util.config;

/**
 * @author Greg Marut
 */
public class InvalidFileException extends Exception
{
	public InvalidFileException(final String message)
	{
		super(message);
	}
	
	public InvalidFileException(final String message, final Throwable cause)
	{
		super(message, cause);
	}
}
