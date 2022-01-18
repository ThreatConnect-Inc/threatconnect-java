package com.threatconnect.app.addons.util.config.im;

import com.threatconnect.app.addons.util.config.install.Param;

import java.util.ArrayList;
import java.util.List;

public class Section
{
	private String sectionName;
	
	private final List<Param> params;
	
	public Section()
	{
		params = new ArrayList<>();
	}
	
	public String getSectionName()
	{
		return sectionName;
	}
	
	public void setSectionName(final String sectionName)
	{
		this.sectionName = sectionName;
	}
	
	public List<Param> getParams()
	{
		return params;
	}
}
