package com.threatconnect.app.playbooks.util;

import com.threatconnect.app.addons.util.config.install.StandardPlaybookType;
import com.threatconnect.app.playbooks.variable.InvalidVariableNamespace;
import com.threatconnect.app.playbooks.variable.InvalidVariableType;
import com.threatconnect.app.playbooks.variable.PlaybooksVariable;
import com.threatconnect.app.playbooks.variable.PlaybooksVariableNamespace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Greg Marut
 */
public class PlaybooksVariableUtil
{
	private static final Logger logger = LoggerFactory.getLogger(PlaybooksVariableUtil.class);
	
	/**
	 * Holds the regex pattern that identifies a variable anywhere in a string
	 */
	public static final String VARIABLE_REGEX =
		"#([A-Za-z]+):([\\d]+):([A-Za-z0-9_.-]+)!(StringArray|BinaryArray|KeyValueArray|TCEntityArray|TCEnhancedEntityArray|String|Binary|KeyValue|TCEntity|TCEnhancedEntity|(?:(?!String)(?!Binary)(?!KeyValue)(?!TCEntity)(?!TCEnhancedEntity)[A-Za-z0-9_-]+))";
	public static final Pattern VARIABLE_PATTERN = Pattern.compile(VARIABLE_REGEX);
	
	/**
	 * Holds the regex pattern that determines if a string is a variable with nothing else
	 */
	public static final String VARIABLE_REGEX_EXACT = "^" + VARIABLE_REGEX + "$";
	public static final Pattern VARIABLE_PATTERN_EXACT = Pattern.compile(VARIABLE_REGEX_EXACT);
	
	public static final int VARIABLE_GROUP_NAMESPACE = 1;
	public static final int VARIABLE_GROUP_ID = 2;
	public static final int VARIABLE_GROUP_NAME = 3;
	public static final int VARIABLE_GROUP_TYPE = 4;
	
	private static Matcher getVariablePatternMatcher(final String input)
	{
		//make sure the input is not null
		if (null == input)
		{
			throw new IllegalArgumentException("input cannot be null");
		}
		
		return VARIABLE_PATTERN.matcher(input.trim());
	}
	
	private static Matcher getVariablePatternExactMatcher(final String input)
	{
		//make sure the input is not null
		if (null == input)
		{
			throw new IllegalArgumentException("input cannot be null");
		}
		
		return VARIABLE_PATTERN_EXACT.matcher(input.trim());
	}
	
	/**
	 * Returns whether the input text is a variable
	 *
	 * @param input the text that is checked to see if it is a variable
	 * @return true if the input is not null and is a variable, false otherwise
	 */
	public static boolean isVariable(final String input)
	{
		return (null != input) && getVariablePatternExactMatcher(input).matches();
	}
	
	/**
	 * Returns whether the input text contains a variable anywhere.
	 *
	 * @param input the text to use to look for variables
	 * @return true if the input is not null and contains a variable, false otherwise
	 */
	public static boolean containsVariable(final String input)
	{
		return (null != input) && getVariablePatternMatcher(input).find();
	}
	
	public static String extractVariableName(final String variable)
	{
		//make sure this is a variable
		if (!isVariable(variable))
		{
			throw new IllegalArgumentException(variable + " is not a valid variable");
		}
		
		Matcher matcher = getVariablePatternExactMatcher(variable);
		matcher.find();
		return matcher.group(VARIABLE_GROUP_NAME);
	}
	
	/**
	 * Returns a list of playbooks variables
	 *
	 * @param variable the variable text
	 * @return a list ok playbook variables that were found inside this text
	 */
	public static PlaybooksVariable extractPlaybooksVariable(final String variable)
	{
		//make sure this is a variable
		if (!isVariable(variable))
		{
			throw new IllegalArgumentException(variable + " is not a valid variable");
		}
		
		//create a new matcher based on the text
		Matcher matcher = getVariablePatternMatcher(variable);
		matcher.find();
		return toPlaybookVariable(matcher);
	}
	
	/**
	 * Returns a list of playbooks variables
	 *
	 * @param text the text to search for playbook variables
	 * @return a list ok playbook variables that were found inside this text
	 */
	public static List<PlaybooksVariable> extractPlaybooksVariables(final String text)
	{
		return extractPlaybooksVariables(text, null);
	}
	
