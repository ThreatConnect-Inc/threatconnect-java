package com.threatconnect.app.playbooks.content.accumulator;

import com.threatconnect.app.addons.util.config.install.PlaybookVariableType;
import com.threatconnect.app.playbooks.content.converter.StringKeyValueConverter;
import com.threatconnect.app.playbooks.content.entity.StringKeyValue;
import com.threatconnect.app.playbooks.db.DBService;
import com.threatconnect.app.playbooks.util.StringKeyValueUtil;

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
	
	/**
	 * Reads the content while looking for embedded variables and replacing them with their resolved value.
	 *
	 * @param key                      the key to check to resolve any embedded variables
	 * @param resolveEmbeddedVariables when true, recursion is allowed when resolving variables. Therefore, if a
	 *                                 variable is resolved and it contains more variables embedded in the string, the
	 *                                 lookups continue until all variables have been recursively resolved or a variable
	 *                                 could not be found.
	 * @return
	 * @throws ContentException
	 */
	public StringKeyValue readContent(final String key, final boolean resolveEmbeddedVariables)
		throws ContentException
	{
		//read the key value object and check for variables
		StringKeyValue result = super.readContent(key);
		
		//ensure that embedded variables should be resolved
		if (resolveEmbeddedVariables)
		{
			StringKeyValueUtil.resolveEmbeddedVariables(result, stringAccumulator);
		}
		
		return result;
	}
	
	@Override
	public StringKeyValue readContent(final String key) throws ContentException
	{
		return readContent(key, true);
	}
}
