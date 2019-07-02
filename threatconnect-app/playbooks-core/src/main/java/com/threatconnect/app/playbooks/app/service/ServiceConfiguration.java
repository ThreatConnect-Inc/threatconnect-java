package com.threatconnect.app.playbooks.app.service;

import com.threatconnect.app.apps.service.message.NameValuePair;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ServiceConfiguration
{
	private final long configId;
	private final List<NameValuePair<String, String>> config;
	
	public ServiceConfiguration(final long configId)
	{
		this.configId = configId;
		this.config = new ArrayList<NameValuePair<String, String>>();
	}
	
	public List<NameValuePair<String, String>> getConfig()
	{
		return config;
	}
	
	public long getConfigId()
	{
		return configId;
	}
	
	public Optional<NameValuePair<String, String>> findConfig(final String name)
	{
		//for each of the name/value pairs
		for (NameValuePair<String, String> param : getConfig())
		{
			if (param.getName().equals(name))
			{
				return Optional.of(param);
			}
		}
		
		return Optional.empty();
	}
	
	@Override
	public boolean equals(final Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (o == null || getClass() != o.getClass())
		{
			return false;
		}
		final ServiceConfiguration that = (ServiceConfiguration) o;
		return configId == that.configId;
	}
	
	@Override
	public int hashCode()
	{
		return Objects.hash(configId);
	}
}
