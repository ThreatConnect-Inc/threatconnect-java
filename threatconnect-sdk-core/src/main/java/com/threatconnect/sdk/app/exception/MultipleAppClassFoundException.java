package com.threatconnect.sdk.app.exception;

import com.threatconnect.sdk.app.App;
import com.threatconnect.sdk.app.AppMain;
import com.threatconnect.sdk.app.MultiAppMain;

/**
 * @author Greg Marut
 */
public class MultipleAppClassFoundException extends RuntimeException
{
	public MultipleAppClassFoundException()
	{
		super("Multiple classes of type " + App.class.getName()
			+ " were found which could provide unexpected results. If there are multiple classes of type " + App.class
			.getName() + " in the classpath, please extend " + AppMain.class.getName()
			+ " to ensure that the correct app is called. If your intention is to execute multiple apps with one execution, please use the "
			+ MultiAppMain.class.getName() + " instead.");
	}
}
