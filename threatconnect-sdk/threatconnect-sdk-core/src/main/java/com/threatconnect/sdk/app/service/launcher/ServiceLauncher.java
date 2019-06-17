package com.threatconnect.sdk.app.service.launcher;

import com.threatconnect.app.apps.AppConfig;
import com.threatconnect.app.apps.service.Service;
import com.threatconnect.sdk.app.exception.AppInitializationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Greg Marut
 */
public abstract class ServiceLauncher
{
	private static final Logger logger = LoggerFactory.getLogger(ServiceLauncher.class);
	
	private final AppConfig appConfig;
	private final Service service;
	
	public ServiceLauncher(final AppConfig appConfig, final Class<? extends Service> serviceClass)
		throws AppInitializationException
	{
		if (null == appConfig)
		{
			throw new IllegalArgumentException("appConfig cannot be null.");
		}
		
		if (null == serviceClass)
		{
			throw new IllegalArgumentException("serviceClass cannot be null.");
		}
		
		this.appConfig = appConfig;
		
		try
		{
			// instantiate a new service class
			logger.trace("Instantiating service class: " + serviceClass.getName());
			this.service = serviceClass.newInstance();
		}
		catch (InstantiationException | IllegalAccessException e)
		{
			throw new AppInitializationException(e);
		}
	}
	
	public AppConfig getAppConfig()
	{
		return appConfig;
	}
	
	public Service getService()
	{
		return service;
	}
	
	public abstract void start();
}
