package com.threatconnect.plugin.pkg.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class InstallJson
{
	private static final String APPLICATION_NAME = "applicationName";
	private static final String PROGRAM_VERSION = "programVersion";
	
	private final JsonObject root;
	
	public InstallJson(final File installJsonFile) throws InvalidInstallJsonFileException
	{
		// make sure the file is not null
		if (null == installJsonFile)
		{
			throw new IllegalArgumentException("installJsonFile cannot be null");
		}
		
		try
		{
			// parse the install file
			JsonParser jsonParser = new JsonParser();
			root = jsonParser.parse(new FileReader(installJsonFile)).getAsJsonObject();
		}
		catch (JsonIOException | JsonSyntaxException | FileNotFoundException e)
		{
			throw new InvalidInstallJsonFileException(installJsonFile, e);
		}
	}
	
	public String getApplicatioName()
	{
		return getAsString(APPLICATION_NAME);
	}
	
	public String getProgramVersion()
	{
		return getAsString(PROGRAM_VERSION);
	}
	
	private String getAsString(final String... paths)
	{
		return getAsString(root, paths);
	}
	
	/**
	 * Given a root node, traverses the json object and safely handles any null objects along the
	 * way
	 * 
	 * @param root
	 * @param paths
	 * @return
	 */
	private String getAsString(final JsonElement root, final String... paths)
	{
		JsonElement current = root;
		
		// for each of the paths
		for (String path : paths)
		{
			// make sure the current is not null
			if (null != current)
			{
				current = current.getAsJsonObject().get(path);
			}
		}
		
		// make sure the current element is not null
		if (null != current)
		{
			return current.getAsString();
		}
		else
		{
			return null;
		}
	}
}
