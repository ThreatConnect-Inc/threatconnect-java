package com.threatconnect.sdk.client.reader;

import com.threatconnect.sdk.conn.AbstractRequestExecutor.HttpMethod;
import com.threatconnect.sdk.conn.Connection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.threatconnect.sdk.conn.AbstractRequestExecutor.HttpMethod.GET;
import static com.threatconnect.sdk.conn.AbstractRequestExecutor.HttpMethod.POST;

/**
 * Created by cblades on 7/14/2016.
 */
public class DataStoreReaderAdapter extends AbstractReaderAdapter
{
    private final Logger logger = LoggerFactory.getLogger(getClass().getSimpleName());

    public DataStoreReaderAdapter(Connection conn)
    {
        super(conn);
    }

    public String getOrganization(String type, String search, String owner)
    {
        return get("Organization", type, search, owner, null /* body */);
    }

    public String getSystem(String type, String search)
    {
        return get("System", type, search, null /* owner */, null /* body */);
    }

    public String getLocal(String type, String search)
    {
        return get("Local", type, search, null /* owner */, null /* body */);
    }

    protected String get(String domain, String type, String search, String owner, String body)
    {
        Map<String, String> params = new HashMap<>();
        params.put("domain", domain);
        params.put("type", type);
        params.put("search", search);

        try
        {
            return getAsText(GET, params, owner, body);
        } catch (IOException ioe)
        {
            logger.error("Error while retrieving dataStore query");
        }

        return null;
    }

    protected String getAsText(HttpMethod esMethod, Map<String, String> params, String owner, String body) throws IOException
    {
        String url = getConn().getUrlConfig().getUrl(getUrlBasePrefix());

        if (owner != null && !owner.trim().isEmpty())
        {
            url += String.format("?owner=%s", owner);
        }

        for (Map.Entry<String, String> param : params.entrySet()) {
            url = url.replace(String.format("{%s}", param.getKey()), param.getValue());
        }

        Map<String, String> headers = new HashMap<>();
        headers.put("DB-Method", esMethod.name());

        logger.trace("calling url={}", url);
        String content = executor.execute(url, POST, headers, body).getEntityAsString();

        logger.trace("returning content={}", content);

        return content;
    }

    protected String getUrlBasePrefix() {
        return "v2.datastore";
    }
}
