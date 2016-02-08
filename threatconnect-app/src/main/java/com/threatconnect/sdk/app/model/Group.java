package com.threatconnect.sdk.app.model;

public abstract class Group extends Item
{
	private final GroupType groupType;
	
	private String name;
	
	public Group(final GroupType groupType)
	{
		super(ItemType.GROUP);
		this.groupType = groupType;
	}
	
	public final GroupType getGroupType()
	{
		return groupType;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
}
