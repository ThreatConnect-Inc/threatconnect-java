package com.threatconnect.app.apps.service.api.mapping;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ApiPath
{
	public static final String SEPARATOR = "/";
	
	public static final String REGEX_VARIABLE_CONTENT = "([a-zA-Z0-9%_\\-]+)";
	public static final String REGEX_VARIABLE = "\\{" + REGEX_VARIABLE_CONTENT + "\\}";
	
	private final String path;
	private final String pathRegex;
	private final Pattern pathPattern;
	private final Map<String, Integer> variableGroupMap;
	
	public ApiPath(final String path)
	{
		//prepend the leading slash if necessary
		this.path = normalize(path);
		this.pathRegex = this.path.replaceAll(Pattern.quote("/"), "\\\\/").replaceAll(REGEX_VARIABLE, REGEX_VARIABLE_CONTENT);
		this.pathPattern = Pattern.compile(pathRegex);
		this.variableGroupMap = new HashMap<String, Integer>();
		
		Matcher matcher = Pattern.compile(REGEX_VARIABLE).matcher(path);
		int group = 1;
		while (matcher.find())
		{
			this.variableGroupMap.put(matcher.group(1), group++);
		}
	}
	
	public boolean matches(final String path)
	{
		return pathPattern.matcher(path).matches();
	}
	
	public String resolveVariable(final String path, final String variable)
	{
		//find the group for this variable
		Integer group = variableGroupMap.get(variable);
		if (null != group)
		{
			//match the pattern to this path and extract the group
			Matcher matcher = pathPattern.matcher(path);
			if (matcher.find())
			{
				return matcher.group(group);
			}
		}
		
		//the variable was not found
		return null;
	}
	
	public boolean containsVariables()
	{
		return !variableGroupMap.isEmpty();
	}
	
	public ApiMethodPath toApiMethodPath(final Method method)
	{
		return new ApiMethodPath(method, this);
	}
	
	public String getPath()
	{
		return path;
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
		
		final ApiPath apiPath = (ApiPath) o;
		return pathRegex.equals(apiPath.pathRegex);
	}
	
	@Override
	public int hashCode()
	{
		return Objects.hash(pathRegex);
	}
	
	public static String normalize(final String path)
	{
		String result = path.startsWith(SEPARATOR) ? path : SEPARATOR + path;
		if (result.endsWith(SEPARATOR))
		{
			return result.substring(0, result.length() - 1);
		}
		else
		{
			return result;
		}
	}
}
