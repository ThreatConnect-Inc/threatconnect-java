package com.threatconnect.sdk.model;

import com.threatconnect.sdk.model.util.AttributeUtil;
import com.threatconnect.sdk.model.util.ItemUtil;
import com.threatconnect.sdk.model.util.merge.MergeAttributeStrategy;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class ItemUtilTest
{
	@Test
	public void mergeTest()
	{
		Address address1 = new Address();
		address1.setIp("1.1.1.1");
		AttributeUtil.addAttribute(address1, "Type1", "value1");
		
		Address address2 = new Address();
		address2.setIp("1.1.1.1");
		AttributeUtil.addAttribute(address2, "Type2", "value2");
		
		Address address3 = new Address();
		address3.setIp("3.3.3.3");
		AttributeUtil.addAttribute(address3, "Type3", "value3");
		
		ItemUtil.mergeIndicators(Arrays.asList(address1, address2, address3), new MergeAttributeStrategy());
		
		Assert.assertEquals(2, address1.getAttributes().size());
		Assert.assertEquals(2, address2.getAttributes().size());
	}
	
	@Test
	public void mergeTest2()
	{
		Address address1 = new Address();
		address1.setIp("1.1.1.1");
		AttributeUtil.addAttribute(address1, "Type1", "value1");
		AttributeUtil.addAttribute(address1, "Type2", "value2");
		
		ItemUtil.mergeIndicators(Arrays.asList(address1, address1), new MergeAttributeStrategy());
		
		Assert.assertEquals(2, address1.getAttributes().size());
	}
}
