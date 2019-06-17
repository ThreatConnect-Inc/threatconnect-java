package com.threatconnect.sdk.app.service.launcher;

import com.threatconnect.app.apps.AppConfig;
import com.threatconnect.app.apps.service.message.CommandMessage;
import com.threatconnect.app.apps.service.message.WebHookEvent;
import com.threatconnect.app.apps.service.api.ApiService;
import com.threatconnect.app.apps.service.api.mapping.ApiNotFoundException;
import com.threatconnect.app.apps.service.api.mapping.ApiRouter;
import com.threatconnect.sdk.app.exception.AppInitializationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;

public class WebhookServiceLauncher extends DefaultServiceLauncher<ApiService>
{
	private static final Logger logger = LoggerFactory.getLogger(WebhookServiceLauncher.class);
	
	private final ApiRouter webhookRouter;
	
	public WebhookServiceLauncher(final AppConfig appConfig, final ApiService apiService) throws AppInitializationException
	{
		super(appConfig, apiService);
		
		this.webhookRouter = new ApiRouter(apiService);
	}
	
	@Override
	protected void onMessageReceived(final CommandMessage.Command command, final String message)
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
		try
		{
			//route this webhook event to the correct method
			Object result = webhookRouter.routeWebhookEvent(webHookEvent);
			
			
		}
		catch (ApiNotFoundException | InvocationTargetException | IllegalAccessException e)
		{
			logger.error(e.getMessage(), e);
		}
	}
}
