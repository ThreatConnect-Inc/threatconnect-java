package com.threatconnect.sdk.parserapp.service.writer;

import com.threatconnect.sdk.conn.Connection;
import com.threatconnect.sdk.model.Incident;
import com.threatconnect.sdk.server.entity.Group.Type;

public class IncidentWriter extends GroupWriter<Incident, com.threatconnect.sdk.server.entity.Incident>
{
	public IncidentWriter(Connection connection, Incident source)
	{
		super(connection, source, com.threatconnect.sdk.server.entity.Incident.class, Type.Incident);
	}
}
