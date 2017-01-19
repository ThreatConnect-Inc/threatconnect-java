package com.threatconnect.plugin.pkg;

import java.util.List;

/**
 * @author Greg Marut
 */
public class PythonPackageFileFIlter extends PackageFileFilter
{
	public static final String BUILD = "build";
	public static final String TEMP = "temp";
	
	public PythonPackageFileFIlter(final List<String> exclude, final boolean includeDefaults)
	{
		super(exclude, includeDefaults);
		
		if(includeDefaults)
		{
			getExclude().add(BUILD);
			getExclude().add(TEMP);
		}
	}
}
