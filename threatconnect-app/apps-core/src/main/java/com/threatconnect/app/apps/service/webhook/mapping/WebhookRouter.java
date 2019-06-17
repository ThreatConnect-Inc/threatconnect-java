package com.threatconnect.app.apps.service.webhook.mapping;

import com.threatconnect.app.apps.service.message.WebHookEvent;
import com.threatconnect.app.apps.service.webhook.WebhookService;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class WebhookRouter extends WebhookMapper
{
	private final WebhookService webhookService;
	
	public WebhookRouter(final WebhookService webhookService)
	{
		super(webhookService.getClass());
		this.webhookService = webhookService;
	}
	
	public Object routeWebhookEvent(final WebHookEvent webHookEvent)
		throws WebhookNotFoundException, InvocationTargetException, IllegalAccessException
	{
		final String key = buildKey(webHookEvent.getMethod(), webHookEvent.getPath());
		Method method = getWebhookMap().get(key);
		if (null != method)
		{
			Class<?>[] paramTypes = method.getParameterTypes();
			Object[] args = new Object[paramTypes.length];
			
			//for each of the param types
			for (int i = 0; i < paramTypes.length; i++)
			{
				final Class<?> type = paramTypes[i];
				
				//check to see if this type is a webhook event type
				if (WebHookEvent.class.isAssignableFrom(type))
				{
					args[i] = webHookEvent;
				}
				else
				{
					throw new RuntimeException("Unable to map unknown parameter: " + type.getName());
				}
			}
			
			return method.invoke(webhookService, args);
		}
		else
		{
			throw new WebhookNotFoundException();
		}
	}
}
