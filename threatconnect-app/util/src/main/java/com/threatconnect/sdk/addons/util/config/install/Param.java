package com.threatconnect.sdk.addons.util.config.install;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.threatconnect.sdk.addons.util.JsonUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Greg Marut
 */
public class Param
{
	private static final String NAME = "name";
	private static final String TYPE = "type";
	private static final String PLAYBOOK_DATA_TYPE = "playbookDataType";
	
	private final String name;
	private final ParamDataType type;
	private final List<PlaybookVariableType> playbookDataTypes;
	
	public Param(final JsonObject root)
	{
		this.name = JsonUtil.getAsString(root, NAME);
		this.type = ParamDataType.fromString(JsonUtil.getAsString(root, TYPE));
		this.playbookDataTypes = new ArrayList<PlaybookVariableType>();
		
		//retrieve the playbook data types and make sure it is not null
		JsonElement dataTypeElement = root.get(PLAYBOOK_DATA_TYPE);
		if (null != dataTypeElement)
		{
			//for each of the data types
			JsonArray array = dataTypeElement.getAsJsonArray();
			for (JsonElement element : array)
			{
				//convert this json object to a playbook variable and add it to the results
				playbookDataTypes.add(PlaybookVariableType.fromString(element.getAsString()));
			}
		}
	}
	
	public Param(final String name, final ParamDataType type)
	{
		this(name, type, null);
	}
	
	public Param(final String name, final ParamDataType type, final List<PlaybookVariableType> playbookDataTypes)
	{
		this.name = name;
		this.type = type;
		this.playbookDataTypes =
			(null != playbookDataTypes ? playbookDataTypes : new ArrayList<PlaybookVariableType>());
	}
	
	public String getName()
	{
		return name;
	}
	
	public ParamDataType getType()
	{
		return type;
	}
	
	public List<PlaybookVariableType> getPlaybookDataTypes()
	{
		return playbookDataTypes;
	}
	
	public boolean isPlaybookParam()
	{
		return !getPlaybookDataTypes().isEmpty();
	}
}
