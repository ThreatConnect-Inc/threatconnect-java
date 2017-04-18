package com.threatconnect.sdk.client.fluent;

import com.threatconnect.sdk.server.entity.Campaign;

import java.util.Date;

public class CampaignBuilder extends AbstractGroupBuilder<CampaignBuilder>
{
	private Date firstSeen;
	
	public CampaignBuilder withFirstSeenDate(Date firstSeen)
	{
		this.firstSeen = firstSeen;
		return this;
	}
	
	public Campaign createCampaign()
	{
		return new Campaign(id, name, type, owner, ownerName, dateAdded, webLink, firstSeen);
	}
}