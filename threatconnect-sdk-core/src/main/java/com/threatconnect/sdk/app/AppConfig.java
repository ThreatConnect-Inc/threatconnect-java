package com.threatconnect.sdk.app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.logging.log4j.Level;

public final class AppConfig
{
	public static final String TC_MAIN_APP_CLASS = "tc_main_app_class";
	public static final String TC_LOG_PATH = "tc_log_path";
	public static final String TC_TEMP_PATH = "tc_temp_path";
	public static final String TC_OUT_PATH = "tc_out_path";
	public static final String TC_IN_PATH = "tc_in_path";
	public static final String TC_API_PATH = "tc_api_path";
	public static final String TC_SPACE_ELEMENT_ID = "tc_space_element_id";
	public static final String TC_API_ACCESS_ID = "api_access_id";
	public static final String TC_API_SECRET = "api_secret_key";
	public static final String TC_API_TOKEN_KEY = "tc_api_token_key";
	public static final String TC_TOKEN = "tc_token";
        public static final String TC_TOKEN_EXPIRES = "tc_token_expires";
	public static final String TC_API_DEFAULT_ORG = "api_default_org";
	public static final String TC_PROXY_HOST = "tc_proxy_host";
	public static final String TC_PROXY_PORT = "tc_proxy_port";
	public static final String TC_PROXY_USERNAME = "tc_proxy_username";
	public static final String TC_PROXY_PASSWORD = "tc_proxy_password";
	public static final String TC_API_MAX_RESULT = "api_max_results";
	public static final String TC_APPLY_PROXY = "apply_proxy_tc";
	public static final String TC_LOG_LEVEL = "tc_log_level";
	public static final String TC_LOG_TO_API = "tc_log_to_api";
	
	public static final int DEFAULT_MAX_RESULTS = 350;
	public static final String DEFAULT_LOG_LEVEL = "INFO";
	
	// holds the instance of this singleton
	private static AppConfig instance;
	
	// holds the map of all of the configuration settings
	private final Map<String, String> configuration;
	
	private AppConfig()
	{
		// holds the map of configuration settings
		configuration = new HashMap<String, String>();
	}
	
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
	
