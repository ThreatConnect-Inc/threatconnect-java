package com.threatconnect.app.playbooks.content.accumulator;

import com.threatconnect.app.playbooks.content.converter.ContentConverter;
import com.threatconnect.app.playbooks.content.converter.ConversionException;
import com.threatconnect.app.playbooks.db.DBReadException;
import com.threatconnect.app.playbooks.db.DBService;
import com.threatconnect.app.playbooks.db.DBWriteException;
import com.threatconnect.app.playbooks.util.PlaybooksVariableUtil;
import com.threatconnect.app.playbooks.variable.PlaybooksVariable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class ContentAccumulator<T>
{
	//we can assume any content over 1000 characters is not a playbook key. This is even being extremely conservative
	private static final int KEY_SIZE_THRESHOLD = 1000;
	
	private static final Logger logger = LoggerFactory.getLogger(ContentAccumulator.class.getName());
	
	private final DBService dbService;
	private final ContentConverter<T> contentConverter;
	
	public ContentAccumulator(final DBService dbService, final ContentConverter<T> contentConverter)
	{
		//make sure the db service is not null
		if (null == dbService)
		{
			throw new IllegalArgumentException("dbService cannot be null");
		}
		
		//make sure the content contentConverter is not null
		if (null == contentConverter)
		{
			throw new IllegalArgumentException("contentConverter cannot be null or empty");
		}
		
		this.dbService = dbService;
		this.contentConverter = contentConverter;
	}
	
	/**
	 * Writes the content to the database. Returns whether or not the value was successfully saved
	 *
	 * @param key     the database key to use
	 * @param content the content to write to the database
	 * @throws ContentException if there was an issue reading/writing to the database.
	 */
	public void writeContent(final String key, final T content) throws ContentException
	{
		try
		{
			//convert the value to a byte array and write the raw byte value to the database
			dbService.saveValue(verifyKey(key).toString().trim(), contentConverter.toByteArray(content));
		}
		catch (DBWriteException | ConversionException e)
		{
			throw new ContentException(e);
		}
	}
	
	/**
	 * Retrieve a list of content for the given keys
	 *
	 * @param keys an array of keys to read from the database
	 * @return the list of content associated with these keys
	 * @throws ContentException if there was an issue reading/writing to the database.
	 */
	public List<T> readContentAsList(String... keys) throws ContentException
	{
		//holds the list of results to return
		List<T> result = new ArrayList<T>();
		
		//for each of the keys
		for (String key : keys)
		{
			//read the content from the database and make sure that it is not null
			T value = readContent(key);
			if (null != value)
			{
				result.add(value);
			}
		}
		
		return result;
	}
	
	/**
	 * Reads the content for the given key
	 *
	 * @param key the database key to use
	 * @return the content of the key
	 * @throws ContentException if there was an issue reading/writing to the database.
	 */
	public T readContent(final String key) throws ContentException
	{
		try
		{
			//read the value from the database service as a raw byte array and check to see if the content is not null
			byte[] content = resolveValue(key, new HashSet<String>());
			if (content == null)
			{
				return null;
			}
			
			return contentConverter.fromByteArray(content);
		}
		catch (DBReadException | ConversionException e)
		{
			throw new ContentException(e);
		}
	}
	
	private byte[] resolveValue(final String key, final Set<String> keySet) throws DBReadException
	{
		//make sure this key does not already exist in the stack
		if (!keySet.contains(key))
		{
			//push this key onto the stack
			keySet.add(key);
			
			//read the content from the database
			byte[] content = dbService.getValue(verifyKey(key).toString().trim());
			if (null != content)
			{
				//check to see if the content is within the threshold so we are not unnecessarily creating a string out of a very
				//large binary file
				if (content.length < KEY_SIZE_THRESHOLD)
				{
					//check to see if this is a content is another key
					String contentString = new String(content).trim();
					if (PlaybooksVariableUtil.isVariable(contentString))
					{
						//recurse until we find the value
						return resolveValue(contentString, keySet);
					}
					else
					{
						//this content is not another key so return this instead
						return content;
					}
				}
				else
				{
					//this is a large piece of content, this should not be another key
					return content;
				}
			}
			else
			{
				//there is no value
				return null;
			}
		}
		else
		{
			throw new DBReadException("Infinite playbook key recursion detected.");
		}
	}
	
	protected PlaybooksVariable verifyKey(final String key)
	{
		//make sure the key is not null or empty
		if (null == key || key.isEmpty())
		{
			throw new IllegalArgumentException("key cannot be null");
		}
		
		//extract the type from this key
		return PlaybooksVariableUtil.extractPlaybooksVariable(key);
	}
}
