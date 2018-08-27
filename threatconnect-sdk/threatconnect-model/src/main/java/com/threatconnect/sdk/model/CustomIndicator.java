package com.threatconnect.sdk.model;

/**
 * @author Greg Marut
 */
public class CustomIndicator extends Indicator
{
	private String value;
	private String apiBranch;
	
	public CustomIndicator(final String indicatorType)
	{
		super(indicatorType);
		
		//protected against a custom indicator being created with the same type as a standard indicator
		switch (indicatorType)
		{
			case Address.INDICATOR_TYPE:
			case EmailAddress.INDICATOR_TYPE:
			case Host.INDICATOR_TYPE:
			case File.INDICATOR_TYPE:
			case Url.INDICATOR_TYPE:
				throw new IllegalArgumentException(
					indicatorType + " is already a predefined indicator type and cannot be used as a custom indicator.");
		}
	}
	
	public String getValue()
	{
		return value;
	}
	
	public void setValue(final String value)
	{
		this.value = value;
	}
	
	@Override
	public String getIdentifier()
	{
		return value;
	}
}
