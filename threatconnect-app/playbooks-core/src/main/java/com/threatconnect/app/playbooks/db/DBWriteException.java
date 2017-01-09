package com.threatconnect.app.playbooks.db;

/**
 * @author Greg Marut
 */
public class DBWriteException extends DBException
{
	public DBWriteException(Exception cause)
	{
		super(cause);
	}
}
