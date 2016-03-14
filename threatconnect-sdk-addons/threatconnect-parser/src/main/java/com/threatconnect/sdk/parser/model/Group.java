package com.threatconnect.sdk.parser.model;

import java.util.ArrayList;
import java.util.List;

public abstract class Group extends Item
{
	private final GroupType groupType;
	private final List<Indicator> associatedItems;
	private String name;
	
	public Group(final GroupType groupType)
	{
		super(ItemType.GROUP);
		this.groupType = groupType;
		this.associatedItems = new ArrayList<Indicator>();
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
	
	@Override
	public List<Indicator> getAssociatedItems()
	{
		return associatedItems;
	}
}
