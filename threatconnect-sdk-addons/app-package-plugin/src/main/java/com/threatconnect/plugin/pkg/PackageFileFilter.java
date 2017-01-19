package com.threatconnect.plugin.pkg;

import org.apache.commons.io.filefilter.NotFileFilter;
import org.apache.commons.io.filefilter.WildcardFileFilter;

import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Greg Marut
 */
public class PackageFileFilter
{
	public static final String POM_XML = "pom.xml";
	public static final String TARGET = "target";
	public static final String GIT = ".git";
	public static final String GITIGNORE = ".gitignore";
	
	//holds the list of files to exclude
	private final List<String> exclude;
	
	public PackageFileFilter()
	{
		this(new ArrayList<String>(), true);
	}
	
	public PackageFileFilter(final List<String> exclude)
	{
		this(exclude, true);
	}
	
	public PackageFileFilter(final List<String> exclude, final boolean includeDefaults)
	{
		//make sure the exclude list is not null
		if (null == exclude)
		{
			throw new IllegalArgumentException("exclude cannot be null");
		}
		
		this.exclude = exclude;
		
		if (includeDefaults)
		{
			getExclude().add(POM_XML);
			getExclude().add(TARGET);
			getExclude().add(GIT);
			getExclude().add(GITIGNORE);
		}
	}
	
	public FilenameFilter createFilenameFilter()
	{
		//create the new filter to exclude these file names
		return new NotFileFilter(new WildcardFileFilter(exclude.toArray(new String[] {})));
	}
	
	public List<String> getExclude()
	{
		return exclude;
	}
}