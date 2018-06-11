package com.threatconnect.opendxl;

import com.threatconnect.opendxl.client.SslUtil;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

/**
 * @author Greg Marut
 */
public class LoadSSLTest
{
	@Test
	public void loadSSLTest() throws Exception
	{
		//load the file to use for testing
		File caCertFile = new File("src/test/resources/ca-broker.crt");
		File certFile = new File("src/test/resources/client.crt");
		File keyFile = new File("src/test/resources/client.key");
		Assert.assertTrue(caCertFile.exists());
		Assert.assertTrue(certFile.exists());
		Assert.assertTrue(keyFile.exists());
		
		SslUtil.getSocketFactory(caCertFile, certFile, keyFile);
	}
	
	@Test
	public void loadSSL2Test() throws Exception
	{
		//load the file to use for testing
		File caCertFile = new File("src/test/resources/brokercerts.crt");
		File certFile = new File("src/test/resources/openDxlClientCert.crt");
		File keyFile = new File("src/test/resources/client_rsa.key");
		Assert.assertTrue(caCertFile.exists());
		Assert.assertTrue(certFile.exists());
		Assert.assertTrue(keyFile.exists());
		
		SslUtil.getSocketFactory(caCertFile, certFile, keyFile);
	}
}
