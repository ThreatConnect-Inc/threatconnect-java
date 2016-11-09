package com.threatconnect.app.playbooks.content.accumulator;

import com.threatconnect.app.playbooks.content.converter.StringKeyValueConverter;
import com.threatconnect.app.playbooks.content.entity.StringKeyValue;
import com.threatconnect.app.playbooks.db.DBService;
import com.threatconnect.app.playbooks.util.StringKeyValueUtil;
import com.threatconnect.app.addons.util.config.install.PlaybookVariableType;

/**
 * @author Greg Marut
 */
public class StringKeyValueAccumulator extends ContentAccumulator<StringKeyValue>
{
	private final StringAccumulator stringAccumulator;
	
	public StringKeyValueAccumulator(final DBService dbService)
	{
		super(dbService, PlaybookVariableType.KeyValue, new StringKeyValueConverter());
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
