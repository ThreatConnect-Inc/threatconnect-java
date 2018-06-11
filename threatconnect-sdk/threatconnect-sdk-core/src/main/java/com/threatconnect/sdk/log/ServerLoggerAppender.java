package com.threatconnect.sdk.log;

import com.threatconnect.app.apps.AppConfig;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;

public class ServerLoggerAppender extends AbstractAppender
{
	// holds the object that will transform a logevent to a logentry
	private final LogEntryTransformer logEntryTransformer;
	
	private final AppConfig appConfig;
	
	protected ServerLoggerAppender(final String name, final Filter filter,
		final LogEntryTransformer logEntryTransformer, final AppConfig appConfig)
	{
		super(name, filter, null);
		
		this.appConfig = appConfig;
		this.logEntryTransformer = logEntryTransformer;
	}
	
	@Override
	public void append(LogEvent event)
	{
		// create a log entry from this log event
		LogEntry logEntry = logEntryTransformer.transform(event);
		
		// add this to the server logger
		ServerLogger.getInstance(appConfig).addLogEntry(logEntry);
	}
	
	public static ServerLoggerAppender createAppender(final String name, final AppConfig appConfig)
	{
		if (name == null)
		{
			LOGGER.error("No name provided for ServerLoggerAppender");
			return null;
		}
		
		return new ServerLoggerAppender(name, ThreadLogFilter.getInstance(), new DefaultLogEntryTransformer(),
			appConfig);
	}
}
