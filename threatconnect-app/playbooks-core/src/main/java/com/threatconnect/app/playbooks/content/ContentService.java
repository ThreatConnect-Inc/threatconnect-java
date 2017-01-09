package com.threatconnect.app.playbooks.content;

import com.threatconnect.app.addons.util.config.install.PlaybookVariableType;
import com.threatconnect.app.playbooks.content.accumulator.ContentAccumulator;
import com.threatconnect.app.playbooks.content.accumulator.ContentException;
import com.threatconnect.app.playbooks.content.accumulator.StringAccumulator;
import com.threatconnect.app.playbooks.content.accumulator.StringKeyValueAccumulator;
import com.threatconnect.app.playbooks.content.accumulator.StringKeyValueArrayAccumulator;
import com.threatconnect.app.playbooks.content.converter.ByteArrayConverter;
import com.threatconnect.app.playbooks.content.converter.ByteMatrixConverter;
import com.threatconnect.app.playbooks.content.converter.StringListConverter;
import com.threatconnect.app.playbooks.content.converter.TCEntityConverter;
import com.threatconnect.app.playbooks.content.converter.TCEntityListConverter;
import com.threatconnect.app.playbooks.content.entity.StringKeyValue;
import com.threatconnect.app.playbooks.content.entity.TCEntity;
import com.threatconnect.app.playbooks.db.DBService;
import com.threatconnect.app.playbooks.util.PlaybooksVariableUtil;

import java.util.List;

/**
 * @author Greg Marut
 */
public class ContentService
{
	//holds the database service object
	private final DBService dbService;
	
	//holds all of the content accumulators that will be responsible for reading/writing/converting data to and from
	//the database
	private final StringAccumulator stringAccumulator;
	private final ContentAccumulator<List<String>> stringListAccumulator;
	private final ContentAccumulator<TCEntity> tcEntityAccumulator;
	private final ContentAccumulator<List<TCEntity>> tcEntityListAccumulator;
	private final ContentAccumulator<byte[]> binaryAccumulator;
	private final ContentAccumulator<byte[][]> binaryArrayAccumulator;
	private final StringKeyValueAccumulator stringKeyValueContentAccumulator;
	private final StringKeyValueArrayAccumulator stringKeyValueArrayContentAccumulator;
	
	public ContentService(final DBService dbService)
	{
		this.dbService = dbService;
		
		this.stringAccumulator = new StringAccumulator(dbService);
		this.stringListAccumulator =
			new ContentAccumulator<List<String>>(dbService, PlaybookVariableType.StringArray, new
				StringListConverter());
		this.tcEntityAccumulator =
			new ContentAccumulator<TCEntity>(dbService, PlaybookVariableType.TCEntity, new TCEntityConverter());
		this.tcEntityListAccumulator =
			new ContentAccumulator<List<TCEntity>>(dbService, PlaybookVariableType.TCEntityArray,
				new TCEntityListConverter());
		this.binaryAccumulator = new ContentAccumulator<byte[]>(dbService, PlaybookVariableType.Binary, new
			ByteArrayConverter());
		this.binaryArrayAccumulator = new ContentAccumulator<byte[][]>(dbService, PlaybookVariableType.BinaryArray, new
			ByteMatrixConverter());
		this.stringKeyValueContentAccumulator = new StringKeyValueAccumulator(dbService);
		this.stringKeyValueArrayContentAccumulator = new StringKeyValueArrayAccumulator(dbService);
	}
	
	public String readString(final String content) throws ContentException
	{
		//read the content from the database
		return stringAccumulator.readContent(content);
	}
	
	/**
	 * Reads the content while looking for embedded variables and replacing them with their resolved value.
	 *
	 * @param content                     the content to check to resolve any embedded variables
	 * @param recursiveVariableResolution when true, recursion is allowed when resolving variables. Therefore, if a
	 *                                    variable is resolved and it contains more variables embedded in the string,
	 *                                    the lookups continue until all variables have been recursively resolved or a
	 *                                    variable could not be found.
	 * @return
	 * @throws ContentException
	 */
	public String readString(final String content, final boolean recursiveVariableResolution) throws ContentException
	{
		//read the content from the database
		return stringAccumulator.readContent(content, recursiveVariableResolution);
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
	
	public byte[][] readBinaryArray(final String key) throws ContentException
	{
		verifyKeyIsVariable(key);
		return binaryArrayAccumulator.readContent(key);
	}
	
	public void writeBinaryArray(final String key, final byte[][] value) throws ContentException
	{
		verifyKeyIsVariable(key);
		binaryArrayAccumulator.writeContent(key, value);
	}
	
	public StringKeyValue readKeyValue(final String key) throws ContentException
	{
		verifyKeyIsVariable(key);
		return stringKeyValueContentAccumulator.readContent(key);
	}
	
	public StringKeyValue readKeyValue(final String key, final boolean resolveEmbeddedVariables) throws ContentException
	{
		verifyKeyIsVariable(key);
		return stringKeyValueContentAccumulator.readContent(key, resolveEmbeddedVariables);
	}
	
	public void writeKeyValue(final String key, final StringKeyValue value) throws ContentException
	{
		verifyKeyIsVariable(key);
		stringKeyValueContentAccumulator.writeContent(key, value);
	}
	
	public List<StringKeyValue> readKeyValueArray(final String key) throws ContentException
	{
		verifyKeyIsVariable(key);
		return stringKeyValueArrayContentAccumulator.readContent(key);
	}
	
	public List<StringKeyValue> readKeyValueArray(final String key, final boolean resolveEmbeddedVariables)
		throws ContentException
	{
		verifyKeyIsVariable(key);
		return stringKeyValueArrayContentAccumulator.readContent(key, resolveEmbeddedVariables);
	}
	
	public void writeKeyValueArray(final String key, final List<StringKeyValue> value) throws ContentException
	{
		verifyKeyIsVariable(key);
		stringKeyValueArrayContentAccumulator.writeContent(key, value);
	}
	
	public DBService getDbService()
	{
		return dbService;
	}
	
	private void verifyKeyIsNotNull(final String key)
	{
		//make sure the key is not null
		if (null == key)
		{
			throw new IllegalArgumentException("key cannot be null");
		}
	}
	
	private void verifyKeyIsVariable(final String key)
	{
		//make sure the key is not null
		verifyKeyIsNotNull(key);
		
		//check to see if this string input is a variable
		if (!PlaybooksVariableUtil.isVariable(key))
		{
			throw new IllegalArgumentException("key is not a valid variable");
		}
	}
}
