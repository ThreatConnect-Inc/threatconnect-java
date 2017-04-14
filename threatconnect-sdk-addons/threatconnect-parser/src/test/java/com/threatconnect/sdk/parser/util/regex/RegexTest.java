package com.threatconnect.sdk.parser.util.regex;

import org.junit.Assert;
import org.junit.Test;

import com.threatconnect.sdk.parser.util.RegexUtil;

public class RegexTest
{
	@Test
	public void testIPAddress()
	{
		Assert.assertTrue(RegexUtil.REGEX_IP_FORMAT.matcher("68.168.209.242").matches());
		Assert.assertTrue(RegexUtil.REGEX_IP_FORMAT.matcher("10.0.0.1").matches());
		Assert.assertTrue(RegexUtil.REGEX_IP_FORMAT.matcher("28.148.247.32").matches());
		Assert.assertTrue(RegexUtil.REGEX_IP_FORMAT.matcher("1.1.1.1").matches());
		Assert.assertTrue(RegexUtil.REGEX_IP_FORMAT.matcher("128.128.128.128").matches());
	}
}
