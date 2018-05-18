package com.threatconnect.sdk.client.reader;

import com.threatconnect.sdk.conn.AbstractRequestExecutor;
import com.threatconnect.sdk.conn.Connection;

import java.io.IOException;
import java.net.URLEncoder;

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
		
		//indicators with spaces are causing issues so we need to make sure the type is encoded properly
		String encodedType = URLEncoder.encode(type, "UTF-8").replace("+", "%20");
		url = url.replace("{type}", encodedType);
		
		return executeAsString(AbstractRequestExecutor.HttpMethod.GET, url);
	}
}
