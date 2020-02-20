package com.threatconnect.app.services;

import com.threatconnect.app.apps.AppConfig;

public abstract class Service
{
	// holds the reference to the app config object
	private AppConfig appConfig;
	
	public void init(final AppConfig appConfig)
	{
		this.appConfig = appConfig;
	}
	
	public AppConfig getAppConfig()
	{
		return appConfig;
	}
	
	public abstract void onStartUp();
	
	public abstract void onShutdown();
}
