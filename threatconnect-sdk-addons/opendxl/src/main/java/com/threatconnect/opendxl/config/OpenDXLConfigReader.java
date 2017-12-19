package com.threatconnect.opendxl.config;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @author Greg Marut
 */
public class OpenDXLConfigReader extends OpenDXLConfigIO
{
	private static final Logger logger = LoggerFactory.getLogger(OpenDXLConfigReader.class);
	
	public static final String CONFIG_FILENAME = "dxlclient.config";
	
	public static OpenDXLConfig read(final String fileName, final byte[] zipContents)
		throws OpenDXLReaderException, IOException
	{
		File file = createTempFile(fileName);
		
		//write the file contents to a temporary file on the file system
		logger.debug("Creating a temporary file: " + file.getAbsolutePath());
		try (FileOutputStream fileOutputStream = new FileOutputStream(file))
		{
			//write the contents of the file
			fileOutputStream.write(zipContents);
			fileOutputStream.flush();
			
			return read(file);
		}
		finally
		{
			//delete the file after we are done
			logger.debug("Deleting temporary file: " + file.getAbsolutePath());
			file.delete();
		}
	}
	
	public static OpenDXLConfig read(final File file) throws OpenDXLReaderException, IOException
	{
		try (ZipFile zipFile = new ZipFile(file))
		{
			return read(zipFile);
		}
	}
	
	public static OpenDXLConfig read(final ZipFile zipFile) throws OpenDXLReaderException
	{
		try
		{
			//read all of the files from the zip file
			final Map<String, byte[]> fileMap = readZipFile(zipFile);
			
			//get the client file and parse the contents
			final byte[] configFile = getRequired(fileMap, CONFIG_FILENAME);
			final ClientConfig clientConfig = ClientConfigReader.read(configFile);
			
			//create the open dxl configuration
			OpenDXLConfig openDXLConfig = new OpenDXLConfig();
			openDXLConfig.setClientConfig(clientConfig);
			
			//add the files
			openDXLConfig.setBrokerCertChain(getRequired(fileMap, clientConfig.getBrokerCertChain()));
			openDXLConfig.setCertFile(getRequired(fileMap, clientConfig.getCertFile()));
			openDXLConfig.setPrivateKey(getRequired(fileMap, clientConfig.getPrivateKey()));
			openDXLConfig.setClientConfigContents(configFile);
			
			return openDXLConfig;
		}
		catch (IOException e)
		{
			throw new OpenDXLReaderException(e);
		}
	}
	
	private static byte[] getRequired(final Map<String, byte[]> fileMap, final String fileName)
		throws OpenDXLReaderException
	{
		//get the file and make sure it exists
		final byte[] fileContents = fileMap.get(fileName);
		if (null != fileContents)
		{
			return fileContents;
		}
		else
		{
			throw new OpenDXLReaderException("Missing required file \"" + fileName + "\"");
		}
	}
	
	/**
	 * Reads the zip file and extracts all of the files into a map by filename
	 *
	 * @param zipFile
	 * @return
	 * @throws IOException
	 */
	private static Map<String, byte[]> readZipFile(final ZipFile zipFile) throws IOException
	{
		final Map<String, byte[]> fileMap = new HashMap<String, byte[]>();
		
		//for each of the entries in the zip file
		Enumeration<? extends ZipEntry> entries = zipFile.entries();
		while (entries.hasMoreElements())
		{
			//retrieve the next element in the file
			ZipEntry zipEntry = entries.nextElement();
			
			//read the file entry and store it in the map
			try (InputStream inputStream = zipFile.getInputStream(zipEntry))
			{
				fileMap.put(zipEntry.getName(), IOUtils.toByteArray(inputStream));
			}
		}
		
		return fileMap;
	}
}
