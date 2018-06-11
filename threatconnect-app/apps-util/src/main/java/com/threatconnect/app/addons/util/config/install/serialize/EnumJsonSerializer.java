package com.threatconnect.app.addons.util.config.install.serialize;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * @author Greg Marut
 */
public abstract class EnumJsonSerializer<T extends Enum<T>> implements JsonDeserializer<T>
{
	private final Class<T> enumClass;
	
	public EnumJsonSerializer(final Class<T> enumClass)
	{
		this.enumClass = enumClass;
	}
	
	@Override
	public T deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext ctx)
	{
		//check to see if this object is a primitive
		if (json.isJsonPrimitive())
		{
			return EnumJsonSerializer.toEnum(json.getAsString(), enumClass);
		}
		else
		{
			throw new JsonParseException("Unexpected JSON type: " + json.getClass());
		}
	}
	
	public static <T extends Enum<T>> T toEnum(final String value, final Class<T> clazz)
	{
		//for each of the enum constants
		for (T enumConstant : clazz.getEnumConstants())
		{
			//check to see if the enum constants match (not case sensitive)
			if (enumConstant.toString().equalsIgnoreCase(value))
			{
				return enumConstant;
			}
		}
		
		//if the code gets this far then we were not able to find a valid enum
		
		//build the values text for the error message
		StringBuilder values = new StringBuilder();
		for (T constant : clazz.getEnumConstants())
		{
			if (values.length() > 0)
			{
				values.append(", ");
			}
			
			values.append(constant.toString());
		}
		
		throw new InvalidEnumException(value + " is not a valid value. Possible values are [" + values + "]");
	}
	
	public abstract Type getType();
}
