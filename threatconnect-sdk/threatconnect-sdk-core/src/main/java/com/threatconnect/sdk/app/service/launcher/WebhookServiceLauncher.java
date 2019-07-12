package com.threatconnect.sdk.app.service.launcher;

import com.threatconnect.app.apps.AppConfig;
import com.threatconnect.app.services.message.CommandMessage;
import com.threatconnect.app.services.message.CommandType;
import com.threatconnect.app.services.message.WebHookEvent;
import com.threatconnect.playbooks.service.ServiceConfiguration;
import com.threatconnect.playbooks.service.webhook.WebhookService;
import com.threatconnect.sdk.app.exception.AppInitializationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

public class WebhookServiceLauncher extends PlaybookServiceLauncher<WebhookService>
{
	private static final Logger logger = LoggerFactory.getLogger(WebhookServiceLauncher.class);
	
	public WebhookServiceLauncher(final AppConfig appConfig, final WebhookService webhookService) throws AppInitializationException
	{
		super(appConfig, webhookService);
	}
	
	@Override
	protected void onMessageReceived(final CommandType command, final String message)
	{
		switch (command)
		{
			case WebHookEvent:
				handleWebhookEvent(gson.fromJson(message, WebHookEvent.class));
				break;
			default:
				//forward the message up the chain
				super.onMessageReceived(command, message);
				break;
		}
	}
	
	private void handleWebhookEvent(final WebHookEvent webHookEvent)
	{
		Collection<ServiceConfiguration> serviceConfigurations = getService().getServiceConfigurations().values();
		if (!serviceConfigurations.isEmpty())
		{
			getService().onWebhookEvent(webHookEvent, serviceConfigurations, getService().getFireEventListener());
		}
		else
		{
			logger.warn("Webhook Received with no service configurations. Skipping.");
		}
	}
}
