package com.threatconnect.playbooks.service;

import com.threatconnect.app.services.Service;

import java.util.HashMap;
import java.util.Map;

public abstract class PlaybookService extends Service
{
	private final Map<Long, ServiceConfiguration> serviceConfigurations;
	
	private FireEventListener fireEventListener;
	
	public PlaybookService()
	{
		this.serviceConfigurations = new HashMap<Long, ServiceConfiguration>();
	}
	
	public final Map<Long, ServiceConfiguration> getServiceConfigurations()
	{
		return serviceConfigurations;
	}
	
	public final void createServiceConfiguration(final ServiceConfiguration serviceConfiguration)
	{
		serviceConfigurations.put(serviceConfiguration.getConfigId(), serviceConfiguration);
		onServiceConfigurationCreated(serviceConfiguration);
	}
	
	public final void updateServiceConfiguration(final ServiceConfiguration serviceConfiguration)
	{
		serviceConfigurations.put(serviceConfiguration.getConfigId(), serviceConfiguration);
		onServiceConfigurationUpdated(serviceConfiguration);
	}
	
	public final void deleteServiceConfiguration(final long configId)
	{
		ServiceConfiguration serviceConfiguration = serviceConfigurations.remove(configId);
		if (null != serviceConfiguration)
		{
			onServiceConfigurationDeleted(serviceConfiguration);
		}
	}
	
	public FireEventListener getFireEventListener()
	{
		return fireEventListener;
	}
	
	public void setFireEventListener(final FireEventListener fireEventListener)
	{
		this.fireEventListener = fireEventListener;
	}
	
	protected void fireEvent(final ServiceConfiguration serviceConfiguration)
	{
		//make sure the fire event listener is not null
		if (null != fireEventListener)
		{
			fireEventListener.fireEvent(serviceConfiguration);
		}
	}
	
	public abstract void onServiceConfigurationCreated(ServiceConfiguration serviceConfiguration);
	
	public abstract void onServiceConfigurationUpdated(ServiceConfiguration serviceConfiguration);
	
	public abstract void onServiceConfigurationDeleted(ServiceConfiguration serviceConfiguration);
}
