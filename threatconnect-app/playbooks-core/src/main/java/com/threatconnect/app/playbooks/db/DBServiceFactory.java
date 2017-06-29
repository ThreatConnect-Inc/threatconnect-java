package com.threatconnect.app.playbooks.db;

import com.threatconnect.app.apps.AppConfig;
import com.threatconnect.app.playbooks.app.PlaybooksAppConfig;
import com.threatconnect.app.playbooks.db.tcapi.TCApiDBService;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Greg Marut
 */
public class DBServiceFactory
{
	public static final String DB_TYPE_DISTRIBUTED = "Redis";
	public static final String DB_TYPE_TC_API = "TCKeyValueAPI";
	
	//holds the map which will store customized db service objects
	private static final Map<String, DBService> customDBServiceMap = new HashMap<String, DBService>();
	
	public static DBService buildFromAppConfig(final AppConfig appConfig)
	{
		//retrieve the reference to the playbooks config
		final PlaybooksAppConfig playbooksAppConfig = new PlaybooksAppConfig(appConfig);
		final String dbType = playbooksAppConfig.getDBType();
		
		//check to see if the database is a distributed
		if (DB_TYPE_DISTRIBUTED.equals(dbType))
		{
			return new RedisDBService(playbooksAppConfig);
		}
		else if (DB_TYPE_TC_API.equals(dbType))
		{
			return new TCApiDBService(playbooksAppConfig);
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
		if (DB_TYPE_DISTRIBUTED.equals(name) || DB_TYPE_TC_API.equals(name))
		{
			throw new IllegalArgumentException(name + " is a reserved DBService and cannot be overwritten.");
		}
		
		customDBServiceMap.put(name, dbService);
	}
}
