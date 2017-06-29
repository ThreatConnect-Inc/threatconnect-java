package com.threatconnect.app.playbooks.db;

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
