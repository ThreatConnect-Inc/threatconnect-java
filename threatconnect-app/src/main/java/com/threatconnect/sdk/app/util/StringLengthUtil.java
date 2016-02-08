package com.threatconnect.sdk.app.util;

public class StringLengthUtil
{
	public static final int ML_INCIDENT_NAME = 100;
	
	public static final String trimIncidentName(final String incidentName)
	{
		return trimString(incidentName, ML_INCIDENT_NAME);
	}
	
	public static final String trimString(final String string, final int maxLength)
	{
		if (string.trim().length() > maxLength)
		{
			return string.trim().substring(0, maxLength);
		}
		else
		{
			return string.trim();
		}
	}
}
