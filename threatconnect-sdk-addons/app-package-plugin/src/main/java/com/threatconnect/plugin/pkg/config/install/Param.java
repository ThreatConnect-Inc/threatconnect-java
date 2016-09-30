package com.threatconnect.plugin.pkg.config.install;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.threatconnect.plugin.pkg.util.JsonUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Greg Marut
 */
public class Param
{
	private final JsonObject root;
	
	private static final String NAME = "name";
	private static final String TYPE = "type";
	private static final String PLAYBOOK_DATA_TYPE = "blueprintDataType";
	
	public Param(final JsonObject root)
	{
		this.root = root;
	}
	
	public String getName()
	{
		return getAsString(NAME);
	}
	
	public String getType()
	{
		return getAsString(TYPE);
	}
	
	public List<String> getPlaybookDataTypes()
	{
		List<String> results = new ArrayList<String>();
		
		//retrieve the playbook data types and make sure it is not null
		JsonElement dataTypeElement = root.get(PLAYBOOK_DATA_TYPE);
		if (null != dataTypeElement)
		{
			//for each of the data types
			JsonArray array = dataTypeElement.getAsJsonArray();
			for (JsonElement element : array)
			{
				//convert this json object to a playbook variable and add it to the results list
				results.add(element.getAsString());
			}
		}
		
		return results;
	}
	
	public boolean isPlaybookParam()
	{
		return !getPlaybookDataTypes().isEmpty();
	}
	
	private String getAsString(final String... paths)
	{
		return JsonUtil.getAsString(root, paths);
	}
}
