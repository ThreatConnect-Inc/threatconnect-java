package com.threatconnect.app.addons.util.config.install;

import java.util.Arrays;

public enum StandardPlaybookType
{
	String,
	StringArray,
	TCEntity,
	TCEntityArray,
	TCEnhancedEntity,
	TCEnhancedEntityArray,
	Binary,
	BinaryArray,
	KeyValue,
	KeyValueArray;
	
	public static boolean isValid(final String type)
	{
		try
		{
			fromString(type);
			return true;
		}
		catch (IllegalArgumentException e)
		{
			return false;
		}
	}
	
	public static StandardPlaybookType fromString(final String type)
	{
		//for each of the values
		for (StandardPlaybookType standardPlaybookType : StandardPlaybookType.values())
		{
			if (standardPlaybookType.toString().equalsIgnoreCase(type))
			{
				return standardPlaybookType;
			}
		}
		
		throw new IllegalArgumentException(
			type + " is not a valid " + StandardPlaybookType.class.getSimpleName() + ". Possible values are: " + Arrays
				.toString(StandardPlaybookType.values()));
	}
	
	
}
