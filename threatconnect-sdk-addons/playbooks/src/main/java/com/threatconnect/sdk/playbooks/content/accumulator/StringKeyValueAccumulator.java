package com.threatconnect.sdk.playbooks.content.accumulator;

import com.threatconnect.sdk.playbooks.content.StandardType;
import com.threatconnect.sdk.playbooks.content.converter.StringKeyValueConverter;
import com.threatconnect.sdk.playbooks.content.entity.StringKeyValue;
import com.threatconnect.sdk.playbooks.db.DBService;
import com.threatconnect.sdk.playbooks.util.StringKeyValueUtil;

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
		//read the key value object and check for variables
		StringKeyValue result = super.readContent(key);
		StringKeyValueUtil.resolveEmbeddedVariables(result, stringAccumulator);
		
		return result;
	}
}
