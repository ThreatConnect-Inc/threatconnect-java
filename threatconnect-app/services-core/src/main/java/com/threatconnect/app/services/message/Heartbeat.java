package com.threatconnect.app.services.message;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Heartbeat extends CommandMessage
{
	private final Map<String, String> metric;
	private String serviceKey;
	private String serviceSessionId;
	private String serverName;
	private Date launchTime;
	private double memoryPercent;
	private double cpuPercent;
	
	public Heartbeat()
	{
		super(CommandType.Heartbeat);
		
		metric = new HashMap<String, String>();
	}
	
	public Map<String, String> getMetric()
	{
		return metric;
	}
	
	public String getServiceKey()
	{
		return serviceKey;
	}
	
	public void setServiceKey(final String serviceKey)
	{
		this.serviceKey = serviceKey;
	}
	
	public String getServiceSessionId()
	{
		return serviceSessionId;
	}
	
	public void setServiceSessionId(final String serviceSessionId)
	{
		this.serviceSessionId = serviceSessionId;
	}
	
	public String getServerName()
	{
		return serverName;
	}
	
	public void setServerName(final String serverName)
	{
		this.serverName = serverName;
	}
	
	public Date getLaunchTime()
	{
		return launchTime;
	}
	
	public void setLaunchTime(final Date launchTime)
	{
		this.launchTime = launchTime;
	}
	
	public double getMemoryPercent()
	{
		return memoryPercent;
	}
	
	public void setMemoryPercent(final double memoryPercent)
	{
		this.memoryPercent = memoryPercent;
	}
	
	public double getCpuPercent()
	{
		return cpuPercent;
	}
	
	public void setCpuPercent(final double cpuPercent)
	{
		this.cpuPercent = cpuPercent;
	}
}
