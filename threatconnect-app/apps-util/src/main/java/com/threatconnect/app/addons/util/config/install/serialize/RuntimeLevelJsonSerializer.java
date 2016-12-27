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
	@Override
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
				values.add(toRunLevel(element.getAsString()));
			}
		}
		//check to see if this object is a primitive
		else if (json.isJsonPrimitive())
		{
			values.add(toRunLevel(json.getAsString()));
		}
		else
		{
			throw new RuntimeException("Unexpected JSON type: " + json.getClass());
		}
		
		return values;
	}
	
	private RunLevel toRunLevel(final String runLevel)
	{
		try
		{
			return RunLevel.valueOf(runLevel);
		}
		catch (IllegalArgumentException e)
		{
			//build the values text for the error message
			StringBuilder values = new StringBuilder();
			for (RunLevel level : RunLevel.values())
			{
				if (values.length() > 0)
				{
					values.append(", ");
				}
				
				values.append(level.toString());
			}
			
			throw new IllegalArgumentException(
				runLevel + " is not a valid runtimeLevel. Possible values are [" + values + "]", e);
		}
	}
}