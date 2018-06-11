package com.threatconnect.opendxl;

import com.threatconnect.opendxl.config.ClientConfig;
import com.threatconnect.opendxl.config.OpenDXLConfig;
import com.threatconnect.opendxl.config.OpenDXLConfigReader;
import com.threatconnect.opendxl.config.OpenDXLReaderException;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * @author Greg Marut
 */
public class OpenDXLReaderTest
{
	@Test
	public void readTest() throws IOException, OpenDXLReaderException
	{
		//load the file to use for testing
		File file = new File("src/test/resources/opendxlclientconfig.zip");
		Assert.assertTrue(file.exists());
		
		//parse the zip file
		OpenDXLConfig openDXLConfig = OpenDXLConfigReader.read(file);
		ClientConfig clientConfig = openDXLConfig.getClientConfig();
		
		//verify the data
		Assert.assertNotNull(openDXLConfig.getBrokerCertChain());
		Assert.assertNotNull(openDXLConfig.getCertFile());
		Assert.assertNotNull(openDXLConfig.getPrivateKey());
		Assert.assertEquals("ca-broker.crt", clientConfig.getBrokerCertChain());
		Assert.assertEquals("client.crt", clientConfig.getCertFile());
		Assert.assertEquals("client.key", clientConfig.getPrivateKey());
		Assert.assertEquals("localhost", clientConfig.getHost());
		Assert.assertEquals(8883, clientConfig.getPort());
	}
}
