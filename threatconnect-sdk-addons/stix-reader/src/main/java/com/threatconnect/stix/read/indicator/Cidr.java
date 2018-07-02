package com.threatconnect.stix.read.indicator;

import com.threatconnect.sdk.model.CustomIndicator;

/**
 * @author Greg Marut
 */
public class Cidr extends CustomIndicator
{
	public static final String INDICATOR_TYPE = "Cidr";
	
	public Cidr()
	{
		super(INDICATOR_TYPE);
	}
}
