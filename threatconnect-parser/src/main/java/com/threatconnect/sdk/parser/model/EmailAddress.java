package com.threatconnect.sdk.parser.model;

public class EmailAddress extends Indicator
{
	private String address;
	
	public EmailAddress()
	{
		super(IndicatorType.EMAIL_ADDRESS);
	}
	
	public String getAddress()
	{
		return address;
	}
	
	public void setAddress(String address)
	{
		this.address = address;
	}
}
