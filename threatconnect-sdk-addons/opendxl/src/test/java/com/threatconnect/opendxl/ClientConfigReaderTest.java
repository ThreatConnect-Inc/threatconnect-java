package com.threatconnect.opendxl;

import com.threatconnect.opendxl.config.ClientConfig;
import com.threatconnect.opendxl.config.ClientConfigReader;
import com.threatconnect.opendxl.config.ClientConfigReaderException;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * @author Greg Marut
 */
public class ClientConfigReaderTest
{
	@Test
	public void readTest() throws IOException, ClientConfigReaderException
	{
		//load the file to use for testing
		File file = new File("src/test/resources/dxlclient.config");
		Assert.assertTrue(file.exists());
		
		//parse the file
		ClientConfig clientConfig = ClientConfigReader.read(file);
		
		//verify the data
		Assert.assertEquals("ca-broker.crt", clientConfig.getBrokerCertChain());
		Assert.assertEquals("client.crt", clientConfig.getCertFile());
		Assert.assertEquals("client.key", clientConfig.getPrivateKey());
		Assert.assertEquals("localhost", clientConfig.getHost());
		Assert.assertEquals(8883, clientConfig.getPort());
	}
}
