/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.conn;

import com.threatconnect.sdk.config.Configuration;
import com.threatconnect.sdk.config.URLConfiguration;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
/*
import org.jboss.resteasy.core.Dispatcher;
import org.jboss.resteasy.spi.Registry;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
*/

import javax.net.ssl.SSLContext;
import java.io.Closeable;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dtineo
 */
public class Connection<T> implements Closeable
{

    private final Logger logger = Logger.getLogger(getClass().getSimpleName());

    private CloseableHttpClient apiClient;
    protected Configuration config;
    private AbstractRequestExecutor executor;

    private final URLConfiguration urlConfig;

    public Connection() throws IOException {
        String fileName = System.getProperties().getProperty("threatconnect.api.config");
        Properties props = ConnectionUtil.loadProperties(fileName);
        this.config = Configuration.build(props);

        this.urlConfig = URLConfiguration.build();
    }

    public Connection(Configuration config) throws IOException {
        this.config = config;
        this.urlConfig = URLConfiguration.build();
    }

    /*
    public void setInMemoryDispatcher(Object dispatcher)
    {
        if ( executor == null )
        {
            executor = new InMemoryRequestExecutor(this);
        }

        ((InMemoryRequestExecutor)executor).setDispatcher((Dispatcher) dispatcher);
    }

    public void setInMemoryProviderFactory(Object providerFactory)
    {
        if ( executor == null )
        {
            executor = new InMemoryRequestExecutor(this);
        }

        ((InMemoryRequestExecutor)executor).setResteasyProviderFactory((ResteasyProviderFactory)providerFactory);
    }

    public void setInMemoryRegistry(Object registry)
    {
        ((InMemoryRequestExecutor)executor).setRegistry((Registry)registry);
    }
    */

    public AbstractRequestExecutor getExecutor() {
        if ( executor == null )
        {
            if ( config.getTcApiUrl().equalsIgnoreCase("embedded") )
            {
                executor = new InMemoryRequestExecutor(this);
            } else {
                executor = new HttpRequestExecutor(this);
            }
        }

        return executor;
    }

    /**
     * @return the config
     */
    public Configuration getConfig() {
        return config;
    }

    /**
     * @param config to set
     */
    public void setConfig(Configuration config) {
        this.config = config;
    }

    private CloseableHttpClient createApiClient() {

        SSLContext sslcontext;
        CloseableHttpClient httpClient = null;

        try {
            sslcontext = SSLContexts.custom()
                .loadTrustMaterial(null, new TrustSelfSignedStrategy())
                .build();

            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslcontext,
                new String[]{"TLSv1"},
                null,
                SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

            httpClient = HttpClients.custom().setSSLSocketFactory(sslsf).build();

        } catch (NoSuchAlgorithmException | KeyStoreException | KeyManagementException ex) {
            logger.log(Level.SEVERE, "Error creating httpClient", ex);
        }

        return httpClient;
    }

    CloseableHttpClient getApiClient() {
        if ( apiClient == null )
        {
            apiClient = createApiClient();
        }
        return apiClient;
    }

    /**
     * @return the urlConfig
     */
    public URLConfiguration getUrlConfig() {
        return urlConfig;
    }

    public void disconnect() {
        if ( apiClient != null )
        {
            try {
                apiClient.close();
            } catch (IOException ex) {
                logger.log(Level.SEVERE, "Error disconnecting from httpClient", ex);
            } finally {
                apiClient = null;
            }
        }
    }

    /**
     * Calls disconnect to close httpClient
     *
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void close() throws IOException
    {
       this.disconnect();
    }
}
