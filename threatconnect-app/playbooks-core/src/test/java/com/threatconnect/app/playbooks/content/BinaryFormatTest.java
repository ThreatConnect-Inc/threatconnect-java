package com.threatconnect.app.playbooks.content;

import com.gregmarut.support.beangenerator.BeanPropertyGenerator;
import com.threatconnect.app.playbooks.content.accumulator.ContentException;
import com.threatconnect.app.playbooks.db.DBReadException;
import com.threatconnect.app.playbooks.db.EmbeddedMapDBService;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

/**
 * @author Greg Marut
 */
public class BinaryFormatTest
{
	private static Logger logger = LoggerFactory.getLogger(BinaryFormatTest.class);
	
	private EmbeddedMapDBService embeddedMapDBService;
	private ContentService contentService;
	private BeanPropertyGenerator beanPropertyGenerator;
	
	@Before
	public void init()
	{
		embeddedMapDBService = new EmbeddedMapDBService();
		contentService = new ContentService(embeddedMapDBService);
		beanPropertyGenerator = new BeanPropertyGenerator();
	}
	
	@Test
	public void stringTest() throws ContentException, DBReadException
	{
		//the database key
		final String key = "#App:123:value!String";
		
		//create the sample and expected data sets
		String value = "This is a test";
		
		//these are the exact bytes expected to be written to the database
		byte[] expected = "\"This is a test\"".getBytes();
		
		//write the value to the content service
		contentService.writeString(key, value);
		
		//extract the raw bytes from the underlying data storage
		byte[] raw = embeddedMapDBService.getValue(key);
		String result = contentService.readString(key);
		logger.debug("Expected: {}", new String(expected));
		logger.debug("Actual:   {}", new String(raw));
		
		//ensure that the raw bytes equal the expected bytes
		Assert.assertArrayEquals(expected, raw);
		
		//ensure that the resulting value is the same as the original value saved
		Assert.assertEquals(value, result);
	}
	
	@Test
	public void stringArrayTest() throws ContentException, DBReadException
	{
		//the database key
		final String key = "#App:123:value!StringArray";
		
		//create the sample and expected data sets
		List<String> values = Arrays.asList("one", "two", "three");
		
		//these are the exact bytes expected to be written to the database
		byte[] expected = "[\"one\",\"two\",\"three\"]".getBytes();
		
		//write the value to the content service
		contentService.writeStringList(key, values);
		
		//extract the raw bytes from the underlying data storage
		byte[] raw = embeddedMapDBService.getValue(key);
		List<String> results = contentService.readStringList(key);
		logger.debug("Expected: {}", new String(expected));
		logger.debug("Actual:   {}", new String(raw));
		
		//ensure that the raw bytes equal the expected bytes
		Assert.assertArrayEquals(expected, raw);
		
		//ensure that the resulting value is the same as the original value saved
		Assert.assertEquals(values, results);
	}
	
	@Test
	public void binaryTest() throws ContentException, DBReadException
	{
		//the database key
		final String key = "#App:123:value!Binary";
		
		//create the sample and expected data sets
		byte[] value = "This is a test".getBytes();
		
		//these are the exact bytes expected to be written to the database
		byte[] expected = "\"VGhpcyBpcyBhIHRlc3Q=\"".getBytes();
		
		//write the value to the content service
		contentService.writeBinary(key, value);
		
		//extract the raw bytes from the underlying data storage
		byte[] raw = embeddedMapDBService.getValue(key);
		byte[] result = contentService.readBinary(key);
		logger.debug("Expected: {}", new String(expected));
		logger.debug("Actual:   {}", new String(raw));
		
		//ensure that both raw and expected are base 64 encoded byte arrays
		Assert.assertTrue("Expected byte array is not Base64 encoded.", Base64.isBase64(stripQuotes(expected)));
		Assert.assertTrue("Raw byte array is not Base64 encoded.", Base64.isBase64(stripQuotes(raw)));
		
		//ensure that the raw bytes equal the expected bytes
		Assert.assertArrayEquals(expected, raw);
		
		//ensure that the resulting value is the same as the original value saved
		Assert.assertArrayEquals(value, result);
	}
	
	@Test
	public void binaryArrayTest() throws ContentException, DBReadException
	{
		//the database key
		final String key = "#App:123:value!BinaryArray";
		
		//create the sample and expected data sets
		byte[][] values = new byte[][] { "one".getBytes(), "two".getBytes(), "three".getBytes() };
		
		//these are the exact bytes expected to be written to the database
		byte[] expected = "[\"b25l\",\"dHdv\",\"dGhyZWU=\"]".getBytes();
		
		Assert.assertArrayEquals("b25l".getBytes(), Base64.encodeBase64("one".getBytes()));
		Assert.assertArrayEquals("dHdv".getBytes(), Base64.encodeBase64("two".getBytes()));
		Assert.assertArrayEquals("dGhyZWU=".getBytes(), Base64.encodeBase64("three".getBytes()));
		
		//write the value to the content service
		contentService.writeBinaryArray(key, values);
		
		//extract the raw bytes from the underlying data storage
		byte[] raw = embeddedMapDBService.getValue(key);
		byte[][] result = contentService.readBinaryArray(key);
		logger.debug("Expected: {}", new String(expected));
		logger.debug("Actual:   {}", new String(raw));
		
		//ensure that the raw bytes equal the expected bytes
		Assert.assertArrayEquals(expected, raw);
	}
	
	private byte[] stripQuotes(byte[] bytes)
	{
		if ("\"".equals(new String(new byte[] { bytes[0] })) && "\"".equals(new String(new byte[] { bytes[bytes.length - 1] })))
		{
			return ArrayUtils.subarray(bytes, 1, bytes.length - 2);
		}
		else
		{
			return bytes;
		}
	}
}
