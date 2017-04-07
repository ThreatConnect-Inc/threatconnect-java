package com.threatconnect.sdk.parser.service.writer;

import com.threatconnect.sdk.conn.Connection;
import com.threatconnect.sdk.parser.model.Campaign;
import com.threatconnect.sdk.server.entity.Group.Type;

public class CampaignWriter extends GroupWriter<Campaign, com.threatconnect.sdk.server.entity.Campaign>
{
	public CampaignWriter(Connection connection, Campaign source)
	{
		super(connection, source, com.threatconnect.sdk.server.entity.Campaign.class, Type.Campaign);
	}
}
