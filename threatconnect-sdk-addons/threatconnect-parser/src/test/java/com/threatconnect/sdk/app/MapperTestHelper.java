package com.threatconnect.sdk.app;

import org.junit.Assert;

import com.threatconnect.sdk.parser.model.Address;
import com.threatconnect.sdk.parser.model.Attribute;
import com.threatconnect.sdk.parser.model.Indicator;

public class MapperTestHelper
{
	public static void verifyAddress(Address source, com.threatconnect.sdk.server.entity.Address target)
	{
		Assert.assertEquals(source.getIp(), target.getIp());
	}
	
	public static void verifyIndicator(Indicator source, com.threatconnect.sdk.server.entity.Indicator target)
	{
		Assert.assertEquals(source.getDescription(), target.getDescription());
		Assert.assertEquals(source.getSource(), target.getSource());
		Assert.assertEquals(source.getSummary(), target.getSummary());
		Assert.assertEquals(source.getWebLink(), target.getWebLink());
		Assert.assertEquals(source.getConfidence(), target.getConfidence());
		Assert.assertEquals(source.getRating(), target.getRating());
		Assert.assertEquals(source.getThreatAssessConfidence(), target.getThreatAssessConfidence());
		Assert.assertEquals(source.getThreatAssessRating(), target.getThreatAssessRating());
	}
	
	public static void verifyAttribute(Attribute source, com.threatconnect.sdk.server.entity.Attribute target)
	{
		Assert.assertEquals(source.getType(), target.getType());
		Assert.assertEquals(source.getValue(), target.getValue());
	}
}
