package com.threatconnect.sdk.parser.util.regex;

import com.threatconnect.sdk.parser.util.RegexUtil;

public class MD5Extractor extends RegexExtractor
{
	public MD5Extractor(String input) throws MatchNotFoundException
	{
		super(RegexUtil.REGEX_MD5_EXTRACT, input);
	}
	
	public String getMD5()
	{
		return group(1);
	}
}
