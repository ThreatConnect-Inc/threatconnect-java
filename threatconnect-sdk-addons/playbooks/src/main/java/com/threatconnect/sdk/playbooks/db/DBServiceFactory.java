package com.threatconnect.sdk.playbooks.db;

import com.threatconnect.sdk.playbooks.app.PlaybooksAppConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Greg Marut
 */
public class DBServiceFactory
{
	public static final String DB_TYPE_DISTRIBUTED = "Redis";
	public static final String DB_TYPE_EMBEDDED = "RocksDB";

	//holds the map which will store customized db service objects
	private static final Map<String, DBService> customDBServiceMap = new HashMap<String, DBService>();

	public static DBService buildFromAppConfig()
	{
		//retrieve the reference to the playbooks config
		final PlaybooksAppConfig playbooksAppConfig = PlaybooksAppConfig.getInstance();
		final String dbType = playbooksAppConfig.getDBType();

		//check to see if the database is a distributed
		if (DB_TYPE_DISTRIBUTED.equals(dbType))
		{
			return new RedisDBService(playbooksAppConfig.getDBContext());
		}
		//check to see if the database is embedded
		else if (DB_TYPE_EMBEDDED.equals(dbType))
		{
			return new RocksDBService();
		}
		//check to see if there is a custom database that was registered
		else if (customDBServiceMap.containsKey(dbType))
		{
			return customDBServiceMap.get(dbType);
		}
		else
		{
			//invalid database configuration
			throw new RuntimeException("Invalid database type. " + PlaybooksAppConfig.PARAM_DB_TYPE
				+ " parameter must be set to a valid value.");
		}
	}

	public static void registerCustomDBService(final String name, final DBService dbService)
	{
		//make sure the name is not a reserved name
		if (DB_TYPE_DISTRIBUTED.equals(name) || DB_TYPE_EMBEDDED.equals(name))
		{
			throw new IllegalArgumentException(name + " is a reserved DBService and cannot be overwritten.");
		}

		customDBServiceMap.put(name, dbService);
	}
}
