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
			
			//check to see if the value is a variable
			if (PlaybooksVariableUtil.isVariable(result.getKey()))
			{
				//convert all embedded variables
				String value = stringAccumulator.readContent(result.getValue());
				
				//to eliminate the double-quotation mark issue, we need to remove the extra quotes that were added
				//because multiple strings were deserialized
				if (value.startsWith("\"") && value.endsWith("\""))
				{
					//chop off the beginning and ending quotation marks
					value = value.substring(1, value.length() - 2);
				}
				
				result.setValue(value);
			}
			else
			{
				//convert all embedded variables as needed
				result.setValue(stringAccumulator.readContent(result.getValue()));
			}
		}
	}
}
