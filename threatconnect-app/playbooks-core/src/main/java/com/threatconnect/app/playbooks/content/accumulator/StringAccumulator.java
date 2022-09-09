package com.threatconnect.app.playbooks.content.accumulator;

import com.threatconnect.app.addons.util.config.install.StandardPlaybookType;
import com.threatconnect.app.playbooks.content.converter.StringConverter;
import com.threatconnect.app.apps.db.DBService;
import com.threatconnect.app.playbooks.util.PlaybooksVariableUtil;
import com.threatconnect.app.playbooks.variable.PlaybooksVariable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Greg Marut
 */
public class StringAccumulator extends TypedContentAccumulator<String>
{
	private static final Logger logger = LoggerFactory.getLogger(StringAccumulator.class);
	
	public static final String VARIABLE_NOT_FOUND = "<VARIABLE_NOT_FOUND>";
	public static final String CYCLICAL_VARIABLE_REFERENCE = "<CYCLICAL_VARIABLE_REFERENCE>";
	
	public StringAccumulator(final DBService dbService)
	{
		super(dbService, StandardPlaybookType.String, new StringConverter());
	}
	
	@Override
	public String readContent(final String content) throws ContentException
	{
		return readContent(content, true);
	}
	
	/**
	 * Reads the content while looking for embedded variables and replacing them with their resolved value.
	 *
	 * @param content                     the content to check to resolve any embedded variables
	 * @param recursiveVariableResolution when true, recursion is allowed when resolving variables. Therefore, if a
	 *                                    variable is resolved and it contains more variables embedded in the string,
	 *                                    the lookups continue until all variables have been recursively resolved or a
	 *                                    variable could not be found.
	 * @return the content read from the database using the given key.
	 * @throws ContentException if there was an issue reading/writing to the database.
	 */
	public String readContent(final String content, final boolean recursiveVariableResolution) throws ContentException
	{
		return readContent(content, recursiveVariableResolution, new Stack<String>());
	}
	
	private String readContent(final String content, final boolean recursiveVariableResolution,
		final Stack<String> stack) throws ContentException
	{
		String value = content;
		
		//make sure the value is not null
		if (null != value)
		{
			//create a matcher that will check to see if there are any embedded string variables in here that need to be
			//resolved
			List<PlaybooksVariable> playbooksVariables = PlaybooksVariableUtil.extractPlaybooksVariables(value);
			
			//while there are more results
			logger.trace("Looking for embedded variables.");
			for (PlaybooksVariable playbooksVariable : playbooksVariables)
			{
				final String variable = playbooksVariable.toString();
				logger.trace("Variable found: {}", variable);
				
				//make sure that this stack does not already contain this variable to prevent an infinite loop
				if (!stack.contains(variable))
				{
					//add this variable to the stack
					stack.add(variable);
					
					//make sure that this variable is another string
					if (PlaybooksVariableUtil.isStringType(variable))
					{
						//lookup the value
						final String resolvedVariable = super.readContent(variable);
						final String embeddedResult;
						
						//check to see if variables should be looked up recursively
						if (recursiveVariableResolution)
						{
							//recursively check the resolved variable to see if it contains any embedded variables that need to be resolved
							embeddedResult = readContent(resolvedVariable, true, stack);
						}
						else
						{
							//since there was no recursive variable resolution, the result is limited to only 1 lookup
							embeddedResult = resolvedVariable;
						}
						
						//make sure the embedded result is not null
						if (null != embeddedResult)
						{
							value = value.replaceFirst(playbooksVariable.toRegexReplaceString(),
								Matcher.quoteReplacement(embeddedResult));
						}
						//the variable resolved to null so now check to see if the entire value string was the variable
						else if (value.equals(variable))
						{
							return null;
						}
						else
						{
							//this variable could not be resolved so replace it with the default text
							value = value
								.replaceFirst(Pattern.quote(variable), Matcher.quoteReplacement(VARIABLE_NOT_FOUND));
						}
					}
					
					//pop the variable off of the stack
					stack.pop();
				}
				else
				{
					logger.warn("Cyclical variable lookups detected. {} cannot be resolved to a concrete value.",
						variable);
					
					//this variable is cyclical
					value = value
						.replaceFirst(Pattern.quote(variable), Matcher.quoteReplacement(CYCLICAL_VARIABLE_REFERENCE));
				}
			}
		}
		
		return value;
	}
}
