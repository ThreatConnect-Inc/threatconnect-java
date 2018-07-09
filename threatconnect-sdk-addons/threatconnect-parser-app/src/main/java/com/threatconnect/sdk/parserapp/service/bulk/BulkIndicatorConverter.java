package com.threatconnect.sdk.parserapp.service.bulk;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.threatconnect.sdk.model.Address;
import com.threatconnect.sdk.model.Attribute;
import com.threatconnect.sdk.model.EmailAddress;
import com.threatconnect.sdk.model.Host;
import com.threatconnect.sdk.model.Indicator;
import com.threatconnect.sdk.model.Url;
import com.threatconnect.sdk.model.serialize.InvalidIndicatorException;
import com.threatconnect.sdk.model.util.TagUtil;
import com.threatconnect.sdk.parser.util.IndicatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BulkIndicatorConverter
{
	private static final Logger logger = LoggerFactory.getLogger(BulkIndicatorConverter.class);
	
	public List<Indicator> convertToIndicators(final JsonObject jsonObject)
	{
		// holds the list of indicators to return
		List<Indicator> indicators = new ArrayList<Indicator>();
		
		JsonElement indicatorElement = jsonObject.get("indicator");
		
		//check to see if there is an indicator element
		if (null != indicatorElement)
		{
			JsonArray indicatorsJsonArray = indicatorElement.getAsJsonArray();
			
			// for each object in the json array
			for (JsonElement element : indicatorsJsonArray)
			{
				try
				{
					// create the indicator
					JsonObject indicatorObject = element.getAsJsonObject();
					Indicator indicator = convertToIndicator(indicatorObject);
					
					try
					{
						// copy the rating
						indicator.setRating(indicatorObject.get("rating").getAsDouble());
					}
					catch (NullPointerException e)
					{
						// its ok to ignore this if the rating do not exist.
					}
					
					try
					{
						// copy the confidence
						indicator.setConfidence(indicatorObject.get("confidence").getAsDouble());
					}
					catch (NullPointerException e)
					{
						// its ok to ignore this if the confidence do not exist.
					}
					
					// check to see if there are tags
					JsonElement tagElement = indicatorObject.get("tag");
					if (null != tagElement)
					{
						// for each of the tags
						JsonArray tags = tagElement.getAsJsonArray();
						for (JsonElement tag : tags)
						{
							TagUtil.addTag(tag.getAsJsonObject().get("name").getAsString(), indicator);
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
				catch (InvalidIndicatorException e)
				{
					logger.warn(e.getMessage(), e);
				}
			}
		}
		
		return indicators;
	}
	
	public Indicator convertToIndicator(final JsonObject indicatorObject) throws InvalidIndicatorException
	{
		final String indicatorType = indicatorObject.get("type").getAsString();
		
		if (null == indicatorType)
		{
			throw new IllegalArgumentException("indicatorType cannot be null");
		}
		
		final String summary = indicatorObject.get("summary").getAsString();
		logger.debug("Converting: {}", summary);
		
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
				return IndicatorUtil.createFile(summary);
			case "Host":
				Host host = new Host();
				host.setHostName(summary);
				return host;
			case "Url":
			case "URL":
				Url url = new Url();
				url.setText(summary);
				return url;
			default:
				throw new InvalidIndicatorException(indicatorType + " is not a valid indicatorType");
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
			try
			{
				// convert this indicator and add it to the array
				indicatorsJsonArray.add(convertToJson(indicator));
			}
			catch (InvalidIndicatorException e)
			{
				logger.warn(e.getMessage(), e);
			}
		}
		
		return indicatorJsonObject;
	}
	
	public JsonObject convertToJson(final Indicator indicator) throws InvalidIndicatorException
	{
		// holds the json object that will represent this indicator
		JsonObject indicatorJsonObject = new JsonObject();
		
		indicatorJsonObject.addProperty("rating", indicator.getRating());
		indicatorJsonObject.addProperty("confidence", indicator.getConfidence());
		
		indicatorJsonObject.addProperty("summary", indicator.toString());
		
		indicatorJsonObject.addProperty("type", indicator.getIndicatorType());
		
		// create a new json array of the ids
		JsonArray associatedGroups = new JsonArray();
		
		indicatorJsonObject.add("associatedGroup", associatedGroups);
		
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
}
