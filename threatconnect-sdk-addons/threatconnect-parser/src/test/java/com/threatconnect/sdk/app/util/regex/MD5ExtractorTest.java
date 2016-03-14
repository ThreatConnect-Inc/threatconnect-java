package com.threatconnect.sdk.app.util.regex;

import org.junit.Assert;
import org.junit.Test;

import com.threatconnect.sdk.parser.util.regex.MD5Extractor;
import com.threatconnect.sdk.parser.util.regex.MatchNotFoundException;

public class MD5ExtractorTest
{
	@Test
	public void extractMD5FromLink() throws MatchNotFoundException
	{
		MD5Extractor extractor = new MD5Extractor("http://www.example.com/d77bb7ba87916f64f85b42e781269296/file");
		
		Assert.assertEquals("d77bb7ba87916f64f85b42e781269296", extractor.getMD5());
	}
}
