package com.threatconnect.sdk.app.exception;

import com.threatconnect.app.apps.App;

/**
 * @author Greg Marut
 */
public class NoAppClassFoundException extends RuntimeException
{
	public NoAppClassFoundException()
	{
		super("No apps were found. Please make sure your app is a subclass of " + App.class.getName());
	}
}
