package com.threatconnect.app.playbooks.content.accumulator;

import com.threatconnect.app.addons.util.config.install.StandardPlaybookType;
import com.threatconnect.app.playbooks.content.converter.KeyValueConverter;
import com.threatconnect.app.playbooks.content.entity.KeyValue;
import com.threatconnect.app.playbooks.db.DBService;
import com.threatconnect.app.playbooks.util.KeyValueUtil;

/**
 * @author Greg Marut
 */
public class KeyValueAccumulator extends TypedContentAccumulator<KeyValue>
{
	private final StringAccumulator stringAccumulator;
	
	public KeyValueAccumulator(final DBService dbService)
	{
		super(dbService, StandardPlaybookType.KeyValue, new KeyValueConverter());
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
	 * @return the content read from the database using the given key.
	 * @throws ContentException if there was an issue reading/writing to the database.
	 */
	public KeyValue readContent(final String key, final boolean resolveEmbeddedVariables)
		throws ContentException
	{
		//read the key value object and check for variables
		KeyValue result = super.readContent(key);
		
		//ensure that embedded variables should be resolved
		if (resolveEmbeddedVariables)
		{
			KeyValueUtil.resolveEmbeddedVariables(result, stringAccumulator);
		}
		
		return result;
	}
	
	@Override
	public KeyValue readContent(final String key) throws ContentException
	{
		return readContent(key, true);
	}
}
