/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.conn;

import com.threatconnect.app.apps.AppConfig;
import com.threatconnect.sdk.app.AppUtil;
import com.threatconnect.sdk.app.SdkAppConfig;
import com.threatconnect.sdk.config.Configuration;
import com.threatconnect.sdk.config.URLConfiguration;
import org.apache.http.impl.client.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;
import java.util.Properties;

/**
 * @author dtineo
 */
public class Connection implements Closeable
{
	private final Logger logger = LoggerFactory.getLogger(getClass().getSimpleName());
	
	protected Configuration config;
	private AbstractRequestExecutor executor;
	
	private final URLConfiguration urlConfig;
	
	public Connection(final AppConfig appConfig) throws IOException
	{
		String fileName = System.getProperties().getProperty("threatconnect.api.config");
		Properties props = ConnectionUtil.loadProperties(fileName);
		this.config = Configuration.build(props, appConfig);
		
		this.urlConfig = URLConfiguration.build();
	}
	
	public Connection(Configuration config) throws IOException
	{
		this.config = config;
		this.urlConfig = URLConfiguration.build();
	}
	
	/*
	 * public void setInMemoryDispatcher(Object dispatcher) { if ( executor ==
	 * null ) { executor = new InMemoryRequestExecutor(this); }
	 * ((InMemoryRequestExecutor)executor).setDispatcher((Dispatcher)
	 * dispatcher); }
	 * public void setInMemoryProviderFactory(Object providerFactory) { if (
	 * executor == null ) { executor = new InMemoryRequestExecutor(this); }
	 * ((InMemoryRequestExecutor)executor).setResteasyProviderFactory((
	 * ResteasyProviderFactory)providerFactory); }
	 * public void setInMemoryRegistry(Object registry) {
	 * ((InMemoryRequestExecutor)executor).setRegistry((Registry)registry); }
	 */
	
	public AbstractRequestExecutor getExecutor()
	{
		if (executor == null)
		{
			if (config.getTcApiUrl().equalsIgnoreCase("embedded"))
			{
				executor = new InMemoryRequestExecutor(this);
			}
			else
			{
				executor = new HttpRequestExecutor(this);
			}
		}
		
		return executor;
	}
	
	/**
	 * @return the config
	 */
	public Configuration getConfig()
	{
		return config;
	}
	
	/**
	 * @param config to set
	 */
	public void setConfig(Configuration config)
	{
		this.config = config;
	}
	
	public CloseableHttpClient getApiClient()
	{
		// :TODO: should we add an SDK param to trust self signed certs?
		return AppUtil.createClient(config.getAppConfig().isProxyTC(), true, config.getAppConfig());
	}
	
	public CloseableHttpClient getExternalClient()
	{
		// :TODO: should we add an SDK param to trust self signed certs?
		return AppUtil.createClient(config.getAppConfig().isProxyExternal(), true, config.getAppConfig());
	}
	
	/**
	 * @return the urlConfig
	 */
	public URLConfiguration getUrlConfig()
	{
		return urlConfig;
	}
	
	@Override
	public void close() throws IOException
	{
	
	}
}
