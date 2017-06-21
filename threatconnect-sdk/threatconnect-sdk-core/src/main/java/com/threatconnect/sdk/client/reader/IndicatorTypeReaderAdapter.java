package com.threatconnect.sdk.client.reader;

import com.threatconnect.sdk.conn.AbstractRequestExecutor;
import com.threatconnect.sdk.conn.Connection;

import java.io.IOException;

/**
 * @author Greg Marut
 */
public class IndicatorTypeReaderAdapter extends AbstractReaderAdapter
{
	public IndicatorTypeReaderAdapter(final Connection conn)
	{
		super(conn);
	}
	
	public String getAllIndicatorTypesAsText() throws IOException
	{
		return getAsText("v2.indicatortypes.all");
	}
	
	public String getIndicatorTypeAsText(final String type) throws IOException
	{
		String url = getConn().getUrlConfig().getUrl("v2.indicatortypes.byType");
		url = url.replace("{type}", type);
		
		return executeAsString(AbstractRequestExecutor.HttpMethod.GET, url);
	}
}
