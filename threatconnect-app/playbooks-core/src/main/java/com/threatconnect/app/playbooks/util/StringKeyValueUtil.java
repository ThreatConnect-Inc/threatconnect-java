package com.threatconnect.app.playbooks.util;

import com.threatconnect.app.playbooks.content.accumulator.ContentException;
import com.threatconnect.app.playbooks.content.accumulator.StringAccumulator;
import com.threatconnect.app.playbooks.content.entity.StringKeyValue;

/**
 * @author Greg Marut
 */
public class StringKeyValueUtil
{
	/**
	 * Checks the key and value of the StringKeyValue object to resolve any embedded variables
	 *
	 * @param result            the StringKeyValue object to use which will be checked for embedded variables
	 * @param stringAccumulator the object that is used to read the content
	 * @throws ContentException if there was an issue reading/writing to the database.
	 */
	public static void resolveEmbeddedVariables(StringKeyValue result, final StringAccumulator stringAccumulator)
		throws ContentException
	{
		//make sure the result is not null
		if (null != result)
		{
			//check the key and value to see if any variables need to be resolved within
			result.setKey(stringAccumulator.readContent(result.getKey()));
			result.setValue(stringAccumulator.readContent(result.getValue()));
		}
	}
}
