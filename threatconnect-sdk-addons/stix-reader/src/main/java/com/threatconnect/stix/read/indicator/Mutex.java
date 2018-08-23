package com.threatconnect.stix.read.indicator;

import com.threatconnect.sdk.model.CustomIndicator;

/**
 * @author Greg Marut
 */
public class Mutex extends CustomIndicator
{
	public static final String INDICATOR_TYPE = "Mutex";
	
	public Mutex()
	{
		super(INDICATOR_TYPE);
	}
}
