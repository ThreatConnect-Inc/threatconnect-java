package com.threatconnect.sdk.parser.source;

import java.io.IOException;
import java.io.InputStream;

public class InputStreamDataSource implements DataSource
{
	private final InputStream inputStream;
	
	public InputStreamDataSource(final InputStream inputStream)
	{
		this.inputStream = inputStream;
	}
	
	public InputStream getInputStream()
	{
		return inputStream;
	}
	
	@Override
	public InputStream read() throws IOException
	{
		return inputStream;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		return inputStream.equals(obj);
	}
	
	@Override
	public int hashCode()
	{
		return inputStream.hashCode();
	}
	
	@Override
	public String toString()
	{
		return inputStream.toString();
	}
}
