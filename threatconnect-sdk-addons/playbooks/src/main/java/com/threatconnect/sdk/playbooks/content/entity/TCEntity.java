package com.threatconnect.sdk.playbooks.content.entity;

public class TCEntity
{
	private Integer id;
	private String type;
	private String value;
	private String ownerName;
	
	public TCEntity()
	{
		this(null, null);
	}
	
	public TCEntity(Object type, Object value)
	{
		this.type = type == null ? null : type.toString();
		this.value = value == null ? null : value.toString();
	}
	
	public Integer getId()
	{
		return id;
	}
	
	public void setId(final Integer id)
	{
		this.id = id;
	}
	
	public String getType()
	{
		return type;
	}
	
	public void setType(final String type)
	{
		this.type = type;
	}
	
	public String getValue()
	{
		return value;
	}
	
	public void setValue(final String value)
	{
		this.value = value;
	}
	
	public String getOwnerName()
	{
		return ownerName;
	}
	
	public void setOwnerName(final String ownerName)
	{
		this.ownerName = ownerName;
	}
}
