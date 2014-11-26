/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyber2.api.lib.client;

import com.cyber2.api.lib.conn.Connection;
import com.cyber2.api.lib.conn.RequestExecutor;
import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

/**
 *
 * <p>
 * Base client class used by {@link com.cyber2.api.lib.client.reader} and
 * {@link com.cyber2.api.lib.client.writer}. Conceptually works as an adapter
 * with a {@link com.cyber2.api.lib.conn.Connection} and a
 * {@link com.cyber2.api.lib.conn.RequestExecutor}.
 * </p>
 *
 * <p>
 * Implementing classes should abstract away low level API calls to the
 * {@link com.cyber2.api.lib.conn.RequestExecutor} and return high-level
 * {@link com.cyber2.api.lib.entities} style classes.
 * </p>
 *
 *
 */
public abstract class AbstractClientAdapter {

    private final Logger logger = LogManager.getLogger(AbstractClientAdapter.class);

    /**
     * Base {@link com.cyber2.api.lib.conn.Connection} to REST API interface
     */
    private Connection conn;
    /**
     * {@link com.cyber2.api.lib.conn.RequestExecutor} used to interact with
     * REST API
     */
    protected RequestExecutor executor;
    /**
     * ObjectMapper used to marshall and unmarshall data with the REST API
     */
    protected final ObjectMapper mapper = new ObjectMapper();

    /**
     * Base constructor with required fields to create a basic client adapter
     *
     * @param conn Main object managing API connection
     * @param executor Manages low level REST calls
     */
    public AbstractClientAdapter(Connection conn, RequestExecutor executor) {
        this.conn = conn;
        this.executor = executor;

    }

    /**
     * @return the conn
     */
    public Connection getConn() {
        return conn;
    }

    /**
     * @param conn the conn to set
     */
    public void setConn(Connection conn) {
        this.conn = conn;
    }

    protected static Map<String,Object> createParamMap( Object... keyOrValue ) {
        Map<String,Object> map = new HashMap<>();
        for(int i=0; i<keyOrValue.length; i+=2) {
            map.put(keyOrValue[i].toString(), keyOrValue[i+1]);
        }
        return map;
    }
}
