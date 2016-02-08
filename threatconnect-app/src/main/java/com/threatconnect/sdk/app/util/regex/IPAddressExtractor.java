package com.threatconnect.sdk.app.util.regex;

import com.threatconnect.sdk.app.util.RegexUtil;

public class IPAddressExtractor extends RegexExtractor
{
	public IPAddressExtractor(String input) throws MatchNotFoundException
	{
		super(RegexUtil.REGEX_IP_EXTRACT, input);
	}
	
	public String getIPAddress()
	{
		return group(1);
	}
}
