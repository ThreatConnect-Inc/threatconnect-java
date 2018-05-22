package com.threatconnect.sdk.app.exception;

import com.threatconnect.app.apps.App;

/**
 * @author Greg Marut
 */
public class MultipleAppClassFoundException extends RuntimeException
{
	public MultipleAppClassFoundException()
	{
		super("Multiple classes of type " + App.class.getName()
			+ " were found which could provide unexpected results. Please specify which app was intended to be run by setting the \"mainAppClass\" field in your install.json");
	}
}
