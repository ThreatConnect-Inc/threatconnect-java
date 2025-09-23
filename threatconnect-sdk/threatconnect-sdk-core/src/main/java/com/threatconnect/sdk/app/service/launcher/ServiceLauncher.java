package com.threatconnect.sdk.app.service.launcher;

import com.threatconnect.app.apps.AppConfig;
import com.threatconnect.app.services.MQTTServiceCommunicationClient;
import com.threatconnect.app.services.Service;
import com.threatconnect.app.services.message.CommandType;
import com.threatconnect.app.services.message.Heartbeat;
import com.threatconnect.app.services.message.Ready;
import com.threatconnect.sdk.app.exception.AppInitializationException;
import com.threatconnect.sdk.log.ServerLogger;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.ssl.SSLContexts;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Timer;
import java.util.TimerTask;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

/**
 * @author Greg Marut
 */
public class ServiceLauncher<S extends Service> extends MQTTServiceCommunicationClient
{
	private static final Logger logger = LoggerFactory.getLogger(ServiceLauncher.class);
	
	private final AppConfig appConfig;
	private final S service;
	
	private Timer heartbeatWatchTimer;
	private final Object heartbeatLock = new Object();
	
	public ServiceLauncher(final AppConfig appConfig, final S service) throws AppInitializationException
	{
		super("ssl://" + appConfig.getTcSvcBrokerHost() + ":" + appConfig.getTcSvcBrokerPort(), createMqttConnectOptions(appConfig));
		
		if (null == service)
		{
			throw new IllegalArgumentException("service cannot be null.");
		}
		
		if (null == appConfig.getTcServiceServerTopic())
		{
			throw new AppInitializationException("Unable to initialize service: " + AppConfig.TC_SVC_SERVER_TOPIC + " parameter is missing.");
		}
		
		if (null == appConfig.getTcServiceClientTopic())
		{
			throw new AppInitializationException("Unable to initialize service: " + AppConfig.TC_SVC_CLIENT_TOPIC + " parameter is missing.");
		}
		
		if (null == appConfig.getTcServiceHeartbeatTimeoutSeconds())
		{
			throw new AppInitializationException(
				"Unable to initialize service: " + AppConfig.TC_SVC_HEARTBEAT_TIMEOUT_SECONDS + " parameter is missing.");
		}
		
		this.appConfig = appConfig;
		this.service = service;
		
		//register a termination request listener
		this.service.addTerminationRequestListener(this::shutdown);
	}
	
	public AppConfig getAppConfig()
	{
		return appConfig;
	}
	
	public S getService()
	{
		return service;
	}
	
	public void start()
	{
		logger.trace("Starting service: " + getClass().getName());
		super.start();
	}
	
	@Override
	protected final void onConnected(final boolean reconnect)
	{
		//check to see if this is an initial connection
		if (!reconnect)
		{
			//send a ready message to the server to notify that this service has started
			Ready ready = new Ready();
			ready.setDiscoveryTypes(service.getDiscoveryTypes());
			sendMessage(ready);
			
			//notify the service that it has started
			service.onStartUp();
		}
	}
	
	@Override
	protected final String getSubscribeTopic()
	{
		return appConfig.getTcServiceServerTopic();
	}
	
	@Override
	protected final String getPublishTopic()
	{
		return appConfig.getTcServiceClientTopic();
	}
	
	@Override
	protected void onMessageReceived(final CommandType command, final String message)
	{
		switch (command)
		{
			case Shutdown:
				shutdown();
				break;
			case Heartbeat:
				handleHeartbeatCommand();
				break;
		}
	}
	
	private void handleHeartbeatCommand()
	{
		//send a heartbeat message in response to the server's heartbeat
		sendHeartbeatMessage();
		
		synchronized (heartbeatLock)
		{
			//check to see if the heartbeat watch timer is not null
			if (null != heartbeatWatchTimer)
			{
				heartbeatWatchTimer.cancel();
			}
			
			//create a timer that will check for the heartbeat timeout
			heartbeatWatchTimer = new Timer();
			heartbeatWatchTimer.schedule(new TimerTask()
			{
				@Override
				public void run()
				{
					logger.warn("No heartbeat was received in the last {} seconds. Shutting down service.",
						appConfig.getTcServiceHeartbeatTimeoutSeconds());
					shutdown();
				}
			}, appConfig.getTcServiceHeartbeatTimeoutSeconds() * 1000L);
		}
	}
	
	private void shutdown()
	{
		logger.info("Shutting down service.");
		
		try
		{
			//notify the service that we are shutting down
			getService().onShutdown();
			
			// flush the logs to the server
			ServerLogger.getInstance(getAppConfig()).flushToServer();
			
			//destroy this client
			destroy();
		}
		catch (Exception e)
		{
			logger.error(e.getMessage());
			System.exit(1);
		}
		finally
		{
			System.exit(0);
		}
	}
	
	private void sendHeartbeatMessage()
	{
		Heartbeat heartbeat = new Heartbeat();
		sendMessage(heartbeat);
	}
	
	private static MqttConnectOptions createMqttConnectOptions(final AppConfig appConfig)
	{
		MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
		mqttConnectOptions.setSSLHostnameVerifier(new NoopHostnameVerifier());
		mqttConnectOptions.setHttpsHostnameVerificationEnabled(false);
		mqttConnectOptions.setUserName("");
		mqttConnectOptions.setPassword(appConfig.getTcSvcBrokerToken().toCharArray());
		try {
            SSLContext sslContext = buildSSLContext(appConfig.getTcSvcBrokerJksFile(), appConfig.getTcSvcBrokerJksPassword());
			mqttConnectOptions.setSocketFactory(sslContext.getSocketFactory());
		} catch (IOException e) {
			logger.error("Could not build SSL Context", e);
		}
		return mqttConnectOptions;
	}
    public static SSLContext buildSSLContext(String keystorePath, String keystorePassword) throws IOException 
    {
        logger.trace("Building sslContext");
    	KeyStore keystore;
        SSLContext sslContext;
        
        try 
        {
            File keystoreFile = new File(keystorePath);
            
            // set default type for in-memory keystore
            keystore = KeyStore.getInstance(KeyStore.getDefaultType());

            // read keystore file into memory stream
            try (java.io.InputStream in = new java.io.FileInputStream(keystoreFile)) 
            {
                // load keystore into memory
                keystore.load(in, keystorePassword.toCharArray());

                // set the KeyManager to the configure keystore
                KeyManagerFactory keyManagerFactory
                        = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
                keyManagerFactory.init(keystore, keystorePassword.toCharArray());

                TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
	            trustManagerFactory.init((KeyStore) null);
	            // set socket protocol of SSL context
                sslContext = SSLContext.getInstance("TLS");

                // initialize SSL context with the configured keystore 
                sslContext.init(
                        keyManagerFactory.getKeyManagers(),
                        trustManagerFactory.getTrustManagers(),
                        new SecureRandom());  


                logger.trace("keystore.getDefaultType()=" + keystore.getDefaultType());
                logger.trace("keystore.containsAlias(\"tc\")=" + keystore.containsAlias("tc"));

                return sslContext;            
            }
        } 
        catch (GeneralSecurityException ex) 
        {
            logger.error("Error building sslContext", ex);
        }

        return null;
    }
}
