package com.threatconnect.app.apps.db;

/**
 * @author Greg Marut
 */
public class DBReadException extends DBException
{
	public DBReadException(Exception cause)
	{
		super(cause);
	}
	
	public DBReadException(final String message)
	{
		super(message);
	}
}
