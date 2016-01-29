/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.conn;

import java.io.Closeable;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;

import com.threatconnect.sdk.config.Configuration;
import com.threatconnect.sdk.config.URLConfiguration;

/**
 *
 * @author dtineo
 */
public class Connection implements Closeable {

	private final Logger logger = Logger.getLogger(getClass().getSimpleName());

	private CloseableHttpClient apiClient;
	protected Configuration config;
	private AbstractRequestExecutor executor;

	private final URLConfiguration urlConfig;

	public Connection() throws IOException {
		String fileName = System.getProperties().getProperty("threatconnect.api.config");
		Properties props = ConnectionUtil.loadProperties(fileName);
		this.config = Configuration.build(props);

		this.urlConfig = URLConfiguration.build();
	}

	public Connection(Configuration config) throws IOException {
		this.config = config;
		this.urlConfig = URLConfiguration.build();
	}

	/*
	 * public void setInMemoryDispatcher(Object dispatcher) { if ( executor ==
	 * null ) { executor = new InMemoryRequestExecutor(this); }
	 * 
	 * ((InMemoryRequestExecutor)executor).setDispatcher((Dispatcher)
	 * dispatcher); }
	 * 
	 * public void setInMemoryProviderFactory(Object providerFactory) { if (
	 * executor == null ) { executor = new InMemoryRequestExecutor(this); }
	 * 
	 * ((InMemoryRequestExecutor)executor).setResteasyProviderFactory((
	 * ResteasyProviderFactory)providerFactory); }
	 * 
	 * public void setInMemoryRegistry(Object registry) {
	 * ((InMemoryRequestExecutor)executor).setRegistry((Registry)registry); }
	 */

	public AbstractRequestExecutor getExecutor() {
		if (executor == null) {
			if (config.getTcApiUrl().equalsIgnoreCase("embedded")) {
				executor = new InMemoryRequestExecutor(this);
			} else {
				executor = new HttpRequestExecutor(this);
			}
		}

		return executor;
	}

	/**
	 * @return the config
	 */
	public Configuration getConfig() {
		return config;
	}

	/**
	 * @param config
	 *            to set
	 */
	public void setConfig(Configuration config) {
		this.config = config;
	}

	private CloseableHttpClient createApiClient() {

		SSLContext sslcontext;
		CloseableHttpClient httpClient = null;

		try {
			sslcontext = SSLContexts.custom().loadTrustMaterial(null, new TrustSelfSignedStrategy()).build();

			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1" },
					null, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

			HttpClientBuilder builder = HttpClients.custom().setSSLSocketFactory(sslsf);
			// add code here to handle proxy authentication
			// tcproxyhost, tcproxyport, tcproxyusername, tcproxypassword,
			//
			String proxyHost = System.getProperty("tc_proxy_host");
			String proxyPort = System.getProperty("tc_proxy_port");
			String proxyUserName = System.getProperty("tc_proxy_username");
			String proxyPassword = System.getProperty("tc_proxyp_assword");
			if (proxyUserName != null) // authentication required
			{
				// we need to check the whether the following parameters exists
				if (proxyHost == null || proxyPort == null || proxyPassword == null)
					logger.log(Level.WARNING,
							"Error: proxyHost == null || proxyPort == null || proxyPassword == null");
				else // add authentication info to builder
				{
					Credentials credentials = new UsernamePasswordCredentials(proxyUserName, proxyPassword);
					AuthScope authScope = new AuthScope(proxyHost, Integer.parseInt(proxyPort));
					CredentialsProvider credsProvider = new BasicCredentialsProvider();
					credsProvider.setCredentials(authScope, credentials);

					builder.setProxy(new HttpHost(proxyHost, Integer.parseInt(proxyPort)))
							.setDefaultCredentialsProvider(credsProvider);
				}
			} else if (proxyHost != null && proxyPort != null) 
			{
				// no username,no authentication
				builder.setProxy(new HttpHost(proxyHost, Integer.parseInt(proxyPort)));
			} else if (getConfig().hasProxySettings())
			{
				 // old way if applicable
				builder.setProxy(new HttpHost(getConfig().getProxyHost(), getConfig().getProxyPort()));
			}
			httpClient = builder.build();
		} catch (NoSuchAlgorithmException | KeyStoreException | KeyManagementException ex) {
			logger.log(Level.SEVERE, "Error creating httpClient", ex);
		}

		return httpClient;
	}

	CloseableHttpClient getApiClient() {
		if (apiClient == null) {
			apiClient = createApiClient();
		}
		return apiClient;
	}

	/**
	 * @return the urlConfig
	 */
	public URLConfiguration getUrlConfig() {
		return urlConfig;
	}

	public void disconnect() {
		if (apiClient != null) {
			try {
				apiClient.close();
			} catch (IOException ex) {
				logger.log(Level.SEVERE, "Error disconnecting from httpClient", ex);
			} finally {
				apiClient = null;
			}
		}
	}

	/**
	 * Calls disconnect to close httpClient
	 *
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	@Override
	public void close() throws IOException {
		this.disconnect();
	}
}