	/**
	 * Returns a list of playbooks variables that match the given namespace
	 *
	 * @param text                             the text to search for the playbooks
	 * @param playbooksVariableNamespaceFilter only return results in this given namespace
	 * @return the list of playbook variables found in the text
	 */
	public static List<PlaybooksVariable> extractPlaybooksVariables(final String text,
		final PlaybooksVariableNamespace playbooksVariableNamespaceFilter)
	{
		//holds the list of playbooks variable results
		List<PlaybooksVariable> results = new ArrayList<PlaybooksVariable>();
		
		//create a new matcher based on the text
		Matcher matcher = getVariablePatternMatcher(text);
		
		//while there are more variables found in this text
		while (matcher.find())
		{
			try
			{
				//create the playbooks variable object
				PlaybooksVariable playbooksVariable = toPlaybookVariable(matcher);
				
				//determine if the playbooks variable namespace filter needs to be applied by either checking if the filter is either null or if they match
				boolean namespaceFilterCheck =
					null == playbooksVariableNamespaceFilter || playbooksVariableNamespaceFilter
						.equals(playbooksVariable.getNamespace());
				
				//check to see if the filters passed before adding to the results
				if (namespaceFilterCheck)
				{
					results.add(playbooksVariable);
				}
			}
			catch (InvalidVariableNamespace | InvalidVariableType e)
			{
				logger.warn(e.getMessage(), e);
			}
		}
		
		return results;
	}
	
	private static PlaybooksVariable toPlaybookVariable(final Matcher matcher)
	{
		PlaybooksVariableNamespace playbooksVariableNamespace;
		String playbookVariableType;
		
		//extract all of the parts from the variable found
		try
		{
			playbooksVariableNamespace = PlaybooksVariableNamespace.valueOf(matcher.group(VARIABLE_GROUP_NAMESPACE));
		}
		catch (IllegalArgumentException e)
		{
			throw new InvalidVariableNamespace(matcher.group(VARIABLE_GROUP_NAMESPACE));
		}
		
		try
		{
			playbookVariableType = trimTrailingExclamation(matcher.group(VARIABLE_GROUP_TYPE));
		}
		catch (IllegalArgumentException e)
		{
			throw new InvalidVariableType(trimTrailingExclamation(matcher.group(VARIABLE_GROUP_NAMESPACE)));
		}
		
		long id = Long.parseLong(matcher.group(VARIABLE_GROUP_ID));
		String name = matcher.group(VARIABLE_GROUP_NAME);
		
		//create a new playbooks variable
		return new PlaybooksVariable(playbooksVariableNamespace, id, name, playbookVariableType);
	}
	
	public static String extractVariableType(final String variable)
	{
		//make sure this is a variable
		if (!isVariable(variable))
		{
			throw new IllegalArgumentException(variable + " is not a valid variable");
		}
		
		Matcher matcher = getVariablePatternExactMatcher(variable);
		matcher.find();
		//:TODO: this is a temporary solution to remove the extra ! that is caught by the regex
		return trimTrailingExclamation(matcher.group(VARIABLE_GROUP_TYPE));
	}
	
	public static boolean isStringType(final String variable)
	{
		return StandardPlaybookType.String.toString().equalsIgnoreCase(extractVariableType(variable));
	}
	
	public static boolean isStringArrayType(final String variable)
	{
		return StandardPlaybookType.StringArray.toString().equalsIgnoreCase(extractVariableType(variable));
	}
	
	public static boolean isBinaryType(final String variable)
	{
		return StandardPlaybookType.Binary.toString().equalsIgnoreCase(extractVariableType(variable));
	}
	
	public static boolean isBinaryArrayType(final String variable)
	{
		return StandardPlaybookType.BinaryArray.toString().equalsIgnoreCase(extractVariableType(variable));
	}
	
	public static boolean isKeyValueType(final String variable)
	{
		return StandardPlaybookType.KeyValue.toString().equalsIgnoreCase(extractVariableType(variable));
	}
	
	public static boolean isKeyValueArrayType(final String variable)
	{
		return StandardPlaybookType.KeyValueArray.toString().equalsIgnoreCase(extractVariableType(variable));
	}
	
	public static boolean isTCEntityType(final String variable)
	{
		return StandardPlaybookType.TCEntity.toString().equalsIgnoreCase(extractVariableType(variable));
	}
	
	public static boolean isTCEntityArrayType(final String variable)
	{
		return StandardPlaybookType.TCEntityArray.toString().equalsIgnoreCase(extractVariableType(variable));
	}
	
	/**
	 * Removes the trailing exclamation point from the text. Used for variable types that may or may not have the
	 * optional ! at the end
	 *
	 * @param text
	 * @return
	 */
	private static String trimTrailingExclamation(final String text)
	{
		//check to see if there is a trailing exclamation point
		if (null != text && text.endsWith("!"))
		{
			return text.substring(0, text.length() - 1);
		}
		else
		{
			return text;
		}
	}
}
