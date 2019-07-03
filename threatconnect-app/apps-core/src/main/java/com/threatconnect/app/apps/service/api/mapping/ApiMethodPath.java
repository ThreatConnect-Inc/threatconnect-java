package com.threatconnect.app.apps.service.api.mapping;

import java.util.Objects;

public class ApiMethodPath
{
	public final Method method;
	public final ApiPath apiPath;
	
	public ApiMethodPath(final String method, final String path)
	{
		this(Method.valueOf(method.toUpperCase()), path);
	}
	
	public ApiMethodPath(final Method method, final String path)
	{
		this(method, new ApiPath(path));
	}
	
	public ApiMethodPath(final Method method, final ApiPath apiPath)
	{
		this.method = method;
		this.apiPath = apiPath;
	}
	
	public Method getMethod()
	{
		return method;
	}
	
	public ApiPath getApiPath()
	{
		return apiPath;
	}
	
	@Override
	public boolean equals(final Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (o == null || getClass() != o.getClass())
		{
			return false;
		}
		
		final ApiMethodPath apiMethodPath = (ApiMethodPath) o;
		return method == apiMethodPath.method &&
			apiPath.equals(apiMethodPath.apiPath);
	}
	
	@Override
	public int hashCode()
	{
		return Objects.hash(method, apiPath);
	}
}
