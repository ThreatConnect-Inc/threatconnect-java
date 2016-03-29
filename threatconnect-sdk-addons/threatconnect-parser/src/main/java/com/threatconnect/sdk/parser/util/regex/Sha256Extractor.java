package com.threatconnect.sdk.parser.util.regex;

import com.threatconnect.sdk.parser.util.RegexUtil;

public class Sha256Extractor extends RegexExtractor
{
	public Sha256Extractor(String input) throws MatchNotFoundException
	{
		super(RegexUtil.REGEX_SHA256_EXTRACT, input);
	}
	
	public String getSha256()
	{
		return group(1);
	}
}
