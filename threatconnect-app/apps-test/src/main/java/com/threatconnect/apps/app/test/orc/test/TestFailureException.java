package com.threatconnect.apps.app.test.orc.test;

/**
 * @author Greg Marut
 */
public class TestFailureException extends RuntimeException
{
	public TestFailureException(final Throwable cause)
	{
		super(cause);
	}
}
