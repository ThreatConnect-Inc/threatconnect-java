package com.threatconnect.app.addons.util.config.install;

import com.google.gson.annotations.SerializedName;

/**
 * @author Greg Marut
 */
public class JobParam
{
	private String name;
	private boolean preventUpdates;
	
	@SerializedName("default")
	private String defaultValue;
	
	public String getName()
	{
		return name;
	}
	
	public void setName(final String name)
	{
		this.name = name;
	}
	
	public boolean isPreventUpdates()
	{
		return preventUpdates;
	}
	
	public void setPreventUpdates(final boolean preventUpdates)
	{
		this.preventUpdates = preventUpdates;
	}
	
	public String getDefaultValue()
	{
		return defaultValue;
	}
	
	public void setDefaultValue(final String defaultValue)
	{
		this.defaultValue = defaultValue;
	}
}
