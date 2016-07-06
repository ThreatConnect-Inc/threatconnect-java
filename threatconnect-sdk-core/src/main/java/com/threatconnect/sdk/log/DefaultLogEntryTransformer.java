package com.threatconnect.sdk.log;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.apache.logging.log4j.core.LogEvent;

public class DefaultLogEntryTransformer implements LogEntryTransformer
{
	@Override
	public LogEntry transform(LogEvent event)
	{
		// create a new log entry
		LogEntry logEntry = new LogEntry();
		logEntry.setLevel(event.getLevel().toString());
		logEntry.setMessage(createMessage(event));
		logEntry.setTimestamp(event.getTimeMillis());
		
		return logEntry;
	}
	
	private String createMessage(LogEvent event)
	{
		StringBuilder sb = new StringBuilder();
		
		// add the formatted message
		sb.append(event.getMessage().getFormattedMessage());
		
		// check to see if there is an exception
		if (null != event.getThrown())
		{
			// create the objects needed to pipe the printed stack trace into a byte array
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PrintStream ps = new PrintStream(baos);
			event.getThrown().printStackTrace(ps);
			
			// now write the bytes from the output stream to the string builder
			sb.append("\r\n");
			sb.append(new String(baos.toByteArray()));
		}
		
		return sb.toString();
	}
}
