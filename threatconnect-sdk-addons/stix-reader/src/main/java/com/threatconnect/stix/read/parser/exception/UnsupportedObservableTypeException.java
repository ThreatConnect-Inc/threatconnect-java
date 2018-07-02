package com.threatconnect.stix.read.parser.exception;

public class UnsupportedObservableTypeException extends Exception
{
	private static final long serialVersionUID = 1L;
	
	public UnsupportedObservableTypeException(final String message)
	{
		super(message);
	}
	
	public UnsupportedObservableTypeException(final String message, final Throwable cause)
	{
		super(message, cause);
	}
}
