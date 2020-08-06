package com.threatconnect.app.services.message;

import java.util.List;

public class Ready extends AbstractCommandConfig
{
	private List<String> discoveryTypes;
	
	public Ready()
	{
		super(CommandType.Ready);
	}
	
	public List<String> getDiscoveryTypes()
	{
		return discoveryTypes;
	}
	
	public void setDiscoveryTypes(final List<String> discoveryTypes)
	{
		this.discoveryTypes = discoveryTypes;
	}
}
