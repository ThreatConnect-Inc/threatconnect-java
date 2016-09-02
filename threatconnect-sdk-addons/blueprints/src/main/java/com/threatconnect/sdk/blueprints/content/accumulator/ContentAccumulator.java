package com.threatconnect.sdk.blueprints.content.accumulator;

import com.threatconnect.sdk.blueprints.content.StandardType;
import com.threatconnect.sdk.blueprints.content.converter.ContentConverter;
import com.threatconnect.sdk.blueprints.content.converter.ConversionException;
import com.threatconnect.sdk.blueprints.content.converter.ConverterFactory;
import com.threatconnect.sdk.blueprints.content.converter.UnknownConverterException;
import com.threatconnect.sdk.blueprints.db.DBReadException;
import com.threatconnect.sdk.blueprints.db.DBService;
import com.threatconnect.sdk.blueprints.db.DBWriteException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class ContentAccumulator<T>
{
	private static final Logger logger = LoggerFactory.getLogger(ContentAccumulator.class.getName());

	private final StandardType[] standardTypes;
	private final DBService dbService;

	public ContentAccumulator(final DBService dbService, final StandardType... standardTypes)
	{
		//make sure the db service is not null
		if (null == dbService)
		{
			throw new IllegalArgumentException("dbService cannot be null");
		}

		//make sure the array of standard types is not null
		if (null == standardTypes || standardTypes.length == 0)
		{
			throw new IllegalArgumentException("standardTypes cannot be null or empty");
		}

		this.dbService = dbService;
		this.standardTypes = standardTypes;
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
		else
		{
			try
			{
				//retrieve the content converter for this type and make sure that the converter is not null
				ContentConverter<T> converter = ConverterFactory.getConverter(key, standardTypes);

				//convert the value to a byte array and write the raw byte value to the database
				dbService.saveValue(key, converter.toByteArray(content));
			}
			catch (DBWriteException | UnknownConverterException | ConversionException e)
			{
				throw new ContentException(e);
			}
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
		List<T> result = new ArrayList<>();

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
		else
		{
			try
			{
				//retrieve the content converter for this type and make sure that the converter is not null
				ContentConverter<T> converter = ConverterFactory.getConverter(key, standardTypes);

				//read the value from the database service as a raw byte array and check to see if the content is not null
				byte[] content = dbService.getValue(key);
				if (content == null)
				{
					return null;
				}

				return converter.fromByteArray(content);
			}
			catch (DBReadException | ConversionException | UnknownConverterException e)
			{
				throw new ContentException(e);
			}
		}
	}
}
