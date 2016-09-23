package com.threatconnect.sdk.playbooks.app;

import com.threatconnect.sdk.app.AppConfig;

/**
 * @author Greg Marut
 */
public class PlaybooksAppConfig
{
	public static final String PARAM_DB_TYPE = "tc_playbook_db_type";
	public static final String PARAM_DB_CONTEXT = "tc_playbook_db_context";
	public static final String PARAM_DB_PATH = "tc_playbook_db_path";
	public static final String PARAM_DB_PORT = "tc_playbook_db_port";
	public static final String PARAM_OUT_VARS = "tc_playbook_out_variables";

	//holds the instance of the playbooks app config
	private static PlaybooksAppConfig instance;
	private static Object lock = new Object();

	private PlaybooksAppConfig()
	{

	}

	public String getDBType()
	{
		return getAppConfig().getString(PARAM_DB_TYPE);
	}

	public String getDBContext()
	{
		return getAppConfig().getString(PARAM_DB_CONTEXT);
	}

	public String getDBPath()
	{
		return getAppConfig().getString(PARAM_DB_PATH);
	}

	public Integer getDBPort()
	{
		return getAppConfig().getInteger(PARAM_DB_PORT);
	}

	public int getDBPort(final int defaultValue)
	{
		return getAppConfig().getInteger(PARAM_DB_PORT, defaultValue);
	}

	public String getOutputVars()
	{
		return getAppConfig().getString(PARAM_OUT_VARS);
	}

	public AppConfig getAppConfig()
	{
		return AppConfig.getInstance();
	}

	public static PlaybooksAppConfig getInstance()
	{
		//check to see if the playbooks app config is null
		if (null == instance)
		{
			//synchronize on the lock object
			synchronized (lock)
			{
				//now that we have a thread lock, check again to see if the config is still null
				if (null == instance)
				{
					instance = new PlaybooksAppConfig();
				}
			}
		}

		return instance;
	}
}
