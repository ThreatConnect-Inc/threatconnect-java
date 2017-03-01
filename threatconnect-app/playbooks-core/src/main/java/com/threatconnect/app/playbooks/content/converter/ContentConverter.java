package com.threatconnect.app.playbooks.content.converter;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;
import org.codehaus.jackson.map.util.ISO8601DateFormat;
import org.codehaus.jackson.type.JavaType;

import java.io.IOException;
import java.text.DateFormat;

public abstract class ContentConverter<T>
{
	public static final DateFormat DEFAULT_DATE_FORMATTER = new ISO8601DateFormat();
	
	private final ObjectMapper mapper;
	
	public ContentConverter()
	{
		mapper = new ObjectMapper();
		mapper.setDateFormat(DEFAULT_DATE_FORMATTER);
		mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}
	
	/**
	 * Converts the data to a byte array
	 *
	 * @param source the object to convert to a byte array
	 * @return the serialized version of the source object
	 * @throws ConversionException if there was an issue reading/writing to the database.
	 */
	public byte[] toByteArray(final T source) throws ConversionException
	{
		TypeFactory typeFactory = mapper.getTypeFactory();
		JavaType type = constructType(typeFactory);
		
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
	 * @param raw the serialized object
	 * @return the object which was read from the serialized byte array
	 * @throws ConversionException if there was an issue reading/writing to the database.
	 */
	public T fromByteArray(byte[] raw) throws ConversionException
	{
		try
		{
			TypeFactory typeFactory = mapper.getTypeFactory();
			JavaType type = constructType(typeFactory);
			
			return new ObjectMapper().readValue(raw, type);
		}
		catch (IOException e)
		{
			throw new ConversionException(e);
		}
	}
	
	protected abstract JavaType constructType(final TypeFactory typeFactory);
}
