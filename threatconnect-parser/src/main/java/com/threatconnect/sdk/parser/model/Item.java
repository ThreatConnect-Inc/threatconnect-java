package com.threatconnect.sdk.parser.model;

import java.util.ArrayList;
import java.util.List;

public abstract class Item
{
	private final ItemType itemType;
	private final List<Item> associatedItems;
	private final List<Attribute> attributes;
	private final List<String> tags;
	
	public Item(final ItemType itemType)
	{
		this.itemType = itemType;
		this.associatedItems = new ArrayList<Item>();
		this.attributes = new ArrayList<Attribute>();
		this.tags = new ArrayList<String>();
	}
	
	public final ItemType getItemType()
	{
		return itemType;
	}
	
	public List<Item> getAssociatedItems()
	{
		return associatedItems;
	}
	
	public List<Attribute> getAttributes()
	{
		return attributes;
	}
	
	public List<String> getTags()
	{
		return tags;
	}
}
