package com.threatconnect.app.apps.db;

/**
 * @author Greg Marut
 */
public class DBWriteException extends DBException
{
	public DBWriteException(final Exception cause)
	{
		super(cause);
	}
	
	public DBWriteException(final String message)
	{
		super(message);
	}
}
