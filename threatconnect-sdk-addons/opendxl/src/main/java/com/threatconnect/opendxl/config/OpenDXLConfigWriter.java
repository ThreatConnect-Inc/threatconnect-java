package com.threatconnect.opendxl.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author Greg Marut
 */
public class OpenDXLConfigWriter extends OpenDXLConfigIO
{
	private static final Logger logger = LoggerFactory.getLogger(OpenDXLConfigWriter.class);
	
	public static byte[] write(final OpenDXLConfig openDXLConfig) throws IOException
	{
		//write the file contents to a byte array
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		try (ZipOutputStream out = new ZipOutputStream(byteArrayOutputStream))
		{
			writeFile(out, openDXLConfig.getClientConfig().getBrokerCertChain(), openDXLConfig.getBrokerCertChain());
			writeFile(out, openDXLConfig.getClientConfig().getCertFile(), openDXLConfig.getCertFile());
			writeFile(out, openDXLConfig.getClientConfig().getPrivateKey(), openDXLConfig.getPrivateKey());
			writeFile(out, OpenDXLConfigReader.CONFIG_FILENAME, openDXLConfig.getClientConfigContents());
		}
		
		return byteArrayOutputStream.toByteArray();
	}
	
	private static void writeFile(final ZipOutputStream out, final String fileName, final byte[] contents)
		throws IOException
	{
		//create the next zip entry
		ZipEntry entry = new ZipEntry(fileName);
		out.putNextEntry(entry);
		
		//write the contents of the zip entry
		out.write(contents, 0, contents.length);
		out.closeEntry();
	}
}
