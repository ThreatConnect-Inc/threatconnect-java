package com.threatconnect.sdk.client.writer;

import com.threatconnect.sdk.conn.AbstractRequestExecutor.HttpMethod;
import com.threatconnect.sdk.conn.Connection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.threatconnect.sdk.conn.AbstractRequestExecutor.HttpMethod.*;

/**
 * Created by cblades on 7/14/2016.
 */
public class DataStoreWriterAdapter extends AbstractWriterAdapter
{
    private final Logger logger = LoggerFactory.getLogger(getClass().getSimpleName());

    public DataStoreWriterAdapter(Connection conn)
    {
        super(conn);
    }

    public String createLocal(String type, String search, String body)
    {
        return create("Local", type, search, null /* owner */, body);
    }


    public String createSystem(String type, String search, String body)
    {
        return create("System", type, search, null /* owner */, body);
    }


    public String createOrganization(String type, String search, String owner, String body)
    {
        return create("Organization", type, search, owner, body);
    }

    public String updateLocal(String type, String search, String body)
    {
        return update("Local", type, search, null /* owner */, body);
    }


    public String updateSystem(String type, String search, String body)
    {
        return update("System", type, search, null /* owner */, body);
    }


    public String updateOrganization(String type, String search, String owner, String body)
    {
        return update("Organization", type, search, owner, body);
    }

    public String deleteLocal(String type, String search, String body)
    {
        return delete("Local", type, search, null /* owner */);
    }


    public String deleteSystem(String type, String search, String body)
    {
        return delete("System", type, search, null /* owner */);
    }


    public String deleteOrganization(String type, String search, String owner)
    {
        return delete("Organization", type, search, owner);
    }

    protected String delete(String domain, String type, String search, String owner)
    {
        return query(DELETE, domain, type, search, owner, null /* body */);
    }

    protected String update(String domain, String type, String search, String owner, String body)
    {
        return query(PUT, domain, type, search, owner, body);
    }

    protected String create(String domain, String type, String search, String owner, String body)
    {
        return query(POST, domain, type, search, owner, body);
    }

    protected String query(HttpMethod dbMethod, String domain, String type, String search, String owner, String body)
    {
        Map<String, String> params = new HashMap<>();
        params.put("domain", domain);
        params.put("type", type);
        params.put("search", search);

        try
        {
            return getAsText(dbMethod, params, owner, body);
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
            url = url.replace(
                    String.format("{%s}", param.getKey()),
                    param.getValue() != null ? param.getValue() : "");
        }

        Map<String, String> headers = new HashMap<>();
        headers.put("DB-Method", esMethod.name());

        logger.trace("calling url={}", url);
        String content = executor.execute(url, POST, headers, body).getEntity();

        logger.trace("returning content={}", content);

        return content;
    }

    protected String getUrlBasePrefix() {
        return "v2.datastore";
    }
}
