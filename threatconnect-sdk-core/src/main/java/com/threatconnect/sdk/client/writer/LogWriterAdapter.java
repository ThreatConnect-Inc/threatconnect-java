package com.threatconnect.sdk.client.writer;

import java.io.IOException;
import java.util.Map;

import com.threatconnect.sdk.conn.AbstractRequestExecutor.HttpMethod;
import com.threatconnect.sdk.conn.Connection;
import com.threatconnect.sdk.exception.FailedResponseException;
import com.threatconnect.sdk.log.LogEntry;
import com.threatconnect.sdk.log.ThreadLogFilter;
import com.threatconnect.sdk.server.response.entity.ApiEntitySingleResponse;
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
	
	@Override
	protected <T extends ApiEntitySingleResponse> T modifyItem(String propName, Class<T> type, String ownerName,
		Map<String, Object> paramMap, Object saveObject, HttpMethod requestType)
			throws IOException, FailedResponseException
	{
		try
		{
			// due to the recursive logging, we need to temporarily disable logging for this thread
			// while modifyItem is called.
			// :TODO: This logic should be revisited to see if there are more optimal solutions
			ThreadLogFilter.getInstance().set(false);
			
			return super.modifyItem(propName, type, ownerName, paramMap, saveObject, requestType);
		}
		finally
		{
			// re-enable logging for this thread
			ThreadLogFilter.getInstance().set(true);
		}
	}
}
