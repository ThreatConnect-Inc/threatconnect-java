package com.threatconnect.sdk.app;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggerUtil
{
	/**
	 * Reconfigures the global logger setting up the log file and the log level
	 * 
	 * @param logFile
	 * the log file to write the output
	 * @param appConfig
	 * the app configuration
	 * @throws IOException
	 */
	public static void reconfigureGlobalLogger(final File logFile, final AppConfig appConfig) throws IOException
	{
		// retrieve the global JCL logger
		Logger logger = Logger.getGlobal();
		
		reconfigureLogger(logger, logFile, appConfig);
	}
	
	/**
	 * Reconfigures the global logger setting up the log file and the log level
	 * 
	 * @param logger
	 * the logger to reconfigure
	 * @param logFile
	 * the log file to write the output
	 * @param appConfig
	 * the app configuration
	 * @throws IOException
	 */
	public static void reconfigureLogger(final Logger logger, final File logFile, final AppConfig appConfig)
		throws IOException
	{
		// set the log level for this logger
		logger.setLevel(appConfig.getTcLogLevel());
		
		// create a new file handler to output the logs
		FileHandler fileHandler = new FileHandler(logFile.getAbsolutePath());
		
		// create a simple formatter for the log file
		Formatter formatterTxt = new SimpleFormatter();
		fileHandler.setFormatter(formatterTxt);
		
		// add the file handler to the logger
		logger.addHandler(fileHandler);
	}
	
	/**
	 * Writes a message to the standard out
	 * 
	 * @param msg
	 * @param fmtArgs
	 */
	public static void logOut(final String msg, final Object... fmtArgs)
	{
		System.out.printf(msg + "\n", fmtArgs);
	}
	
	/**
	 * Writes a message to the standard error
	 * 
	 * @param msg
	 * @param fmtArgs
	 */
	public static void logErr(final String msg, final Object... fmtArgs)
	{
		System.err.printf(msg + "\n", fmtArgs);
	}
	
	/**
	 * Writes a message to the standard error
	 * 
	 * @param msg
	 * @param fmtArgs
	 */
	public static void logErr(final Exception e, final String msg, final Object... fmtArgs)
	{
		logErr(msg + "\n", fmtArgs);
		e.printStackTrace();
	}
}
