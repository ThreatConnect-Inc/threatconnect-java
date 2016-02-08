package com.threatconnect.sdk.app.util.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexExtractor
{
	private final Matcher matcher;
	
	public RegexExtractor(final String regex, final String input) throws MatchNotFoundException
	{
		this(Pattern.compile(regex), input);
	}
	
	public RegexExtractor(final Pattern pattern, final String input) throws MatchNotFoundException
	{
		// ensure that the pattern is not null
		if (null == pattern)
		{
			throw new IllegalArgumentException("pattern cannot be null");
		}
		
		this.matcher = pattern.matcher(input);
		
		// check to see if a match was not found
		if (!matcher.find())
		{
			throw new MatchNotFoundException();
		}
	}
	
	public Matcher getMatcher()
	{
		return matcher;
	}
	
	public String group(final int group)
	{
		return matcher.group(group);
	}
}
