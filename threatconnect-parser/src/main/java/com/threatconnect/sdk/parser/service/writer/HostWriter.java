package com.threatconnect.sdk.parser.service.writer;

import com.threatconnect.sdk.conn.Connection;
import com.threatconnect.sdk.parser.model.Host;
import com.threatconnect.sdk.server.entity.Indicator.Type;

public class HostWriter extends IndicatorWriter<Host, com.threatconnect.sdk.server.entity.Host>
{
	public HostWriter(final Connection connection, final Host host)
	{
		super(connection, host, com.threatconnect.sdk.server.entity.Host.class, Type.Host);
	}
	
	@Override
	protected String buildID()
	{
		// make sure that the host name is not null
		if (null != indicatorSource.getHostName())
		{
			return indicatorSource.getHostName().toLowerCase();
		}
		else
		{
			return null;
		}
	}
}
