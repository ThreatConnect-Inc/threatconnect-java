package com.threatconnect.stix.write;

import com.threatconnect.sdk.model.Address;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StixWriterTest
{
	private static final Logger logger = LoggerFactory.getLogger(StixWriterTest.class);
	
	@Test
	public void writeTest1()
	{
		Address address = new Address();
		address.setIp("10.11.12.13");
		address.setRating(3.0);
		address.setConfidence(60.0);
		address.setSource("Some Fake Source");
		address.setDescription("This ip is potentially bad");
		
		StixWriter stixWriter = new StixWriter();
		String xml = stixWriter.writeStix(true, true, address);
		
		logger.info(xml);
	}
}
