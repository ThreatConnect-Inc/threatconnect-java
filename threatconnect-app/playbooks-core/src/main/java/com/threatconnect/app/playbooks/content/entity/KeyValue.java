package com.threatconnect.app.playbooks.content.entity;

import com.threatconnect.app.addons.util.config.install.StandardPlaybookType;
import com.threatconnect.sdk.model.Item;

import java.util.List;
import java.util.Objects;

public class KeyValue
{
	private String key;
	private Object value;
	private String variableType;
	
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
		this.variableType = StandardPlaybookType.String.toString();
	}
	
	public void setStringArrayValue(final List<String> value)
	{
		this.value = value;
		this.variableType = StandardPlaybookType.StringArray.toString();
	}
	
	public void setBinaryValue(final byte[] value)
	{
		this.value = value;
		this.variableType = StandardPlaybookType.Binary.toString();
	}
	
	public void setBinaryArrayValue(final byte[][] value)
	{
		this.value = value;
		this.variableType = StandardPlaybookType.BinaryArray.toString();
	}
	
	public void setTCEntityValue(final TCEntity value)
	{
		this.value = value;
		this.variableType = StandardPlaybookType.TCEntity.toString();
	}
	
	public void setTCEntityArrayValue(final List<TCEntity> value)
	{
		this.value = value;
		this.variableType = StandardPlaybookType.TCEntityArray.toString();
	}
	
	public void setTCEnhancedEntityValue(final Item value)
	{
		this.value = value;
		this.variableType = StandardPlaybookType.TCEnhancedEntity.toString();
	}
	
	public void setTCEnhancedEntityArrayValue(final List<Item> value)
	{
		this.value = value;
		this.variableType = StandardPlaybookType.TCEnhancedEntityArray.toString();
	}
	
	public void setCustomTypeValue(final byte[] value, final String type)
	{
		this.value = value;
		this.variableType = type;
	}
	
	public Object getValue()
	{
		return value;
	}
	
	public String getVariableType()
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

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		KeyValue keyValue = (KeyValue) o;
		return Objects.equals(getKey(), keyValue.getKey()) &&
				Objects.equals(getValue(), keyValue.getValue()) &&
				Objects.equals(getVariableType(), keyValue.getVariableType());
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(getKey(), getValue(), getVariableType());
	}
}