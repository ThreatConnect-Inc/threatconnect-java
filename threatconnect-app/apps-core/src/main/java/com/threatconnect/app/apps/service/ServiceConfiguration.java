package com.threatconnect.app.apps.service;

import com.threatconnect.app.apps.service.message.NameValuePair;

import java.util.ArrayList;
import java.util.List;

public class ServiceConfiguration
{
	private final List<NameValuePair<String, String>> params;
	
	private long configId;
	
	public ServiceConfiguration()
	{
		this.params = new ArrayList<NameValuePair<String, String>>();
	}
	
	public List<NameValuePair<String, String>> getParams()
	{
		return params;
	}
	
	public long getConfigId()
	{
		return configId;
	}
	
	public void setConfigId(final long configId)
	{
		this.configId = configId;
	}
}
