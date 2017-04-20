package com.threatconnect.sdk.parser.service.writer;

import com.threatconnect.sdk.conn.Connection;
import com.threatconnect.sdk.model.EmailAddress;
import com.threatconnect.sdk.server.entity.Indicator.Type;

public class EmailAddressWriter extends TypedIndicatorWriter<EmailAddress, com.threatconnect.sdk.server.entity.EmailAddress>
{
	public EmailAddressWriter(final Connection connection, final EmailAddress emailAddress)
	{
		super(connection, emailAddress, com.threatconnect.sdk.server.entity.EmailAddress.class, Type.EmailAddress);
	}
	
	@Override
	protected String buildID()
	{
		return indicatorSource.getAddress();
	}
}
