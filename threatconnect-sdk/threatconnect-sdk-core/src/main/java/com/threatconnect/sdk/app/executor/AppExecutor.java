package com.threatconnect.sdk.app.executor;

import com.threatconnect.app.apps.App;
import com.threatconnect.app.apps.AppConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Greg Marut
 */
public abstract class AppExecutor
{
	private static final Logger logger = LoggerFactory.getLogger(AppExecutor.class);
	
	private final AppConfig appConfig;
	private final Class<? extends App> appClass;
	
	public AppExecutor(final AppConfig appConfig, final Class<? extends App> appClass)
	{
		if(null == appConfig)
		{
			throw new IllegalArgumentException("appConfig cannot be null.");
		}
		
		if(null == appClass)
		{
			throw new IllegalArgumentException("appClass cannot be null.");
		}
		
		this.appConfig = appConfig;
		this.appClass = appClass;
	}
	
	public AppConfig getAppConfig()
	{
		return appConfig;
	}
	
	public Class<? extends App> getAppClass()
	{
		return appClass;
	}
	
	protected App instantiateApp() throws IllegalAccessException, InstantiationException
	{
		// instantiate a new app class
		logger.trace("Instantiating app class: " + getAppClass().getName());
		return getAppClass().newInstance();
	}
	
	public abstract int execute();
}
