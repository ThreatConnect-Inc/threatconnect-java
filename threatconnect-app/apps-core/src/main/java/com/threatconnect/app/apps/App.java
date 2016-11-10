package com.threatconnect.app.apps;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Represents the core of an app. All child classes of this class must contain a no argument
 * constructor.
 *
 * @author Greg Marut
 */
public abstract class App
{
	private static final Logger logger = LoggerFactory.getLogger(App.class);
	
	// holds the reference to the app config object
	private AppConfig appConfig;
	
	public void init(final AppConfig appConfig)
	{
		this.appConfig = appConfig;
	}
	
	/**
	 * Executes the app
	 *
	 * @param appConfig the configuration for this app
	 * @return abstract
	 * @throws Exception on error
	 */
	public abstract ExitStatus execute(AppConfig appConfig) throws Exception;
	
	/**
	 * Retrieves the name of the log file for this app to log to
	 *
	 * @return the name of the log file for this app to log to
	 */
	public abstract String getLogFilename();
	
	/**
	 * Writes a message out to the application's message log file
	 *
	 * @param message Message to append to to log
	 */
	public void writeMessageTc(String message)
	{
		PrintWriter writer = null;
		
		try
		{
			// write the message out to the message.tc file
			writer = new PrintWriter(getMessageLogFile(), "UTF-8");
			writer.println(message);
		}
		catch (FileNotFoundException | UnsupportedEncodingException e)
		{
			logger.error("Failed to write message.tc file", e);
		}
		finally
		{
			// make sure the writer is not null
			if (null != writer)
			{
				writer.close();
			}
		}
	}
	
	/**
	 * Writes the results out to the application's results log file
	 *
	 * @param results Results to write to log file
	 */
	public void writeResultsTc(Map<String, String> results)
	{
		PrintWriter writer = null;
		
		try
		{
			// create the writer object
			writer = new PrintWriter(getResultsLogFile(), "UTF-8");
			
			// for each of the results
			for (Map.Entry<String, String> entry : results.entrySet())
			{
				// write the result to the file
				writer.println(String.format("%s = %s", entry.getKey(), entry.getValue()));
			}
		}
		catch (FileNotFoundException | UnsupportedEncodingException e)
		{
			logger.error("Failed to write results.tc file", e);
		}
		finally
		{
			IOUtils.closeQuietly(writer);
		}
	}
	
	/**
	 * Writes data out to an output file for this app
	 *
	 * @param fileName the filename to write to
	 * @param data     the data to write
	 * @throws IOException if there was an error writing the file
	 */
	public void writeOutFile(final String fileName, final byte[] data) throws IOException
	{
		// retrieve the file to write to
		File file = getOutFile(fileName);
		
		// write the data to this file
		try (FileOutputStream out = new FileOutputStream(file))
		{
			out.write(data);
		}
	}
	
	public AppConfig getAppConfig()
	{
		return appConfig;
	}
	
	/**
	 * Returns the log file for this app
	 *
	 * @return the log file for this app
	 */
	public File getAppLogFile()
	{
		return new File(getAppConfig().getTcLogPath() + File.separator + getLogFilename());
	}
	
	/**
	 * Returns the message.tc log file
	 *
	 * @return the message.tc log file
	 */
	public File getMessageLogFile()
	{
		return getOutFile("message.tc");
	}
	
	/**
	 * Returns the results.tc log file
	 *
	 * @return the results.tc log file
	 */
	public File getResultsLogFile()
	{
		return getOutFile("results.tc");
	}
	
	public File getOutFile(final String fileName)
	{
		return new File(getAppConfig().getTcOutPath() + File.separator + fileName);
	}
}
