package com.threatconnect.sdk.app.aot;

import java.util.Map;

public class AOTMessage
{
	private String type;
	private Map<String, String> params;
	
	public String getType()
	{
		return type;
	}
	
	public void setType(final String type)
	{
		this.type = type;
	}
	
	public Map<String, String> getParams()
	{
		return params;
	}
	
	public void setParams(final Map<String, String> params)
	{
		this.params = params;
	}
}
