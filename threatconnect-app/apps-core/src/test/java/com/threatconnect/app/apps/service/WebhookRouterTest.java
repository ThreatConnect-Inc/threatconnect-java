package com.threatconnect.app.apps.service;

import com.threatconnect.app.apps.service.message.WebHookEvent;
import com.threatconnect.app.apps.service.webhook.mapping.WebhookNotFoundException;
import com.threatconnect.app.apps.service.webhook.mapping.WebhookRouter;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

public class WebhookRouterTest
{
	@Test
	public void simpleTest() throws IllegalAccessException, InvocationTargetException, WebhookNotFoundException
	{
		SimpleWebhookService simpleWebhookService = new SimpleWebhookService();
		WebhookRouter webhookRouter = new WebhookRouter(simpleWebhookService);
		
		WebHookEvent webHookEvent = new WebHookEvent();
		webHookEvent.setMethod("GET");
		webHookEvent.setPath("/say/hi");
		
		Object result = webhookRouter.routeWebhookEvent(webHookEvent);
		Assert.assertEquals(result, "Hi");
	}
}
