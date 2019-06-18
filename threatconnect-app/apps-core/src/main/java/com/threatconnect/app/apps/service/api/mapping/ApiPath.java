package com.threatconnect.app.apps.service.api.mapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ApiPath
{
	public static final String SEPARATOR = "/";
	
	private static final String REGEX_VARIABLE_CONTENT = "[a-zA-Z0-9%_\\-]+";
	private static final String REGEX_VARIABLE = "\\{(" + REGEX_VARIABLE_CONTENT + ")\\}";
	
	private final String path;
	private final String pathRegex;
	private final List<String> variables;
	
	public ApiPath(final String path)
	{
		//prepend the leading slash if necessary
		this.path = normalize(path);
		this.pathRegex = this.path.replaceAll(Pattern.quote("/"), "\\\\/").replaceAll(REGEX_VARIABLE, REGEX_VARIABLE_CONTENT);
		this.variables = new ArrayList<String>();
		
		Matcher matcher = Pattern.compile(REGEX_VARIABLE).matcher(path);
		while (matcher.find())
		{
			this.variables.add(matcher.group(1));
		}
	}
	
	public boolean matches(final String path)
	{
		return path.matches(pathRegex);
	}
	
	public boolean containsVariables()
	{
		return !variables.isEmpty();
	}
	
	public ApiMethodPath toApiMethodPath(final Method method)
	{
		return new ApiMethodPath(method, this);
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
