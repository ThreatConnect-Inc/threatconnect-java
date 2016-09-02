package com.threatconnect.sdk.blueprints.content.converter;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;
import org.codehaus.jackson.type.JavaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class ContentConverter<T>
{
	private static final Logger logger = LoggerFactory.getLogger(ContentConverter.class);

	private final Class<T> genericClass;

	public ContentConverter(final Class<T> genericClass)
	{
		this.genericClass = genericClass;
	}

	/**
	 * Converts the data to a byte array
	 *
	 * @param source
	 * @return
	 * @throws ConversionException
	 */
	public byte[] toByteArray(final T source) throws ConversionException
	{
		ObjectMapper mapper = new ObjectMapper();
		TypeFactory typeFactory = mapper.getTypeFactory();
		JavaType type = constructType(typeFactory, genericClass);

		try
		{
			String content = mapper.writerWithType(type).writeValueAsString(source);
			return content.getBytes();
		}
		catch (IOException e)
		{
			throw new ConversionException(e);
		}
	}

	/**
	 * Converts the raw byte array data to an object
	 *
	 * @param raw
	 * @return
	 * @throws ConversionException
	 */
	public T fromByteArray(byte[] raw) throws ConversionException
	{
		try
		{
			ObjectMapper mapper = new ObjectMapper();
			TypeFactory typeFactory = mapper.getTypeFactory();
			JavaType type = constructType(typeFactory, genericClass);

			return new ObjectMapper().readValue(raw, type);
		}
		catch (IOException e)
		{
			throw new ConversionException(e);
		}
	}

	protected JavaType constructType(final TypeFactory typeFactory, final Class<T> genericClass)
	{
		return typeFactory.constructType(genericClass);
	}
}
