package com.threatconnect.plugin.pkg;

import java.util.List;

/**
 * @author Greg Marut
 */
public class PythonPackageFileFilter extends PackageFileFilter
{
	public static final String BUILD = "build";
	public static final String TEMP = "temp";
	public static final String PYC = "*.pyc";
	
	public PythonPackageFileFilter(final List<String> exclude)
	{
		this(exclude, true);
	}
	
	public PythonPackageFileFilter(final List<String> exclude, final boolean includeDefaults)
	{
		super(exclude, includeDefaults);
		
		if (includeDefaults)
		{
			getExclude().add(BUILD);
			getExclude().add(TEMP);
			getExclude().add(PYC);
		}
	}
}
