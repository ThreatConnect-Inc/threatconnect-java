package com.threatconnect.sdk.parser.source;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class UrlDataSource implements DataSource
{
	// holds the url to read
	private final URL url;
	
	public UrlDataSource(final String url) throws MalformedURLException
	{
		this.url = new URL(url);
	}
	
	@Override
	public InputStream read() throws IOException
	{
		// load the url and read the content
		return url.openConnection().getInputStream();
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof FileDataSource)
		{
			UrlDataSource other = (UrlDataSource) obj;
			return url.equals(other.url);
		}
		
		return false;
	}
	
	@Override
	public int hashCode()
	{
		return url.hashCode();
	}
	
	@Override
	public String toString()
	{
		return url.toString();
	}
}
