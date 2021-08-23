package com.threatconnect.app.addons.util.config.install;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Greg Marut
 */
public class PlaybookOutputVariable
{
	private String name;
	private String type;
	private final List<String> intelType;
	private boolean sensitive;
	
	public PlaybookOutputVariable()
	{
		this.intelType = new ArrayList<String>();
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(final String name)
	{
		this.name = name;
	}
	
	public String getType()
	{
		return type;
	}
	
	public void setType(final String type)
	{
		this.type = type;
	}
	
	public List<String> getIntelType()
	{
		return intelType;
	}
	
	public boolean isSensitive()
	{
		return sensitive;
	}
	
	public void setSensitive(final boolean sensitive)
	{
		this.sensitive = sensitive;
	}
}
