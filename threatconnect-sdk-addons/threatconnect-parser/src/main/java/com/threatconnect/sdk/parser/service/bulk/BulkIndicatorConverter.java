package com.threatconnect.sdk.parser.service.bulk;

import java.util.Collection;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.threatconnect.sdk.parser.model.Attribute;
import com.threatconnect.sdk.parser.model.Indicator;
import com.threatconnect.sdk.parser.model.IndicatorType;

public class BulkIndicatorConverter
{
	public JsonObject convertToJson(final Collection<Indicator> indicators)
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
