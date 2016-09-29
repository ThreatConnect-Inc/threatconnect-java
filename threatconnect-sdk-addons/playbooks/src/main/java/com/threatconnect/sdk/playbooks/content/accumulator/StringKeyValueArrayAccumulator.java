package com.threatconnect.sdk.playbooks.content.accumulator;

import com.threatconnect.sdk.playbooks.content.StandardType;
import com.threatconnect.sdk.playbooks.content.converter.StringKeyValueListConverter;
import com.threatconnect.sdk.playbooks.content.entity.StringKeyValue;
import com.threatconnect.sdk.playbooks.db.DBService;
import com.threatconnect.sdk.playbooks.util.PlaybooksVariableUtil;

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
		
		for (StringKeyValue result : results)
		{
			//check to see if the key is a variable
			if (PlaybooksVariableUtil.isVariable(result.getKey()))
			{
				result.setKey(stringAccumulator.readContent(result.getKey()));
			}
			
			//check to see if the value is a variable
			if (PlaybooksVariableUtil.isVariable(result.getValue()))
			{
				result.setValue(stringAccumulator.readContent(result.getValue()));
			}
		}
		
		return results;
	}
}
