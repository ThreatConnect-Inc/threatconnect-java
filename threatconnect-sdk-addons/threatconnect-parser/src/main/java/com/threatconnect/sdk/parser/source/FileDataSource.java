package com.threatconnect.sdk.parser.source;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileDataSource implements DataSource
{
	private final File file;
	
	public FileDataSource(final File file)
	{
		this.file = file;
	}
	
	public File getFile()
	{
		return file;
	}
	
	@Override
	public InputStream read() throws IOException
	{
		return new FileInputStream(file);
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof FileDataSource)
		{
			FileDataSource other = (FileDataSource) obj;
			return file.getAbsolutePath().equals(other.file.getAbsolutePath());
		}
		
		return false;
	}
	
	@Override
	public int hashCode()
	{
		return file.getAbsolutePath().hashCode();
	}
	
	@Override
	public String toString()
	{
		return file.getAbsolutePath();
	}
}
