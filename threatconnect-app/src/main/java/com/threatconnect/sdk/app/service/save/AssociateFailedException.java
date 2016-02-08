package com.threatconnect.sdk.app.service.save;

public class AssociateFailedException extends Exception
{
	private static final long serialVersionUID = 1L;
	
	public AssociateFailedException(final Exception cause)
	{
		super(cause);
	}
}
