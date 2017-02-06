package com.threatconnect.sdk.parser.model;

public class Address extends Indicator
{
	public static final String INDICATOR_TYPE = "Address";
	
	private String ip;
	
	public Address()
	{
		super(INDICATOR_TYPE);
	}
	
	public String getIp()
	{
		return ip;
	}
	
	public void setIp(String ip)
	{
		this.ip = ip;
	}
	
	@Override
	public String getIdentifier()
	{
		return getIp();
	}
}
