package com.threatconnect.app.services.api.mapping;

import com.threatconnect.app.services.api.ApiService;
import com.threatconnect.app.services.message.ServiceItem;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ApiMapper
{
	private final Map<ApiMethodPath, Method> apiMap;
	private final List<ServiceItem> serviceItems;
	
	public ApiMapper(final Class<? extends ApiService> apiServiceClass)
	{
		HashMap<ApiMethodPath, Method> apiMap = new HashMap<ApiMethodPath, Method>();
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
					final ApiMethodPath apiMethodPath = new ApiMethodPath(apiMapping.method(), apiMapping.path());
					if (!apiMap.containsKey(apiMethodPath))
					{
						validatePathVariables(apiMethodPath.getApiPath(), method);
						
						//add this method to the apiMapping map
						apiMap.put(apiMethodPath, method);
						
						ServiceItem serviceItem = serviceItems.computeIfAbsent(apiMapping.path(), path -> {
							ServiceItem item = new ServiceItem();
							item.setMethods(new ArrayList<String>());
							item.setPath(path);
							item.setName(apiMapping.name());
							item.setDescription(apiMapping.description());
							return item;
						});
						
						serviceItem.getMethods().add(apiMapping.method().toString());
					}
					else
					{
						throw new RuntimeException("ApiMapping was already defined to another method: " + apiMap.get(apiMethodPath).getName());
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
		
		checkForMappingAmbiguity();
	}
	
	public Map.Entry<ApiMethodPath, Method> find(final com.threatconnect.app.services.api.mapping.Method method, final String path)
	{
		return find(method, path, this.apiMap);
	}
	
	private Map.Entry<ApiMethodPath, Method> find(final com.threatconnect.app.services.api.mapping.Method method, final String path,
		Map<ApiMethodPath, Method> apiMap)
	{
		//for each of the api mappings
		for (Map.Entry<ApiMethodPath, Method> entry : apiMap.entrySet())
		{
			//check to see if this api method and path match what was given
			if (entry.getKey().getMethod().equals(method) && entry.getKey().getApiPath().matches(path))
			{
				return entry;
			}
		}
		
		//no mapped path was found
		return null;
	}
	
	public List<ServiceItem> getServiceItems()
	{
		return serviceItems;
	}
	
	/**
	 * Checks to see if a path that resolves to this mapping would also resolve to another one. In this event, it is possible for
	 * a path to be ambiguous and we do not know which one is the correct one. Therefore, unique path specifications are required to prevent this.
	 */
	private void checkForMappingAmbiguity()
	{
		//make a mutable copy of the api map
		final Map<ApiMethodPath, Method> tempMap = new HashMap<ApiMethodPath, Method>(this.apiMap);
		
		//for every ApiMethodPath in the original map
		for (Map.Entry<ApiMethodPath, Method> entry : this.apiMap.entrySet())
		{
			//temporarily remove this object from the temp map so that we can search for all other mappings (excluding this one)
			tempMap.remove(entry.getKey());
			
			//build a path that would map to this path
			String path = entry.getKey().getApiPath().getPath();
			Matcher matcher = Pattern.compile(ApiPath.REGEX_VARIABLE).matcher(path);
			while (matcher.find())
			{
				path = path.replaceFirst(ApiPath.REGEX_VARIABLE, matcher.group(1));
			}
			
			//check to see if there are any other mappings that already exist that could map to this one
			Map.Entry<ApiMethodPath, Method> existing = find(entry.getKey().getMethod(), path, tempMap);
			if (null != existing)
			{
				throw new RuntimeException(
					String.format("Ambiguous api mapping detected. %s %s conflicts with an already mapped endpoint: %s %s",
						entry.getKey().getMethod(), entry.getKey().getApiPath().getPath(), existing.getKey().getMethod(),
						existing.getKey().getApiPath().getPath()));
			}
			
			//add this entry back to the temp map
			tempMap.put(entry.getKey(), entry.getValue());
		}
	}
	
	private void validatePathVariables(final ApiPath apiPath, final Method method)
	{
		Annotation[][] parameterAnnotations = method.getParameterAnnotations();
		
		//for each of the param types
		for (Annotation[] annotations : parameterAnnotations)
		{
			//get the annotations for this parameter
			PathVariable pathVariable = findAnnotation(annotations, PathVariable.class);
			if (null != pathVariable)
			{
				//check to see if this path does not contain a variable by this name
				if (!apiPath.containsVariable(pathVariable.value()))
				{
					throw new RuntimeException("Unable to resolve unknown variable \"" + pathVariable.value() + "\" in path: " + apiPath.getPath());
				}
			}
		}
	}
	
	protected <A extends Annotation> A findAnnotation(final Annotation[] annotations, final Class<A> clazz)
	{
		//make sure the array is not nul
		if (null != annotations)
		{
			//for each of the annotations
			for (Annotation annotation : annotations)
			{
				if (annotation.annotationType().equals(clazz))
				{
					return (A) annotation;
				}
			}
		}
		
		//no annotation was found
		return null;
	}
}
