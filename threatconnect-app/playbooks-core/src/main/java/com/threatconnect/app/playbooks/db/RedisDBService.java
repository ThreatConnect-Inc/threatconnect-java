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
		this(playbooksAppConfig.getDBPath(), playbooksAppConfig.getDBPort(DEFAULT_REDIS_PORT), playbooksAppConfig.getDBContext());
	}
	
	public RedisDBService(final PlaybooksAppConfig playbooksAppConfig, final Jedis jedis)
	{
		this.redis = jedis;
		this.contextKey = playbooksAppConfig.getDBContext();
	}
	
	public RedisDBService(final String host, final int port, final String contextKey)
	{
		//building the redis connection object
		logger.trace("Building RedisDBService on {}:{}", host, port);
		this.redis = new Jedis(host, port);
		
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
