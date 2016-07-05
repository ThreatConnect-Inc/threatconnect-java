package com.threatconnect.sdk.client.writer;

import java.io.IOException;

import com.threatconnect.sdk.conn.Connection;
import com.threatconnect.sdk.exception.FailedResponseException;
import com.threatconnect.sdk.log.LogEntry;
import com.threatconnect.sdk.server.response.entity.LogEntryResponse;

public class LogWriterAdapter extends AbstractWriterAdapter
{
	public LogWriterAdapter(Connection conn)
	{
		super(conn);
	}
	
	public Integer writeLogEntires(final LogEntry... logEntries) throws IOException, FailedResponseException
	{
		LogEntryResponse data = createItem("v2.logs.app", LogEntryResponse.class, logEntries);
		
		return data.getData().getData();
	}
}
