package com.threatconnect.sdk.conn;

/**
 * Created by dtineo on 1/12/16.
 */
public interface ApiCallListener
{
    // primarily used for counters/usage
    public void apiCall(String method, String path, long ms);
}
