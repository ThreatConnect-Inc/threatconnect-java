package com.threatconnect.app.playbooks.db;

/**
 * @author Greg Marut
 */
public class DBReadException extends DBException
{
	public DBReadException(Exception cause)
	{
		super(cause);
	}
}
