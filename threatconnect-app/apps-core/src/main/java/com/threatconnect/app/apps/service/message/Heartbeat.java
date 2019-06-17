package com.threatconnect.app.apps.service.message;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Heartbeat extends CommandMessage
{
	private Map<String, String> metric;
	
	private String serviceKey;
	private String serviceSessionId;
	private String serverName;
	private Date launchTime;
	private double memoryPercent;
	private double cpuPercent;
	
	public Heartbeat()
	{
		super(Command.Heartbeat);
		
		metric = new ConcurrentHashMap<>();
	}
	
	public Map<String, String> getMetric()
	{
		return metric;
	}
	
	public void setMetric(Map<String, String> metric)
	{
		this.metric = metric;
	}
	
	public String getServerName()
	{
		return serverName;
	}
	
	public void setServerName(String serverName)
	{
		this.serverName = serverName;
	}
	
	public double getMemoryPercent()
	{
		return memoryPercent;
	}
	
	public void setMemoryPercent(double memoryPercent)
	{
		this.memoryPercent = memoryPercent;
	}
	
	public double getCpuPercent()
	{
		return cpuPercent;
	}
	
	public void setCpuPercent(double cpuPercent)
	{
		this.cpuPercent = cpuPercent;
	}
	
	public String getServiceSessionId()
	{
		return serviceSessionId;
	}
	
	public void setServiceSessionId(String serviceSessionId)
	{
		this.serviceSessionId = serviceSessionId;
	}
	
	public String getServiceKey()
	{
		return serviceKey;
	}
	
	public void setServiceKey(String serviceKey)
	{
		this.serviceKey = serviceKey;
	}
	
	public Date getLaunchTime()
	{
		return launchTime;
	}
	
	public void setLaunchTime(Date launchTime)
	{
		this.launchTime = launchTime;
	}
}
