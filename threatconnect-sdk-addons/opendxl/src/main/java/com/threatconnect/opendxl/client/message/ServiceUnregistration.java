package com.threatconnect.opendxl.client.message;

/**
 * @author Greg Marut
 */
public class ServiceUnregistration
{
	private String serviceGuid;
	
	public String getServiceGuid()
	{
		return serviceGuid;
	}
	
	public void setServiceGuid(final String serviceGuid)
	{
		this.serviceGuid = serviceGuid;
	}
}
