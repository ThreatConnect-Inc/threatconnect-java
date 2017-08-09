package com.threatconnect.app.playbooks.content.entity;

import com.threatconnect.app.addons.util.config.install.PlaybookVariableType;
import com.threatconnect.sdk.model.Item;

import java.util.List;

public class KeyValue
{
	private String key;
	private Object value;
	private PlaybookVariableType variableType;
	
	public KeyValue()
	{
		setStringValue(null);
	}
	
	public KeyValue(final KeyValue other)
	{
		this.key = other.key;
		this.value = other.value;
		this.variableType = other.variableType;
	}
	
	public KeyValue(String key, String value)
	{
		this.key = key;
		setStringValue(value);
	}
	
	public void setKey(String key)
	{
		this.key = key;
	}
	
	public String getKey()
	{
		return key;
	}
	
	public void setStringValue(final String value)
	{
		this.value = value;
		this.variableType = PlaybookVariableType.String;
	}
	
	public void setStringArrayValue(final List<String> value)
	{
		this.value = value;
		this.variableType = PlaybookVariableType.StringArray;
	}
	
	public void setBinaryValue(final byte[] value)
	{
		this.value = value;
		this.variableType = PlaybookVariableType.Binary;
	}
	
	public void setBinaryArrayValue(final byte[][] value)
	{
		this.value = value;
		this.variableType = PlaybookVariableType.BinaryArray;
	}
	
	public void setTCEntityValue(final TCEntity value)
	{
		this.value = value;
		this.variableType = PlaybookVariableType.TCEntity;
	}
	
	public void setTCEntityArrayValue(final List<TCEntity> value)
	{
		this.value = value;
		this.variableType = PlaybookVariableType.TCEntityArray;
	}
	
	public void setTCEnhancedEntityValue(final Item value)
	{
		this.value = value;
		this.variableType = PlaybookVariableType.TCEnhancedEntity;
	}
	
	public void setTCEnhancedEntityArrayValue(final List<Item> value)
	{
		this.value = value;
		this.variableType = PlaybookVariableType.TCEnhancedEntityArray;
	}
	
	public void setCustomTypeValue(final byte[] value, final PlaybookVariableType type)
	{
		this.value = value;
		this.variableType = type;
	}
	
	public Object getValue()
	{
		return value;
	}
	
	public PlaybookVariableType getVariableType()
	{
		return variableType;
	}
	
	@Override
	public String toString()
	{
		return "KeyValue{" +
			"key=" + key +
			", value=" + value +
			'}';
	}
}