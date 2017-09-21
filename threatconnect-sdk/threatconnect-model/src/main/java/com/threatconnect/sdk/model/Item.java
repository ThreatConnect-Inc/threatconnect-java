package com.threatconnect.sdk.model;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public abstract class Item
{
	private final ItemType itemType;
	private final Set<Attribute> attributes;
	private final Set<String> tags;
	private final List<SecurityLabel> securityLabels;
	private String xid;

	public Item(final ItemType itemType)
	{
		this.itemType = itemType;
		this.attributes = new LinkedHashSet<Attribute>();
		this.tags = new LinkedHashSet<String>();
		this.securityLabels = new ArrayList<SecurityLabel>();
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
	
	public List<SecurityLabel> getSecurityLabels()
	{
		return securityLabels;
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

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Item item = (Item) o;
		return getItemType() == item.getItemType() &&
				Objects.equals(getAttributes(), item.getAttributes()) &&
				Objects.equals(getTags(), item.getTags()) &&
				Objects.equals(getXid(), item.getXid());
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(getItemType(), getAttributes(), getTags(), getXid());
	}
}
