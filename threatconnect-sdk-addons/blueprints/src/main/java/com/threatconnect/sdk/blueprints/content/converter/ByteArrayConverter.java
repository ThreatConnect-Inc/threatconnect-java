package com.threatconnect.sdk.blueprints.content.converter;

/**
 * @author Greg Marut
 */
public class ByteArrayConverter extends DefaultContentConverter<byte[]>
{
	public ByteArrayConverter()
	{
		super(byte[].class);
	}
}
