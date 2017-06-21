package com.threatconnect.app.addons.util.config.install;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Greg Marut
 */
public enum PlaybookVariableType
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
	KeyValueArray,
	Any;
	
	private static final Logger logger = LoggerFactory.getLogger(PlaybookVariableType.class);
	
	public static PlaybookVariableType fromString(final String type)
	{
		try
		{
			//convert this string to a param data type
			return PlaybookVariableType.valueOf(type);
		}
		catch (IllegalArgumentException e)
		{
			logger.error("{} is not a valid PlaybookVariableType. Possible values are: {}", type, PlaybookVariableType.values());
			throw e;
		}
	}
}
