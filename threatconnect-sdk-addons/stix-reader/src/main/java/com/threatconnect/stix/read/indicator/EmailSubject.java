package com.threatconnect.stix.read.indicator;

import com.threatconnect.sdk.model.CustomIndicator;

public class EmailSubject extends CustomIndicator
{
	public static final String INDICATOR_TYPE = "Email Subject";
	
	public EmailSubject()
	{
		super(INDICATOR_TYPE);
	}
}
