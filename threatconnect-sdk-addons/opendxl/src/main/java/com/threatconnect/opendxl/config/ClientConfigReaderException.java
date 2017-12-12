package com.threatconnect.opendxl.config;

/**
 * @author Greg Marut
 */
public class ClientConfigReaderException extends OpenDXLReaderException
{
	public ClientConfigReaderException(final String message)
	{
		super(message);
	}
	
	public ClientConfigReaderException(final String message, final Throwable cause)
	{
		super(message, cause);
	}
	
	public ClientConfigReaderException(final Throwable cause)
	{
		super(cause);
	}
}
