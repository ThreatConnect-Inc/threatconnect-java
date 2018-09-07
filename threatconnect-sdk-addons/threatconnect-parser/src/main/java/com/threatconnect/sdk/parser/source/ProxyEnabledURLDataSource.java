package com.threatconnect.sdk.parser.source;

import com.threatconnect.app.apps.AppConfig;
import com.threatconnect.sdk.app.AppUtil;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

public class ProxyEnabledURLDataSource extends UrlDataSource
{
	private final AppConfig appConfig;
	private final boolean useProxyIfAvailable;
	
	public ProxyEnabledURLDataSource(final String url, final AppConfig appConfig) throws MalformedURLException
	{
		this(url, appConfig, true);
	}
	
	public ProxyEnabledURLDataSource(final String url, final AppConfig appConfig, final boolean useProxyIfAvailable) throws MalformedURLException
	{
		super(url);
		this.appConfig = appConfig;
		this.useProxyIfAvailable = useProxyIfAvailable;
	}
	
	@Override
	public InputStream read() throws IOException
	{
		// create a new get request
		HttpGet get = new HttpGet(getUrl().toString());
		
		// execute the request
		return buildHttpClient().execute(get).getEntity().getContent();
	}
	
	protected CloseableHttpClient buildHttpClient()
	{
		return AppUtil.createClientBuilder(useProxyIfAvailable, true, appConfig).build();
	}
}
