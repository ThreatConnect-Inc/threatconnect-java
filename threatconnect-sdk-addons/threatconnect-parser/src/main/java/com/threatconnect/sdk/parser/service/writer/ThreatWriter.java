package com.threatconnect.sdk.parser.service.writer;

import com.threatconnect.sdk.conn.Connection;
import com.threatconnect.sdk.model.Threat;
import com.threatconnect.sdk.server.entity.Group.Type;

public class ThreatWriter extends GroupWriter<Threat, com.threatconnect.sdk.server.entity.Threat>
{
	public ThreatWriter(Connection connection, Threat source)
	{
		super(connection, source, com.threatconnect.sdk.server.entity.Threat.class, Type.Threat);
	}
}
