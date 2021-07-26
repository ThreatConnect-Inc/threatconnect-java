package com.threatconnect.app.execution.converter;

/**
 * @author Greg Marut
 */
public class ConversionException extends Exception
{
	public ConversionException(final String message)
	{
		super(message);
	}
	
	public ConversionException(Throwable cause)
	{
		super(cause);
	}
}
