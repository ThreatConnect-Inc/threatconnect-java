package com.threatconnect.app.addons.util.config.install;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Greg Marut
 */
public enum ParamDataType
{
	String,
	Boolean,
	Choice,
	MultiChoice,
	StringMixed,
	KeyValueList;
	
	private static final Logger logger = LoggerFactory.getLogger(ParamDataType.class);
	
	public static ParamDataType fromString(final String type)
	{
		try
		{
			//convert this string to a param data type
			return ParamDataType.valueOf(type);
		}
		catch (IllegalArgumentException e)
		{
			logger.error("{} is not a valid ParamDataType. Possible values are: {}", type, ParamDataType.values());
			throw e;
		}
	}
}
