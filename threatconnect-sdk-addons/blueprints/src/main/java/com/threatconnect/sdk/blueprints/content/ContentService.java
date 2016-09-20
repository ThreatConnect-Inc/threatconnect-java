package com.threatconnect.sdk.blueprints.content;

import com.threatconnect.sdk.blueprints.content.accumulator.ContentAccumulator;
import com.threatconnect.sdk.blueprints.content.accumulator.ContentException;
import com.threatconnect.sdk.blueprints.content.accumulator.StringAccumulator;
import com.threatconnect.sdk.blueprints.content.accumulator.StringKeyValueAccumulator;
import com.threatconnect.sdk.blueprints.content.converter.ByteArrayConverter;
import com.threatconnect.sdk.blueprints.content.converter.StringListConverter;
import com.threatconnect.sdk.blueprints.content.converter.TCEntityConverter;
import com.threatconnect.sdk.blueprints.content.converter.TCEntityListConverter;
import com.threatconnect.sdk.blueprints.content.entity.StringKeyValue;
import com.threatconnect.sdk.blueprints.content.entity.TCEntity;
import com.threatconnect.sdk.blueprints.db.DBService;
import com.threatconnect.sdk.blueprints.util.BlueprintVariableUtil;

import java.util.List;

/**
 * @author Greg Marut
 */
public class ContentService
{
	//holds the database service object
	private final DBService dbService;
	
	//holds all of the content accumulators that will be responsbile for reading/writing/converting data to and from
	//the database
	private final ContentAccumulator<String> stringAccumulator;
	private final ContentAccumulator<List<String>> stringListAccumulator;
	private final ContentAccumulator<TCEntity> tcEntityAccumulator;
	private final ContentAccumulator<List<TCEntity>> tcEntityListAccumulator;
	private final ContentAccumulator<byte[]> binaryAccumulator;
	private final ContentAccumulator<StringKeyValue> stringKeyValueContentAccumulator;
	
	public ContentService(final DBService dbService)
	{
		this.dbService = dbService;
		
		this.stringAccumulator = new StringAccumulator(dbService);
		this.stringListAccumulator = new ContentAccumulator<List<String>>(dbService, StandardType.StringArray, new
			StringListConverter());
		this.tcEntityAccumulator =
			new ContentAccumulator<TCEntity>(dbService, StandardType.TCEntity, new TCEntityConverter());
		this.tcEntityListAccumulator = new ContentAccumulator<List<TCEntity>>(dbService, StandardType.TCEntityArray,
			new TCEntityListConverter());
		this.binaryAccumulator = new ContentAccumulator<byte[]>(dbService, StandardType.Binary, new
			ByteArrayConverter());
		this.stringKeyValueContentAccumulator = new StringKeyValueAccumulator(dbService);
	}
	
	public String readString(final String content) throws ContentException
	{
		//check to see if this string input is a variable
		if (BlueprintVariableUtil.isVariable(content))
		{
			//read the content from the database
			return stringAccumulator.readContent(content);
		}
		else
		{
			//this is a literal value
			return content;
		}
	}
	
	public void writeString(final String key, final String value) throws ContentException
	{
		verifyKeyIsVariable(key);
		stringAccumulator.writeContent(key, value);
	}
	
	public List<String> readStringList(final String key) throws ContentException
	{
		verifyKeyIsVariable(key);
		return stringListAccumulator.readContent(key);
	}
	
	public void writeStringList(final String key, final List<String> value) throws ContentException
	{
		verifyKeyIsVariable(key);
		stringListAccumulator.writeContent(key, value);
	}
	
	public TCEntity readTCEntity(final String key) throws ContentException
	{
		verifyKeyIsVariable(key);
		return tcEntityAccumulator.readContent(key);
	}
	
	public void writeTCEntity(final String key, final TCEntity value) throws ContentException
	{
		verifyKeyIsVariable(key);
		tcEntityAccumulator.writeContent(key, value);
	}
	
	public List<TCEntity> readTCEntityList(final String key) throws ContentException
	{
		verifyKeyIsVariable(key);
		return tcEntityListAccumulator.readContent(key);
	}
	
	public void writeTCEntityList(final String key, final List<TCEntity> value) throws ContentException
	{
		verifyKeyIsVariable(key);
		tcEntityListAccumulator.writeContent(key, value);
	}
	
	public byte[] readBinary(final String key) throws ContentException
	{
		verifyKeyIsVariable(key);
		return binaryAccumulator.readContent(key);
	}
	
	public void writeBinary(final String key, final byte[] value) throws ContentException
	{
		verifyKeyIsVariable(key);
		binaryAccumulator.writeContent(key, value);
	}
	
	public StringKeyValue readKeyValue(final String key) throws ContentException
	{
		verifyKeyIsVariable(key);
		return stringKeyValueContentAccumulator.readContent(key);
	}
	
	public void writeKeyValue(final String key, final StringKeyValue value) throws ContentException
	{
		verifyKeyIsVariable(key);
		stringKeyValueContentAccumulator.writeContent(key, value);
	}
	
	public DBService getDbService()
	{
		return dbService;
	}
	
	private void verifyKeyIsVariable(final String key)
	{
		//make sure the key is not null
		if (null == key)
		{
			throw new IllegalArgumentException("key cannot be null");
		}
		
		//check to see if this string input is a variable
		if (!BlueprintVariableUtil.isVariable(key))
		{
			throw new IllegalArgumentException("key is not a valid variable");
		}
	}
}
