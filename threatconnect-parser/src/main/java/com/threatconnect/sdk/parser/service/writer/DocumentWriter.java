package com.threatconnect.sdk.parser.service.writer;

import com.threatconnect.sdk.conn.Connection;
import com.threatconnect.sdk.parser.model.Document;
import com.threatconnect.sdk.server.entity.Group.Type;

public class DocumentWriter extends GroupWriter<Document, com.threatconnect.sdk.server.entity.Document>
{
	public DocumentWriter(Connection connection, Document source)
	{
		super(connection, source, com.threatconnect.sdk.server.entity.Document.class, Type.Document);
	}
}
