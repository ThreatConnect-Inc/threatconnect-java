package com.threatconnect.app.apps.service.api.mapping;

import com.threatconnect.app.apps.service.api.ApiService;
import com.threatconnect.app.apps.service.message.RunService;

import java.lang.annotation.Annotation;
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
		final ApiMethodPath apiMethodPath = new ApiMethodPath(runService.getMethod(), runService.getPath());
		Method method = getApiMap().get(apiMethodPath);
		if (null != method)
		{
			Class<?>[] paramTypes = method.getParameterTypes();
			Object[] args = new Object[paramTypes.length];
			Annotation[][] parameterAnnotations = method.getParameterAnnotations();
			
			//for each of the param types
			for (int i = 0; i < paramTypes.length; i++)
			{
				final Class<?> type = paramTypes[i];
				
				//get the annotations for this parameter
				PathParam pathParam = findAnnotation(parameterAnnotations[i], PathParam.class);
				
				//check to see if this type is a webhook event type
				if (RunService.class.isAssignableFrom(type))
				{
					args[i] = runService;
				}
				else if (null != pathParam)
				{
					ApiPath apiPath = apiMethodPath.getApiPath();
					
					//:TODO: map the api path variable
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
	
	private <A extends Annotation> A findAnnotation(final Annotation[] annotations, final Class<A> clazz)
	{
		//make sure the array is not nul
		if (null != annotations)
		{
			//for each of the annotations
			for (Annotation annotation : annotations)
			{
				if (annotation.getClass().equals(clazz))
				{
					return (A) annotation;
				}
			}
		}
		
		//no annotation was found
		return null;
	}
}
