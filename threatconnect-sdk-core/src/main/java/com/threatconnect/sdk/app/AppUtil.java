package com.threatconnect.sdk.app;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import com.threatconnect.sdk.conn.ConnectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppUtil
{
	private static final Logger logger = LoggerFactory.getLogger(ConnectionUtil.class.getSimpleName());

	/**
	 * Creates a HttpClientBuilder object with the proxy settings if they are configured
	 * 
	 * @param useProxyIfAvailable
	 * if the proxy settings are available, this connection will be initiated using the configured
	 * proxy
	 * @param trustSelfSignedCertificates
	 * whether or not self signed certificates should be trusted
	 * @return a CloseableHttpClient
	 */
	public static HttpClientBuilder createClientBuilder(final boolean useProxyIfAvailable,
		final boolean trustSelfSignedCertificates)
	{
		if (useProxyIfAvailable)
		{
			String proxyHost = AppConfig.getInstance().getTcProxyHost();
			Integer proxyPort = AppConfig.getInstance().getTcProxyPort();
			String proxyUserName = AppConfig.getInstance().getTcProxyUsername();
			String proxyPassword = AppConfig.getInstance().getTcProxyPassword();

			logger.debug(String.format("creating http client with proxy setings http://%s:%s %s:%s",
					proxyHost, proxyPort, proxyUserName, proxyPassword));

			return ConnectionUtil.createClientBuilder(proxyHost, proxyPort, proxyUserName, proxyPassword,
				trustSelfSignedCertificates);
		}
		else
		{
			logger.debug("Creating http client without proxy settings");
			return ConnectionUtil.createClientBuilder(null, null, null, null, trustSelfSignedCertificates);
		}
	}
	
	/**
	 * Creates a CloseableHttpClient object with the proxy settings if they are configured
	 * 
	 * @param useProxyIfAvailable
	 * if the proxy settings are available, this connection will be initiated using the configured
	 * proxy
	 * @param trustSelfSignedCertificates
	 * whether or not self signed certificates should be trusted
	 * @return a CloseableHttpClient
	 */
	public static CloseableHttpClient createClient(final boolean useProxyIfAvailable,
		final boolean trustSelfSignedCertificates)
	{
		if (useProxyIfAvailable)
		{
			String proxyHost = AppConfig.getInstance().getTcProxyHost();
			Integer proxyPort = AppConfig.getInstance().getTcProxyPort();
			String proxyUserName = AppConfig.getInstance().getTcProxyUsername();
			String proxyPassword = AppConfig.getInstance().getTcProxyPassword();
			
			return ConnectionUtil.createClient(proxyHost, proxyPort, proxyUserName, proxyPassword,
				trustSelfSignedCertificates);
		}
		else
		{
			
			return ConnectionUtil.createClient(trustSelfSignedCertificates);
		}
	}
}
