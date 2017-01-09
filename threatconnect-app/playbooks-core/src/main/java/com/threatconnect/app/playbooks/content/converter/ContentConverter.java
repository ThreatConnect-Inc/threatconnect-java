package com.threatconnect.app.playbooks.content.converter;

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
	 * @param raw
	 * @return
	 * @throws ConversionException
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
