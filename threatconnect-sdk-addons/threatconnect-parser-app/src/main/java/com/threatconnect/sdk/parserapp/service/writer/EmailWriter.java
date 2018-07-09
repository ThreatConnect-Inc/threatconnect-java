package com.threatconnect.sdk.parserapp.service.writer;

import com.threatconnect.sdk.conn.Connection;
import com.threatconnect.sdk.model.Email;
import com.threatconnect.sdk.server.entity.Group.Type;

public class EmailWriter extends GroupWriter<Email, com.threatconnect.sdk.server.entity.Email>
{
	public EmailWriter(Connection connection, Email source)
	{
		super(connection, source, com.threatconnect.sdk.server.entity.Email.class, Type.Email);
	}
}
