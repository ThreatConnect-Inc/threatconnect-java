package com.threatconnect.sdk.playbooks.content.accumulator;

import com.threatconnect.sdk.playbooks.content.StandardType;
import com.threatconnect.sdk.playbooks.content.converter.StringConverter;
import com.threatconnect.sdk.playbooks.db.DBService;
import com.threatconnect.sdk.playbooks.util.PlaybooksVariableUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Greg Marut
 */
public class StringAccumulator extends ContentAccumulator<String>
{
	private static final Logger logger = LoggerFactory.getLogger(StringAccumulator.class);

	public StringAccumulator(DBService dbService)
	{
		super(dbService, StandardType.String, new StringConverter());
	}

	@Override
	public String readContent(String key) throws ContentException
	{
		return readContent(key, new Stack<String>());
	}

	private String readContent(final String key, final Stack<String> stack) throws ContentException
	{
		//lookup the value
		String value = super.readContent(key);

		//create a matcher that will check to see if there are any embedded string variables in here that need to be
		//resolved
		Matcher matcher = PlaybooksVariableUtil.VARIABLE_PATTERN.matcher(value);

		//while there are more results
		logger.trace("Looking for embedded variables.");
		while (matcher.find())
		{
			final String variable = matcher.group();
			logger.trace("Variable found: {}", variable);

			//make sure that this stack does not already contain this variable to prevent an infinite loop
			if (!stack.contains(variable))
			{
				//add this variable to the stack
				stack.add(variable);

				String embeddedResult = readContent(variable, stack);
				value = value.replaceFirst(Pattern.quote(variable), embeddedResult);

				//pop the variable off of the stack
				stack.pop();
			}
			else
			{
				logger.warn("Cyclical variable lookups detected. {} cannot be resolved to a concrete value.", variable);
			}
		}

		return value;
	}
}
