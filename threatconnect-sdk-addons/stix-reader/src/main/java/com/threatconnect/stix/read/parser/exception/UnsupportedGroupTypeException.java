package com.threatconnect.stix.read.parser.exception;

public class UnsupportedGroupTypeException extends Exception
{
	private static final long serialVersionUID = 1L;
	
	public UnsupportedGroupTypeException(final String message)
	{
		super(message);
	}
}
