package com.threatconnect.sdk.parserapp.util.attribute;

public class InvalidAttributeDefinitionFileException extends RuntimeException
{
	private static final long serialVersionUID = 1L;
	
	public InvalidAttributeDefinitionFileException(final Exception cause)
	{
		super(cause);
	}
	
	public InvalidAttributeDefinitionFileException(final String message)
	{
		super(message);
	}
}
