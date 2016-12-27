package com.threatconnect.app.addons.util.config.install.serialize;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.threatconnect.app.addons.util.config.install.RunLevel;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Greg Marut
 */
public class RuntimeLevelJsonSerializer implements JsonDeserializer<List<RunLevel>>
{
	public List<RunLevel> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext ctx)
	{
		//holds the list of runlevel values to return
		List<RunLevel> values = new ArrayList<RunLevel>();
		
		//check to see if this element is an array
		if (json.isJsonArray())
		{
			//for each of the elements in the json array
			for (JsonElement element : json.getAsJsonArray())
			{
				values.add((RunLevel) ctx.deserialize(element, RunLevel.class));
			}
		}
		//check to see if this object is a primitive
		else if (json.isJsonPrimitive())
		{
			values.add((RunLevel) ctx.deserialize(json, RunLevel.class));
		}
		else
		{
			throw new RuntimeException("Unexpected JSON type: " + json.getClass());
		}
		
		return values;
	}
}