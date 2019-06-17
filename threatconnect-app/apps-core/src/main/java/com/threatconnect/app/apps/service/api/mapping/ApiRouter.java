package com.threatconnect.app.apps.service.api.mapping;

import com.threatconnect.app.apps.service.message.RunService;
import com.threatconnect.app.apps.service.api.ApiService;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ApiRouter extends ApiMapper
{
	private final ApiService apiService;
	
	public ApiRouter(final ApiService apiService)
	{
		super(apiService.getClass());
		this.apiService = apiService;
	}
	
	public Object routeApiEvent(final RunService runService)
		throws ApiNotFoundException, InvocationTargetException, IllegalAccessException
	{
		final String key = buildKey(runService.getMethod(), runService.getPath());
		Method method = getApiMap().get(key);
		if (null != method)
		{
			Class<?>[] paramTypes = method.getParameterTypes();
			Object[] args = new Object[paramTypes.length];
			
			//for each of the param types
			for (int i = 0; i < paramTypes.length; i++)
			{
				final Class<?> type = paramTypes[i];
				
				//check to see if this type is a webhook event type
				if (RunService.class.isAssignableFrom(type))
				{
					args[i] = runService;
				}
				else
				{
					throw new RuntimeException("Unable to map unknown parameter: " + type.getName());
				}
			}
			
			return method.invoke(apiService, args);
		}
		else
		{
			throw new ApiNotFoundException();
		}
	}
}
