package com.threatconnect.playbooks.service.webhook;

import com.threatconnect.app.services.message.WebhookEvent;
import com.threatconnect.playbooks.service.FireEventListener;
import com.threatconnect.playbooks.service.PlaybookService;
import com.threatconnect.playbooks.service.ServiceConfiguration;

import java.util.Collection;

public abstract class WebhookService extends PlaybookService
{
	/**
	 * Called whenever a webhook event is received. The implementing WebhookService is responsible for checking all service configurations
	 * and firing events for each configuration that matches the critiera.
	 *
	 * @param webHookEvent
	 * @param serviceConfigurations
	 */
	public abstract void onWebhookEvent(WebhookEvent webHookEvent, Collection<ServiceConfiguration> serviceConfigurations,
		FireEventListener fireEventListener);
	
	@Override
	public void onServiceConfigurationCreated(final ServiceConfiguration serviceConfiguration)
	{
	
	}
	
	@Override
	public void onServiceConfigurationUpdated(final ServiceConfiguration serviceConfiguration)
	{
	
	}
	
	@Override
	public void onServiceConfigurationDeleted(final ServiceConfiguration serviceConfiguration)
	{
	
	}
}
