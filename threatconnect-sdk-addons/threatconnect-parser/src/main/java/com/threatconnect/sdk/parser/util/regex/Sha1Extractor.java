package com.threatconnect.sdk.parser.util.regex;

import com.threatconnect.sdk.parser.util.RegexUtil;

public class Sha1Extractor extends RegexExtractor
{
	public Sha1Extractor(String input) throws MatchNotFoundException
	{
		super(RegexUtil.REGEX_SHA1_EXTRACT, input);
	}
	
	public String getSha1()
	{
		return group(1);
	}
}
