package com.threatconnect.app.addons.util.config.install;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.threatconnect.app.addons.util.JsonUtil;
import com.threatconnect.app.addons.util.config.InvalidJsonFileException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Greg Marut
 */
public class Param
{
	private static final Logger logger = LoggerFactory.getLogger(Param.class);
	
	private static final String NAME = "name";
	private static final String TYPE = "type";
	private static final String VALID_VALUES = "validValues";
	private static final String PLAYBOOK_DATA_TYPE = "playbookDataType";
	
	private final String name;
	private final ParamDataType type;
	private final List<String> validValues;
	private final List<PlaybookVariableType> playbookDataTypes;
	
	public Param(final JsonObject root) throws InvalidJsonFileException
	{
		//make sure the root object is not null
		if (null == root)
		{
			throw new IllegalArgumentException("root cannot be null");
		}
		
		this.name = JsonUtil.getAsString(root, NAME);
		this.type = ParamDataType.fromString(JsonUtil.getAsString(root, TYPE));
		this.validValues = new ArrayList<String>();
		this.playbookDataTypes = new ArrayList<PlaybookVariableType>();
		
		logger.debug("name={}, type={}", name, type);
		
		//retrieve the valid values element
		JsonElement validValuesElement = root.get(VALID_VALUES);
		if (null != validValuesElement)
		{
			//make sure this is an array
			if (!validValuesElement.isJsonArray())
			{
				throw new InvalidJsonFileException(VALID_VALUES + " must be an array");
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
			if (!dataTypeElement.isJsonArray())
			{
				throw new InvalidJsonFileException(PLAYBOOK_DATA_TYPE + " must be an array");
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
	
	public List<String> getValidValues()
	{
		return validValues;
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
