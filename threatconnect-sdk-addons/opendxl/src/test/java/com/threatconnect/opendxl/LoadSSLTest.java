package com.threatconnect.opendxl;

import com.threatconnect.opendxl.client.SslUtil;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;

/**
 * @author Greg Marut
 */
public class LoadSSLTest
{
	@Test
	public void loadSSLTest()
		throws CertificateException, UnrecoverableKeyException, NoSuchAlgorithmException, IOException,
		KeyManagementException, KeyStoreException, InvalidKeySpecException
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
}
