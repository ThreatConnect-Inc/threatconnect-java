package com.threatconnect.sdk.playbooks.db;

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
