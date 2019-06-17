package com.threatconnect.app.apps.service.api.mapping;

import com.threatconnect.app.apps.service.message.ServiceItem;
import com.threatconnect.app.apps.service.api.ApiService;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApiMapper
{
	private final Map<String, Method> apiMap;
	private final List<ServiceItem> serviceItems;
	
	public ApiMapper(final Class<? extends ApiService> apiServiceClass)
	{
		HashMap<String, Method> apiMap = new HashMap<String, Method>();
		HashMap<String, ServiceItem> serviceItems = new HashMap<String, ServiceItem>();
		
		//for each of the methods
		for (Method method : apiServiceClass.getDeclaredMethods())
		{
			//check to see if there is an annotation
			ApiMapping apiMapping = method.getAnnotation(ApiMapping.class);
			if (null != apiMapping)
			{
				//make sure the method is public
				if (Modifier.isPublic(method.getModifiers()))
				{
					//make sure this key does not yet exist (prevent duplicate implementations)
					final String key = buildKey(apiMapping);
					if (!apiMap.containsKey(key))
					{
						//add this method to the apiMapping map
						apiMap.put(key, method);
						
						ServiceItem serviceItem = serviceItems.computeIfAbsent(apiMapping.uri(), uri -> {
							ServiceItem item = new ServiceItem();
							item.setMethods(new ArrayList<String>());
							item.setPath(uri);
							item.setName(apiMapping.name());
							item.setDescription(apiMapping.description());
							return item;
						});
						
						serviceItem.getMethods().add(apiMapping.method().toString());
					}
					else
					{
						throw new RuntimeException("ApiMapping was already defined to another method: " + apiMap.get(key).getName());
					}
				}
				else
				{
					throw new RuntimeException("ApiMapping method is not public: " + method.getName());
				}
			}
		}
		
		this.apiMap = Collections.unmodifiableMap(apiMap);
		this.serviceItems = Collections.unmodifiableList(new ArrayList<ServiceItem>(serviceItems.values()));
	}
	
	public Map<String, Method> getApiMap()
	{
		return apiMap;
	}
	
	public List<ServiceItem> getServiceItems()
	{
		return serviceItems;
	}
	
	protected String buildKey(final ApiMapping apiMapping)
	{
		return buildKey(apiMapping.method().toString(), apiMapping.uri());
	}
	
	protected String buildKey(final String method, final String uri)
	{
		return method.toUpperCase() + " " + uri.toLowerCase();
	}
}
