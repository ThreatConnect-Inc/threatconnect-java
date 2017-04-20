package com.threatconnect.sdk.model;

public class Host extends Indicator
{
	public static final String INDICATOR_TYPE = "Host";
	
	public Host()
	{
		super(INDICATOR_TYPE);
	}
	
	private String hostName;
	private String dnsActive;
	private String whoisActive;
	
	public String getHostName()
	{
		return hostName;
	}
	
	public void setHostName(final String hostName)
	{
		if (null != hostName)
		{
			this.hostName = hostName.toLowerCase();
		}
		else
		{
			this.hostName = null;
		}
	}
	
	public String getDnsActive()
	{
		return dnsActive;
	}
	
	public void setDnsActive(final String dnsActive)
	{
		this.dnsActive = dnsActive;
	}
	
	public String getWhoisActive()
	{
		return whoisActive;
	}
	
	public void setWhoisActive(final String whoisActive)
	{
		this.whoisActive = whoisActive;
	}
	
	@Override
	public String getIdentifier()
	{
		return getHostName();
	}
}
