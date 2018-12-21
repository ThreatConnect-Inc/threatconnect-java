package com.threatconnect.app.addons.util.config.layout;

import java.util.ArrayList;
import java.util.List;

public class Layout
{
	private final List<LayoutInput> inputs;
	private final List<LayoutOutput> outputs;
	
	public Layout()
	{
		this.inputs = new ArrayList<LayoutInput>();
		this.outputs = new ArrayList<LayoutOutput>();
	}
	
	public List<LayoutInput> getInputs()
	{
		return inputs;
	}
	
	public List<LayoutOutput> getOutputs()
	{
		return outputs;
	}
}
