package com.threatconnect.sdk.app;

import java.util.logging.Level;

public class AppConfig
{
	public static final String TC_MAIN_APP_CLASS = "tc_main_app_class";
	public static final String TC_LOG_PATH = "tc_log_path";
	public static final String TC_TEMP_PATH = "tc_temp_path";
	public static final String TC_OUT_PATH = "tc_out_path";
	public static final String TC_API_PATH = "tc_api_path";
	public static final String TC_SPACE_ELEMENT_ID = "tc_space_element_id";
	public static final String TC_API_ACCESS_ID = "api_access_id";
	public static final String TC_API_SECRET = "api_secret_key";
	public static final String TC_API_TOKEN_KEY = "tc_api_token_key";
	public static final String TC_API_DEFAULT_ORG = "api_default_org";
	private static final String TC_PROXY_HOST = "tc_proxy_host";
	private static final String TC_PROXY_PORT = "tc_proxy_port";
	private static final String TC_PROXY_USERNAME = "tc_proxy_username";
	private static final String TC_PROXY_PASSWORD = "tc_proxy_password";
	public static final String TC_API_MAX_RESULT = "api_max_results";
	public static final String TC_OWNER = "owner";
	public static final String TC_LOG_LEVEL = "tc_log_level";
	
	public static final int DEFAULT_MAX_RESULTS = 350;
	public static final String DEFAULT_LOG_LEVEL = "INFO";
	
	public String getTcMainAppClass()
	{
		return getString(TC_MAIN_APP_CLASS);
	}
	
	public String getTcLogPath()
	{
		return getString(TC_LOG_PATH);
	}
	
	public String getTcTempPath()
	{
		return getString(TC_TEMP_PATH);
	}
	
	public String getTcOutPath()
	{
		return getString(TC_OUT_PATH);
	}
	
	public String getTcApiPath()
	{
		return getString(TC_API_PATH);
	}
	
	public Integer getTcSpaceElementId()
	{
		return getInteger(TC_SPACE_ELEMENT_ID);
	}
	
	public String getTcApiAccessID()
	{
		return getString(TC_API_ACCESS_ID);
	}
	
	public String getTcApiUserSecretKey()
	{
		return getString(TC_API_SECRET);
	}
	
	public String getTcApiTokenKey()
	{
		return getString(TC_API_TOKEN_KEY);
	}
	
	public String getApiDefaultOrg()
	{
		return getString(TC_API_DEFAULT_ORG);
	}
	
	public String getTcProxyHost()
	{
		return getString(TC_PROXY_HOST);
	}
	
	public Integer getTcProxyPort()
	{
		return getInteger(TC_PROXY_PORT);
	}
	
	public String getTcProxyUsername()
	{
		return getString(TC_PROXY_USERNAME);
	}
	
	public String getTcProxyPassword()
	{
		return getString(TC_PROXY_PASSWORD);
	}
	
	public Integer getApiMaxResults()
	{
		return getApiMaxResults(DEFAULT_MAX_RESULTS);
	}
	
	public Integer getApiMaxResults(int defaultMax)
	{
		Integer maxResults = getInteger(TC_API_MAX_RESULT);
		return null == maxResults ? defaultMax : maxResults;
	}
	
	public String getOwner()
	{
		return getString(TC_OWNER);
	}
	
	public Level getTcLogLevel()
	{
		return getTcLogLevel(DEFAULT_LOG_LEVEL);
	}
	
	public Level getTcLogLevel(String defaultLevel)
	{
		String level = getString(TC_LOG_LEVEL);
		return null == level ? Level.parse(defaultLevel) : Level.parse(level.toUpperCase());
	}
	
	/**
	 * Returns a system property as a string
	 * 
	 * @param key
	 * @return
	 */
	public String getString(final String key)
	{
		return System.getProperty(key);
	}
	
	/**
	 * Returns a system property as an integer. Returns null if the key does not exist or if the
	 * value is not an integer
	 * 
	 * @param key
	 * @return
	 */
	public Integer getInteger(final String key)
	{
		try
		{
			return Integer.parseInt(getString(key));
		}
		catch (NumberFormatException e)
		{
			return null;
		}
	}
	
	/**
	 * Returns a system property as an integer. Returns null if the key does not exist or if the
	 * value is not an double
	 * 
	 * @param key
	 * @return
	 */
	public Double getDouble(final String key)
	{
		try
		{
			return Double.parseDouble(getString(key));
		}
		catch (NumberFormatException e)
		{
			return null;
		}
	}
}
