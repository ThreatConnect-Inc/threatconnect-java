package com.threatconnect.sdk.app.util.regex;

import org.junit.Assert;
import org.junit.Test;

public class HostNameExtractorTest
{
	@Test
	public void validURLTest1() throws MatchNotFoundException
	{
		HostNameExtractor extractor = new HostNameExtractor("http://www.example.com/");
		
		Assert.assertEquals("http://www.example.com", extractor.getDomain());
		Assert.assertEquals("http://", extractor.getScheme());
		Assert.assertEquals("www.example.com", extractor.getHostName());
	}
	
	@Test
	public void validURLTest2() throws MatchNotFoundException
	{
		HostNameExtractor extractor = new HostNameExtractor("http://www.example.com/something/page.html");
		
		Assert.assertEquals("http://www.example.com", extractor.getDomain());
		Assert.assertEquals("http://", extractor.getScheme());
		Assert.assertEquals("www.example.com", extractor.getHostName());
	}
	
	@Test
	public void validURLTest3() throws MatchNotFoundException
	{
		HostNameExtractor extractor = new HostNameExtractor("http://example.com/something/page.html");
		
		Assert.assertEquals("http://example.com", extractor.getDomain());
		Assert.assertEquals("http://", extractor.getScheme());
		Assert.assertEquals("example.com", extractor.getHostName());
	}
	
	@Test
	public void validURLTest4() throws MatchNotFoundException
	{
		HostNameExtractor extractor = new HostNameExtractor("https://somedomain.somethingelse.example.com/");
		
		Assert.assertEquals("https://somedomain.somethingelse.example.com", extractor.getDomain());
		Assert.assertEquals("https://", extractor.getScheme());
		Assert.assertEquals("somedomain.somethingelse.example.com", extractor.getHostName());
	}
	
	@Test
	public void validURLTest5() throws MatchNotFoundException
	{
		HostNameExtractor extractor = new HostNameExtractor("somedomain.somethingelse.example.com/");
		
		Assert.assertEquals("somedomain.somethingelse.example.com", extractor.getDomain());
		Assert.assertNull(extractor.getScheme());
		Assert.assertEquals("somedomain.somethingelse.example.com", extractor.getHostName());
	}
}
