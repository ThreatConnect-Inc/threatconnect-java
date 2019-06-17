package com.threatconnect.app.apps.service;

import com.threatconnect.app.apps.service.webhook.WebhookService;
import com.threatconnect.app.apps.service.webhook.mapping.Method;
import com.threatconnect.app.apps.service.webhook.mapping.Webhook;

public class SimpleWebhookService extends WebhookService
{
	@Webhook(uri = "/say/hi", method = Method.GET)
	public String sayHi()
	{
		return "Hi";
	}
	
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
	
	@Override
	public void onShutdown()
	{
	
	}
}
