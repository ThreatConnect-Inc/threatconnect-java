package com.threatconnect.sdk.conn;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by dtineo on 5/28/15.
 */
public abstract class AbstractRequestExecutor
{

    protected final Logger logger = LoggerFactory.getLogger(getClass().getSimpleName());
    protected final Connection conn;
    protected List<ApiCallListener> apiCallListeners = new ArrayList<>();

    public static enum HttpMethod {
        GET, PUT, DELETE, POST
    }

    public AbstractRequestExecutor(Connection conn) {
        this.conn = conn;

        if (this.conn.getConfig() == null) {
            throw new IllegalStateException("Can't execute requests when configuration is undefined.");
        }

    }

    public abstract String execute(String path, HttpMethod type, Object obj) throws IOException;
    public abstract String executeUploadByteStream(String path, File file) throws IOException;
    public abstract InputStream executeDownloadByteStream(String path) throws IOException;

    public String execute(HttpMethod method, String path) throws IOException
    {
        return execute(path, method, null);
    }

    public void addApiCallListener(ApiCallListener listener)
    {
        apiCallListeners.add(listener);
    }

    public boolean removeApiCallListener(ApiCallListener listener)
    {
        return apiCallListeners.remove(listener);
    }

}
