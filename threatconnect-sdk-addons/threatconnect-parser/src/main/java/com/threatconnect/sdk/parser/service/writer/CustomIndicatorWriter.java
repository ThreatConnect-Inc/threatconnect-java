package com.threatconnect.sdk.parser.service.writer;

import com.threatconnect.sdk.client.reader.AbstractIndicatorReaderAdapter;
import com.threatconnect.sdk.client.reader.ReaderAdapterFactory;
import com.threatconnect.sdk.client.writer.AbstractIndicatorWriterAdapter;
import com.threatconnect.sdk.client.writer.WriterAdapterFactory;
import com.threatconnect.sdk.conn.Connection;
import com.threatconnect.sdk.parser.model.CustomIndicator;

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
		return ReaderAdapterFactory.createCustomIndicatorReader(connection, indicatorSource.getIndicatorType());
	}
	
	@Override
	protected AbstractIndicatorWriterAdapter<com.threatconnect.sdk.server.entity.CustomIndicator> createWriterAdapter()
	{
		return WriterAdapterFactory.createCustomIndicatorWriter(connection, indicatorSource.getIndicatorType());
	}
}
