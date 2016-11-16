package com.threatconnect.app.playbooks.content.accumulator;

import com.threatconnect.app.addons.util.config.install.PlaybookVariableType;
import com.threatconnect.app.playbooks.content.converter.StringConverter;
import com.threatconnect.app.playbooks.db.DBService;
import com.threatconnect.app.playbooks.util.PlaybooksVariableUtil;
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
	
	public static final String VARIABLE_NOT_FOUND = "<VARIABLE_NOT_FOUND>";
	public static final String CYCLICAL_VARIABLE_REFERENCE = "<CYCLICAL_VARIABLE_REFERENCE>";
	
	public StringAccumulator(DBService dbService)
	{
		super(dbService, PlaybookVariableType.String, new StringConverter());
	}
	
	@Override
	public String readContent(String content) throws ContentException
	{
		return readContent(content, new Stack<String>());
	}
	
	private String readContent(final String content, final Stack<String> stack) throws ContentException
	{
		String value = content;
		
		//make sure the value is not null
		if (null != value)
		{
			//create a matcher that will check to see if there are any embedded string variables in here that need to be
			//resolved
			Matcher matcher = PlaybooksVariableUtil.getVariablePatternMatcher(value);
			
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
					
					//lookup the value
					String resolvedVariable = super.readContent(variable);
					
					//recursively check the resolved variable to see if it contains any embedded variables that need to be resolved
					String embeddedResult = readContent(resolvedVariable, stack);
					
					//make sure the embedded result is not null
					if(null != embeddedResult)
					{
						value = value.replaceFirst(Pattern.quote(variable), embeddedResult);
					}
					//the variable resolved to null so now check to see if the entire value string was the variable
					else if (value.equals(variable))
					{
						return null;
					}
					else
					{
						//this variable could not be resolved so replace it with the default text
						value = value.replaceFirst(Pattern.quote(variable), VARIABLE_NOT_FOUND);
					}
					
					//pop the variable off of the stack
					stack.pop();
				}
				else
				{
					logger.warn("Cyclical variable lookups detected. {} cannot be resolved to a concrete value.",
						variable);
					
					//this variable is cyclical
					value = value.replaceFirst(Pattern.quote(variable), CYCLICAL_VARIABLE_REFERENCE);
				}
			}
		}
		
		return value;
	}
}
