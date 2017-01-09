package com.threatconnect.sdk.parser.util;

import com.google.gson.JsonElement;

public class GsonUtil
{
	private GsonUtil()
	{
	
	}
	
	/**
	 * Given a root node, traverses the json object and safely handles any null objects along the
	 * way
	 * 
	 * @param element
	 * @param paths
	 * @return the element at the end of the path or null if any of the path elements were null
	 */
	public static JsonElement get(final JsonElement element, final String... paths)
	{
		JsonElement current = element;
		
		// for each of the paths
		for (String path : paths)
		{
			// make sure the current is not null
			if (null != current)
			{
				current = current.getAsJsonObject().get(path);
			}
		}
		
		return current;
	}
	
	/**
	 * Given a root node, traverses the json object and safely handles any null objects along the
	 * way
	 * 
	 * @param element
	 * @param paths
	 * @return
	 */
	public static String getAsString(final JsonElement element, final String... paths)
	{
		JsonElement current = get(element, paths);
		
		// make sure the current element is not null
		if (null != current)
		{
			return current.getAsString();
		}
		else
		{
			return null;
		}
	}
}
