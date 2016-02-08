package com.threatconnect.sdk.app.writer;

import com.threatconnect.sdk.app.model.Address;
import com.threatconnect.sdk.conn.Connection;
import com.threatconnect.sdk.server.entity.Indicator.Type;

public class AddressWriter extends IndicatorWriter<Address, com.threatconnect.sdk.server.entity.Address>
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
