package com.threatconnect.sdk.app.util.regex;

import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Test;

import com.threatconnect.sdk.app.util.RegexUtil;

public class RegexTest
{
	@Test
	public void testIPAddress()
	{
		Assert.assertTrue(Pattern.matches(RegexUtil.REGEX_IP_FORMAT, "68.168.209.242"));
		Assert.assertTrue(Pattern.matches(RegexUtil.REGEX_IP_FORMAT, "10.0.0.1"));
		Assert.assertTrue(Pattern.matches(RegexUtil.REGEX_IP_FORMAT, "28.148.247.32"));
		Assert.assertTrue(Pattern.matches(RegexUtil.REGEX_IP_FORMAT, "1.1.1.1"));
		Assert.assertTrue(Pattern.matches(RegexUtil.REGEX_IP_FORMAT, "128.128.128.128"));
	}
}
