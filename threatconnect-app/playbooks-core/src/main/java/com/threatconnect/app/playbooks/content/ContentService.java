package com.threatconnect.app.playbooks.content;

import com.threatconnect.app.addons.util.config.install.StandardPlaybookType;
import com.threatconnect.app.playbooks.content.accumulator.ContentAccumulator;
import com.threatconnect.app.playbooks.content.accumulator.ContentException;
import com.threatconnect.app.playbooks.content.accumulator.CustomTypeContentAccumulator;
import com.threatconnect.app.playbooks.content.accumulator.KeyValueAccumulator;
import com.threatconnect.app.playbooks.content.accumulator.KeyValueArrayAccumulator;
import com.threatconnect.app.playbooks.content.accumulator.StringAccumulator;
import com.threatconnect.app.playbooks.content.accumulator.TypedContentAccumulator;
import com.threatconnect.app.playbooks.content.converter.ByteArrayConverter;
import com.threatconnect.app.playbooks.content.converter.ByteMatrixConverter;
import com.threatconnect.app.playbooks.content.converter.StringListConverter;
import com.threatconnect.app.playbooks.content.converter.TCEnhancedEntityConverter;
import com.threatconnect.app.playbooks.content.converter.TCEnhancedEntityListConverter;
import com.threatconnect.app.playbooks.content.converter.TCEntityConverter;
import com.threatconnect.app.playbooks.content.converter.TCEntityListConverter;
import com.threatconnect.app.playbooks.content.entity.KeyValue;
import com.threatconnect.app.playbooks.content.entity.TCEntity;
import com.threatconnect.app.playbooks.db.DBService;
import com.threatconnect.app.playbooks.util.PlaybooksVariableUtil;
import com.threatconnect.app.playbooks.variable.PlaybooksVariable;
import com.threatconnect.sdk.model.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Greg Marut
 */
public class ContentService
{
	private static final Logger logger = LoggerFactory.getLogger(ContentService.class);
	
	//holds the database service object
	private final DBService dbService;
	
	//holds all of the content accumulators that will be responsible for reading/writing/converting data to and from
	//the database
	private final StringAccumulator stringAccumulator;
	private final ContentAccumulator<List<String>> stringListAccumulator;
	private final ContentAccumulator<TCEntity> tcEntityAccumulator;
	private final ContentAccumulator<List<TCEntity>> tcEntityListAccumulator;
	private final ContentAccumulator<Item> tcEnhancedEntityAccumulator;
	private final ContentAccumulator<List<Item>> tcEnhancedEntityListAccumulator;
	private final ContentAccumulator<byte[]> binaryAccumulator;
	private final ContentAccumulator<byte[][]> binaryArrayAccumulator;
	private final KeyValueAccumulator keyValueContentAccumulator;
	private final KeyValueArrayAccumulator keyValueArrayContentAccumulator;
	
	private final CustomTypeContentAccumulator customDataTypeAccumulator;
	
