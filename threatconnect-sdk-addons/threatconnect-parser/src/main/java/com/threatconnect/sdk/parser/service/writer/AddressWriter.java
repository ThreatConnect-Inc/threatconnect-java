package com.threatconnect.sdk.parser.service.writer;

import com.threatconnect.sdk.conn.Connection;
import com.threatconnect.sdk.model.Address;
import com.threatconnect.sdk.server.entity.Indicator.Type;

public class AddressWriter extends TypedIndicatorWriter<Address, com.threatconnect.sdk.server.entity.Address>
{
	public AddressWriter(final Connection connection, final Address address)
	{
		super(connection, address, com.threatconnect.sdk.server.entity.Address.class, Type.Address);
	}
	
	@Override
	protected String buildID()
	{
		return indicatorSource.getIp();
	}
}
