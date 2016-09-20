package com.threatconnect.sdk.blueprints.content.accumulator;

import com.threatconnect.sdk.blueprints.content.StandardType;
import com.threatconnect.sdk.blueprints.content.converter.StringKeyValueConverter;
import com.threatconnect.sdk.blueprints.content.entity.StringKeyValue;
import com.threatconnect.sdk.blueprints.db.DBService;

/**
 * @author Greg Marut
 */
public class StringKeyValueAccumulator extends ContentAccumulator<StringKeyValue>
{
	private final StringAccumulator stringAccumulator;
	
	public StringKeyValueAccumulator(final DBService dbService)
	{
		super(dbService, StandardType.KeyValue, new StringKeyValueConverter());
		this.stringAccumulator = new StringAccumulator(dbService);
	}
	
	@Override
	public StringKeyValue readContent(final String key) throws ContentException
	{
		return super.readContent(key);
	}
}
