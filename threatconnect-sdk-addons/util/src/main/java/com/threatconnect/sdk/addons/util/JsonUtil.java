package com.threatconnect.sdk.addons.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

/**
 * @author Greg Marut
 */
public class JsonUtil
{
	private JsonUtil()
	{
		
	}
	
	/**
	 * Given a root node, traverses the json object and safely handles any null objects along the
	 * way
	 *
	 * @param root
	 * @param paths
	 * @return
	 */
	public static JsonArray getAsJsonArray(final JsonElement root, final String... paths)
	{
		// make sure the element is not null
		JsonElement element = get(root, paths);
		if (null != element)
		{
			return element.getAsJsonArray();
		}
		else
		{
			return null;
		}
	}
	
	/**
	 * Given a root node, traverses the json object and safely handles any null objects along the
	 * way
	 *
	 * @param root
	 * @param paths
	 * @return
	 */
	public static String getAsString(final JsonElement root, final String... paths)
	{
		// make sure the element is not null
		JsonElement element = get(root, paths);
		if (null != element)
		{
			return element.getAsString();
		}
		else
		{
			return null;
		}
	}
	
	/**
	 * Given a root node, traverses the json object and safely handles any null objects along the
	 * way
	 *
	 * @param root
	 * @param paths
	 * @return
	 */
	public static Integer getAsInt(final JsonElement root, final String... paths)
	{
		// make sure the element is not null
		JsonElement element = get(root, paths);
		if (null != element)
		{
			return element.getAsInt();
		}
		else
		{
			return null;
		}
	}
	
	public static JsonElement get(final JsonElement root, final String... paths)
	{
		JsonElement current = root;
		
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
}
