package com.threatconnect.app.addons.util.config.install;

import java.util.Arrays;

/**
 * @author Greg Marut
 */
public class PlaybookVariableType
{
	public static PlaybookVariableType String = new PlaybookVariableType(StandardType.String);
	public static PlaybookVariableType StringArray = new PlaybookVariableType(StandardType.StringArray);
	public static PlaybookVariableType TCEntity = new PlaybookVariableType(StandardType.TCEntity);
	public static PlaybookVariableType TCEntityArray = new PlaybookVariableType(StandardType.TCEntityArray);
	public static PlaybookVariableType TCEnhancedEntity = new PlaybookVariableType(StandardType.TCEnhancedEntity);
	public static PlaybookVariableType TCEnhancedEntityArray =
		new PlaybookVariableType(StandardType.TCEnhancedEntityArray);
	public static PlaybookVariableType Binary = new PlaybookVariableType(StandardType.Binary);
	public static PlaybookVariableType BinaryArray = new PlaybookVariableType(StandardType.BinaryArray);
	public static PlaybookVariableType KeyValue = new PlaybookVariableType(StandardType.KeyValue);
	public static PlaybookVariableType KeyValueArray = new PlaybookVariableType(StandardType.KeyValueArray);
	
	private String type;
	private boolean custom;
	
	public PlaybookVariableType(final String type)
	{
		//make sure the type is not null
		if (null == type)
		{
			throw new IllegalArgumentException("type cannot be null");
		}
		
		this.type = type;
		this.custom = !StandardType.isValid(type);
	}
	
	public PlaybookVariableType(final StandardType type)
	{
		this(type.toString());
	}
	
	public String getType()
	{
		return type;
	}
	
	public boolean isCustom()
	{
		return custom;
	}
	
	@Override
	public boolean equals(final Object o)
	{
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		
		final PlaybookVariableType that = (PlaybookVariableType) o;
		
		return type.equalsIgnoreCase(that.type);
	}
	
	@Override
	public int hashCode()
	{
		return type.hashCode();
	}
	
	public enum StandardType
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
		
		public static StandardType fromString(final String type)
		{
			//for each of the values
			for (StandardType standardType : StandardType.values())
			{
				if (standardType.toString().equalsIgnoreCase(type))
				{
					return standardType;
				}
			}
			
			throw new IllegalArgumentException(
				type + " is not a valid PlaybookVariableType.StandardType. Possible values are: " +
					Arrays.toString(StandardType.values()));
		}
	}
}
