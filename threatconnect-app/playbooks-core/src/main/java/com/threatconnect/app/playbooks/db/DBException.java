package com.threatconnect.app.playbooks.db;

/**
 * @author Greg Marut
 */
public class DBException extends Exception
{
	public DBException(final Exception cause)
	{
		super(cause);
	}
	
	public DBException(final String message)
	{
		super(message);
	}
}
