package com.threatconnect.sdk.app.service.launcher;

import com.threatconnect.app.apps.AppConfig;
import com.threatconnect.app.apps.service.FireEventListener;
import com.threatconnect.app.apps.service.Service;

/**
 * @author Greg Marut
 */
public abstract class ServiceLauncher<S extends Service>
{
	private final AppConfig appConfig;
	private final S service;
	
	public ServiceLauncher(final AppConfig appConfig, final S service)
	{
		if (null == appConfig)
		{
			throw new IllegalArgumentException("appConfig cannot be null.");
		}
		
		if (null == service)
		{
			throw new IllegalArgumentException("service cannot be null.");
		}
		
		this.appConfig = appConfig;
		this.service = service;
		this.service.setFireEventListener(createFireEventListener());
	}
	
	public AppConfig getAppConfig()
	{
		return appConfig;
	}
	
	public S getService()
	{
		return service;
	}
	
	public abstract void start();
	
	protected abstract FireEventListener createFireEventListener();
}
