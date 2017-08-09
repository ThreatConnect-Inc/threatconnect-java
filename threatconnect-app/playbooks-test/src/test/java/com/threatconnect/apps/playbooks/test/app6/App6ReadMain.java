package com.threatconnect.apps.playbooks.test.app6;

import com.threatconnect.app.apps.App;
import com.threatconnect.app.apps.AppConfig;
import com.threatconnect.app.apps.AppExecutor;

/**
 * @author Greg Marut
 */
public class App6ReadMain implements AppExecutor
{
	@Override
	public Class<? extends App> getAppClassToExecute(final AppConfig appConfig) throws ClassNotFoundException
	{
		return App6Read.class;
	}
	
	public static void main(String[] args)
	{
		//this is just a test case so do nothing
	}
}
