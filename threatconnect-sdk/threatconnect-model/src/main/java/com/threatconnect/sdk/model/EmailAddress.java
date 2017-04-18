package com.threatconnect.sdk.model;

public class EmailAddress extends Indicator
{
	public static final String INDICATOR_TYPE = "EmailAddress";
	
	private String address;
	
	public EmailAddress()
	{
		super(INDICATOR_TYPE);
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
	public String getIdentifier()
	{
		return getAddress();
	}
}
