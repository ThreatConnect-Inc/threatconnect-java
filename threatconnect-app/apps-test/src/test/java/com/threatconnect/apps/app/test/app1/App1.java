package com.threatconnect.apps.app.test.app1;

import com.threatconnect.app.apps.App;
import com.threatconnect.app.apps.AppConfig;
import com.threatconnect.app.apps.ExitStatus;
import org.junit.Assert;

/**
 * @author Greg Marut
 */
public class App1 extends App
{
	public static final String PARAM_FIRST_NAME = "first_name";
	public static final String PARAM_LAST_NAME = "last_name";
	
	@Override
	public ExitStatus execute(final AppConfig appConfig) throws Exception
	{
		//read the first and last name
		final String firstName = appConfig.getString(PARAM_FIRST_NAME);
		final String lastName = appConfig.getString(PARAM_LAST_NAME);
		
		Assert.assertEquals("Greg", firstName);
		Assert.assertEquals("Marut", lastName);
		
		writeMessageTc("Hello " + firstName + " " + lastName);
		
		return ExitStatus.Success;
	}
	
	@Override
	public String getLogFilename()
	{
		return "App1.log";
	}
}
