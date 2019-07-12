package com.threatconnect.playbooks.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class ServiceConfiguration
{
	private final long configId;
	private final Map<String, Object> config;
	
	public ServiceConfiguration(final long configId)
	{
		this.configId = configId;
		this.config = new HashMap<String, Object>();
	}
	
	public Map<String, Object> getConfig()
	{
		return config;
	}
	
	public long getConfigId()
	{
		return configId;
	}
	
	public Optional<Map.Entry<String, Object>> findConfig(final String name)
	{
		//for each of the name/value pairs
		for (Map.Entry<String, Object> param : getConfig().entrySet())
		{
			if (param.getKey().equals(name))
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
