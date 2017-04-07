package com.threatconnect.sdk.parser.model;

import java.util.Date;

public class Campaign extends Group
{
	private Date firstSeen;
	
	public Campaign()
	{
		super(GroupType.CAMPAIGN);
	}
	
	public Date getFirstSeen()
	{
		return firstSeen;
	}
	
	public void setFirstSeen(final Date firstSeen)
	{
		this.firstSeen = firstSeen;
	}
}
