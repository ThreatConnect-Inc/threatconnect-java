package com.threatconnect.sdk.parser.service.save;

public class AssociateFailedException extends Exception
{
	private static final long serialVersionUID = 1L;
	
	public AssociateFailedException(final Exception cause)
	{
		super(cause);
	}
}
