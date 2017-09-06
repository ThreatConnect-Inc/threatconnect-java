package com.threatconnect.plugin.pkg;

import java.io.File;

public class Profile<T>
{
	private final String profileName;
	private final File sourceFile;
	private final T obj;
	
	public Profile(final String profileName, final File sourceFile, final T obj)
	{
		this.profileName = profileName;
		this.sourceFile = sourceFile;
		this.obj = obj;
	}
	
	public String getProfileName()
	{
		return profileName;
	}
	
	public File getSourceFile()
	{
		return sourceFile;
	}
	
	public T getSource()
	{
		return obj;
	}
}
