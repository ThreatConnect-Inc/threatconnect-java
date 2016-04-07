package com.threatconnect.sdk.app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents the core of an app
 * 
 * @author Greg Marut
 */
public abstract class App
{
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	// holds the reference to the app config object
	private AppConfig appConfig;
	
	/**
	 * Executes the app
	 * 
	 * @param appConfig
	 * the configuration for this app
	 * @return abstract
	 * @throws Exception
	 * on error
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
	 * @param message
	 * Message to append to to log
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
			LoggerUtil.logErr(e, "Failed to write message.tc file");
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
	 * @param results
	 * Results to write to log file
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
			LoggerUtil.logErr(e, "Failed to write message.tc file");
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
	 * Writes a results out to the application's results log file
	 * 
	 * @param results
	 * Results to append to to log
	 */
	public void writeResultsTc(String results)
	{
		PrintWriter writer = null;
		
		try
		{
			// write the message out to the results.tc file
			writer = new PrintWriter(getResultsLogFile(), "UTF-8");
			writer.println(results);
		}
		catch (FileNotFoundException | UnsupportedEncodingException e)
		{
			LoggerUtil.logErr(e, "Failed to write results.tc file");
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
	 * Reads the results of the results.tc file
	 * 
	 * @return
	 */
	public String readResultsTc()
	{
		try
		{
			return IOUtils.toString(new FileInputStream(getResultsLogFile()));
		}
		catch (IOException e)
		{
			LoggerUtil.logErr(e, "Failed to read results.tc file");
			return null;
		}
	}
	
	public Logger getLogger()
	{
		return logger;
	}
	
	public AppConfig getAppConfig()
	{
		return appConfig;
	}
	
	public void setAppConfig(AppConfig appConfig)
	{
		this.appConfig = appConfig;
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
		return new File(getAppConfig().getTcOutPath() + File.separator + "message.tc");
	}
	
	/**
	 * Returns the results.tc log file
	 * 
	 * @return the results.tc log file
	 */
	public File getResultsLogFile()
	{
		return new File(getAppConfig().getTcOutPath() + File.separator + "results.tc");
	}
}
