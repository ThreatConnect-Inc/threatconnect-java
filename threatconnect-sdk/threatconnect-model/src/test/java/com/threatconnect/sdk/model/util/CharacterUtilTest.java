package com.threatconnect.sdk.model.util;

import org.junit.Assert;
import org.junit.Test;

public class CharacterUtilTest
{
	@Test
	public void replaceEmDash()
	{
		final String text = "Dash \u2013";
		final String result = CharacterUtil.replaceSpecialCharacters(text);
		Assert.assertEquals("Dash -", result);
	}
	
	@Test
	public void doubleQuoteTest()
	{
		final String text = "this is in “Quotes”";
		final String result = CharacterUtil.replaceSpecialCharacters(text);
		Assert.assertEquals("this is in \"Quotes\"", result);
	}
	
	@Test
	public void singleQuoteTest()
	{
		final String text = "this is in single ‘Quotes’";
		final String result = CharacterUtil.replaceSpecialCharacters(text);
		Assert.assertEquals("this is in single 'Quotes'", result);
	}
}