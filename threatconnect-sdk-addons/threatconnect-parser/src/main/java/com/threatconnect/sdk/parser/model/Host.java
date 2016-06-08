package com.threatconnect.sdk.parser.model;

public class Host extends Indicator
{
	public Host()
	{
		super(IndicatorType.HOST);
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
	public String toString()
	{
		return getHostName();
	}
}
