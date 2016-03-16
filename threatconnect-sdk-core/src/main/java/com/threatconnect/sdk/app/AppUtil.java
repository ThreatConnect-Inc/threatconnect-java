package com.threatconnect.sdk.app;

import org.apache.http.impl.client.CloseableHttpClient;

import com.threatconnect.sdk.conn.ConnectionUtil;

public class AppUtil
{
	/**
	 * Creates a CloseableHttpClient object with the proxy settings if they are configured
	 * 
	 * @param useProxyIfAvailable
	 * if the proxy settings are available, this connection will be initiated using the configured
	 * proxy
	 * @return a CloseableHttpClient
	 */
	public static CloseableHttpClient createClient(final boolean useProxyIfAvailable)
	{
		// :TODO: should trustSelfSignedCertificates be false by default? if so, how do we enable it
		// when needed?
		boolean trustSelfSignedCertificates = true;
		
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
