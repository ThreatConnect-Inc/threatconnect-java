package com.threatconnect.sdk.app.model;

public class Address extends Indicator
{
	private String ip;
	
	public Address()
	{
		super(IndicatorType.ADDRESS);
	}
	
	public String getIp()
	{
		return ip;
	}
	
	public void setIp(String ip)
	{
		this.ip = ip;
	}
}
