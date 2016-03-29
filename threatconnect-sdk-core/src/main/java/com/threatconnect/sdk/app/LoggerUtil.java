package com.threatconnect.sdk.app;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.appender.FileAppender;
import org.apache.logging.log4j.core.layout.PatternLayout;


public class LoggerUtil
{
	/**
	 * Reconfigures the global logger setting up the log file and the log level
	 * 
	 * @param logFile
	 * the log file to write the output
	 * @param appConfig
	 * the app configuration
	 * @throws IOException On error
	 */
	public static void reconfigureGlobalLogger(final File logFile, final AppConfig appConfig) throws IOException
	{
		// retrieve the root logger
		Logger logger = (Logger) LogManager.getRootLogger();
		
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
	 * @throws IOException On error
	 */
	public static void reconfigureLogger(final Logger logger, final File logFile, final AppConfig appConfig)
		throws IOException
	{
		// set the log level for this logger
		logger.setLevel(appConfig.getTcLogLevel());
		//TODO replace the following with log4j2
		//logger.removeAllAppenders();
		
		// Define log pattern layout
		PatternLayout layout= PatternLayout.createLayout("%-5p %d{HH:mm:ss} %c - %m%n", null, null, null, Charset.defaultCharset(),false,false,null,null);
        
		//PatternLayout layout = new PatternLayout("%-5p %d{HH:mm:ss} %c - %m%n");
		
		// Add the appender to root logger 
		logger.addAppender(FileAppender.createAppender(logFile.getAbsolutePath(), "true", "false", "fileAppender", "true", "true", "true", "8192", layout, null, "false", null, null));
	}
	
	/**
	 * Writes a message to the standard out
	 * 
	 * @param msg Message to write to log
	 * @param fmtArgs Formatter string
	 */
	public static void logOut(final String msg, final Object... fmtArgs)
	{
		System.out.printf(msg + "\n", fmtArgs);
	}
	
	/**
	 * Writes a message to the standard error
	 * 
	 * @param msg Message to write to logs
	 * @param fmtArgs Formatter string
	 */
	public static void logErr(final String msg, final Object... fmtArgs)
	{
		System.err.printf(msg + "\n", fmtArgs);
	}
	
	/**
	 * Writes a message to the standard error
	 *
	 * @param msg Message to write to logs
	 * @param fmtArgs Formatter string
	 * @param e Exception
	 */
	public static void logErr(final Exception e, final String msg, final Object... fmtArgs)
	{
		logErr(msg + "\n", fmtArgs);
		e.printStackTrace();
	}
}
