package com.threatconnect.sdk.blueprints.db;

/**
 * @author Greg Marut
 */
public class DBException extends Exception
{
	public DBException(Exception cause)
	{
		super(cause);
	}
}
