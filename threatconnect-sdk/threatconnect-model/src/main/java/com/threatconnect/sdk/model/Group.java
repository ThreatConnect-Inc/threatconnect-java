package com.threatconnect.sdk.model;

import java.util.LinkedHashSet;
import java.util.Set;

public abstract class Group extends Item
{
	private final GroupType groupType;
	private final Set<Item> associatedItems;
	private String name;
	
	public Group(final GroupType groupType)
	{
		super(ItemType.GROUP);
		this.groupType = groupType;
		this.associatedItems = new LinkedHashSet<Item>();
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
	public Set<Item> getAssociatedItems()
	{
		return associatedItems;
	}
	
	@Override
	public String toString()
	{
		return getName();
	}
}
