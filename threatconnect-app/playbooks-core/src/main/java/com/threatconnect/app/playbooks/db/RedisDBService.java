package com.threatconnect.app.playbooks.db;

import com.threatconnect.app.playbooks.app.PlaybooksAppConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisException;

public class RedisDBService implements DBService
{
    private static final Logger logger = LoggerFactory.getLogger(RedisDBService.class);

    private static final int DEFAULT_REDIS_PORT = 6379;

    private JedisPool redisPool;
    private final String contextKey;

    public RedisDBService(final PlaybooksAppConfig playbooksAppConfig)
    {
        //building the redis connection object
        String host = playbooksAppConfig.getDBPath();
        int port = playbooksAppConfig.getDBPort(DEFAULT_REDIS_PORT);
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(2);
        redisPool = new JedisPool(config, host, port);

        this.contextKey = playbooksAppConfig.getDBContext();
    }

    private Jedis redis()
    {
        return redisPool.getResource();
    }

    @Override
    public void saveValue(String key, byte[] value)
    {
        logger.debug("savingValue: session={}, key={}, value={}\n", contextKey, key, new String(value));

        try (Jedis redis = redis())
        {
            redis.hset(contextKey.getBytes(), key.getBytes(), value);
        } catch (JedisException e)
        {
            logger.error("Failed to set key " + contextKey, e);
        }
    }

    @Override
    public byte[] getValue(String key)
    {
        byte[] value = null;
        try (Jedis redis = redis())
        {
            value = redis.hget(contextKey.getBytes(), key.getBytes());
        } catch (JedisException e)
        {
            logger.error("Failed to get key " + contextKey, e);
        }

        logger.trace(String.format("getValue: session=%s, key=%s", contextKey, key));
        logger.trace("\t" + (value == null ? "NULL" : new String(value)));
        return value;
    }

}
