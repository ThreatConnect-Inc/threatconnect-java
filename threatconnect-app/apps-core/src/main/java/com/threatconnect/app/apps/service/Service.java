package com.threatconnect.app.apps.service;

public abstract class Service
{
	public abstract void onConfigurationCreated(ServiceConfiguration serviceConfiguration);
	public abstract void onConfigurationUpdated(ServiceConfiguration serviceConfiguration);
	public abstract void onConfigurationDeleted(ServiceConfiguration serviceConfiguration);
	
	public abstract void onShutdown();
}
