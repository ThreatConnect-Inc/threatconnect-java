package com.threatconnect.app.addons.util.config.install;

import com.threatconnect.app.addons.util.config.install.type.PlaybookVariableType;

/**
 * @author Greg Marut
 */
public class PlaybookOutputVariable
{
	private String name;
	private PlaybookVariableType type;
	
	public String getName()
	{
		return name;
	}
	
	public void setName(final String name)
	{
		this.name = name;
	}
	
	public PlaybookVariableType getType()
	{
		return type;
	}
	
	public void setType(final PlaybookVariableType type)
	{
		this.type = type;
	}
}
