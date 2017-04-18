package com.threatconnect.sdk.client.fluent;

import com.threatconnect.sdk.server.entity.Owner;

import java.util.Date;

public abstract class AbstractGroupBuilder<T extends AbstractGroupBuilder<T>>
{
	protected Integer id;
	protected String name;
	protected String type;
	protected Owner owner;
	protected String ownerName;
	protected Date dateAdded;
	protected String webLink;
	
	public T withId(Integer id)
	{
		this.id = id;
		return getThis();
	}
	
	public T withName(String name)
	{
		this.name = name;
		return getThis();
	}
	
	public T withType(String type)
	{
		this.type = type;
		return getThis();
	}
	
	public T withOwner(Owner owner)
	{
		this.owner = owner;
		return getThis();
	}
	
	public T withOwnerName(String ownerName)
	{
		this.ownerName = ownerName;
		return getThis();
	}
	
	public T withDateAdded(Date dateAdded)
	{
		this.dateAdded = dateAdded;
		return getThis();
	}
	
	public T withWebLink(String webLink)
	{
		this.webLink = webLink;
		return getThis();
	}
	
	private T getThis()
	{
		return (T) this;
	}
}