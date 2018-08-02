package com.threatconnect.apps.playbooks.test;

import com.threatconnect.app.apps.App;
import com.threatconnect.app.apps.AppConfig;
import com.threatconnect.app.apps.AppLauncher;

/**
 * @author Greg Marut
 */
public class AppMainStub extends AppLauncher
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
	public void launch()
	{
		//do nothing
	}
	
	public static void main(String[] args)
	{
		//do nothing
	}
}