	public String getTcInPath()
	{
		return getString(TC_IN_PATH);
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
	
	public String getTcToken()
	{
		return getString(TC_TOKEN);
	}

	public String getTcTokenExpires()
	{
		return getString(TC_TOKEN_EXPIRES);
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
	
	public int getApiMaxResults(int defaultMax)
	{
		return getInteger(TC_API_MAX_RESULT, defaultMax);
	}
	
	public boolean isTcApplyProxy()
	{
		return getBoolean(TC_APPLY_PROXY);
	}
	
	public Level getTcLogLevel()
	{
		return getTcLogLevel(DEFAULT_LOG_LEVEL);
	}
	
	public Level getTcLogLevel(String defaultLevel)
	{
		String level = getString(TC_LOG_LEVEL);
		return null == level ? Level.toLevel(defaultLevel) : Level.toLevel(level.toUpperCase());
	}
	
	public boolean isTcLogToApi()
	{
		return getBoolean(TC_LOG_TO_API);
	}
	
	/**
	 * Returns a system property as a string
	 * 
	 * @param key
	 * Name of system property
	 * @return a system property as a string
	 */
	public String getString(final String key)
	{
		// check to see if this key exists
		if (configuration.containsKey(key))
		{
			return configuration.get(key);
		}
		else
		{
			// load this setting and put it in the map
			String value = loadSetting(key);
			configuration.put(key, value);
			return value;
		}
	}
	
	/**
	 * Returns a value as a list by splitting the string using the delimiter
	 * 
	 * @param key
	 * System property name
	 * @param delimiter
	 * The delimiter to use
	 * @return A list of strings
	 */
	public List<String> getStringList(final String key, final String delimiter)
	{
		String value = getString(key);
		
		// make sure that the value is not null
		if (null != value && !value.isEmpty())
		{
			String[] array = value.split(Pattern.quote(delimiter));
			return Arrays.asList(array);
		}
		else
		{
			return new ArrayList<String>();
		}
	}
	
	/**
	 * Returns a system property as an integer. Returns null if the key does not exist or if the
	 * value is not an integer
	 * 
	 * @param key
	 * System property name
	 * @return An integer value of System property key
	 */
	public Integer getInteger(final String key)
	{
		try
		{
			return Integer.parseInt(getString(key));
		}
		catch (NullPointerException | NumberFormatException e)
		{
			return null;
		}
	}
	
	/**
	 * Returns a system property as an integer. Returns the defaultValue if the key does not exist
	 * or if the value is not an integer
	 * 
	 * @param key
	 * System property name
	 * @param defaultValue
	 * Value to return if key doesn't exist
	 * @return An integer value of System property key
	 */
	public int getInteger(final String key, final int defaultValue)
	{
		Integer value = getInteger(key);
		return value == null ? defaultValue : value;
	}
	
	/**
	 * Returns a system property as a long. Returns null if the key does not exist or if the
	 * value is not a long
	 * 
	 * @param key
	 * System property name
	 * @return A long value of System property key
	 */
	public Long getLong(final String key)
	{
		try
		{
			return Long.parseLong(getString(key));
		}
		catch (NullPointerException | NumberFormatException e)
		{
			return null;
		}
	}
	
	/**
	 * Returns a system property as a long. Returns the defaultValue if the key does not exist
	 * or if the value is not a long
	 * 
	 * @param key
	 * System property name
	 * @param defaultValue
	 * Value to return if key doesn't exist
	 * @return A long value of System property key
	 */
	public long getLong(final String key, final long defaultValue)
	{
		Long value = getLong(key);
		return value == null ? defaultValue : value;
	}
	
	/**
	 * Returns a system property as a boolean. Returns false if the key does not exist or if the
	 * value is not a boolean
	 * 
	 * @param key
	 * System property name
	 * @return A boolean value of System property key
	 */
	public boolean getBoolean(final String key)
	{
		return Boolean.parseBoolean(getString(key));
	}
	
	/**
	 * Returns a system property as an integer. Returns null if the key does not exist or if the
	 * value is not an double
	 * 
	 * @param key
	 * System property name
	 * @return An integer value of System property key
	 */
	public Double getDouble(final String key)
	{
		try
		{
			return Double.parseDouble(getString(key));
		}
		catch (NullPointerException | NumberFormatException e)
		{
			return null;
		}
	}
	
	/**
	 * Returns a system property as an double. Returns the defaultValue if the key does not exist
	 * or if the value is not a double
	 * 
	 * @param key
	 * System property name
	 * @param defaultValue
	 * Value to return if key doesn't exist
	 * @return A double value of System property key
	 */
	public double getDouble(final String key, final double defaultValue)
	{
		Double value = getDouble(key);
		return value == null ? defaultValue : value;
	}
	
	/**
	 * Sets a string value in the configuration map. The object's toString() method is used to
	 * calculate the string
	 * 
	 * @param key
	 * the key of the configuration
	 * @param value
	 * this value will be set in the configuration and it may be null
	 */
	public void set(final String key, final Object value)
	{
		// make sure the value is not null
		if (null != value)
		{
			configuration.put(key, value.toString());
		}
		else
		{
			configuration.put(key, null);
		}
	}
	
	/**
	 * Loads one individual setting given the key
	 * 
	 * @param key
	 * the setting to load
	 * @return
	 */
	protected String loadSetting(final String key)
	{
		return System.getProperty(key);
	}
	
	/**
	 * Retrieves the instance of this singleton
	 * 
	 * @return Instance of singleton
	 */
	public static synchronized AppConfig getInstance()
	{
		// check to see if the instance is null
		if (null == instance)
		{
			instance = new AppConfig();
		}
		
		return instance;
	}
}
