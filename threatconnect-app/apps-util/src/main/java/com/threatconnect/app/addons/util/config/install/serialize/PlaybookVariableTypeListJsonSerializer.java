package com.threatconnect.app.addons.util.config.install.serialize;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.threatconnect.app.addons.util.config.install.PlaybookVariableType;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Greg Marut
 */
public class PlaybookVariableTypeListJsonSerializer implements JsonDeserializer<List<PlaybookVariableType>>
{
	@Override
	public List<PlaybookVariableType> deserialize(final JsonElement json, final Type typeOfT,
		final JsonDeserializationContext ctx)
	{
		//holds the list of values to return
		List<PlaybookVariableType> values = new ArrayList<PlaybookVariableType>();
		
		//check to see if this element is an array
		if (json.isJsonArray())
		{
			//for each of the elements in the json array
			for (JsonElement element : json.getAsJsonArray())
			{
				values.add(new PlaybookVariableType(element.getAsString()));
			}
		}
		//check to see if this object is a primitive
		else if (json.isJsonPrimitive())
		{
			values.add(new PlaybookVariableType(json.getAsString()));
		}
		else
		{
			throw new RuntimeException("Unexpected JSON type: " + json.getClass());
		}
		
		return values;
	}
	
	public Type getType()
	{
		return new TypeToken<List<PlaybookVariableType>>()
		{
		}.getType();
	}
}