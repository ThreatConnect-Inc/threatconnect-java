package com.threatconnect.sdk.parserapp.service.save;

import com.threatconnect.sdk.model.GroupType;

/**
 * @author Greg Marut
 */
public class GroupIdentifier
{
	private Integer id;
	private GroupType type;
	
	public Integer getId()
	{
		return id;
	}
	
	public void setId(final Integer id)
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
