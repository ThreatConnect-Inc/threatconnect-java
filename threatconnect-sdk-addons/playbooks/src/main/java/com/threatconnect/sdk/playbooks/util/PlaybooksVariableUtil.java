package com.threatconnect.sdk.playbooks.util;

import com.threatconnect.sdk.playbooks.content.StandardType;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Greg Marut
 */
public class PlaybooksVariableUtil
{
	//holds the regex pattern that identifies a variable
	public static final String VARIABLE_REGEX = "(#(?:[A-Za-z]+):(?:[\\d]+):(?:[A-Za-z0-9_.-]+)!([A-Za-z0-9_-]+))";
	public static final Pattern VARIABLE_PATTERN = Pattern.compile(VARIABLE_REGEX);
	
	public static boolean isVariable(final String input)
	{
		//make sure the input is not null
		if (null == input)
		{
			throw new IllegalArgumentException("input cannot be null");
		}
		
		return VARIABLE_PATTERN.matcher(input).matches();
	}
	
	public static StandardType extractVariableType(final String variableName)
	{
		//make sure this is a variable
		if (!isVariable(variableName))
		{
			throw new IllegalArgumentException(variableName + " is not a valid variable");
		}
		
		Matcher matcher = VARIABLE_PATTERN.matcher(variableName);
		matcher.find();
		return StandardType.valueOf(matcher.group(2));
	}
	
	public static boolean isStringType(final String variableName)
	{
		return StandardType.String.equals(extractVariableType(variableName));
	}
	
	public static boolean isStringArrayType(final String variableName)
	{
		return StandardType.StringArray.equals(extractVariableType(variableName));
	}
	
	public static boolean isBinaryType(final String variableName)
	{
		return StandardType.Binary.equals(extractVariableType(variableName));
	}
	
	public static boolean isBinaryArrayType(final String variableName)
	{
		return StandardType.BinaryArray.equals(extractVariableType(variableName));
	}
	
	public static boolean isKeyValueType(final String variableName)
	{
		return StandardType.KeyValue.equals(extractVariableType(variableName));
	}
	
	public static boolean isKeyValueArrayType(final String variableName)
	{
		return StandardType.KeyValueArray.equals(extractVariableType(variableName));
	}
	
	public static boolean isTCEntityType(final String variableName)
	{
		return StandardType.TCEntity.equals(extractVariableType(variableName));
	}
	
	public static boolean isTCEntityArrayType(final String variableName)
	{
		return StandardType.TCEntityArray.equals(extractVariableType(variableName));
	}
}
