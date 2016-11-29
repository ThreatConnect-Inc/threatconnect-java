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
public class Param
{
	private static final String NAME = "name";
	private static final String TYPE = "type";
	private static final String VALID_VALUES = "validValues";
	private static final String PLAYBOOK_DATA_TYPE = "playbookDataType";
	
	private final String name;
	private final ParamDataType type;
	private final List<String> validValues;
	private final List<PlaybookVariableType> playbookDataTypes;
	
	public Param(final JsonObject root) throws InvalidInstallJsonFileException
	{
		this.name = JsonUtil.getAsString(root, NAME);
		this.type = ParamDataType.fromString(JsonUtil.getAsString(root, TYPE));
		System.out.println("name="+name+",type="+type);
		this.validValues = new ArrayList<String>();
		this.playbookDataTypes = new ArrayList<PlaybookVariableType>();
		
		//retrieve the valid values element
		JsonElement validValuesElement = root.get(VALID_VALUES);
		if (null != validValuesElement)
		{
			//make sure this is an array
			if(!validValuesElement.isJsonArray())
			{
				throw new InvalidInstallJsonFileException(VALID_VALUES + " must be an array");
			}
			
			//for each of the data types
			JsonArray array = validValuesElement.getAsJsonArray();
			for (JsonElement element : array)
			{
				//convert this json object to a playbook variable and add it to the results
				validValues.add(element.getAsString());
			}
		}
		
		//retrieve the playbook data types and make sure it is not null
		JsonElement dataTypeElement = root.get(PLAYBOOK_DATA_TYPE);
		if (null != dataTypeElement)
		{
			//make sure this is an array
			if(!dataTypeElement.isJsonArray())
			{
				throw new InvalidInstallJsonFileException(PLAYBOOK_DATA_TYPE + " must be an array");
			}
			
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
		this.validValues = new ArrayList<String>();
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
	
	public static String getValidValues()
	{
		return getValidValues();
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
