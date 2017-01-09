package com.threatconnect.sdk.log;

import com.threatconnect.sdk.app.LoggerUtil;
import com.threatconnect.sdk.client.writer.LogWriterAdapter;

public class LogWriterTask implements Runnable
{
	// holds the object used to write the log files to the server
	private final LogWriterAdapter logWriterAdapter;
	
	// holds the array of log entries to send to the server
	private final LogEntry[] logEntryArray;
	
	public LogWriterTask(final LogWriterAdapter logWriterAdapter, final LogEntry[] logEntryArray)
	{
		// make sure the log writer adapter is not null
		if (null == logWriterAdapter)
		{
			throw new IllegalArgumentException("logWriterAdapter cannot be null");
		}
		
		this.logWriterAdapter = logWriterAdapter;
		this.logEntryArray = logEntryArray;
	}
	
	@Override
	public void run()
	{
		// make sure the array of entries is not null
		if (null != logEntryArray)
		{
			try
			{
				// send the log entry array to the server
				logWriterAdapter.writeLogEntires(logEntryArray);
			}
			catch (Exception e)
			{
				LoggerUtil.logErr(e, e.getMessage());
			}
		}
	}
}
