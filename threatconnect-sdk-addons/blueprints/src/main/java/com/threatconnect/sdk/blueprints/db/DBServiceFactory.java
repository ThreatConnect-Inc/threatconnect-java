package com.threatconnect.sdk.blueprints.db;

import com.threatconnect.sdk.blueprints.app.BlueprintsAppConfig;

/**
 * @author Greg Marut
 */
public class DBServiceFactory
{
	public static final String DB_TYPE_DISTRIBUTED = "Redis";
	public static final String DB_TYPE_EMBEDDED = "RocksDB";

	private DBServiceFactory()
	{

	}

	public static final DBService buildFromAppConfig()
	{
		//retrieve the reference to the blueprints config
		final BlueprintsAppConfig blueprintsAppConfig = BlueprintsAppConfig.getInstance();

		//check to see if the database is a redis database
		if (DB_TYPE_DISTRIBUTED.equals(blueprintsAppConfig.getDBType()))
		{
			return new RedisDBService(blueprintsAppConfig.getDBContext());
		}
		else if (DB_TYPE_EMBEDDED.equals(blueprintsAppConfig.getDBType()))
		{
			return new RocksDBService();
		}
		else
		{
			//invalid database configuration
			throw new RuntimeException("Invalid database type. " + BlueprintsAppConfig.PARAM_DB_TYPE
				+ " parameter must be set to a valid value.");
		}
	}
}
