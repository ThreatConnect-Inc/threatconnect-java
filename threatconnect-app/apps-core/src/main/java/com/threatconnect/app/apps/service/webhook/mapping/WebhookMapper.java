package com.threatconnect.app.apps.service.webhook.mapping;

import com.threatconnect.app.apps.service.message.ServiceItem;
import com.threatconnect.app.apps.service.webhook.WebhookService;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WebhookMapper
{
	private final Map<String, Method> webhookMap;
	private final List<ServiceItem> serviceItems;
	
	public WebhookMapper(final Class<? extends WebhookService> webhookClass)
	{
		HashMap<String, Method> webhookMap = new HashMap<String, Method>();
		HashMap<String, ServiceItem> serviceItems = new HashMap<String, ServiceItem>();
		
		//for each of the methods
		for (Method method : webhookClass.getDeclaredMethods())
		{
			//check to see if there is an annotation
			Webhook webhook = method.getAnnotation(Webhook.class);
			if (null != webhook)
			{
				//make sure the method is public
				if (Modifier.isPublic(method.getModifiers()))
				{
					//make sure this key does not yet exist (prevent duplicate implementations)
					final String key = buildKey(webhook);
					if (!webhookMap.containsKey(key))
					{
						//add this method to the webhook map
						webhookMap.put(key, method);
						
						ServiceItem serviceItem = serviceItems.computeIfAbsent(webhook.uri(), uri -> {
							ServiceItem item = new ServiceItem();
							item.setMethods(new ArrayList<String>());
							item.setPath(uri);
							item.setName(webhook.name());
							item.setDescription(webhook.description());
							return item;
						});
						
						serviceItem.getMethods().add(webhook.method().toString());
					}
					else
					{
						throw new RuntimeException("Webhook was already defined to another method: " + webhookMap.get(key).getName());
					}
				}
				else
				{
					throw new RuntimeException("Webhook method is not public: " + method.getName());
				}
			}
		}
		
		this.webhookMap = Collections.unmodifiableMap(webhookMap);
		this.serviceItems = Collections.unmodifiableList(new ArrayList<ServiceItem>(serviceItems.values()));
	}
	
	public Map<String, Method> getWebhookMap()
	{
		return webhookMap;
	}
	
	public List<ServiceItem> getServiceItems()
	{
		return serviceItems;
	}
	
	protected String buildKey(final Webhook webhook)
	{
		return buildKey(webhook.method().toString(), webhook.uri());
	}
	
	protected String buildKey(final String method, final String uri)
	{
		return method.toUpperCase() + " " + uri.toLowerCase();
	}
}
