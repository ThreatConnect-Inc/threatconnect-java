package com.threatconnect.plugin.pkg;

import org.apache.commons.io.filefilter.NotFileFilter;
import org.apache.commons.io.filefilter.WildcardFileFilter;

import java.io.FilenameFilter;
import java.util.List;

/**
 * @author Greg Marut
 */
public class PackageFileFilter
{
	public static final String POM_XML = "pom.xml";
	public static final String TARGET = "target";
	public static final String DOT_STAR = ".*";
	public static final String IML = "*.iml";
	public static final String INSTALL_JSON = "*install.json";
	public static final String README_MD = "*README.md";
	public static final String INSTALL_CONF = "*install.conf";
	public static final String BITBUCKET_PIPELINES = "bitbucket-pipelines.yml";
	
	//holds the list of files to exclude
	private final List<String> exclude;
	
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
			getExclude().add(DOT_STAR);
			getExclude().add(IML);
			getExclude().add(INSTALL_JSON);
			getExclude().add(INSTALL_CONF);
			getExclude().add(README_MD);
			getExclude().add(BITBUCKET_PIPELINES);
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