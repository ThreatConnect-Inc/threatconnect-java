package com.threatconnect.sdk.client.fluent;

import com.threatconnect.sdk.server.entity.Host;

public class HostBuilder extends AbstractIndicatorBuilder<HostBuilder>
{
	private String hostName;
	private String dnsActive;
	private String whoisActive;
	
	public HostBuilder withHostName(String hostName)
	{
		this.hostName = hostName;
		return this;
	}
	
	public HostBuilder withDnsActive(String dnsActive)
	{
		this.dnsActive = dnsActive;
		return this;
	}
	
	public HostBuilder withWhoisActive(String whoisActive)
	{
		this.whoisActive = whoisActive;
		return this;
	}
	
	public Host createHost()
	{
		return new Host(id, owner, ownerName, type, dateAdded, lastModified, rating, confidence, threatAssessRating,
			threatAssessConfidence, webLink, source, description, summary, hostName, dnsActive, whoisActive);
	}
}