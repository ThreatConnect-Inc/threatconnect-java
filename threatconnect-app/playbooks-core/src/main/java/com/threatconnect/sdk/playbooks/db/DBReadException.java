package com.threatconnect.sdk.playbooks.db;

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
