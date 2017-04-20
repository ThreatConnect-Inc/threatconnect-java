package com.threatconnect.sdk.model.serialize;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal.Streams;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.threatconnect.sdk.model.Address;
import com.threatconnect.sdk.model.Adversary;
import com.threatconnect.sdk.model.Campaign;
import com.threatconnect.sdk.model.CustomIndicator;
import com.threatconnect.sdk.model.Document;
import com.threatconnect.sdk.model.Email;
import com.threatconnect.sdk.model.EmailAddress;
import com.threatconnect.sdk.model.File;
import com.threatconnect.sdk.model.Group;
import com.threatconnect.sdk.model.GroupType;
import com.threatconnect.sdk.model.Host;
import com.threatconnect.sdk.model.Incident;
import com.threatconnect.sdk.model.Indicator;
import com.threatconnect.sdk.model.Item;
import com.threatconnect.sdk.model.ItemType;
import com.threatconnect.sdk.model.Signature;
import com.threatconnect.sdk.model.Threat;
import com.threatconnect.sdk.model.Url;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * This TypeAdapterFactory instructs Gson how to properly read and wrote the ThreatConnect data model to and from json.
 * This needs to be registered with the {@link com.google.gson.GsonBuilder} object.
 * <p>
 * <code>
 * gsonBuilder.registerTypeAdapterFactory(new ItemTypeAdapterFactory());
 * </code>
 * </p>
 *
 * @author Greg Marut
 */
public class ItemTypeAdapterFactory implements TypeAdapterFactory
{
	private static final Logger logger = LoggerFactory.getLogger(ItemTypeAdapterFactory.class);
	
	@Override
	public <T> TypeAdapter<T> create(final Gson gson, final TypeToken<T> type)
	{
		//make sure this type is an item
		if (type.getRawType().isAssignableFrom(Item.class))
		{
			logger.trace("Creating type adapter for {}", type.toString());
			
			return new TypeAdapter<T>()
			{
				@Override
				public T read(final JsonReader in) throws IOException
				{
					JsonElement jsonElement = Streams.parse(in);
					return (T) findDelegate(gson, jsonElement).fromJsonTree(jsonElement);
				}
				
				@Override
				public void write(final JsonWriter out, final T value) throws IOException
				{
					TypeAdapter<T> delegate = (TypeAdapter<T>)
						gson.getDelegateAdapter(ItemTypeAdapterFactory.this, TypeToken.get(value.getClass()));
					Streams.write(delegate.toJsonTree(value), out);
				}
			};
		}
		else
		{
			return null;
		}
	}
	
	private TypeAdapter<?> findDelegate(final Gson gson, final JsonElement jsonElement)
	{
		switch (determineItemType(jsonElement))
		{
			case GROUP:
				return gson.getDelegateAdapter(this, TypeToken.get(determineGroupClass(jsonElement)));
			case INDICATOR:
				return gson.getDelegateAdapter(this, TypeToken.get(determineIndicatorClass(jsonElement)));
			default:
				throw new IllegalArgumentException("Invalid Item Type");
		}
	}
	
	private ItemType determineItemType(final JsonElement json)
	{
		return ItemType.valueOf(json.getAsJsonObject().get("itemType").getAsString());
	}
	
	private Class<? extends Indicator> determineIndicatorClass(final JsonElement jsonElement)
	{
		switch (jsonElement.getAsJsonObject().get("indicatorType").getAsString())
		{
			case Address.INDICATOR_TYPE:
				return Address.class;
			case EmailAddress.INDICATOR_TYPE:
				return EmailAddress.class;
			case File.INDICATOR_TYPE:
				return File.class;
			case Host.INDICATOR_TYPE:
				return Host.class;
			case Url.INDICATOR_TYPE:
				return Url.class;
			default:
				return CustomIndicator.class;
		}
	}
	
	private Class<? extends Group> determineGroupClass(final JsonElement jsonElement)
	{
		switch (GroupType.valueOf(jsonElement.getAsJsonObject().get("groupType").getAsString()))
		{
			case ADVERSARY:
				return Adversary.class;
			case CAMPAIGN:
				return Campaign.class;
			case DOCUMENT:
				return Document.class;
			case EMAIL:
				return Email.class;
			case INCIDENT:
				return Incident.class;
			case SIGNATURE:
				return Signature.class;
			case THREAT:
				return Threat.class;
			default:
				throw new IllegalArgumentException("Json contains and invalid groupType");
		}
	}
}
