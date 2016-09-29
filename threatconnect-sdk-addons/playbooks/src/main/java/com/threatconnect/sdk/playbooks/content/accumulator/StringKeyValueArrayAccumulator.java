package com.threatconnect.sdk.playbooks.content.accumulator;

import com.threatconnect.sdk.playbooks.content.StandardType;
import com.threatconnect.sdk.playbooks.content.converter.StringKeyValueListConverter;
import com.threatconnect.sdk.playbooks.content.entity.StringKeyValue;
import com.threatconnect.sdk.playbooks.db.DBService;
import com.threatconnect.sdk.playbooks.util.StringKeyValueUtil;

import java.util.List;

/**
 * @author Greg Marut
 */
public class StringKeyValueArrayAccumulator extends ContentAccumulator<List<StringKeyValue>>
{
	private final StringAccumulator stringAccumulator;
	
	public StringKeyValueArrayAccumulator(final DBService dbService)
	{
		super(dbService, StandardType.KeyValue, new StringKeyValueListConverter());
		this.stringAccumulator = new StringAccumulator(dbService);
	}
	
	@Override
	public List<StringKeyValue> readContent(final String key) throws ContentException
	{
		//read the key value object
		List<StringKeyValue> results = super.readContent(key);
		
		//for each of th results
		for (StringKeyValue result : results)
		{
			//resolve any embedded variables in this StringKeyValue
			StringKeyValueUtil.resolveEmbeddedVariables(result, stringAccumulator);
		}
		
		return results;
	}
}
