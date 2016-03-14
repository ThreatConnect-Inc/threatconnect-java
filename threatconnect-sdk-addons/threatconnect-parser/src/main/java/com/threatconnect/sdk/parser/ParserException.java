package com.threatconnect.sdk.parser;

public class ParserException extends Exception
{
	private static final long serialVersionUID = 1L;
	
	public ParserException(final Exception cause)
	{
		super(cause);
	}
	
	public ParserException(final String message)
	{
		super(message);
	}
}
