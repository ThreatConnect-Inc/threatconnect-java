package com.threatconnect.app.playbooks.content.accumulator;

import com.threatconnect.app.addons.util.config.install.StandardPlaybookType;
import com.threatconnect.app.playbooks.content.converter.KeyValueListConverter;
import com.threatconnect.app.playbooks.content.entity.KeyValue;
import com.threatconnect.app.apps.db.DBService;
import com.threatconnect.app.playbooks.util.KeyValueUtil;

import java.util.List;

/**
 * @author Greg Marut
 */
public class KeyValueArrayAccumulator extends TypedContentAccumulator<List<KeyValue>>
{
	private final StringAccumulator stringAccumulator;
	
	public KeyValueArrayAccumulator(final DBService dbService)
	{
		super(dbService, StandardPlaybookType.KeyValueArray, new KeyValueListConverter());
		this.stringAccumulator = new StringAccumulator(dbService);
	}
	
	@Override
	public List<KeyValue> readContent(final String key) throws ContentException
	{
		return readContent(key, true);
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
	public List<KeyValue> readContent(final String key, final boolean resolveEmbeddedVariables)
		throws ContentException
	{
		//read the key value object
		List<KeyValue> results = super.readContent(key);
		
		//check to see if embedded variables should be resolved and if the results are not null
		if (resolveEmbeddedVariables && results != null)
		{
			//for each of the results
			for (KeyValue result : results)
			{
				//resolve any embedded variables in this KeyValue
				KeyValueUtil.resolveEmbeddedVariables(result, stringAccumulator);
			}
		}
		
		return results;
	}
}
