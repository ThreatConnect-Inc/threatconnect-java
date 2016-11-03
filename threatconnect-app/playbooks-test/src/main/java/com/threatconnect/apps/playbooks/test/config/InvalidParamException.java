package com.threatconnect.apps.playbooks.test.config;

/**
 * @author Greg Marut
 */
public class InvalidParamException extends RuntimeException
{
	public InvalidParamException(final String message)
	{
		super(message);
	}
}
