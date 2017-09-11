package com.threatconnect.sdk.parser.util;

public class StringLengthUtil
{
	public static final String ELLIPSIS = "...";
	public static final int ML_GROUP_NAME = 100;
	
	public static String trimGroupName(final String groupName)
	{
		return trimString(groupName, ML_GROUP_NAME);
	}
	
	public static String trimString(final String string, final int maxLength)
	{
		return trimString(string, maxLength, false);
	}
	
	public static String trimString(final String string, final int maxLength, final boolean ellipsis)
	{
		//make sure the string is not null
		if (null != string)
		{
			if (string.trim().length() > maxLength)
			{
				if (ellipsis)
				{
					return string.trim().substring(0, maxLength - ELLIPSIS.length()) + ELLIPSIS;
				}
				else
				{
					return string.trim().substring(0, maxLength);
				}
			}
			else
			{
				return string.trim();
			}
		}
		else
		{
			return null;
		}
	}
}
