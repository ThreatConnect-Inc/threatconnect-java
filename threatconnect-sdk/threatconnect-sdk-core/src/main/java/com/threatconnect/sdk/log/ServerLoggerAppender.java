package com.threatconnect.sdk.log;

import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;

public class ServerLoggerAppender extends AbstractAppender
{
	// holds the object that will transform a logevent to a logentry
	private LogEntryTransformer logEntryTransformer;
	
	protected ServerLoggerAppender(String name, Filter filter, LogEntryTransformer logEntryTransformer)
	{
		super(name, filter, null);
		
		this.logEntryTransformer = logEntryTransformer;
	}
	
	@Override
	public void append(LogEvent event)
	{
		// create a log entry from this log event
		LogEntry logEntry = logEntryTransformer.transform(event);
		
		// add this to the server logger
		ServerLogger.getInstance().addLogEntry(logEntry);
	}
	
	public static ServerLoggerAppender createAppender(String name)
	{
		if (name == null)
		{
			LOGGER.error("No name provided for ServerLoggerAppender");
			return null;
		}
		
		return new ServerLoggerAppender(name, ThreadLogFilter.getInstance(), new DefaultLogEntryTransformer());
	}
}
