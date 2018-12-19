package com.threatconnect.app.addons.util.config.layout;

import java.util.ArrayList;
import java.util.List;

public class Layout
{
	private final List<LayoutInput> inputs;
	
	public Layout()
	{
		this.inputs = new ArrayList<LayoutInput>();
	}
	
	public List<LayoutInput> getInputs()
	{
		return inputs;
	}
}
