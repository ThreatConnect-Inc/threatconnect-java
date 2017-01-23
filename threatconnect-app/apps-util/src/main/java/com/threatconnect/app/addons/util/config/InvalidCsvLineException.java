package com.threatconnect.app.addons.util.config;

/**
 * @author Greg Marut
 */
public class InvalidCsvLineException extends Exception
{
	public InvalidCsvLineException(final String message)
	{
		super(message);
	}
}
