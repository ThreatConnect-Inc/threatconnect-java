package com.threatconnect.app.playbooks.content.entity;

/**
 * @author Greg Marut
 */
public class StringKeyValue extends KeyValue<String, String>
{
	public StringKeyValue()
	{
	}
	
	public StringKeyValue(final String key, final String value)
	{
		super(key, value);
	}
	
	public StringKeyValue(final String key, final String value, final String displayValue)
	{
		super(key, value, displayValue);
	}
}
