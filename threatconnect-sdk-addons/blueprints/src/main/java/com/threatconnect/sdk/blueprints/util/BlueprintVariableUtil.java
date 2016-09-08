package com.threatconnect.sdk.blueprints.util;

import java.util.regex.Pattern;

/**
 * @author Greg Marut
 */
public class BlueprintVariableUtil
{
	//holds the regex pattern that identifies a variable
	private static final String VARIABLE_REGEX = "(#(?:[A-Za-z]+):(?:[\\d]+):(?:[A-Za-z0-9_-]+)!(?:[A-Za-z0-9_-]+))";
	private static final Pattern VARIABLE_PATTERN = Pattern.compile(VARIABLE_REGEX);

	public static boolean isVariable(final String input)
	{
		return VARIABLE_PATTERN.matcher(input).matches();
	}
}
