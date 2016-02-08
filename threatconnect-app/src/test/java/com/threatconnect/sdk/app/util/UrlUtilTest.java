package com.threatconnect.sdk.app.util;

import org.junit.Assert;
import org.junit.Test;

public class UrlUtilTest
{
	@Test
	public void toAbsoluteUrlTest()
	{
		Assert.assertEquals("http://www.example.com/one/two",
			UrlUtil.toAbsoluteURL("http://www.example.com/one/two", "http://www.example.com/"));
		Assert.assertEquals("http://www.example.com/one/two",
			UrlUtil.toAbsoluteURL("http://www.example.com/one/two", "http://www.example.com"));
		Assert.assertEquals("http://www.example.com/one/two",
			UrlUtil.toAbsoluteURL("/one/two", "http://www.example.com/"));
		Assert.assertEquals("http://www.example.com/one/two",
			UrlUtil.toAbsoluteURL("one/two", "http://www.example.com/"));
		Assert.assertEquals("http://www.example.com/one/two",
			UrlUtil.toAbsoluteURL("/one/two", "http://www.example.com"));
		Assert.assertEquals("http://www.example.com/one/two",
			UrlUtil.toAbsoluteURL("one/two", "http://www.example.com"));
	}
}
