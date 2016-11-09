package com.threatconnect.app.playbooks.content.accumulator;

/**
 * @author Greg Marut
 */
public class ContentException extends Exception
{
	public ContentException(String message)
	{
		super(message);
	}

	public ContentException(Throwable cause)
	{
		super(cause);
	}
}
