package com.threatconnect.sdk.app;

import org.dozer.Mapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.gregmarut.support.beangenerator.BeanPropertyGenerator;
import com.threatconnect.sdk.app.model.Address;
import com.threatconnect.sdk.app.model.Attribute;
import com.threatconnect.sdk.app.util.MapperUtil;

public class MapperTest
{
	private final Mapper mapper;
	private BeanPropertyGenerator beanPropertyGenerator;
	
	public MapperTest()
	{
		mapper = MapperUtil.createMapper();
	}
	
	@Before
	public void init()
	{
		beanPropertyGenerator = new BeanPropertyGenerator();
	}
	
	@Test
	public void mapAddressTest()
	{
		Address source = beanPropertyGenerator.get(Address.class);
		com.threatconnect.sdk.server.entity.Address target =
			mapper.map(source, com.threatconnect.sdk.server.entity.Address.class);
			
		MapperTestHelper.verifyAddress(source, target);
	}
	
	@Test
	public void mapAttributeTest()
	{
		Attribute source = beanPropertyGenerator.get(Attribute.class);
		com.threatconnect.sdk.server.entity.Attribute target =
			mapper.map(source, com.threatconnect.sdk.server.entity.Attribute.class);
			
		MapperTestHelper.verifyAttribute(source, target);
	}
	
	@Test
	public void mapNullTest()
	{
		Attribute source = new Attribute();
		source.setKey("abcd123");
		source.setValue(null);
		
		com.threatconnect.sdk.server.entity.Attribute target =
			beanPropertyGenerator.get(com.threatconnect.sdk.server.entity.Attribute.class);
		mapper.map(source, target);
		
		Assert.assertEquals("abcd123", source.getKey());
		Assert.assertNull(source.getValue());
		
		Assert.assertEquals("abcd123", target.getType());
		Assert.assertNotNull(target.getValue());
		Assert.assertEquals("value", target.getValue());
	}
	
	@Test
	public void mapNullTest2()
	{
		Address source = new Address();
		source.setIp("10.0.0.1");
		source.setDescription(null);
		
		com.threatconnect.sdk.server.entity.Address target =
			beanPropertyGenerator.get(com.threatconnect.sdk.server.entity.Address.class);
		mapper.map(source, target);
		
		Assert.assertEquals("10.0.0.1", source.getIp());
		Assert.assertNull(source.getDescription());
		
		Assert.assertEquals("10.0.0.1", target.getIp());
		Assert.assertNotNull(target.getDescription());
		Assert.assertEquals("description", target.getDescription());
	}
}
