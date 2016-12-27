package com.threatconnect.app.addons.util.config.install;

import com.google.gson.JsonObject;
import com.threatconnect.app.addons.util.JsonUtil;

/**
 * @author Greg Marut
 */
public class PlaybookOutputVariable
{
	private static final String NAME = "name";
	private static final String TYPE = "type";
	
	private final String name;
	private final PlaybookVariableType type;
	
	public PlaybookOutputVariable(final JsonObject root)
	{
		//make sure the root object is not null
		if(null == root)
		{
			throw new IllegalArgumentException("root cannot be null");
		}
		
		this.name = JsonUtil.getAsString(root, NAME);
		this.type = PlaybookVariableType.fromString(JsonUtil.getAsString(root, TYPE));
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
