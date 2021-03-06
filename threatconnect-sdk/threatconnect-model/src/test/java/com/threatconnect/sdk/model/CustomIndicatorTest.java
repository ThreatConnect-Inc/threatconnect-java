package com.threatconnect.sdk.model;

import org.junit.Test;

/**
 * @author Greg Marut
 */
public class CustomIndicatorTest
{
	@Test(expected = IllegalArgumentException.class)
	public void addressCustomIndicator()
	{
		new CustomIndicator(Address.INDICATOR_TYPE);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void emailAddressCustomIndicator()
	{
		new CustomIndicator(EmailAddress.INDICATOR_TYPE);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void hostCustomIndicator()
	{
		new CustomIndicator(Host.INDICATOR_TYPE);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void fileCustomIndicator()
	{
		new CustomIndicator(File.INDICATOR_TYPE);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void urlCustomIndicator()
	{
		new CustomIndicator(Url.INDICATOR_TYPE);
	}
	
	@Test
	public void cidrCustomIndicator()
	{
		new CustomIndicator("CIDR");
	}
}
