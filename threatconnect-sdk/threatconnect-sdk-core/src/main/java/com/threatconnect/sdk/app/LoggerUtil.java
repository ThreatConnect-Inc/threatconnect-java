package com.threatconnect.sdk.app;

import com.threatconnect.app.apps.AppConfig;
import com.threatconnect.sdk.log.ServerLoggerAppender;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.FileAppender;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.layout.PatternLayout;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

public class LoggerUtil
{
	public static final String DEFAULT_LOGGER_PATTERN = "%-5p %d{HH:mm:ss} %c - %m%n";
	
	/**
	 * Reconfigures the global logger setting up the log file and the log level
	 *
	 * @param logFile   the log file to write the output
	 * @param appConfig the app configuration
	 */
	public static void reconfigureGlobalLogger(final File logFile, final AppConfig appConfig) throws IOException
	{
		reconfigureLogger(LogManager.ROOT_LOGGER_NAME, logFile, appConfig);
	}
	
	/**
	 * Reconfigures the global logger setting up the log file and the log level
	 *
	 * @param loggerPath the logger to reconfigure
	 * @param logFile    the log file to write the output
	 * @param appConfig  the app configuration
	 */
	public static void reconfigureLogger(final String loggerPath, final File logFile, final AppConfig appConfig)
	{
		addFileAppender(loggerPath, logFile, appConfig);
		addServerAppender(loggerPath, appConfig);
	}
	
	public static void addFileAppender(final String loggerPath, final File logFile, final AppConfig appConfig)
	{
		// retrieve the logger context
		LoggerContext loggerContext = (LoggerContext) LogManager.getContext(false);
		Configuration configuration = loggerContext.getConfiguration();
		
		// retrieve the root logger config
		LoggerConfig loggerConfig = configuration.getLoggerConfig(loggerPath);
		loggerConfig.setLevel(Level.toLevel(appConfig.getTcLogLevel()));
		
		// Define log pattern layout
		PatternLayout layout = PatternLayout.createLayout(DEFAULT_LOGGER_PATTERN, null, null, null,
			Charset.defaultCharset(), false, false, null, null);
		
		// create the appenders
		FileAppender fileAppender =
			FileAppender.createAppender(logFile.getAbsolutePath(), "true", "false", "fileAppender",
				"true", "true", "true", "8192", layout, null, "false", null, null);
		fileAppender.start();
		
		// add the appenders
		loggerConfig.addAppender(fileAppender, Level.toLevel(appConfig.getTcLogLevel()), null);
		loggerContext.updateLoggers();
	}
	
	public static void addServerAppender(final String loggerPath, final AppConfig appConfig)
	{
		// retrieve the logger context
		LoggerContext loggerContext = (LoggerContext) LogManager.getContext(false);
		Configuration configuration = loggerContext.getConfiguration();
		
		// retrieve the root logger config
		LoggerConfig loggerConfig = configuration.getLoggerConfig(loggerPath);
		loggerConfig.setLevel(Level.toLevel(appConfig.getTcLogLevel()));
		
		// create the appenders
		ServerLoggerAppender serverLoggerAppender =
			ServerLoggerAppender.createAppender("serverLoggerAppender", appConfig);
		serverLoggerAppender.start();
		
		// add the appenders
		loggerConfig.addAppender(serverLoggerAppender, Level.toLevel(appConfig.getTcLogLevel()), null);
		loggerContext.updateLoggers();
	}
	
	/**
	 * Writes a message to the standard out
	 *
	 * @param msg     Message to write to log
	 * @param fmtArgs Formatter string
	 */
	public static void logOut(final String msg, final Object... fmtArgs)
	{
		System.out.printf(msg + "\n", fmtArgs);
	}
	
	/**
	 * Writes a message to the standard error
	 *
	 * @param msg     Message to write to logs
	 * @param fmtArgs Formatter string
	 */
	public static void logErr(final String msg, final Object... fmtArgs)
	{
		System.err.printf(msg + "\n", fmtArgs);
	}
	
	/**
	 * Writes a message to the standard error
	 *
	 * @param msg     Message to write to logs
	 * @param fmtArgs Formatter string
	 * @param e       Exception
	 */
	public static void logErr(final Exception e, final String msg, final Object... fmtArgs)
	{
		logErr(msg + "\n", fmtArgs);
		e.printStackTrace();
	}
}
