package com.threatconnect.opendxl.config;

/**
 * @author Greg Marut
 */
public class ClientConfig
{
	private String host;
	private int port;
	private String brokerCertChain;
	private String certFile;
	private String privateKey;
	
	public String getHost()
	{
		return host;
	}
	
	public void setHost(final String host)
	{
		this.host = host;
	}
	
	public int getPort()
	{
		return port;
	}
	
	public void setPort(final int port)
	{
		this.port = port;
	}
	
	public String getBrokerCertChain()
	{
		return brokerCertChain;
	}
	
	public void setBrokerCertChain(final String brokerCertChain)
	{
		this.brokerCertChain = brokerCertChain;
	}
	
	public String getCertFile()
	{
		return certFile;
	}
	
	public void setCertFile(final String certFile)
	{
		this.certFile = certFile;
	}
	
	public String getPrivateKey()
	{
		return privateKey;
	}
	
	public void setPrivateKey(final String privateKey)
	{
		this.privateKey = privateKey;
	}
}
