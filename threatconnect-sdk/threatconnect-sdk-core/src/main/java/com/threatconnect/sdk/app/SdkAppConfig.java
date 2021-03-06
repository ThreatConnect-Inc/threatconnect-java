package com.threatconnect.sdk.app;

import com.threatconnect.app.apps.AppConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Greg Marut
 */
public class SdkAppConfig extends AppConfig
{
	private static final Logger logger = LoggerFactory.getLogger(SdkAppConfig.class);
	
	private static SdkAppConfig instance;
	
	protected SdkAppConfig()
	{
		
	}
	
	@Override
	protected String loadSetting(final String key)
	{
		String value = System.getProperty(key);
		logger.trace("Reading param {}={}", key, value);
		
		return value;
	}
	
	/**
	 * NOTE: This method is deprecated and only the AppConfig instance given to an app should be used
	 *
	 * Retrieves the instance of this singleton
	 *
	 * @return Instance of singleton
	 */
	@Deprecated
	public static synchronized SdkAppConfig getInstance()
	{
		// check to see if the instance is null
		if (null == instance)
		{
			instance = new SdkAppConfig();
		}
		
		return instance;
	}
}
