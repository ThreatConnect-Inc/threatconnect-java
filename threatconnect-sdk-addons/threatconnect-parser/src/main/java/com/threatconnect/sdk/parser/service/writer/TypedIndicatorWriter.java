package com.threatconnect.sdk.parser.service.writer;

import com.threatconnect.sdk.client.reader.AbstractIndicatorReaderAdapter;
import com.threatconnect.sdk.client.reader.ReaderAdapterFactory;
import com.threatconnect.sdk.client.writer.AbstractIndicatorWriterAdapter;
import com.threatconnect.sdk.client.writer.WriterAdapterFactory;
import com.threatconnect.sdk.conn.Connection;
import com.threatconnect.sdk.model.Indicator;

/**
 * @author Greg Marut
 */
public abstract class TypedIndicatorWriter<E extends Indicator, T extends com.threatconnect.sdk.server.entity.Indicator> extends IndicatorWriter<E, T>
{
	private final com.threatconnect.sdk.server.entity.Indicator.Type tcIndicatorType;
	
	public TypedIndicatorWriter(final Connection connection, final E source,
		final Class<T> tcModelClass, final com.threatconnect.sdk.server.entity.Indicator.Type tcIndicatorType)
	{
		super(connection, source, tcModelClass);
		this.tcIndicatorType = tcIndicatorType;
	}
	
	/**
	 * Creates a reader adapter for this class
	 *
	 * @return the reader adapter for this indicator
	 */
	@Override
	protected AbstractIndicatorReaderAdapter<T> createReaderAdapter()
	{
		return ReaderAdapterFactory.createIndicatorReader(tcIndicatorType, connection);
	}
	
	/**
	 * A convenience method for creating a writer adapter for this class
	 *
	 * @return the writer adapter for this indicator
	 */
	@Override
	protected AbstractIndicatorWriterAdapter<T> createWriterAdapter()
	{
		return WriterAdapterFactory.createIndicatorWriter(tcIndicatorType, connection);
	}
}
