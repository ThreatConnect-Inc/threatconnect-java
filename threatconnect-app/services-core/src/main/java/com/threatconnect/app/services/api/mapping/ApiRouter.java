package com.threatconnect.app.services.api.mapping;

import com.threatconnect.app.services.api.ApiService;
import com.threatconnect.app.services.message.NameValuePair;
import com.threatconnect.app.services.message.RunService;
import com.threatconnect.app.services.message.RunServiceAcknowledgedMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ApiRouter extends ApiMapper
{
	private static final Logger logger = LoggerFactory.getLogger(ApiRouter.class);
	
	private final ApiService apiService;
	
	public ApiRouter(final ApiService apiService)
	{
		super(apiService.getClass());
		this.apiService = apiService;
	}
	
	public Object routeApiEvent(final RunService runService, final RunServiceAcknowledgedMessage response)
		throws ApiNotFoundException, InvocationTargetException, IllegalAccessException
	{
		//look up the method and path to find a matching api endpoint
		final Map.Entry<ApiMethodPath, Method> entry =
			find(com.threatconnect.app.services.api.mapping.Method.valueOf(runService.getMethod()), runService.getPath());
		if (null != entry)
		{
			final ApiMethodPath apiMethodPath = entry.getKey();
			final Method method = entry.getValue();
			Class<?>[] paramTypes = method.getParameterTypes();
			Object[] args = new Object[paramTypes.length];
			Annotation[][] parameterAnnotations = method.getParameterAnnotations();
			
			//extract the api mapping annotation
			ApiMapping apiMapping = method.getAnnotation(ApiMapping.class);
			
			//convert the headers to a list of name/value pairs
			List<NameValuePair<String, String>> headers = Arrays.stream(apiMapping.headers()).map(h -> {
				NameValuePair<String, String> nvp = new NameValuePair<String, String>();
				nvp.setName(h.key());
				nvp.setValue(h.value());
				return nvp;
			}).collect(Collectors.toList());
			response.setHeaders(headers);
			
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
				else if (RunServiceAcknowledgedMessage.class.isAssignableFrom(type))
				{
					args[i] = response;
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
			logger.warn("Unmapped request: " + runService.getPath());
			throw new ApiNotFoundException();
		}
	}
}
