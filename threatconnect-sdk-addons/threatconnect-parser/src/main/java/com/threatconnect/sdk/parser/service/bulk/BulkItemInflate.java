package com.threatconnect.sdk.parser.service.bulk;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.threatconnect.app.addons.util.JsonUtil;
import com.threatconnect.sdk.model.Address;
import com.threatconnect.sdk.model.Attribute;
import com.threatconnect.sdk.model.CustomIndicator;
import com.threatconnect.sdk.model.EmailAddress;
import com.threatconnect.sdk.model.Host;
import com.threatconnect.sdk.model.Indicator;
import com.threatconnect.sdk.model.Item;
import com.threatconnect.sdk.model.Url;
import com.threatconnect.sdk.model.util.TagUtil;
import com.threatconnect.sdk.parser.util.IndicatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * :FIXME: this class is not finished
 */
public class BulkItemInflate
{
	private static final Logger logger = LoggerFactory.getLogger(BulkItemInflate.class);
	
	public List<Item> convertToItems(final JsonObject jsonObject)
	{
		// holds the list of indicators to return
		List<Item> items = new ArrayList<Item>();
		
		JsonArray groupArray = JsonUtil.getAsJsonArray(jsonObject, "group");
		JsonArray indicatorArray = JsonUtil.getAsJsonArray(jsonObject, "indicator");
		
		//check to see if there is an indicator array
		if (null != indicatorArray)
		{
			// for each object in the indicator array
			for (JsonElement indicatorElement : indicatorArray)
			{
				try
				{
					// create the indicator
					Indicator indicator = convertToIndicator(indicatorElement);
					
					//copy the rating and confidence values
					indicator.setRating(JsonUtil.getAsDouble(indicatorElement, "rating"));
					indicator.setConfidence(JsonUtil.getAsDouble(indicatorElement, "confidence"));
					
					//copy tags and attributes for the indicator
					copyTagsAndAttributes(indicatorElement, indicator);
					
					items.add(indicator);
				}
				catch (InvalidIndicatorException e)
				{
					logger.warn(e.getMessage(), e);
				}
			}
		}
		
		return items;
	}
	
	private void copyTagsAndAttributes(JsonElement element, final Item item)
	{
		// check to see if there are tags
		JsonArray tagArray = JsonUtil.getAsJsonArray(element, "tag");
		if (null != tagArray)
		{
			// for each of the tags
			for (JsonElement tagElement : tagArray)
			{
				TagUtil.addTag(JsonUtil.getAsString(tagElement, "name"), item);
			}
		}
		
		// check to see if there are attributes
		JsonArray attributeArray = JsonUtil.getAsJsonArray(element, "attribute");
		if (null != attributeArray)
		{
			// for each of the attributes
			JsonArray attributes = attributeArray.getAsJsonArray();
			for (JsonElement attribute : attributes)
			{
				item.getAttributes().add(convertToAttribute(attribute.getAsJsonObject()));
			}
		}
	}
	
	private Indicator convertToIndicator(final JsonElement indicatorElement) throws InvalidIndicatorException
	{
		final String indicatorType = JsonUtil.getAsString(indicatorElement, "type");
		
		if (null == indicatorType)
		{
			throw new IllegalArgumentException("indicatorType cannot be null");
		}
		
		final String summary = JsonUtil.getAsString(indicatorElement, "summary");
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
				CustomIndicator customIndicator = new CustomIndicator(indicatorType);
				customIndicator.setSummary(summary);
				return customIndicator;
		}
	}
	
	public Attribute convertToAttribute(final JsonObject jsonObject)
	{
		Attribute attribute = new Attribute();
		attribute.setType(jsonObject.get("type").getAsString());
		attribute.setValue(jsonObject.get("value").getAsString());
		
		return attribute;
	}
}
