package com.threatconnect.sdk.blueprints.content.entity;

public class TCEntity
{
	private String type;
	private String value;

	public TCEntity()
	{
		this(null, null);
	}

	public TCEntity(Object type, Object value)
	{
		this.type = type == null ? null : type.toString();
		this.value = value == null ? null : value.toString();
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{
		this.value = value;
	}
}
