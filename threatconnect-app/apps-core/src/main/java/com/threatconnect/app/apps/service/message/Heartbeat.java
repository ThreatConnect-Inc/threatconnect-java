package com.threatconnect.app.apps.service.message;

import java.util.HashMap;
import java.util.Map;

public class Heartbeat extends CommandMessage
{
	private final Map<String, String> metric;
	
	public Heartbeat()
	{
		super(Command.Heartbeat);
		
		metric = new HashMap<String, String>();
	}
	
	public Map<String, String> getMetric()
	{
		return metric;
	}
}
