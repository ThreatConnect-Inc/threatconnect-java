package com.threatconnect.app.playbooks.content.accumulator;

import com.threatconnect.app.playbooks.content.converter.ContentConverter;
import com.threatconnect.app.playbooks.content.converter.ConversionException;
import com.threatconnect.app.playbooks.db.DBService;
import com.threatconnect.app.addons.util.config.install.PlaybookVariableType;
import com.threatconnect.app.playbooks.db.DBReadException;
import com.threatconnect.app.playbooks.db.DBWriteException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class ContentAccumulator<T>
{
	private static final Logger logger = LoggerFactory.getLogger(ContentAccumulator.class.getName());
	
	private final PlaybookVariableType standardType;
	private final DBService dbService;
	private final ContentConverter<T> contentConverter;
	
	public ContentAccumulator(final DBService dbService, final PlaybookVariableType standardType,
		final ContentConverter<T>
			contentConverter)
	{
		//make sure the db service is not null
		if (null == dbService)
		{
			throw new IllegalArgumentException("dbService cannot be null");
		}
		
		//make sure the standard type is not null
		if (null == standardType)
		{
			throw new IllegalArgumentException("standardType cannot be null or empty");
		}
		
		//make sure the content contentConverter is not null
		if (null == contentConverter)
		{
			throw new IllegalArgumentException("contentConverter cannot be null or empty");
		}
		
		this.dbService = dbService;
		this.standardType = standardType;
		this.contentConverter = contentConverter;
	}
	
	/**
	 * Writes the content to the database. Returns whether or not the value was successfully saved
	 *
	 * @param key
	 * @param content
	 * @return whether or not the value was successfully saved
	 * @throws DBWriteException
	 */
	public void writeContent(final String key, final T content) throws ContentException
	{
		//make sure the key is not null or empty
		if (null == key || key.isEmpty())
		{
			throw new IllegalArgumentException("key cannot be null");
		}
		
		try
		{
			//convert the value to a byte array and write the raw byte value to the database
			dbService.saveValue(key, contentConverter.toByteArray(content));
		}
		catch (DBWriteException | ConversionException e)
		{
			throw new ContentException(e);
		}
	}
	
	/**
	 * Retrieve a list of content for the given keys
	 *
	 * @param keys
	 * @return
	 * @throws ContentException
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
	 * @param key
	 * @return
	 * @throws ContentException
	 */
	public T readContent(final String key) throws ContentException
	{
		//make sure the key is not null or empty
		if (null == key || key.isEmpty())
		{
			throw new IllegalArgumentException("key cannot be null");
		}
		
		try
		{
			//read the value from the database service as a raw byte array and check to see if the content is not null
			byte[] content = dbService.getValue(key);
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
}
