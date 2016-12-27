package com.threatconnect.app.addons.util.config.install;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.threatconnect.app.addons.util.JsonUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Greg Marut
 */
public class Deprecation
{
	private static final String INDICATOR_TYPES = "indicatorTypes";
	private static final String INTERVAL_DAYS = "intervalDays";
	private static final String CONFIDENCE_AMOUNT = "confidenceAmount";
	private static final String PERCENTAGE = "percentage";
	
	//holds the root json object
	private JsonObject root;
	
	//holds the list of indicator types
	private final List<String> indicatorTypes;
	
	public Deprecation(final JsonObject root)
	{
		//make sure the root object is not null
		if(null == root)
		{
			throw new IllegalArgumentException("root cannot be null");
		}
		
		this.root = root;
		indicatorTypes = new ArrayList<String>();
		
		//retrieve the array of indicator types from the json object
		JsonArray indicatorTypesArray = JsonUtil.getAsJsonArray(root, INDICATOR_TYPES);
		if (null != indicatorTypesArray)
		{
			//for each of the indicator types in the array
			for (JsonElement jsonElement : indicatorTypesArray)
			{
				//add this type to the list
				indicatorTypes.add(jsonElement.getAsString());
			}
		}
	}
	
	public String getIntervalDays()
	{
		return JsonUtil.getAsString(root, INTERVAL_DAYS);
	}
	
	public String getConfidenceAmount()
	{
		return JsonUtil.getAsString(root, CONFIDENCE_AMOUNT);
	}
	
	public boolean isPercentage()
	{
		return JsonUtil.getAsBoolean(root, PERCENTAGE);
	}
}
