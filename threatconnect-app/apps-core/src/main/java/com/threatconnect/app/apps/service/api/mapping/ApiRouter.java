package com.threatconnect.app.apps.service.api.mapping;

import com.threatconnect.app.apps.service.api.ApiService;
import com.threatconnect.app.apps.service.message.RunService;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

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
		//look up the method and path to find a matching api endpoint
		final Map.Entry<ApiMethodPath, Method> entry =
			find(com.threatconnect.app.apps.service.api.mapping.Method.valueOf(runService.getMethod()), runService.getPath());
		if (null != entry)
		{
			final ApiMethodPath apiMethodPath = entry.getKey();
			final Method method = entry.getValue();
			Class<?>[] paramTypes = method.getParameterTypes();
			Object[] args = new Object[paramTypes.length];
			Annotation[][] parameterAnnotations = method.getParameterAnnotations();
			
			//for each of the param types
			for (int i = 0; i < paramTypes.length; i++)
			{
				final Class<?> type = paramTypes[i];
				
				//get the annotations for this parameter
				PathVariable pathVariable = findAnnotation(parameterAnnotations[i], PathVariable.class);
				
				//check to see if this type is a webhook event type
				if (RunService.class.isAssignableFrom(type))
				{
					args[i] = runService;
				}
				else if (null != pathVariable)
				{
					ApiPath apiPath = apiMethodPath.getApiPath();
					args[i] = apiPath.resolveVariable(runService.getPath(), pathVariable.value());
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
