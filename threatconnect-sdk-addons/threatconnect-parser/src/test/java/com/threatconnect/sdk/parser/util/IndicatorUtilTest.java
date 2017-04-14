package com.threatconnect.sdk.parser.util;

import com.threatconnect.sdk.parser.model.Address;
import org.junit.Assert;
import org.junit.Test;

public class IndicatorUtilTest
{
	@Test
	public void cleanIPTest()
	{
		Assert.assertEquals("10.0.0.1", IndicatorUtil.cleanIP("10.0.0.1"));
		Assert.assertEquals("10.0.0.1", IndicatorUtil.cleanIP("10.00.0.1"));
		Assert.assertEquals("10.0.0.1", IndicatorUtil.cleanIP("10.000.00.1"));
		Assert.assertEquals("10.0.0.1", IndicatorUtil.cleanIP("010.0.00.01"));
		
		Assert.assertEquals("3.43.12.1", IndicatorUtil.cleanIP("03.0043.12.01"));
		
		Assert.assertEquals("0.0.0.0", IndicatorUtil.cleanIP("0.0.0.0"));
		Assert.assertEquals("0.0.0.0", IndicatorUtil.cleanIP("00.00.00.00"));
		Assert.assertEquals("0.0.0.0", IndicatorUtil.cleanIP("000.000.000.000"));
	}
	
	@Test
	public void createHostOrAddressTest()
	{
		Assert.assertEquals(Address.class, IndicatorUtil.createHostOrAddress("178.159.38.81").getClass());
		Assert.assertEquals(Address.class, IndicatorUtil.createHostOrAddress(" 178.159.38.81").getClass());
		Assert.assertEquals(Address.class, IndicatorUtil.createHostOrAddress("178.159.38.81 ").getClass());
	}
}
