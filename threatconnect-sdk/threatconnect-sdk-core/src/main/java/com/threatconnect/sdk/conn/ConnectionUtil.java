/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.conn;

import com.threatconnect.app.apps.AppConfig;
import com.threatconnect.sdk.config.Configuration;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.AbstractHttpMessage;
import org.apache.http.ssl.SSLContextBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

/**
 * @author dtineo
 */
public class ConnectionUtil
{
	private static final int FIVE_MINUTES = 1000 * 60 * 5;
	
	private static final Logger logger = LoggerFactory.getLogger(ConnectionUtil.class.getSimpleName());
	
	public static Properties loadProperties(String fileName) throws IOException
	{
		Properties props = new Properties();
		try
		{
			// check classloader, if running in container, this will fail on NPE
			InputStream in = ConnectionUtil.class.getResourceAsStream(fileName);
			props.load(in);
		}
		catch (NullPointerException npe)
		{
			props = new Properties();
			InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
			props.load(in);
		}
		
		return props;
	}
	
	public static String getHmacSha256Signature(String signature, String apiSecretKey)
	{
		try
		{
			String calculatedSignature;
			SecretKeySpec spec = new SecretKeySpec(apiSecretKey.getBytes(), "HmacSHA256");
			Mac mac = Mac.getInstance("HmacSHA256");
			mac.init(spec);
			byte[] rawSignature = mac.doFinal(signature.getBytes());
			calculatedSignature = Base64.encodeBase64String(rawSignature);
			
			return calculatedSignature;
		}
		catch (NoSuchAlgorithmException | InvalidKeyException | IllegalStateException ex)
		{
			logger.error("Error creating HMAC SHA256 signature", ex);
			return null;
		}
	}
	
	private static String getSignature(Long headerTimestamp, String httpMethod, String urlPath, String urlQuery)
	{
		String query = (urlQuery == null ? "" : "?" + urlQuery);
		return String.format("%s%s:%s:%d", urlPath, query, httpMethod, headerTimestamp);
	}
	
	static void applyHeaders(Configuration config, AbstractHttpMessage message, String httpMethod, String urlPath)
	{
		applyHeaders(config, message, httpMethod, urlPath, null);
	}
	
	static void applyHeaders(Configuration config, AbstractHttpMessage message, String httpMethod, String urlPath,
		String contentType)
	{
		applyHeaders(config, message, httpMethod, urlPath, contentType, config.getContentType());
	}
	
	static void applyHeaders(Configuration config, AbstractHttpMessage message, String httpMethod, String urlPath,
		String contentType, String acceptType)
	{
		if (config.getTcToken() != null)
		{
			message.addHeader("authorization", "TC-Token " + config.getTcToken());
		}
		else
		{
			Long ts = System.currentTimeMillis() / 1000L;
			String sig = getSignature(ts, httpMethod, urlPath, null);
			String hmacSig = getHmacSha256Signature(sig, config.getTcApiUserSecretKey());
			String auth = getAuthorizationText(config, hmacSig);
			
			message.addHeader("timestamp", "" + ts);
			message.addHeader("authorization", auth);
		}
		
		message.addHeader("Accept", acceptType);
		if (contentType != null)
		{
			message.addHeader("Content-Type", contentType);
		}
	}
	
	private static String getAuthorizationText(Configuration config, String hmacSig)
	{
		return String.format("TC %s:%s", config.getTcApiAccessID(), hmacSig);
	}
	
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
			logger.debug(String.format("creating http client with proxy settings http://%s:%s", proxyHost, proxyPort));
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
		
		//set the default timeouts
		RequestConfig.Builder requestConfig = RequestConfig.custom();
		requestConfig.setConnectTimeout(FIVE_MINUTES);
		requestConfig.setConnectionRequestTimeout(FIVE_MINUTES);
		requestConfig.setSocketTimeout(FIVE_MINUTES);
		
		builder.setDefaultRequestConfig(requestConfig.build());
		
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
	
	/**
	 * Creates the configuration object from an AppConfig
	 *
	 * @param appConfig
	 * @return
	 */
	public static Configuration createConfiguration(final AppConfig appConfig)
	{
		// create the configuration for the threatconnect server
		Configuration configuration = new Configuration(appConfig, appConfig.getTcApiPath(), appConfig.getTcApiAccessID(),
			appConfig.getTcApiUserSecretKey(), appConfig.getApiDefaultOrg(), appConfig.getTcToken(), appConfig.getTcTokenExpires());
		
		return configuration;
	}
}
