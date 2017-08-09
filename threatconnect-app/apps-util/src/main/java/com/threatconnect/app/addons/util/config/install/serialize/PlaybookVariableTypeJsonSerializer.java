package com.threatconnect.app.addons.util.config.install.serialize;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.threatconnect.app.addons.util.config.install.PlaybookVariableType;

import java.lang.reflect.Type;

/**
 * @author Greg Marut
 */
public class PlaybookVariableTypeJsonSerializer implements JsonDeserializer<PlaybookVariableType>
{
	@Override
	public PlaybookVariableType deserialize(final JsonElement json, final Type typeOfT,
		final JsonDeserializationContext context) throws JsonParseException
	{
		//check to see if this object is a primitive
		if (json.isJsonPrimitive())
		{
			return new PlaybookVariableType(json.getAsString());
		}
		else
		{
			throw new RuntimeException("Unexpected JSON type: " + json.getClass());
		}
	}
	
	public Type getType()
	{
		return new TypeToken<PlaybookVariableType>()
		{
		}.getType();
	}
}