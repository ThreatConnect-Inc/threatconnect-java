package com.threatconnect.app.apps;

public abstract class AppLauncher<A>
{
	private final AppConfig appConfig;
	
	public AppLauncher(final AppConfig appConfig)
	{
		this.appConfig = appConfig;
	}
	
	/**
	 * returns the app classes that will be instantiated and executed
	 *
	 * @return
	 * @throws ClassNotFoundException
	 */
	public abstract Class<? extends A> getAppClassToExecute() throws ClassNotFoundException;
	
	/**
	 * Launch the application
	 *
	 * @throws Exception
	 */
	public abstract void launch() throws Exception;
	
	public AppConfig getAppConfig()
	{
		return appConfig;
	}
}
