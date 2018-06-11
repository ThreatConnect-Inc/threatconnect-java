package com.threatconnect.opendxl.client;

import com.google.gson.Gson;
import com.threatconnect.opendxl.client.message.DXLMessage;
import com.threatconnect.opendxl.client.message.ServiceRegistration;
import com.threatconnect.opendxl.client.message.ServiceUnregistration;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.SocketFactory;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author Greg Marut
 */
public class DXLServiceClient extends DXLClient
{
	private static final Logger logger = LoggerFactory.getLogger(DXLServiceClient.class);
	
	public static final int DEFAULT_SERVICE_TTL = 60;
	private static final int DEFAULT_THREADS = 5;
	
	public static final String DXL_SERVICE_REGISTER_REQUEST_CHANNEL = "/mcafee/service/dxl/svcregistry/register";
	public static final String DXL_SERVICE_UNREGISTER_REQUEST_CHANNEL = "/mcafee/service/dxl/svcregistry/unregister";
	public static final String DXL_SERVICE_REGISTER_CHANNEL = "/mcafee/event/dxl/svcregistry/register";
	public static final String DXL_SERVICE_UNREGISTER_CHANNEL = "/mcafee/event/dxl/svcregistry/unregister";
	
	private final Gson gson;
	private final Map<String, String> registeredServicesMap;
	private final Executor executor;
	
	public DXLServiceClient(final String sslHost, final byte[] caCert, final byte[] cert,
		final byte[] privateKey) throws MqttException, GeneralSecurityException, IOException
	{
		this(sslHost, caCert, cert, privateKey, MqttConnectOptions.KEEP_ALIVE_INTERVAL_DEFAULT);
	}
	
	public DXLServiceClient(final String sslHost, final byte[] caCert, final byte[] cert,
		final byte[] privateKey, final int mqttKeepAliveIntervalSeconds)
		throws MqttException, GeneralSecurityException, IOException
	{
		super(sslHost, caCert, cert, privateKey, mqttKeepAliveIntervalSeconds);
		this.registeredServicesMap = new HashMap<String, String>();
		this.gson = new Gson();
		this.executor = Executors.newFixedThreadPool(DEFAULT_THREADS);
	}
	
	public DXLServiceClient(final String sslHost, final SocketFactory socketFactory)
		throws MqttException
	{
		this(sslHost, socketFactory, MqttConnectOptions.KEEP_ALIVE_INTERVAL_DEFAULT);
	}
	
	public DXLServiceClient(final String sslHost, final SocketFactory socketFactory,
		final int mqttKeepAliveIntervalSeconds)
		throws MqttException
	{
		super(sslHost, socketFactory, mqttKeepAliveIntervalSeconds);
		this.registeredServicesMap = new HashMap<String, String>();
		this.gson = new Gson();
		this.executor = Executors.newFixedThreadPool(DEFAULT_THREADS);
	}
	
	/**
	 * Registers a new service and returns the service UUID
	 *
	 * @param serviceHandler
	 * @param serviceName
	 * @param channels
	 * @return
	 * @throws MqttException
	 * @throws IOException
	 */
	public String registerService(final ServiceHandler serviceHandler, final String serviceName,
		final List<String> channels) throws MqttException, IOException
	{
		//make sure this service is not already registered
		if (!registeredServicesMap.containsKey(serviceName))
		{
			//for each of the channels
			for (String channel : channels)
			{
				//subscribe to each of the channels
				subscribe(channel, (topicName, mqttMessage, dxlMessage) -> {
					//execute a new thread that being processing the playbook
					executor.execute(() -> {
						try
						{
							//invoke the service handler and retrieve the result
							byte[] result = serviceHandler.invoke(topicName, mqttMessage, dxlMessage);
							
							//create a new DXL message
							DXLMessage dxlResponseMessage = new DXLMessage();
							dxlResponseMessage.setVersion(DXLMessage.MESSAGE_VERSION);
							dxlResponseMessage.setMessageType(DXLMessage.MESSAGE_TYPE_RESPONSE);
							dxlResponseMessage.setMessageId(generateNewID());
							dxlResponseMessage.setPayload(result);
							
							//send the response to the caller
							publish(dxlMessage.getReplyToTopic(), dxlResponseMessage, 0);
						}
						catch (Exception e)
						{
							logger.warn(e.getMessage(), e);
						}
					});
				});
			}
			
			//create a new serviceuuid
			final String serviceUuid = generateNewID();
			
			//set the service uuid and serialize the message
			ServiceRegistration serviceRegistration = new ServiceRegistration();
			serviceRegistration.setServiceType(serviceName);
			serviceRegistration.setRequestChannels(channels);
			serviceRegistration.setServiceGuid(serviceUuid);
			serviceRegistration.setMetaData(new HashMap<String, String>());
			serviceRegistration.setTtlMins(DEFAULT_SERVICE_TTL);
			final String message = gson.toJson(serviceRegistration);
			
			//create a new DXL message
			DXLMessage dxlMessage = new DXLMessage();
			dxlMessage.setVersion(DXLMessage.MESSAGE_VERSION);
			dxlMessage.setMessageType(DXLMessage.MESSAGE_TYPE_REQUEST);
			dxlMessage.setMessageId(generateNewID());
			dxlMessage.setPayload(message.getBytes());
			
			//register the service
			logger.debug("Registering Service...");
			publish(DXL_SERVICE_REGISTER_REQUEST_CHANNEL, dxlMessage, 0);
			
			//add this service uuid to the set
			registeredServicesMap.put(serviceName, serviceUuid);
			
			return serviceUuid;
		}
		else
		{
			return registeredServicesMap.get(serviceName);
		}
	}
	
	public synchronized void unregisterServices()
	{
		//attempt to unregister this service
		logger.debug("Unregistering Services...");
		
		//for each of the registered services
		for (String serviceName : registeredServicesMap.keySet())
		{
			unregisterService(serviceName);
		}
	}
	
	public void unregisterService(final String serviceName)
	{
		//look up the service uuid by the service name and make sure it is not null
		final String serviceUuid = registeredServicesMap.get(serviceName);
		if (null != serviceUuid)
		{
			//attempt to unregister this service
			ServiceUnregistration serviceUnregistration = new ServiceUnregistration();
			serviceUnregistration.setServiceGuid(serviceUuid);
			final String message = gson.toJson(serviceUnregistration);
			
			//create a new DXL message
			DXLMessage dxlMessage = new DXLMessage();
			dxlMessage.setVersion(DXLMessage.MESSAGE_VERSION);
			dxlMessage.setMessageType(DXLMessage.MESSAGE_TYPE_REQUEST);
			dxlMessage.setMessageId(generateNewID());
			dxlMessage.setPayload(message.getBytes());
			
			try
			{
				publish(DXL_SERVICE_UNREGISTER_REQUEST_CHANNEL, dxlMessage, 0);
			}
			catch (IOException | MqttException e)
			{
				logger.error(e.getMessage(), e);
			}
			
			//remove this entry from the map
			registeredServicesMap.remove(serviceName);
		}
		else
		{
			logger.info("The service {} is not registered.", serviceName);
		}
		
	}
	
	@Override
	public void close() throws IOException
	{
		try
		{
			unregisterServices();
		}
		finally
		{
			super.close();
		}
	}
}
