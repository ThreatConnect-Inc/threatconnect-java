package com.threatconnect.apps.playbooks.test.app3;

import com.threatconnect.app.apps.App;
import com.threatconnect.app.apps.AppConfig;
import com.threatconnect.app.apps.AppExecutor;

/**
 * @author Greg Marut
 */
public class App3Main implements AppExecutor
{
	@Override
	public Class<? extends App> getAppClassToExecute(final AppConfig appConfig) throws ClassNotFoundException
	{
		return App3.class;
	}
	
	public static void main(String[] args)
	{
		//this is just a test case so do nothing
	}
}
