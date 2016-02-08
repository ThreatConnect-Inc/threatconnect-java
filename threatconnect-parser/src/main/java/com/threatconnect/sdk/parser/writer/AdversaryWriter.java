package com.threatconnect.sdk.parser.writer;

import com.threatconnect.sdk.conn.Connection;
import com.threatconnect.sdk.parser.model.Adversary;
import com.threatconnect.sdk.server.entity.Group.Type;

public class AdversaryWriter extends GroupWriter<Adversary, com.threatconnect.sdk.server.entity.Adversary>
{
	public AdversaryWriter(Connection connection, Adversary source)
	{
		super(connection, source, com.threatconnect.sdk.server.entity.Adversary.class, Type.Adversary);
	}
}
