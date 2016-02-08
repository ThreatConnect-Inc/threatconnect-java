package com.threatconnect.sdk.app.model;

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
	
	public void setHostName(String hostName)
	{
		this.hostName = hostName;
	}
	
	public String getDnsActive()
	{
		return dnsActive;
	}
	
	public void setDnsActive(String dnsActive)
	{
		this.dnsActive = dnsActive;
	}
	
	public String getWhoisActive()
	{
		return whoisActive;
	}
	
	public void setWhoisActive(String whoisActive)
	{
		this.whoisActive = whoisActive;
	}
}
