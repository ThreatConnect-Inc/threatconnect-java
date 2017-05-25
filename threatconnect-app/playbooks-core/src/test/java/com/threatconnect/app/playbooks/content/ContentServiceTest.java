package com.threatconnect.app.playbooks.content;

import com.gregmarut.support.beangenerator.BeanPropertyGenerator;
import com.threatconnect.app.playbooks.content.accumulator.ContentException;
import com.threatconnect.app.playbooks.content.entity.KeyValue;
import com.threatconnect.app.playbooks.content.entity.TCEntity;
import com.threatconnect.app.playbooks.db.EmbeddedMapDBService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Greg Marut
 */
public class ContentServiceTest
{
	private ContentService contentService;
	private BeanPropertyGenerator beanPropertyGenerator;
	
	@Before
	public void init()
	{
		contentService = new ContentService(new EmbeddedMapDBService());
		beanPropertyGenerator = new BeanPropertyGenerator();
	}
	
	@Test
	public void keyValueTest1() throws ContentException
	{
		KeyValue keyValue = new KeyValue();
		keyValue.setKey("key1");
		keyValue.setStringValue("value1");
		
		contentService.writeKeyValue("#App:123:kv0!KeyValue", keyValue);
		
		KeyValue result = contentService.readKeyValue("#App:123:kv0!KeyValue");
		
		Assert.assertEquals(keyValue.getKey(), result.getKey());
		Assert.assertEquals(keyValue.getValue(), result.getValue());
	}
	
	@Test
	public void keyValueTest2() throws ContentException
	{
		TCEntity entity = beanPropertyGenerator.get(TCEntity.class);
		
		KeyValue keyValue = new KeyValue();
		keyValue.setKey("key1");
		keyValue.setTCEntityValue(entity);
		
		contentService.writeKeyValue("#App:123:kv1!KeyValue", keyValue);
		
		KeyValue result = contentService.readKeyValue("#App:123:kv1!KeyValue");
		TCEntity resultEntity = (TCEntity) result.getValue();
		
		Assert.assertEquals(keyValue.getKey(), result.getKey());
		Assert.assertEquals(entity.getConfidence(), resultEntity.getConfidence());
		Assert.assertEquals(entity.getId(), resultEntity.getId());
		Assert.assertEquals(entity.getOwnerName(), resultEntity.getOwnerName());
		Assert.assertEquals(entity.getRating(), resultEntity.getRating());
		Assert.assertEquals(entity.getThreatAssessConfidence(), resultEntity.getThreatAssessConfidence());
		Assert.assertEquals(entity.getThreatAssessRating(), resultEntity.getThreatAssessRating());
		Assert.assertEquals(entity.getType(), resultEntity.getType());
	}
	
	@Test
	public void keyValueTest3() throws ContentException
	{
		byte[] value = "KV Binary Test".getBytes();
		
		KeyValue keyValue = new KeyValue();
		keyValue.setKey("key1");
		keyValue.setBinaryValue(value);
		
		contentService.writeKeyValue("#App:123:kv2!KeyValue", keyValue);
		
		KeyValue result = contentService.readKeyValue("#App:123:kv2!KeyValue");
		byte[] resultValue = (byte[]) result.getValue();
		
		Assert.assertEquals(keyValue.getKey(), result.getKey());
		Assert.assertArrayEquals(value, resultValue);
		Assert.assertEquals(new String(value), new String(resultValue));
	}
	
	@Test
	public void keyValueTest4() throws ContentException
	{
		List<String> values = new ArrayList<String>();
		values.add("1");
		values.add("2");
		values.add("3");
		values.add("4");
		
		KeyValue keyValue = new KeyValue();
		keyValue.setKey("key4");
		keyValue.setStringArrayValue(values);
		
		contentService.writeKeyValue("#App:123:kv3!KeyValue", keyValue);
		
		KeyValue result = contentService.readKeyValue("#App:123:kv3!KeyValue");
		List<String> resultValues = (List<String>) result.getValue();
		
		Assert.assertEquals(keyValue.getKey(), result.getKey());
		Assert.assertEquals(values.get(0), resultValues.get(0));
		Assert.assertEquals(values.get(1), resultValues.get(1));
		Assert.assertEquals(values.get(2), resultValues.get(2));
		Assert.assertEquals(values.get(3), resultValues.get(3));
		
	}
}
