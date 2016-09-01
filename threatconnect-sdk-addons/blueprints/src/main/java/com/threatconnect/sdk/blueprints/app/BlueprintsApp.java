package com.threatconnect.sdk.blueprints.app;

import com.threatconnect.sdk.app.App;
import com.threatconnect.sdk.app.AppConfig;
import com.threatconnect.sdk.app.ExitStatus;
import com.threatconnect.sdk.blueprints.db.DBService;
import com.threatconnect.sdk.blueprints.db.RedisDBService;
import com.threatconnect.sdk.blueprints.db.RocksDBService;

/**
 * @author Greg Marut
 */
public abstract class BlueprintsApp extends App
{
	public static final String DB_TYPE_DISTRIBUTED = "Redis";
	public static final String DB_TYPE_EMBEDDED = "RocksDB";

	//holds the database service object
	private final DBService dbService;

	public BlueprintsApp()
	{
		final BlueprintsAppConfig blueprintsAppConfig = BlueprintsAppConfig.getBlueprintsAppConfig();

		//check to see if the database is a redis database
		if (DB_TYPE_DISTRIBUTED.equals(blueprintsAppConfig.getDBType()))
		{
			this.dbService = new RedisDBService(blueprintsAppConfig.getDBContext());
		}
		else if (DB_TYPE_EMBEDDED.equals(blueprintsAppConfig.getDBType()))
		{
			this.dbService = new RocksDBService();
		}
		else
		{
			//invalid database configuration
			throw new RuntimeException("Invalid database type. " + BlueprintsAppConfig.PARAM_DB_TYPE
				+ " parameter must be set to a valid value.");
		}
	}

	@Override
	public ExitStatus execute(AppConfig appConfig) throws Exception
	{
		return null;
	}

	protected DBService getDbService()
	{
		return dbService;
	}
}
