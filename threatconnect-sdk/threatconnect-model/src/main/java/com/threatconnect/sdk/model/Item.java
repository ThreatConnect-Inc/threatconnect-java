package com.threatconnect.sdk.model;

import java.util.LinkedHashSet;
import java.util.Set;

public abstract class Item
{
	private final ItemType itemType;
	private final Set<Attribute> attributes;
	private final Set<String> tags;
	private String xid;
	
	public Item(final ItemType itemType)
	{
		this.itemType = itemType;
		this.attributes = new LinkedHashSet<Attribute>();
		this.tags = new LinkedHashSet<String>();
	}
	
	public final ItemType getItemType()
	{
		return itemType;
	}
	
	public Set<Attribute> getAttributes()
	{
		return attributes;
	}
	
	public Set<String> getTags()
	{
		return tags;
	}
	
	public String getXid()
	{
		return xid;
	}
	
	public void setXid(final String xid)
	{
		this.xid = xid;
	}
	
	public abstract Set<? extends Item> getAssociatedItems();
}
