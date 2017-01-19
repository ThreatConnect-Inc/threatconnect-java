package com.threatconnect.plugin.pkg;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Greg Marut
 */
public class JavaPackageFileFilter extends PackageFileFilter
{
	public static final String SRC = "src";
	
	public JavaPackageFileFilter()
	{
		this(new ArrayList<String>(), true);
	}
	
	public JavaPackageFileFilter(final List<String> exclude, final boolean includeDefaults)
	{
		super(exclude, includeDefaults);
		
		if (includeDefaults)
		{
			getExclude().add(SRC);
		}
	}
}
