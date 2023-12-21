package com.threatconnect.app.apps.db;

import com.threatconnect.app.apps.AppConfig;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;

import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.ssl.SSLContexts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Protocol;

public class RedisDBService implements DBService
{
	private static final Logger logger = LoggerFactory.getLogger(RedisDBService.class);
	
	private static final int DEFAULT_REDIS_PORT = 6379;
	
	private final Jedis redis;
	public Jedis getRedis() {
		return redis;
	}
	private final String contextKey;
	
	public RedisDBService(final AppConfig appConfig)
	{
		this(appConfig.getKVStorePath(), appConfig.getKVStorePort(DEFAULT_REDIS_PORT),
			appConfig.getString(AppConfig.PARAM_PB_KVSTORE_CONTEXT),
			appConfig.getString(AppConfig.PARAM_KVSTORE_USERNAME),
			appConfig.getString(AppConfig.PARAM_KVSTORE_PASSWORD),
			appConfig.getBoolean(AppConfig.PARAM_KVSTORE_TLS_ENABLED),
			appConfig.getInteger(AppConfig.PARAM_KVSTORE_TLS_PORT),
			appConfig.getTcSvcBrokerJksFile(),
			appConfig.getTcSvcBrokerJksPassword());
	}
	
	public RedisDBService(final String host, final int port, final String contextKey, String username, String password,
			boolean tls, int tlsport, String keystorePath, String keystorePassword)
	{
		//building the redis connection object
		logger.trace("Building RedisDBService on {}:{}", host, tls?tlsport:port);
		logger.trace("Building RedisDBService: username: {}", username);
		logger.trace("Building RedisDBService: password: {}", password);
		logger.trace("Building RedisDBService: tls: {}", tls);
		logger.trace("Building RedisDBService: tlsport: {}", tlsport);
		logger.trace("Building RedisDBService: keystorePath: {}", keystorePath);
		logger.trace("Building RedisDBService: keystorePassword: {}", keystorePassword);
		SSLSocketFactory sslSocketFactory = null;
		try {
            SSLContext sslContext = SSLContexts.custom()
                    .loadTrustMaterial(TrustSelfSignedStrategy.INSTANCE)
                    .build();
			sslSocketFactory = sslContext.getSocketFactory();
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException e) {
			logger.error("ERROR: Could not build SSLSocketFactory", e);
		}
		this.redis = new Jedis(host,
				tls?tlsport:port,
				Protocol.DEFAULT_TIMEOUT,
				Protocol.DEFAULT_TIMEOUT,
				tls,
				sslSocketFactory,
				null,
				null);
		if (username != null && password != null)
		{
			this.redis.auth(username, password);
		}
		this.contextKey = contextKey;
		if(logger.isTraceEnabled()) {
			this.redis.set("test", "SUCCESS");
			logger.trace("*** Jedis Test: "+this.redis.get("test"));
			this.saveValue(this.contextKey, "test".getBytes());
			logger.trace("*** Jedis Test: "+this.getValue("test"));
		}		
	}
	
	@Override
	public void saveValue(String key, byte[] value)
	{
		logger.trace("savingValue: session={}, key={}, value={}\n", contextKey, key, new String(value));
		
		this.redis.hset(contextKey.getBytes(), key.getBytes(), value);
	}
	
	@Override
	public byte[] getValue(String key)
	{
		//retrieve the value from the database server
		logger.trace("getValue: session={}, key={}\n", contextKey, key);
		byte[] value = this.redis.hget(contextKey.getBytes(), key.getBytes());
		logger.trace("Value received: {}", (value == null ? "NULL" : new String(value)));
		return value;
	}
}
