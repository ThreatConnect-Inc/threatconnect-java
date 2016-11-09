package com.threatconnect.app.playbooks.util;

import com.threatconnect.app.addons.util.config.install.PlaybookVariableType;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Greg Marut
 */
public class PlaybooksVariableUtil
{
	//holds the regex pattern that identifies a variable
	public static final String VARIABLE_REGEX = "(#(?:[A-Za-z]+):(?:[\\d]+):([A-Za-z0-9_.-]+)!([A-Za-z0-9_-]+))";
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
	
	public static String extractVariableName(final String variable)
	{
		//make sure this is a variable
		if (!isVariable(variable))
		{
			throw new IllegalArgumentException(variable + " is not a valid variable");
		}
		
		Matcher matcher = VARIABLE_PATTERN.matcher(variable);
		matcher.find();
		return matcher.group(2);
	}
	
	public static PlaybookVariableType extractVariableType(final String variable)
	{
		//make sure this is a variable
		if (!isVariable(variable))
		{
			throw new IllegalArgumentException(variable + " is not a valid variable");
		}
		
		Matcher matcher = VARIABLE_PATTERN.matcher(variable);
		matcher.find();
		return PlaybookVariableType.valueOf(matcher.group(3));
	}
	
	public static boolean isStringType(final String variable)
	{
		return PlaybookVariableType.String.equals(extractVariableType(variable));
	}
	
	public static boolean isStringArrayType(final String variable)
	{
		return PlaybookVariableType.StringArray.equals(extractVariableType(variable));
	}
	
	public static boolean isBinaryType(final String variable)
	{
		return PlaybookVariableType.Binary.equals(extractVariableType(variable));
	}
	
	public static boolean isBinaryArrayType(final String variable)
	{
		return PlaybookVariableType.BinaryArray.equals(extractVariableType(variable));
	}
	
	public static boolean isKeyValueType(final String variable)
	{
		return PlaybookVariableType.KeyValue.equals(extractVariableType(variable));
	}
	
	public static boolean isKeyValueArrayType(final String variable)
	{
		return PlaybookVariableType.KeyValueArray.equals(extractVariableType(variable));
	}
	
	public static boolean isTCEntityType(final String variable)
	{
		return PlaybookVariableType.TCEntity.equals(extractVariableType(variable));
	}
	
	public static boolean isTCEntityArrayType(final String variable)
	{
		return PlaybookVariableType.TCEntityArray.equals(extractVariableType(variable));
	}
}
