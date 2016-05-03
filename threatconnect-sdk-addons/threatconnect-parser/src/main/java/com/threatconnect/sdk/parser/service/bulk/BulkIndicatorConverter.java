package com.threatconnect.sdk.parser.service.bulk;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.NotImplementedException;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.threatconnect.sdk.parser.model.Address;
import com.threatconnect.sdk.parser.model.Attribute;
import com.threatconnect.sdk.parser.model.EmailAddress;
import com.threatconnect.sdk.parser.model.Host;
import com.threatconnect.sdk.parser.model.Indicator;
import com.threatconnect.sdk.parser.model.IndicatorType;
import com.threatconnect.sdk.parser.model.Url;

public class BulkIndicatorConverter
{
	public List<Indicator> convertToIndicators(final JsonObject jsonObject)
	{
		// holds the list of indicators to return
		List<Indicator> indicators = new ArrayList<Indicator>();
		
		JsonArray indicatorsJsonArray = jsonObject.get("indicator").getAsJsonArray();
		
		// for each object in the json array
		for (JsonElement indicatorElement : indicatorsJsonArray)
		{
			// create the indicator
			JsonObject indicatorObject = indicatorElement.getAsJsonObject();
			Indicator indicator = convertToIndicator(indicatorObject);
			
			// copy the rating and confidence
			indicator.setRating(indicatorObject.get("rating").getAsDouble());
			indicator.setConfidence(indicatorObject.get("confidence").getAsDouble());
			
			// check to see if there are tags
			JsonElement tagElement = indicatorObject.get("tag");
			if (null != tagElement)
			{
				// for each of the tags
				JsonArray tags = tagElement.getAsJsonArray();
				for (JsonElement tag : tags)
				{
					indicator.getTags().add(tag.getAsJsonObject().get("name").getAsString());
				}
			}
			
			// check to see if there are attributes
			JsonElement attributeElement = indicatorObject.get("attribute");
			if (null != attributeElement)
			{
				// for each of the attributes
				JsonArray attributes = attributeElement.getAsJsonArray();
				for (JsonElement attribute : attributes)
				{
					indicator.getAttributes().add(convertToAttribute(attribute.getAsJsonObject()));
				}
			}
			
			indicators.add(indicator);
		}
		
		return indicators;
	}
	
	public Indicator convertToIndicator(final JsonObject indicatorObject)
	{
		final String indicatorType = indicatorObject.get("type").getAsString();
		
		if (null == indicatorType)
		{
			throw new IllegalArgumentException("indicatorType cannot be null");
		}
		
		final String summary = indicatorObject.get("summary").getAsString();
		switch (indicatorType)
		{
			case "Address":
				Address address = new Address();
				address.setIp(summary);
				return address;
			case "EmailAddress":
				EmailAddress emailAddress = new EmailAddress();
				emailAddress.setAddress(summary);
				return emailAddress;
			case "File":
				// :FIXME: finish this
				throw new NotImplementedException("Need to know what file summary looks like");
			case "Host":
				Host host = new Host();
				host.setHostName(summary);
				return host;
			case "Url":
				Url url = new Url();
				url.setText(summary);
				return url;
			default:
				throw new IllegalArgumentException(indicatorType + "is not a valid indicatorType");
		}
	}
	
	public Attribute convertToAttribute(final JsonObject jsonObject)
	{
		Attribute attribute = new Attribute();
		attribute.setType(jsonObject.get("type").getAsString());
		attribute.setValue(jsonObject.get("value").getAsString());
		
		return attribute;
	}
	
	public JsonObject convertToJson(final Collection<? extends Indicator> indicators)
	{
		// create the root indicator json object
		JsonObject indicatorJsonObject = new JsonObject();
		
		// create a new json array to hold all of the indicators
		JsonArray indicatorsJsonArray = new JsonArray();
		indicatorJsonObject.add("indicator", indicatorsJsonArray);
		
		// for each indicator in the collection
		for (Indicator indicator : indicators)
		{
			// convert this indicator and add it to the array
			indicatorsJsonArray.add(convertToJson(indicator));
		}
		
		return indicatorJsonObject;
	}
	
	public JsonObject convertToJson(final Indicator indicator)
	{
		// holds the json object that will represent this indicator
		JsonObject indicatorJsonObject = new JsonObject();
		
		indicatorJsonObject.addProperty("rating", indicator.getRating());
		indicatorJsonObject.addProperty("confidence", indicator.getConfidence());
		
		indicatorJsonObject.addProperty("summary", indicator.toString());
		
		indicatorJsonObject.addProperty("type", indicatorTypeToString(indicator.getIndicatorType()));
		
		// check to see if this indicator has attributes
		if (!indicator.getAttributes().isEmpty())
		{
			// create a new attribute array for this object and add it
			JsonArray attributeJsonArray = new JsonArray();
			indicatorJsonObject.add("attribute", attributeJsonArray);
			
			// for each of the attributes
			for (Attribute attribute : indicator.getAttributes())
			{
				// convert this attribute to an object and add it to the array
				attributeJsonArray.add(convertToJson(attribute));
			}
		}
		
		// check to see if this indicator has any tags
		if (!indicator.getTags().isEmpty())
		{
			// create a new attribute array for this object and add it
			JsonArray tagJsonArray = new JsonArray();
			indicatorJsonObject.add("tag", tagJsonArray);
			
			// for each of the attributes
			for (String tag : indicator.getTags())
			{
				// holds the json object that will represent this tag
				JsonObject attributeJsonObject = new JsonObject();
				attributeJsonObject.addProperty("name", tag);
				tagJsonArray.add(attributeJsonObject);
			}
		}
		
		return indicatorJsonObject;
	}
	
	public JsonObject convertToJson(final Attribute attribute)
	{
		// holds the json object that will represent this attribute
		JsonObject attributeJsonObject = new JsonObject();
		
		attributeJsonObject.addProperty("type", attribute.getType());
		attributeJsonObject.addProperty("value", attribute.getValue());
		// :TODO: displayed?
		
		return attributeJsonObject;
	}
	
	private String indicatorTypeToString(final IndicatorType indicatorType)
	{
		if (null == indicatorType)
		{
			throw new IllegalArgumentException("indicatorType cannot be null");
		}
		
		switch (indicatorType)
		{
			case ADDRESS:
				return "Address";
			case EMAIL_ADDRESS:
				return "EmailAddress";
			case FILE:
				return "File";
			case HOST:
				return "Host";
			case URL:
				return "Url";
			default:
				throw new IllegalArgumentException(indicatorType + "is not a valid indicatorType");
		}
	}
}
