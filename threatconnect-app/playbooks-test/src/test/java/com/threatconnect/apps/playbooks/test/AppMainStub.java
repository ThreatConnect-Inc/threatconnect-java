package com.threatconnect.apps.playbooks.test;

import com.threatconnect.app.apps.App;
import com.threatconnect.app.apps.AppConfig;
import com.threatconnect.app.apps.AppExecutor;
import com.threatconnect.app.apps.ExitStatus;

/**
 * @author Greg Marut
 */
public class AppMainStub extends AppExecutor
{
	public AppMainStub(final AppConfig appConfig)
	{
		super(appConfig);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public Class<? extends App> getAppClassToExecute() throws ClassNotFoundException
	{
		return (Class<? extends App>) Class.forName(getAppConfig().getTcMainAppClass());
	}
	
	@Override
	public ExitStatus execute()
	{
		//do nothing
		return ExitStatus.Success;
	}
	
	public static void main(String[] args)
	{
		//do nothing
	}
}
