package com.threatconnect.app.playbooks.util;

import com.threatconnect.app.playbooks.content.entity.StringKeyValue;
import com.threatconnect.app.playbooks.content.accumulator.ContentException;
import com.threatconnect.app.playbooks.content.accumulator.StringAccumulator;

/**
 * @author Greg Marut
 */
public class StringKeyValueUtil
{
	/**
	 * Checks the key and value of the StringKeyValue object to resolve any embedded variables
	 *
	 * @param result
	 * @param stringAccumulator
	 * @throws ContentException
	 */
	public static void resolveEmbeddedVariables(StringKeyValue result, final StringAccumulator stringAccumulator)
		throws ContentException
	{
		//check to see if the key is a variable
		if (null != result.getKey() && PlaybooksVariableUtil.isVariable(result.getKey()))
		{
			result.setKey(stringAccumulator.readContent(result.getKey()));
		}
		
		//check to see if the value is a variable
		if (null != result.getValue() && PlaybooksVariableUtil.isVariable(result.getValue()))
		{
			result.setValue(stringAccumulator.readContent(result.getValue()));
		}
	}
}
