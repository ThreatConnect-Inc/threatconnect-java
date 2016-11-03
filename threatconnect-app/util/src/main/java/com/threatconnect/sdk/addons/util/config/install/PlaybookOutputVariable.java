package com.threatconnect.sdk.addons.util.config.install;

import com.google.gson.JsonObject;
import com.threatconnect.sdk.addons.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Greg Marut
 */
public class PlaybookOutputVariable
{
	private static final Logger logger = LoggerFactory.getLogger(PlaybookOutputVariable.class);
	
	private static final String NAME = "name";
	private static final String TYPE = "type";
	
	private final String name;
	private final PlaybookVariableType type;
	
	public PlaybookOutputVariable(final JsonObject root)
	{
		this.name = JsonUtil.getAsString(root, NAME);
		final String typeString = JsonUtil.getAsString(root, TYPE);
		
		//convert this json object to a playbook variable and add it to the results
		try
		{
			this.type = PlaybookVariableType.valueOf(typeString);
		}
		catch (IllegalArgumentException e)
		{
			logger.error("{} is not a valid PlaybookVariableType. Possible values are: {}", typeString,
				PlaybookVariableType.values());
			throw e;
		}
	}
	
	public PlaybookOutputVariable(final String name, final PlaybookVariableType type)
	{
		this.name = name;
		this.type = type;
	}
	
	public String getName()
	{
		return name;
	}
	
	public PlaybookVariableType getType()
	{
		return type;
	}
}
