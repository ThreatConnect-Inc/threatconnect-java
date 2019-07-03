package com.threatconnect.app.playbooks.app.service.webhook;

import com.threatconnect.app.apps.service.message.WebHookEvent;
import com.threatconnect.app.playbooks.app.service.FireEventListener;
import com.threatconnect.app.playbooks.app.service.PlaybookService;
import com.threatconnect.app.playbooks.app.service.ServiceConfiguration;

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
	public abstract void onWebhookEvent(WebHookEvent webHookEvent, Collection<ServiceConfiguration> serviceConfigurations,
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
