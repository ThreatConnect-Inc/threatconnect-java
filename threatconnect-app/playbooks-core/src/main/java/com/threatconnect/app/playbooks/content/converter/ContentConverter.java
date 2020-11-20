package com.threatconnect.app.playbooks.content.converter;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;
import com.threatconnect.app.addons.util.config.install.StandardPlaybookType;

import java.io.IOException;
import java.text.DateFormat;

public abstract class ContentConverter<T>
{
	public static final DateFormat DEFAULT_DATE_FORMATTER = new ISO8601DateFormat();
	
	private static final ObjectMapper mapper = new ObjectMapper();
	
	static
	{
		mapper.setDateFormat(DEFAULT_DATE_FORMATTER);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}
	
	private final StandardPlaybookType standardPlaybookType;
	
	public ContentConverter(final StandardPlaybookType standardPlaybookType)
	{
		this.standardPlaybookType = standardPlaybookType;
	}
	
	/**
	 * Converts the data to a byte array
	 *
	 * @param source the object to convert to a byte array
	 * @return the serialized version of the source object
	 * @throws ConversionException if there was an issue converting the data type
	 */
	public byte[] toByteArray(final T source) throws ConversionException
	{
		TypeFactory typeFactory = mapper.getTypeFactory();
		JavaType type = constructType(typeFactory);
		
		try
		{
			return mapper.writerWithType(type).writeValueAsBytes(source);
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
	 * @throws ConversionException if there was an issue converting the data type
	 */
	public T fromByteArray(byte[] raw) throws ConversionException
	{
		try
		{
			TypeFactory typeFactory = mapper.getTypeFactory();
			JavaType type = constructType(typeFactory);
			
			return mapper.readValue(raw, type);
		}
		catch (IOException e)
		{
			throw new ConversionException(e);
		}
	}
	
	public boolean supports(final String type)
	{
		return supports(StandardPlaybookType.valueOf(type));
	}
	
	public boolean supports(final StandardPlaybookType standardPlaybookType)
	{
		return this.standardPlaybookType == standardPlaybookType;
	}
	
	public StandardPlaybookType getStandardPlaybookType()
	{
		return standardPlaybookType;
	}
	
	protected abstract JavaType constructType(final TypeFactory typeFactory);
}
