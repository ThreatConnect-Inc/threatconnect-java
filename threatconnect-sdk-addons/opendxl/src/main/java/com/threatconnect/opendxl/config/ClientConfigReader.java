package com.threatconnect.opendxl.config;

import org.ini4j.Ini;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Greg Marut
 */
public class ClientConfigReader
{
	public static final String GROUP_CERTS = "Certs";
	public static final String GROUP_BROKERS = "Brokers";
	
	public static final String FIELD_BROKER_CERT_CHAIN = "BrokerCertChain";
	public static final String FIELD_CERT_FILE = "CertFile";
	public static final String FIELD_PRIVATE_KEY = "PrivateKey";
	public static final String FIELD_EXTERNAL = "external";
	
	public static final int EXTERNAL_PORT_INDEX = 1;
	public static final int EXTERNAL_HOST_INDEX = 2;
	
	/**
	 * Reads and parses the openxdl configuration file
	 *
	 * @param contents the contents to parse
	 * @return
	 * @throws IOException
	 * @throws ClientConfigReaderException if there was an error parsing the data
	 */
	public static ClientConfig read(final byte[] contents) throws IOException, ClientConfigReaderException
	{
		try (InputStream inputStream = new ByteArrayInputStream(contents))
		{
			return read(inputStream);
		}
	}
	
	/**
	 * Reads and parses the openxdl configuration file
	 *
	 * @param file the file to parse
	 * @return
	 * @throws IOException
	 * @throws ClientConfigReaderException if there was an error parsing the data
	 */
	public static ClientConfig read(final File file) throws IOException, ClientConfigReaderException
	{
		try (InputStream inputStream = new FileInputStream(file))
		{
			return read(inputStream);
		}
	}
	
	/**
	 * Reads and parses the openxdl configuration file
	 *
	 * @param inputStream the input stream of the file to parse
	 * @return
	 * @throws IOException
	 * @throws ClientConfigReaderException if there was an error parsing the data
	 */
	public static ClientConfig read(final InputStream inputStream) throws IOException, ClientConfigReaderException
	{
		//read the ini input file
		Ini ini = new Ini(inputStream);
		
		//create a new client config object
		ClientConfig clientConfig = new ClientConfig();
		clientConfig.setBrokerCertChain(getRequired(ini, GROUP_CERTS, FIELD_BROKER_CERT_CHAIN));
		clientConfig.setCertFile(getRequired(ini, GROUP_CERTS, FIELD_CERT_FILE));
		clientConfig.setPrivateKey(getRequired(ini, GROUP_CERTS, FIELD_PRIVATE_KEY));
		
		//read the external field and split it into the expected parts
		final String external = getRequired(ini, GROUP_BROKERS, FIELD_EXTERNAL);
		final String[] externalParts = external.split(";");
		
		try
		{
			//attempt to set the host and port
			clientConfig.setHost(externalParts[EXTERNAL_HOST_INDEX]);
			clientConfig.setPort(Integer.parseInt(externalParts[EXTERNAL_PORT_INDEX]));
		}
		catch (NumberFormatException | ArrayIndexOutOfBoundsException e)
		{
			throw new ClientConfigReaderException(
				"Unable to parse the key \"" + FIELD_EXTERNAL + "\". Unexpected format was found.", e);
		}
		
		return clientConfig;
	}
	
	private static String getRequired(final Ini ini, final String group, final String key)
		throws ClientConfigReaderException
	{
		final String value = ini.get(group, key);
		if (null != value)
		{
			return value;
		}
		else
		{
			throw new ClientConfigReaderException("Missing required key \"" + key + "\" from group \"" + group + "\"");
		}
	}
}
