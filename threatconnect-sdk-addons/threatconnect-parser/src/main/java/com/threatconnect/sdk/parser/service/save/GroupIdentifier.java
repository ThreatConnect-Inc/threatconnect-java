package com.threatconnect.sdk.parser.service.save;

import com.threatconnect.sdk.model.GroupType;

/**
 * @author Greg Marut
 */
public class GroupIdentifier
{
	private Long id;
	private GroupType type;
	
	public Long getId()
	{
		return id;
	}
	
	public void setId(final Long id)
	{
		this.id = id;
	}
	
	public GroupType getType()
	{
		return type;
	}
	
	public void setType(final GroupType type)
	{
		this.type = type;
	}
}
