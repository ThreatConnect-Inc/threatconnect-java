package com.threatconnect.sdk.blueprints.db;

import com.threatconnect.sdk.blueprints.app.BlueprintsAppConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

public class RedisDBService implements DBService
{
	private static final Logger logger = LoggerFactory.getLogger(RedisDBService.class);

	private static final int DEFAULT_REDIS_PORT = 6379;

	private final Jedis redis;
	private final String contextKey;

	public RedisDBService(final String contextKey)
	{
		//building the redis connection object
		String host = BlueprintsAppConfig.getBlueprintsAppConfig().getDBPath();
		int port = BlueprintsAppConfig.getBlueprintsAppConfig().getDBPort(DEFAULT_REDIS_PORT);
		this.redis = new Jedis(host, port);

		this.contextKey = contextKey;
	}

	@Override
	public boolean saveValue(String key, byte[] value)
	{
		logger.debug("savingValue: session={}, key={}, value={}\n", contextKey, key, new String(value));

		redis.hset(contextKey.getBytes(), key.getBytes(), value);
		return true;
	}

	@Override
	public byte[] getValue(String key)
	{
		//retrieve the value from the database server
		byte[] value = redis.hget(contextKey.getBytes(), key.getBytes());
		logger.debug("getValue: session={}, key={}\n", contextKey, key);
		logger.debug("\t" + (value == null ? "NULL" : new String(value)));
		return value;
	}
}
