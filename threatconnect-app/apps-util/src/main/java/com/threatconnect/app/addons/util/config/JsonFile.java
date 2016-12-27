package com.threatconnect.app.addons.util.config;

import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * @author Greg Marut
 */
public class JsonFile
{
	private final File file;
	private final JsonObject root;
	
	public JsonFile(final File file) throws InvalidJsonFileException
	{
		// make sure the file is not null
		if (null == file || !file.exists())
		{
			throw new IllegalArgumentException("file cannot be null and must exist on the filesystem");
		}
		
		this.file = file;
		
		try
		{
			// parse the install file
			JsonParser jsonParser = new JsonParser();
			root = jsonParser.parse(new FileReader(file)).getAsJsonObject();
		}
		catch (JsonIOException | JsonSyntaxException | FileNotFoundException e)
		{
			throw new InvalidJsonFileException(file, e);
		}
	}
	
	public File getFile()
	{
		return file;
	}
	
	public JsonObject getRoot()
	{
		return root;
	}
}
