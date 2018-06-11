package com.threatconnect.opendxl.config;

/**
 * @author Greg Marut
 */
public class OpenDXLReaderException extends Exception
{
	public OpenDXLReaderException(final String message)
	{
		super(message);
	}
	
	public OpenDXLReaderException(final String message, final Throwable cause)
	{
		super(message, cause);
	}
	
	public OpenDXLReaderException(final Throwable cause)
	{
		super(cause);
	}
}
