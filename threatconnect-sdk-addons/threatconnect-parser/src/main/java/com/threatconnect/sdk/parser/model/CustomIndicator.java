package com.threatconnect.sdk.parser.model;

/**
 * @author Greg Marut
 */
public class CustomIndicator extends Indicator
{
	public CustomIndicator(final String indicatorName)
	{
		super(indicatorName);
		
		//protected against a custom indicator being created with the same type as a standard indicator
		switch (indicatorName)
		{
			case Address.INDICATOR_TYPE:
			case EmailAddress.INDICATOR_TYPE:
			case Host.INDICATOR_TYPE:
			case File.INDICATOR_TYPE:
			case Url.INDICATOR_TYPE:
				throw new IllegalArgumentException(
					indicatorName + " is already a predefined indicator type and cannot be used as a custom indicator.");
		}
	}
	
	@Override
	public String getIdentifier()
	{
		return null;
	}
}
