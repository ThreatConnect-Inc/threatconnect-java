package com.threatconnect.app.addons.util.config.layout;

import java.util.ArrayList;
import java.util.List;

public class Layout
{
	private final List<LayoutGroup> groups;
	
	public Layout()
	{
		this.groups = new ArrayList<LayoutGroup>();
	}
	
	public List<LayoutGroup> getGroups()
	{
		return groups;
	}
}
