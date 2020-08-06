package com.threatconnect.app.playbooks.app;

import com.threatconnect.app.apps.AppConfig;

/**
 * @author Greg Marut
 *
 * Deprecated: this really is not needed anymore since these values are always being sent to all apps
 * New values are in {@link AppConfig} with the prefix "tc_cache" and "tc_kvstore"
 */
@Deprecated
public class PlaybooksAppConfig
{
	public static final String PARAM_DB_TYPE = "tc_playbook_db_type";
	public static final String PARAM_DB_CONTEXT = "tc_playbook_db_context";
	public static final String PARAM_DB_PATH = "tc_playbook_db_path";
	public static final String PARAM_DB_PORT = "tc_playbook_db_port";
	public static final String PARAM_OUT_VARS = "tc_playbook_out_variables";
	
	private final AppConfig appConfig;
	
	public PlaybooksAppConfig(final AppConfig appConfig)
	{
		this.appConfig = appConfig;
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
		return appConfig;
	}
}
