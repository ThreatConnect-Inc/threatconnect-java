package com.threatconnect.sdk.parser.service.writer;

import com.threatconnect.sdk.client.reader.AbstractIndicatorReaderAdapter;
import com.threatconnect.sdk.client.reader.ReaderAdapterFactory;
import com.threatconnect.sdk.client.writer.AbstractIndicatorWriterAdapter;
import com.threatconnect.sdk.client.writer.WriterAdapterFactory;
import com.threatconnect.sdk.conn.Connection;
import com.threatconnect.sdk.model.CustomIndicator;

public class CustomIndicatorWriter
	extends IndicatorWriter<CustomIndicator, com.threatconnect.sdk.server.entity.CustomIndicator>
{
	public CustomIndicatorWriter(final Connection connection, final CustomIndicator customIndicator)
	{
		super(connection, customIndicator, com.threatconnect.sdk.server.entity.CustomIndicator.class);
	}
	
	@Override
	protected String buildID()
	{
		return indicatorSource.getIdentifier();
	}
	
	@Override
	protected AbstractIndicatorReaderAdapter<com.threatconnect.sdk.server.entity.CustomIndicator> createReaderAdapter()
	{
		return ReaderAdapterFactory.createCustomIndicatorReader(connection, getApiBranch());
	}
	
	@Override
	protected AbstractIndicatorWriterAdapter<com.threatconnect.sdk.server.entity.CustomIndicator> createWriterAdapter()
	{
		return WriterAdapterFactory.createCustomIndicatorWriter(connection, getApiBranch());
	}
	
	private String getApiBranch()
	{
		switch (indicatorSource.getIndicatorType())
		{
			case "Mutex":
				return "mutexes";
			case "Cidr":
				return "cidrBlocks";
			case "Email Subject":
				return "emailSubject";
			default:
				return indicatorSource.getIndicatorType();
		}
	}
}
