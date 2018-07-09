package com.threatconnect.sdk.parserapp.service.writer;

import com.threatconnect.sdk.conn.Connection;
import com.threatconnect.sdk.model.Signature;
import com.threatconnect.sdk.server.entity.Group.Type;

public class SignatureWriter extends GroupWriter<Signature, com.threatconnect.sdk.server.entity.Signature>
{
	public SignatureWriter(Connection connection, Signature source)
	{
		super(connection, source, com.threatconnect.sdk.server.entity.Signature.class, Type.Signature);
	}
}
