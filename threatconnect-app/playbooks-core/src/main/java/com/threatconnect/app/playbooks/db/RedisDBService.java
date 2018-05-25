package com.threatconnect.app.playbooks.db;

import com.threatconnect.app.playbooks.app.PlaybooksAppConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

public class RedisDBService implements DBService
{
	private static final Logger logger = LoggerFactory.getLogger(RedisDBService.class);
	
	private static final int DEFAULT_REDIS_PORT = 6379;
	
	private final Jedis redis;
	private final String contextKey;
	
	public RedisDBService(final PlaybooksAppConfig playbooksAppConfig)
	{
		//building the redis connection object
		String host = playbooksAppConfig.getDBPath();
		int port = playbooksAppConfig.getDBPort(DEFAULT_REDIS_PORT);
		logger.trace("Building RedisDBService on {}:{}", host, port);
		this.redis = new Jedis(host, port);
		
		this.contextKey = playbooksAppConfig.getDBContext();
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
