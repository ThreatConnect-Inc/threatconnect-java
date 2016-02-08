package com.threatconnect.sdk.app.writer;

import com.threatconnect.sdk.app.model.Url;
import com.threatconnect.sdk.conn.Connection;
import com.threatconnect.sdk.server.entity.Indicator.Type;

public class UrlWriter extends IndicatorWriter<Url, com.threatconnect.sdk.server.entity.Url>
{
	public UrlWriter(final Connection connection, final Url url)
	{
		super(connection, url, com.threatconnect.sdk.server.entity.Url.class, Type.Url);
	}
	
	@Override
	protected String buildID()
	{
		return indicatorSource.getText();
	}
}