	public ContentService(final DBService dbService)
	{
		this.dbService = dbService;
		
		this.stringAccumulator = new StringAccumulator(dbService);
		this.stringListAccumulator =
			new TypedContentAccumulator<List<String>>(dbService, StandardPlaybookType.StringArray, new
				StringListConverter());
		this.tcEntityAccumulator =
			new TypedContentAccumulator<TCEntity>(dbService, StandardPlaybookType.TCEntity, new TCEntityConverter());
		this.tcEntityListAccumulator =
			new TypedContentAccumulator<List<TCEntity>>(dbService, StandardPlaybookType.TCEntityArray,
				new TCEntityListConverter());
		this.tcEnhancedEntityAccumulator =
			new TypedContentAccumulator<Item>(dbService, StandardPlaybookType.TCEnhancedEntity, new
				TCEnhancedEntityConverter());
		this.tcEnhancedEntityListAccumulator =
			new TypedContentAccumulator<List<Item>>(dbService, StandardPlaybookType.TCEnhancedEntityArray, new
				TCEnhancedEntityListConverter());
		this.binaryAccumulator = new TypedContentAccumulator<byte[]>(dbService, StandardPlaybookType.Binary, new
			ByteArrayConverter());
		this.binaryArrayAccumulator =
			new TypedContentAccumulator<byte[][]>(dbService, StandardPlaybookType.BinaryArray, new
				ByteMatrixConverter());
		this.keyValueContentAccumulator = new KeyValueAccumulator(dbService);
		this.keyValueArrayContentAccumulator = new KeyValueArrayAccumulator(dbService);
		
		this.customDataTypeAccumulator = new CustomTypeContentAccumulator(dbService, new ByteArrayConverter());
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
	 * @return the content read from the database using the given key.
	 * @throws ContentException if there was an issue reading/writing to the database.
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
	
	public Item readTCEnhancedEntity(final String key) throws ContentException
	{
		verifyKeyIsVariable(key);
		return tcEnhancedEntityAccumulator.readContent(key);
	}
	
	public void writeTCEnhancedEntity(final String key, final Item value) throws ContentException
	{
		verifyKeyIsVariable(key);
		tcEnhancedEntityAccumulator.writeContent(key, value);
	}
	
	public List<Item> readTCEnhancedEntityList(final String key) throws ContentException
	{
		verifyKeyIsVariable(key);
		return tcEnhancedEntityListAccumulator.readContent(key);
	}
	
	public void writeTCEnhancedEntityList(final String key, final List<Item> value) throws ContentException
	{
		verifyKeyIsVariable(key);
		tcEnhancedEntityListAccumulator.writeContent(key, value);
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
	
	public KeyValue readKeyValue(final String key) throws ContentException
	{
		verifyKeyIsVariable(key);
		KeyValue kv = keyValueContentAccumulator.readContent(key);
		postRead(kv);
		return kv;
	}
	
	public KeyValue readKeyValue(final String key, final boolean resolveEmbeddedVariables) throws ContentException
	{
		verifyKeyIsVariable(key);
		KeyValue kv = keyValueContentAccumulator.readContent(key, resolveEmbeddedVariables);
		postRead(kv);
		return kv;
	}
	
	public void writeKeyValue(final String key, final KeyValue value) throws ContentException
	{
		verifyKeyIsVariable(key);
		
		//make sure the value is not null
		if (null != value)
		{
			//clone the key value object since we will be modifying the original
			KeyValue kv = new KeyValue(value);
			
			preWrite(key, kv);
			keyValueContentAccumulator.writeContent(key, kv);
		}
		else
		{
			keyValueContentAccumulator.writeContent(key, null);
		}
	}
	
	public List<KeyValue> readKeyValueArray(final String key) throws ContentException
	{
		verifyKeyIsVariable(key);
		
		List<KeyValue> kvs = keyValueArrayContentAccumulator.readContent(key);
		
		//make sure the list of values is not null
		if (null != kvs)
		{
			for (KeyValue keyValue : kvs)
			{
				postRead(keyValue);
			}
		}
		
		return kvs;
	}
	
	public List<KeyValue> readKeyValueArray(final String key, final boolean resolveEmbeddedVariables)
		throws ContentException
	{
		verifyKeyIsVariable(key);
		return keyValueArrayContentAccumulator.readContent(key, resolveEmbeddedVariables);
	}
	
	public void writeKeyValueArray(final String key, final List<KeyValue> value) throws ContentException
	{
		verifyKeyIsVariable(key);
		
		//make sure the list of values is not null
		if (null != value)
		{
			//clone the list of key value objects since we will be modifying the originals
			List<KeyValue> kvs = new ArrayList<KeyValue>();
			for (KeyValue keyValue : value)
			{
				KeyValue kv = new KeyValue(keyValue);
				preWrite(key, kv);
				kvs.add(kv);
			}
			
			keyValueArrayContentAccumulator.writeContent(key, kvs);
		}
		else
		{
			//just write null
			keyValueArrayContentAccumulator.writeContent(key, null);
		}
	}
	
	public byte[] readCustomType(final String key) throws ContentException
	{
		verifyKeyIsVariable(key);
		return customDataTypeAccumulator.readContent(key);
	}
	
	public void writeCustomType(final String key, final byte[] value) throws ContentException
	{
		verifyKeyIsVariable(key);
		customDataTypeAccumulator.writeContent(key, value);
	}
	
	/**
	 * Update the key value objects before writing
	 *
	 * @param originalKey
	 * @param keyValue
	 * @throws ContentException
	 */
	private void preWrite(final String originalKey, final KeyValue keyValue) throws ContentException
	{
		//break apart the original key which will be used for creating the new key
		PlaybooksVariable originalVariable = PlaybooksVariableUtil.extractPlaybooksVariable(originalKey);
		PlaybooksVariable valueVariable;
		
		//determine what type of variable is stored in the key value
		if (StandardPlaybookType.StringArray.toString().equalsIgnoreCase(keyValue.getVariableType()))
		{
			//create a new variable to store the value and update the key value's value with a variable
			valueVariable = new PlaybooksVariable(originalVariable.getNamespace(), originalVariable.getId(),
				UUID.randomUUID().toString(), StandardPlaybookType.StringArray);
			stringListAccumulator.writeContent(valueVariable.toString(), (List<String>) keyValue.getValue());
			keyValue.setStringValue(valueVariable.toString());
		}
		else if (StandardPlaybookType.Binary.toString().equalsIgnoreCase(keyValue.getVariableType()))
		{
			//create a new variable to store the value and update the key value's value with a variable
			valueVariable = new PlaybooksVariable(originalVariable.getNamespace(), originalVariable.getId(),
				UUID.randomUUID().toString(), StandardPlaybookType.Binary);
			binaryAccumulator.writeContent(valueVariable.toString(), (byte[]) keyValue.getValue());
			keyValue.setStringValue(valueVariable.toString());
		}
		else if (StandardPlaybookType.BinaryArray.toString().equalsIgnoreCase(keyValue.getVariableType()))
		{
			//create a new variable to store the value and update the key value's value with a variable
			valueVariable = new PlaybooksVariable(originalVariable.getNamespace(), originalVariable.getId(),
				UUID.randomUUID().toString(), StandardPlaybookType.BinaryArray);
			binaryArrayAccumulator.writeContent(valueVariable.toString(), (byte[][]) keyValue.getValue());
			keyValue.setStringValue(valueVariable.toString());
		}
		else if (StandardPlaybookType.TCEntity.toString().equalsIgnoreCase(keyValue.getVariableType()))
		{
			//create a new variable to store the value and update the key value's value with a variable
			valueVariable = new PlaybooksVariable(originalVariable.getNamespace(), originalVariable.getId(),
				UUID.randomUUID().toString(), StandardPlaybookType.TCEntity);
			tcEntityAccumulator.writeContent(valueVariable.toString(), (TCEntity) keyValue.getValue());
			keyValue.setStringValue(valueVariable.toString());
		}
		else if (StandardPlaybookType.TCEntityArray.toString().equalsIgnoreCase(keyValue.getVariableType()))
		{
			//create a new variable to store the value and update the key value's value with a variable
			valueVariable = new PlaybooksVariable(originalVariable.getNamespace(), originalVariable.getId(),
				UUID.randomUUID().toString(), StandardPlaybookType.TCEntityArray);
			tcEntityListAccumulator
				.writeContent(valueVariable.toString(), (List<TCEntity>) keyValue.getValue());
			keyValue.setStringValue(valueVariable.toString());
		}
		else if (StandardPlaybookType.TCEnhancedEntity.toString().equalsIgnoreCase(keyValue.getVariableType()))
		{
			//create a new variable to store the value and update the key value's value with a variable
			valueVariable = new PlaybooksVariable(originalVariable.getNamespace(), originalVariable.getId(),
				UUID.randomUUID().toString(), StandardPlaybookType.TCEnhancedEntity);
			tcEnhancedEntityAccumulator.writeContent(valueVariable.toString(), (Item) keyValue.getValue());
			keyValue.setStringValue(valueVariable.toString());
		}
		else if (StandardPlaybookType.TCEnhancedEntityArray.toString().equalsIgnoreCase(keyValue.getVariableType()))
		{
			//create a new variable to store the value and update the key value's value with a variable
			valueVariable = new PlaybooksVariable(originalVariable.getNamespace(), originalVariable.getId(),
				UUID.randomUUID().toString(), StandardPlaybookType.TCEnhancedEntityArray);
			tcEnhancedEntityListAccumulator
				.writeContent(valueVariable.toString(), (List<Item>) keyValue.getValue());
			keyValue.setStringValue(valueVariable.toString());
		}
		else if (StandardPlaybookType.String.toString().equalsIgnoreCase(keyValue.getVariableType()))
		{
			//ignore strings, no extra work needs to be done here
		}
		else if (
			StandardPlaybookType.KeyValue.toString().equalsIgnoreCase(keyValue.getVariableType()) || StandardPlaybookType.KeyValueArray
			.toString().equalsIgnoreCase(keyValue.getVariableType()))
		{
			//prevent a recursive variable type
			throw new IllegalArgumentException(keyValue.getVariableType() + " is an unsupported value for KeyValue");
		}
		//this must be a custom data type
		else
		{
			//create a new variable to store the value and update the key value's value with a variable
			valueVariable = new PlaybooksVariable(originalVariable.getNamespace(), originalVariable.getId(),
				UUID.randomUUID().toString(), keyValue.getVariableType());
			customDataTypeAccumulator.writeContent(valueVariable.toString(), (byte[]) keyValue.getValue());
			keyValue.setStringValue(valueVariable.toString());
		}
	}
	
	private void postRead(final KeyValue keyValue) throws ContentException
	{
		//make sure the key value object is not null
		if (null != keyValue)
		{
			//check to see if this key value is a string
			if (keyValue.getValue() instanceof String)
			{
				//check to see if the value is actually a variable
				if (PlaybooksVariableUtil.isVariable(keyValue.getValue().toString()))
				{
					//determine what type of variable is stored in the key value
					String extracted = PlaybooksVariableUtil.extractVariableType(keyValue.getValue().toString());
					if (StandardPlaybookType.StringArray.toString().equalsIgnoreCase(extracted))
					{
						keyValue.setStringArrayValue(stringListAccumulator.readContent(keyValue.getValue().toString()));
					}
					else if (StandardPlaybookType.Binary.toString().equalsIgnoreCase(extracted))
					{
						keyValue.setBinaryValue(binaryAccumulator.readContent(keyValue.getValue().toString()));
					}
					else if (StandardPlaybookType.BinaryArray.toString().equalsIgnoreCase(extracted))
					{
						keyValue.setBinaryArrayValue(
							binaryArrayAccumulator.readContent(keyValue.getValue().toString()));
					}
					else if (StandardPlaybookType.TCEntity.toString().equalsIgnoreCase(extracted))
					{
						keyValue.setTCEntityValue(tcEntityAccumulator.readContent(keyValue.getValue().toString()));
					}
					else if (StandardPlaybookType.TCEntityArray.toString().equalsIgnoreCase(extracted))
					{
						keyValue.setTCEntityArrayValue(
							tcEntityListAccumulator.readContent(keyValue.getValue().toString()));
					}
					else if (StandardPlaybookType.TCEnhancedEntity.toString().equalsIgnoreCase(extracted))
					{
						keyValue.setTCEnhancedEntityValue(
							tcEnhancedEntityAccumulator.readContent(keyValue.getValue().toString()));
					}
					else if (StandardPlaybookType.TCEnhancedEntityArray.toString().equalsIgnoreCase(extracted))
					{
						keyValue.setTCEnhancedEntityArrayValue(
							tcEnhancedEntityListAccumulator.readContent(keyValue.getValue().toString()));
					}
					else if (StandardPlaybookType.String.toString().equalsIgnoreCase(extracted))
					{
						//ignore strings, no extra work needs to be done here
					}
					else if (
						StandardPlaybookType.KeyValue.toString().equalsIgnoreCase(extracted) || StandardPlaybookType.KeyValueArray
						.toString().equalsIgnoreCase(extracted))
					{
						//prevent a recursive variable type
						throw new IllegalArgumentException(
							keyValue.getVariableType() + " is an unsupported value for KeyValue");
					}
					//this must be a custom data type
					else
					{
						keyValue.setCustomTypeValue(customDataTypeAccumulator.readContent(
							keyValue.getValue().toString()), extracted);
					}
				}
			}
		}
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
