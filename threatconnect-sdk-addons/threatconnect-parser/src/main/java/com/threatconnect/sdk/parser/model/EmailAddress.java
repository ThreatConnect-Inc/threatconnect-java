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
	
	public void setAddress(final String address)
	{
		if (null != address)
		{
			this.address = address.toLowerCase();
		}
		else
		{
			this.address = null;
		}
	}
	
	@Override
	public String toString()
	{
		return getAddress();
	}
}
