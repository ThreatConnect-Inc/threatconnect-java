package com.threatconnect.sdk.model.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.threatconnect.sdk.model.Item;
import com.threatconnect.sdk.model.serialize.ItemTypeAdapterFactory;

import java.util.List;

/**
 * @author Greg Marut
 */
public class ModelSerializationUtil
{
	public static String toJson(final Object src)
	{
		return createJson().toJson(src);
	}
	
	public static List<Item> fromJson(final String json)
	{
		return createJson().fromJson(json, new TypeToken<List<Item>>()
		{
		}.getType());
	}
	
	public static Gson createJson()
	{
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapterFactory(new ItemTypeAdapterFactory());
		return gsonBuilder.create();
	}
}
