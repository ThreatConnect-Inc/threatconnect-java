package com.threatconnect.app.apps;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public abstract class AppConfig
{
	public static final String TC_MAIN_APP_CLASS = "tc_main_app_class";
	public static final String TC_LOG_PATH = "tc_log_path";
	public static final String TC_TEMP_PATH = "tc_temp_path";
	public static final String TC_OUT_PATH = "tc_out_path";
	public static final String TC_IN_PATH = "tc_in_path";
	public static final String TC_USER_ID = "tc_user_id";
	public static final String TC_API_PATH = "tc_api_path";
	public static final String TC_SPACE_ELEMENT_ID = "tc_space_element_id";
	public static final String TC_API_ACCESS_ID = "tc_api_access_id";
	public static final String TC_API_SECRET = "tc_api_secret_key";
	public static final String TC_TOKEN = "tc_token";
	public static final String TC_TOKEN_EXPIRES = "tc_token_expires";
	public static final String TC_API_DEFAULT_ORG = "api_default_org";
	public static final String TC_PROXY_HOST = "tc_proxy_host";
	public static final String TC_PROXY_PORT = "tc_proxy_port";
	public static final String TC_PROXY_USERNAME = "tc_proxy_username";
	public static final String TC_PROXY_PASSWORD = "tc_proxy_password";
	public static final String TC_API_MAX_RESULT = "api_max_results";
	public static final String TC_LOG_LEVEL = "tc_log_level";
	public static final String TC_LOG_TO_API = "tc_log_to_api";
	public static final String TC_PROXY_TC = "tc_proxy_tc";
	public static final String TC_PROXY_EXTERNAL = "tc_proxy_external";
	public static final String TC_SECURE_PARAMS = "tc_secure_params";
	public static final String VERIFY_SSL_EXTERNAL = "verify_ssl_external";
	public static final String TC_AOT_ENABLED = "tc_aot_enabled";
	public static final String TC_ACTION_CHANNEL = "tc_action_channel";
	public static final String TC_TERMINATE_SECONDS = "tc_terminate_seconds";
	public static final String TC_EXIT_CHANNEL = "tc_exit_channel";
	public static final String TC_CAL_HOST = "tc_cal_host";
	public static final String TC_CAL_TIMESTAMP = "tc_cal_timestamp";
	public static final String TC_CAL_TOKEN = "tc_cal_token";
	public static final String TC_ACTION = "tc_action";
	public static final String TC_INSTANCE_ID = "tc_instance_id";
	public static final String TC_VERIFY = "tc_verify";
	
	public static final String PARAM_KVSTORE_TYPE = "tc_kvstore_type";
	public static final String PARAM_KVSTORE_HOST = "tc_kvstore_host";
	public static final String PARAM_KVSTORE_PORT = "tc_kvstore_port";
	public static final String PARAM_CACHE_KVSTORE_ID = "tc_cache_kvstore_id";
	public static final String PARAM_PB_KVSTORE_ID = "tc_playbook_kvstore_id";
	public static final String PARAM_PB_KVSTORE_CONTEXT = "tc_playbook_kvstore_context";
	public static final String PARAM_PB_OUT_VARS = "tc_playbook_out_variables";
	
	public static final String TC_SMTP_HOST = "tc_smtp_host";
	public static final String TC_SMTP_PORT = "tc_smtp_port";
	public static final String TC_SMTP_USERNAME = "tc_smtp_username";
	public static final String TC_SMTP_PASSWORD = "tc_smtp_password";
	public static final String TC_SYSTEM_EMAIL = "tc_sys_email";
	
	//service specific items
	public static final String TC_SVC_BROKER_CRT_FILE = "tc_svc_broker_cert_file";
	public static final String TC_SVC_BROKER_CA_CRT_FILE = "tc_svc_broker_cacert_file";
	public static final String TC_SVC_BROKER_JKS_FILE = "tc_svc_broker_jks_file";
	public static final String TC_SVC_BROKER_JKS_PASSWORD = "tc_svc_broker_jks_pwd";
	public static final String TC_SVC_BROKER_TOKEN = "tc_svc_broker_token";
	public static final String TC_SVC_BROKER_HOST = "tc_svc_broker_host";
	public static final String TC_SVC_BROKER_PORT = "tc_svc_broker_port";
	public static final String TC_SVC_SERVER_TOPIC = "tc_svc_server_topic";
	public static final String TC_SVC_CLIENT_TOPIC = "tc_svc_client_topic";
	public static final String TC_SVC_HEARTBEAT_TIMEOUT_SECONDS = "tc_svc_hb_timeout_seconds";
	
	@Deprecated
	public static final String APPLY_PROXY_EXTERNAL = "apply_proxy_external";
	
	@Deprecated
	public static final String APPLY_PROXY_EXT = "apply_proxy_ext";
	
	@Deprecated
	public static final String APPLY_PROXY_TC = "apply_proxy_tc";
	
	public static final int DEFAULT_MAX_RESULTS = 350;
	public static final String DEFAULT_LOG_LEVEL = "WARN";
	
	// holds the map of all of the configuration settings
	protected final Map<String, String> configurationMap;
	
	public AppConfig()
	{
		// holds the map of configuration settings
		configurationMap = new HashMap<String, String>();
	}
	
	/**
	 * Copies the configuration from another app config object and returns this object's instance to allow for chaining
	 *
	 * @param appConfig
	 * @return
	 */
	public AppConfig copyFrom(final AppConfig appConfig)
	{
		//copy all of the configurations from the other app config object
		configurationMap.putAll(appConfig.configurationMap);
		
		return this;
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
	
	public Long getTcUserId()
	{
		return getLong(TC_USER_ID);
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
	
	public String getTcSmtpHost()
	{
		return getString(TC_SMTP_HOST);
	}
	
	public Integer getTcSmtpPort()
	{
		return getInteger(TC_SMTP_PORT);
	}
	
	public String getTcSmtpUsername()
	{
		return getString(TC_SMTP_USERNAME);
	}
	
	public String getTcSmtpPassword()
	{
		return getString(TC_SMTP_PASSWORD);
	}
	
	public String getTcSystemEmail()
	{
		return getString(TC_SYSTEM_EMAIL);
	}
	
	public boolean isProxyTC()
	{
		//need to check all past proxy values too to support old apps.
		if (getString(TC_PROXY_TC) != null)
		{
			return getBoolean(TC_PROXY_TC);
		}
		if (getString(APPLY_PROXY_TC) != null)
		{
			return getBoolean(APPLY_PROXY_TC);
		}
		return false;
	}
	
	public boolean isProxyExternal()
	{
		//need to check all past proxy values too to support old apps.
		if (getString(TC_PROXY_EXTERNAL) != null)
		{
			return getBoolean(TC_PROXY_EXTERNAL);
		}
		if (getString(APPLY_PROXY_EXT) != null)
		{
			return getBoolean(APPLY_PROXY_EXT);
		}
		if (getString(APPLY_PROXY_EXTERNAL) != null)
		{
			return getBoolean(APPLY_PROXY_EXTERNAL);
		}
		return false;
	}
	
	public boolean isVerifySSL()
	{
		return getBoolean(VERIFY_SSL_EXTERNAL, true);
	}
	
	public String getTcLogLevel()
	{
		return getTcLogLevel(DEFAULT_LOG_LEVEL);
	}
	
	public String getTcLogLevel(String defaultLevel)
	{
		String level = getString(TC_LOG_LEVEL);
		return null == level ? defaultLevel : level.toUpperCase();
	}
	
	public boolean isTcLogToApi()
	{
		return getBoolean(TC_LOG_TO_API);
	}
	
	public boolean isTcSecureParamsEnabled()
	{
		return getBoolean(TC_SECURE_PARAMS);
	}
	
	public boolean isAOTEnabled()
	{
		return getBoolean(TC_AOT_ENABLED);
	}
	
	public String getTcActionChannel()
	{
		return getString(TC_ACTION_CHANNEL);
	}
	
	public Integer getTerminateSeconds()
	{
		return getInteger(TC_TERMINATE_SECONDS);
	}
	
	public String getTcExitChannel()
	{
		return getString(TC_EXIT_CHANNEL);
	}
	
	public String getTcSvcBrokerCrtFile()
	{
		return getString(TC_SVC_BROKER_CRT_FILE);
	}
	
	public String getTcSvcBrokerJksFile()
	{
		return getString(TC_SVC_BROKER_JKS_FILE);
	}
	
	public String getTcSvcBrokerJksPassword()
	{
		return getString(TC_SVC_BROKER_JKS_PASSWORD);
	}
	
	public String getTcSvcBrokerToken()
	{
		return getString(TC_SVC_BROKER_TOKEN);
	}
	
	public String getTcSvcBrokerHost()
	{
		return getString(TC_SVC_BROKER_HOST);
	}
	
	public Integer getTcSvcBrokerPort()
	{
		return getInteger(TC_SVC_BROKER_PORT);
	}
	
	public String getTcServiceServerTopic()
	{
		return getString(TC_SVC_SERVER_TOPIC);
	}
	
	public String getTcServiceClientTopic()
	{
		return getString(TC_SVC_CLIENT_TOPIC);
	}
	
	public Integer getTcServiceHeartbeatTimeoutSeconds()
	{
		return getInteger(TC_SVC_HEARTBEAT_TIMEOUT_SECONDS);
	}
	
	public String getDBType()
	{
		return getString(PARAM_KVSTORE_TYPE);
	}
	
	public String getKVStorePath()
	{
		return getString(PARAM_KVSTORE_HOST);
	}
	
	public Integer getKVStorePort()
	{
		return getInteger(PARAM_KVSTORE_PORT);
	}
	
	public int getKVStorePort(final int defaultValue)
	{
		return getInteger(PARAM_KVSTORE_PORT, defaultValue);
	}
	
	public String getDBContext()
	{
		return getString(PARAM_PB_KVSTORE_CONTEXT);
	}
	
	/**
	 * Returns a system property as a string
	 *
	 * @param key Name of system property
	 * @return a system property as a string
	 */
	public String getString(final String key)
	{
		// check to see if this key exists
		if (configurationMap.containsKey(key))
		{
			return configurationMap.get(key);
		}
		else
		{
			// load this setting and put it in the map
			String value = loadSetting(key);
			configurationMap.put(key, value);
			return value;
		}
	}
	
	/**
	 * Returns a value as a list by splitting the string using the delimiter
	 *
	 * @param key       System property name
	 * @param delimiter The delimiter to use
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
	 * Returns a system property as an integer. Returns null if the key does not exist or if the value is not an
	 * integer
	 *
	 * @param key System property name
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
	 * Returns a system property as an integer. Returns the defaultValue if the key does not exist or if the value is
	 * not an integer
	 *
	 * @param key          System property name
	 * @param defaultValue Value to return if key doesn't exist
	 * @return An integer value of System property key
	 */
	public int getInteger(final String key, final int defaultValue)
	{
		Integer value = getInteger(key);
		return value == null ? defaultValue : value;
	}
	
	/**
	 * Returns a system property as a long. Returns null if the key does not exist or if the value is not a long
	 *
	 * @param key System property name
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
	 * Returns a system property as a long. Returns the defaultValue if the key does not exist or if the value is not a
	 * long
	 *
	 * @param key          System property name
	 * @param defaultValue Value to return if key doesn't exist
	 * @return A long value of System property key
	 */
	public long getLong(final String key, final long defaultValue)
	{
		Long value = getLong(key);
		return value == null ? defaultValue : value;
	}
	
	/**
	 * Returns a system property as a short. Returns null if the key does not exist or if the value is not a short
	 *
	 * @param key System property name
	 * @return A short value of System property key
	 */
	public Short getShort(final String key)
	{
		try
		{
			return Short.parseShort(getString(key));
		}
		catch (NullPointerException | NumberFormatException e)
		{
			return null;
		}
	}
	
	/**
	 * Returns a system property as a short. Returns the defaultValue if the key does not exist or if the value is not a
	 * short
	 *
	 * @param key          System property name
	 * @param defaultValue Value to return if key doesn't exist
	 * @return A short value of System property key
	 */
	public short getShort(final String key, final short defaultValue)
	{
		Short value = getShort(key);
		return value == null ? defaultValue : value;
	}
	
	/**
	 * Returns a system property as a boolean. Returns false if the key does not exist or if the value is not a boolean
	 *
	 * @param key System property name
	 * @return A boolean value of System property key
	 */
	public boolean getBoolean(final String key)
	{
		return Boolean.parseBoolean(getString(key));
	}
	
	public boolean getBoolean(final String key, final boolean dfault)
	{
		String value = getString(key);
		if (value == null || value.trim().isEmpty())
		{
			return dfault;
		}
		
		return Boolean.parseBoolean(value);
	}
	
	/**
	 * Returns a system property as an integer. Returns null if the key does not exist or if the value is not an double
	 *
	 * @param key System property name
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
	
	public Map<String, String> getKeyValue(final String key)
	{
		final String value = getString(key);
		if (null != value && !value.isEmpty())
		{
			//holds the map to return
			Map<String, String> result = new HashMap<String, String>();
			
			try
			{
				JsonArray jsonArray = new JsonParser().parse(value).getAsJsonArray();
				
				//for each element in the json array
				for (JsonElement jsonElement : jsonArray)
				{
					JsonObject jsonObject = jsonElement.getAsJsonObject();
					final String k = jsonObject.get("key").getAsString();
					final String v = jsonObject.get("value").getAsString();
					
					result.put(k, v);
				}
				
				return result;
			}
			catch (JsonSyntaxException e)
			{
				return null;
			}
		}
		else
		{
			return null;
		}
	}
	
	/**
	 * Returns a system property as an double. Returns the defaultValue if the key does not exist or if the value is not
	 * a double
	 *
	 * @param key          System property name
	 * @param defaultValue Value to return if key doesn't exist
	 * @return A double value of System property key
	 */
	public double getDouble(final String key, final double defaultValue)
	{
		Double value = getDouble(key);
		return value == null ? defaultValue : value;
	}
	
	/**
	 * Returns a file from the specified key
	 *
	 * @param key Name of system property
	 * @return the contents of the file
	 */
	public byte[] getFile(final String key) throws IOException
	{
		//get the value for this key (which is a filename)
		String fileName = getString(key);
		if (null != fileName)
		{
			//open the file for reading from the in directory
			File file = new File(getTcInPath() + File.separator + fileName);
			try (InputStream inputStream = new FileInputStream(file))
			{
				return IOUtils.toByteArray(inputStream);
			}
		}
		else
		{
			throw new FileNotFoundException();
		}
	}
	
	/**
	 * Sets a string value in the configuration map. The object's toString() method is used to calculate the string
	 *
	 * @param key   the key of the configuration
	 * @param value this value will be set in the configuration and it may be null
	 */
	public void set(final String key, final Object value)
	{
		// make sure the value is not null
		if (null != value)
		{
			configurationMap.put(key, value.toString());
		}
		else
		{
			configurationMap.put(key, null);
		}
	}
	
	public void setAll(final Map<String, String> map)
	{
		configurationMap.putAll(map);
	}
	
	/**
	 * Loads one individual setting given the key
	 *
	 * @param key the setting to load
	 * @return
	 */
	protected abstract String loadSetting(final String key);
}
