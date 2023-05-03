package com.threatconnect.app.apps.db;

import com.threatconnect.app.apps.AppConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

public class RedisDBService implements DBService
{
	private static final Logger logger = LoggerFactory.getLogger(RedisDBService.class);
	
	private static final int DEFAULT_REDIS_PORT = 6379;
	
	private final Jedis redis;
	private final String contextKey;
	
	public RedisDBService(final AppConfig appConfig)
	{
		this(appConfig.getKVStorePath(), appConfig.getKVStorePort(DEFAULT_REDIS_PORT),
			appConfig.getString(AppConfig.PARAM_PB_KVSTORE_CONTEXT),
			appConfig.getString(AppConfig.PARAM_KVSTORE_USERNAME),
			appConfig.getString(AppConfig.PARAM_KVSTORE_PASSWORD));
	}
	
	public RedisDBService(final AppConfig appConfig, final Jedis jedis)
	{
		this.redis = jedis;
		if (appConfig.getString(AppConfig.PARAM_KVSTORE_USERNAME) != null
				&& appConfig.getString(AppConfig.PARAM_KVSTORE_PASSWORD) != null)
		{
			this.redis.auth(appConfig.getString(AppConfig.PARAM_KVSTORE_USERNAME), appConfig.getString(AppConfig.PARAM_KVSTORE_PASSWORD));
		}
		this.contextKey = appConfig.getString(AppConfig.PARAM_PB_KVSTORE_CONTEXT);
	}
	
	public RedisDBService(final String host, final int port, final String contextKey, final String username, final String password)
	{
		//building the redis connection object
		logger.trace("Building RedisDBService on {}:{}", host, port);
		this.redis = new Jedis(host, port);
		if (username != null && password != null)
		{
			this.redis.auth(username, password);
		}
		
		this.contextKey = contextKey;
	}
	
	@Override
	public void saveValue(String key, byte[] value)
	{
		logger.trace("savingValue: session={}, key={}, value={}\n", contextKey, key, new String(value));
		
		redis.hset(contextKey.getBytes(), key.getBytes(), value);
	}
	
	@Override
	public byte[] getValue(String key)
	{
		//retrieve the value from the database server
		logger.trace("getValue: session={}, key={}\n", contextKey, key);
		byte[] value = redis.hget(contextKey.getBytes(), key.getBytes());
		logger.trace("Value received: {}", (value == null ? "NULL" : new String(value)));
		return value;
	}
}
