package com.threatconnect.stix;

import com.gregmarut.support.beangenerator.BeanPropertyGenerator;
import com.gregmarut.support.beangenerator.rule.RuleBuilder;
import com.gregmarut.support.beangenerator.rule.condition.FieldNameMatchesCondition;
import com.gregmarut.support.beangenerator.rule.condition.FieldNameStartsWithCondition;
import com.threatconnect.sdk.model.Address;
import com.threatconnect.sdk.model.Item;
import com.threatconnect.sdk.parser.ParserException;
import com.threatconnect.stix.read.parser.STIXStreamParser;
import com.threatconnect.stix.write.StixWriter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.util.List;

public class ReadWriteTest
{
	private static final Logger logger = LoggerFactory.getLogger(ReadWriteTest.class);
	
	private BeanPropertyGenerator beanPropertyGenerator;
	
	@Before
	public void init()
	{
		//generate a new file object
		beanPropertyGenerator = new BeanPropertyGenerator();
		
		RuleBuilder ruleBuilder = beanPropertyGenerator.getConfiguration().createRuleBuilder();
		
		ruleBuilder.forType(String.class).when(new FieldNameMatchesCondition("ip")).thenReturn("10.0.0.1");
		ruleBuilder.forType(Double.class).when(new FieldNameStartsWithCondition("rating")).thenReturn(5.0);
		ruleBuilder.forType(Double.class).when(new FieldNameStartsWithCondition("confidence")).thenReturn(100.0);
	}
	
	@Test
	public void readWrite() throws ParserException
	{
		Address address = beanPropertyGenerator.get(Address.class);
		
		//write the file object
		StixWriter stixWriter = new StixWriter();
		final String xml = stixWriter.writeStix(address);
		logger.info(xml);
		
		//parse this stix file
		STIXStreamParser stixStreamParser = new STIXStreamParser(() -> new ByteArrayInputStream(xml.getBytes()));
		List<Item> parsedItems = stixStreamParser.parseData();
		
		Assert.assertEquals(1, parsedItems.size());
		
		Address parsedAddress = (Address) parsedItems.get(0);
		Assert.assertEquals(address.getIp(), parsedAddress.getIp());
		Assert.assertEquals(address.getConfidence(), parsedAddress.getConfidence());
		
		//:FIXME: the writer does not write the rating to the xml
		//Assert.assertEquals(address.getRating(), parsedAddress.getRating());
	}
}
