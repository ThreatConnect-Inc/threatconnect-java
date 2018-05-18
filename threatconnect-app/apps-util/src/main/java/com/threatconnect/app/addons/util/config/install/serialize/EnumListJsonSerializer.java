package com.threatconnect.app.addons.util.config.install.serialize;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Greg Marut
 */
public abstract class EnumListJsonSerializer<T extends Enum<T>> implements JsonDeserializer<List<T>>
{
	private final Class<T> enumClass;
	
	public EnumListJsonSerializer(final Class<T> enumClass)
	{
		this.enumClass = enumClass;
	}
	
	@Override
	public List<T> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext ctx)
	{
		//holds the list of runlevel values to return
		List<T> values = new ArrayList<T>();
		
		//check to see if this element is an array
		if (json.isJsonArray())
		{
			//for each of the elements in the json array
			for (JsonElement element : json.getAsJsonArray())
			{
				values.add(EnumJsonSerializer.toEnum(element.getAsString(), enumClass));
			}
		}
		//check to see if this object is a primitive
		else if (json.isJsonPrimitive())
		{
			values.add(EnumJsonSerializer.toEnum(json.getAsString(), enumClass));
		}
		else
		{
			throw new JsonParseException("Unexpected JSON type: " + json.getClass());
		}
		
		return values;
	}
	
	public abstract Type getType();
}
