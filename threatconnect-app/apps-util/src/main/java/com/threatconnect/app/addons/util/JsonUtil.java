package com.threatconnect.app.addons.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Greg Marut
 */
public class JsonUtil
{
	private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);
	
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
				//check to see if the current object is a json object
				if (current.isJsonObject())
				{
					current = current.getAsJsonObject().get(path);
				}
				//check to see if the current object is a json array
				else if (current.isJsonArray())
				{
					JsonArray jsonArray = current.getAsJsonArray();
					
					//make sure the json array is not empty
					if (jsonArray.size() > 0)
					{
						current = current.getAsJsonArray().get(0).getAsJsonObject().get(path);
					}
					else
					{
						current = null;
					}
				}
				else
				{
					//unable to determine the type of this json element
					logger.warn("Unable to determine json element type {}", current.toString());
					current = null;
				}
			}
		}
		
		return current;
	}
}
