package com.threatconnect.apps.playbooks.test.app5;

import com.threatconnect.app.apps.App;
import com.threatconnect.app.apps.AppConfig;
import com.threatconnect.app.apps.AppExecutor;

/**
 * @author Greg Marut
 */
public class App5Main implements AppExecutor
{
	@Override
	public Class<? extends App> getAppClassToExecute(final AppConfig appConfig) throws ClassNotFoundException
	{
		return App5.class;
	}
	
	public static void main(String[] args)
	{
		//this is just a test case so do nothing
	}
}
