package com.threatconnect.app.apps;

/**
 * @author Greg Marut
 */
public abstract class AppExecutor
{
	private AppConfig appConfig;
	
	public AppExecutor(final AppConfig appConfig)
	{
		this.appConfig = appConfig;
	}
	
	public AppConfig getAppConfig()
	{
		return appConfig;
	}
	
	public abstract int execute();
	
	public abstract Class<? extends App> getAppClassToExecute() throws ClassNotFoundException;
}
