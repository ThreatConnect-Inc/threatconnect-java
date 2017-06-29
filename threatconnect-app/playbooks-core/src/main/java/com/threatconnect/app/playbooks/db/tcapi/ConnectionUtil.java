/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.app.playbooks.db.tcapi;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

/**
 * @author dtineo
 */
public class ConnectionUtil
{
	private static final Logger logger = LoggerFactory.getLogger(ConnectionUtil.class.getSimpleName());
	
	/**
	 * Adds proxy information to an http client builder
	 * 
	 * @param builder
	 * the HttpClientBuilder builder to add the proxy information to
	 * @param proxyHost
	 * the host of the proxy server
	 * @param proxyPort
	 * the port of the proxy server
	 * @param proxyUserName
	 * the username to authenticate with the proxy server (optional)
	 * @param proxyPassword
	 * the password to authenticate with the proxy server (optional)
	 */
	public static void addProxy(final HttpClientBuilder builder, final String proxyHost,
		final Integer proxyPort, final String proxyUserName, final String proxyPassword)
	{
		// check to see if the the host or port are null
		if (proxyHost == null || proxyPort == null)
		{
			logger.warn("proxyHost and proxyPort are required to connect to a proxy");
		}
		else
		{
			// add the proxy information to the builder
			builder.setProxy(new HttpHost(proxyHost, proxyPort));
			
			// authentication required
			if (proxyUserName != null && proxyPassword != null)
			{
				// add the credentials to the proxy information
				Credentials credentials = new UsernamePasswordCredentials(proxyUserName, proxyPassword);
				AuthScope authScope = new AuthScope(proxyHost, proxyPort);
				CredentialsProvider credsProvider = new BasicCredentialsProvider();
				credsProvider.setCredentials(authScope, credentials);
				builder.setDefaultCredentialsProvider(credsProvider);
			}
		}
	}
	
	/**
	 * Adds the ability to trust self signed certificates for this HttpClientBuilder
	 * 
	 * @param httpClientBuilder
	 * the HttpClientBuilder to apply these settings to
	 */
	public static void trustSelfSignedCerts(final HttpClientBuilder httpClientBuilder)
	{
		logger.debug("Trusting self-signed certs.");
		try
		{
			SSLContextBuilder builder = new SSLContextBuilder();
			builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
				builder.build(), new HostnameVerifier()
				{
					@Override
					public boolean verify(String hostname, SSLSession session)
					{
						// allow all
						return true;
					}
				});
				
			httpClientBuilder.setSSLSocketFactory(sslsf);
		}
		catch (NoSuchAlgorithmException | KeyStoreException | KeyManagementException ex)
		{
			logger.error("Error adding SSLSocketFactory to HttpClientBuilder", ex);
		}
	}
	
	/**
	 * Creates a new CloseableHttpClient
	 * 
	 * @return the new http client
	 */
	public static CloseableHttpClient createClient()
	{
		return createClient(null, null);
	}
	
	/**
	 * Creates a new CloseableHttpClient
	 * 
	 * @param trustSelfSignedCertificates
	 * whether or not self signed certificates should be trusted
	 * @return the new http client
	 */
	public static CloseableHttpClient createClient(boolean trustSelfSignedCertificates)
	{
		return createClient(null, null, null, null, trustSelfSignedCertificates);
	}
	
	/**
	 * Creates a new CloseableHttpClient
	 * 
	 * @param proxyHost
	 * the host of the proxy server (optional)
	 * @param proxyPort
	 * the port of the proxy server (optional)
	 * @return the new http client
	 */
	public static CloseableHttpClient createClient(final String proxyHost, Integer proxyPort)
	{
		return createClient(proxyHost, proxyPort, null, null);
	}
	
	/**
	 * Creates a new CloseableHttpClient
	 * 
	 * @param proxyHost
	 * the host of the proxy server (optional)
	 * @param proxyPort
	 * the port of the proxy server (optional)
	 * @param proxyUserName
	 * the username to authenticate with the proxy server (optional)
	 * @param proxyPassword
	 * the password to authenticate with the proxy server (optional)
	 * @return the new http client
	 */
	public static CloseableHttpClient createClient(final String proxyHost, Integer proxyPort, String proxyUserName,
		String proxyPassword)
	{
		return createClient(proxyHost, proxyPort, proxyUserName, proxyPassword, false);
	}
	
	/**
	 * Creates a builder for an HttpClient
	 * 
	 * @param proxyHost
	 * the host of the proxy server (optional)
	 * @param proxyPort
	 * the port of the proxy server (optional)
	 * @param proxyUserName
	 * the username to authenticate with the proxy server (optional)
	 * @param proxyPassword
	 * the password to authenticate with the proxy server (optional)
	 * @param trustSelfSignedCertificates
	 * whether or not self signed certificates should be trusted
	 * @return the new http client
	 */
	public static HttpClientBuilder createClientBuilder(final String proxyHost, Integer proxyPort, String proxyUserName,
		String proxyPassword, boolean trustSelfSignedCertificates)
	{
		// create a new http client builder
		HttpClientBuilder builder = HttpClients.custom();
		
		// allow self signed certificates
		if (trustSelfSignedCertificates)
		{
			trustSelfSignedCerts(builder);
		}
		
		// check to see if there are proxy settings
		if (null != proxyHost && null != proxyPort)
		{
			// apply the proxy settings
			addProxy(builder, proxyHost, proxyPort, proxyUserName, proxyPassword);
		}
		
		return builder;
	}
	
	/**
	 * Creates a new CloseableHttpClient
	 * 
	 * @param proxyHost
	 * the host of the proxy server (optional)
	 * @param proxyPort
	 * the port of the proxy server (optional)
	 * @param proxyUserName
	 * the username to authenticate with the proxy server (optional)
	 * @param proxyPassword
	 * the password to authenticate with the proxy server (optional)
	 * @param trustSelfSignedCertificates
	 * whether or not self signed certificates should be trusted
	 * @return the new http client
	 */
	public static CloseableHttpClient createClient(final String proxyHost, Integer proxyPort, String proxyUserName,
		String proxyPassword, boolean trustSelfSignedCertificates)
	{
		return createClientBuilder(proxyHost, proxyPort, proxyUserName, proxyPassword, trustSelfSignedCertificates)
			.build();
	}
}
