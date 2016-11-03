package com.threatconnect.app.apps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An implementation of the {@link AppConfig} class which will pull any unknown properties from the system properties or
 * VM arguments
 *
 * @author Greg Marut
 */
public class SystemPropertiesAppConfig extends AppConfig
{
	private static final Logger logger = LoggerFactory.getLogger(SystemPropertiesAppConfig.class);
	
	@Override
	protected String loadSetting(final String key)
	{
		String value = System.getProperty(key);
		logger.trace("Reading param {}={}", key, value);
		
		return value;
	}
}
