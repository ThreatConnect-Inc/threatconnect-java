package com.threatconnect.sdk.log;

import org.apache.logging.log4j.core.LogEvent;

public interface LogEntryTransformer
{
	LogEntry transform(LogEvent event);
}
