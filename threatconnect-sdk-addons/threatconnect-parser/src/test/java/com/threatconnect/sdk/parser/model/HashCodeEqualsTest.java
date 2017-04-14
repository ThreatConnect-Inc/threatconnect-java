package com.threatconnect.sdk.parser.model;

import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author Greg Marut
 */
public class HashCodeEqualsTest
{
	@Test
	public void indicatorTest1()
	{
		Set<Indicator> set = new HashSet<Indicator>();
		
		Url url1 = new Url();
		url1.setText("http://www.malware.com");
		
		Url url2 = new Url();
		url2.setText("http://www.example.com");
		
		Url url3 = new Url();
		url3.setText("http://www.example.com");
		
		Assert.assertTrue(set.add(url1));
		Assert.assertTrue(set.add(url2));
		Assert.assertFalse(set.add(url3));
	}
	
	@Test
	public void indicatorTest2()
	{
		Set<Item> set = new LinkedHashSet<Item>();
		
		Url url1 = new Url();
		url1.setText("http://www.malware.com");
		
		Url url2 = new Url();
		url2.setText("http://www.example.com");
		
		Url url3 = new Url();
		url3.setText("http://www.example.com");
		
		File file1 = new File();
		file1.setMd5("995e5b5015570faa1a3ce2a3b7442b65");
		file1.setSha1("2f2209597a542dd7c7c27b3c1df741b00e0f1f3a");
		file1.setSha256("d5150a1c119437b6d3da7c4f4a61648aa03cc3177ebaa665c7706b6d6a52d0de");
		
		File file2 = new File();
		file2.setMd5("995e5b5015570faa1a3ce2a3b7442b65");
		file2.setSha1("2f2209597a542dd7c7c27b3c1df741b00e0f1f3a");
		file2.setSha256("d5150a1c119437b6d3da7c4f4a61648aa03cc3177ebaa665c7706b6d6a52d0de");
		
		Assert.assertTrue(set.add(file1));
		Assert.assertTrue(set.add(url1));
		Assert.assertTrue(set.add(url2));
		Assert.assertFalse(set.add(url3));
		Assert.assertFalse(set.addAll(Collections.singletonList(file2)));
	}
}
