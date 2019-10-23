package com.threatconnect.app.addons.util.config.metric;

public class CustomMetricConfig
{
	private String category;
	private String name;
	private String dataType;
	private String runInterval;
	private boolean keyedValues;
	private String description;
	
	public String getCategory()
	{
		return category;
	}
	
	public void setCategory(final String category)
	{
		this.category = category;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(final String name)
	{
		this.name = name;
	}
	
	public String getDataType()
	{
		return dataType;
	}
	
	public void setDataType(final String dataType)
	{
		this.dataType = dataType;
	}
	
	public String getRunInterval()
	{
		return runInterval;
	}
	
	public void setRunInterval(final String runInterval)
	{
		this.runInterval = runInterval;
	}
	
	public boolean isKeyedValues()
	{
		return keyedValues;
	}
	
	public void setKeyedValues(final boolean keyedValues)
	{
		this.keyedValues = keyedValues;
	}
	
	public String getDescription()
	{
		return description;
	}
	
	public void setDescription(final String description)
	{
		this.description = description;
	}
}
