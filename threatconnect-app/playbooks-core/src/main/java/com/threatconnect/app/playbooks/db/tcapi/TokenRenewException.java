package com.threatconnect.app.playbooks.db.tcapi;

/**
 * @author Greg Marut
 */
public class TokenRenewException extends Exception
{
	public TokenRenewException(final String message)
	{
		super(message);
	}
	
	public TokenRenewException(final Throwable cause)
	{
		super(cause);
	}
}
