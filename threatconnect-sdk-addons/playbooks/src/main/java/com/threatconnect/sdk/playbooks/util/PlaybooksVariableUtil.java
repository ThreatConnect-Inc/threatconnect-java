package com.threatconnect.sdk.playbooks.util;

import java.util.regex.Pattern;

/**
 * @author Greg Marut
 */
public class PlaybooksVariableUtil
{
	//holds the regex pattern that identifies a variable
	public static final String VARIABLE_REGEX = "(#(?:[A-Za-z]+):(?:[\\d]+):(?:[A-Za-z0-9_-]+)!(?:[A-Za-z0-9_-]+))";
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
}
