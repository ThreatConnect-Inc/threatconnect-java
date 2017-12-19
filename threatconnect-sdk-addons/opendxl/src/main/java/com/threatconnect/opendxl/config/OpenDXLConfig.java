package com.threatconnect.opendxl.config;

/**
 * @author Greg Marut
 */
public class OpenDXLConfig
{
	private ClientConfig clientConfig;
	private byte[] brokerCertChain;
	private byte[] certFile;
	private byte[] privateKey;
	private byte[] clientConfigContents;
	
	public ClientConfig getClientConfig()
	{
		return clientConfig;
	}
	
	public void setClientConfig(final ClientConfig clientConfig)
	{
		this.clientConfig = clientConfig;
	}
	
	public byte[] getBrokerCertChain()
	{
		return brokerCertChain;
	}
	
	public void setBrokerCertChain(final byte[] brokerCertChain)
	{
		this.brokerCertChain = brokerCertChain;
	}
	
	public byte[] getCertFile()
	{
		return certFile;
	}
	
	public void setCertFile(final byte[] certFile)
	{
		this.certFile = certFile;
	}
	
	public byte[] getPrivateKey()
	{
		return privateKey;
	}
	
	public void setPrivateKey(final byte[] privateKey)
	{
		this.privateKey = privateKey;
	}
	
	public byte[] getClientConfigContents()
	{
		return clientConfigContents;
	}
	
	public void setClientConfigContents(final byte[] clientConfigContents)
	{
		this.clientConfigContents = clientConfigContents;
	}
}
