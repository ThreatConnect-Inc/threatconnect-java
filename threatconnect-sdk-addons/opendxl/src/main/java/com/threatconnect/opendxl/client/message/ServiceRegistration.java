package com.threatconnect.opendxl.client.message;

import java.util.List;
import java.util.Map;

/**
 * @author Greg Marut
 */
public class ServiceRegistration
{
	private String serviceType;
	private Map<String, String> metaData;
	private List<String> requestChannels;
	private Integer ttlMins;
	private String serviceGuid;
	
	public String getServiceType()
	{
		return serviceType;
	}
	
	public void setServiceType(final String serviceType)
	{
		this.serviceType = serviceType;
	}
	
	public Map<String, String> getMetaData()
	{
		return metaData;
	}
	
	public void setMetaData(final Map<String, String> metaData)
	{
		this.metaData = metaData;
	}
	
	public List<String> getRequestChannels()
	{
		return requestChannels;
	}
	
	public void setRequestChannels(final List<String> requestChannels)
	{
		this.requestChannels = requestChannels;
	}
	
	public Integer getTtlMins()
	{
		return ttlMins;
	}
	
	public void setTtlMins(final Integer ttlMins)
	{
		this.ttlMins = ttlMins;
	}
	
	public String getServiceGuid()
	{
		return serviceGuid;
	}
	
	public void setServiceGuid(final String serviceGuid)
	{
		this.serviceGuid = serviceGuid;
	}
}
