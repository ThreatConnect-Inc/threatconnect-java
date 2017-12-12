package com.threatconnect.opendxl.client;

import com.google.gson.Gson;
import com.threatconnect.opendxl.client.message.DXLMessage;
import com.threatconnect.opendxl.client.message.DXLMessageListener;
import com.threatconnect.opendxl.client.message.VerboseDXLMessageListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttClientPersistence;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.msgpack.core.MessageBufferPacker;
import org.msgpack.core.MessagePack;
import org.msgpack.core.MessageUnpacker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.SocketFactory;
import java.io.Closeable;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author Greg Marut
 */
public class DXLClient implements MqttCallback, Closeable
{
	private static final Logger logger = LoggerFactory.getLogger(DXLClient.class);
	
	public static final int DEFAULT_REQUEST_TIMEOUT_MINUTES = 5;
	public static final long DEFAULT_CLIENT_TIMEOUT = 5000L;
	
	private final MqttClient client;
	private final MqttConnectOptions connectOptions;
	private final Map<String, IMqttMessageListener> subscriptionMap;
	
	protected final String clientID;
	protected final Gson gson;
	
	public DXLClient(final String sslHost, final byte[] caCert, final byte[] cert, final byte[] privateKey)
		throws MqttException, CertificateException, UnrecoverableKeyException, NoSuchAlgorithmException, IOException,
		KeyManagementException, KeyStoreException, InvalidKeySpecException
	{
		this(sslHost, SslUtil.getSocketFactory(caCert, cert, privateKey));
	}
	
	public DXLClient(final String sslHost, final SocketFactory socketFactory)
		throws MqttException
	{
		this.clientID = generateNewID();
		this.subscriptionMap = new HashMap<String, IMqttMessageListener>();
		this.gson = new Gson();
		
		//create the connection options for mqtt
		connectOptions = new MqttConnectOptions();
		connectOptions.setCleanSession(true);
		connectOptions.setAutomaticReconnect(true);
		connectOptions.setSocketFactory(socketFactory);
		
		//create a persistence for mqtt
		MqttClientPersistence mqttClientPersistence = new MemoryPersistence();
		
		// construct an MQTT blocking mode client
		final String url = "ssl://" + sslHost;
		client = new MqttClient(url, clientID, mqttClientPersistence);
		client.setCallback(this);
		client.setTimeToWait(DEFAULT_CLIENT_TIMEOUT);
		client.connect(connectOptions);
	}
	
	/**
	 * Publishes a payload to a topic
	 *
	 * @param topicName
	 * @param payload
	 * @throws MqttException
	 */
	public void publishEvent(final String topicName, final byte[] payload) throws MqttException, IOException
	{
		publishEvent(topicName, payload, 0);
	}
	
	/**
	 * Publishes a payload to a topic
	 *
	 * @param topicName
	 * @param payload
	 * @param qos
	 * @throws MqttException
	 */
	public void publishEvent(final String topicName, final byte[] payload, final int qos)
		throws MqttException, IOException
	{
		//create a new DXL message
		DXLMessage dxlMessage = new DXLMessage();
		dxlMessage.setVersion(DXLMessage.MESSAGE_VERSION);
		dxlMessage.setMessageType(DXLMessage.MESSAGE_TYPE_EVENT);
		dxlMessage.setMessageId(generateNewID());
		
		dxlMessage.setPayload(payload);
		
		publish(topicName, dxlMessage, qos);
	}
	
	/**
	 * Publishes a request to a service and returns the response
	 *
	 * @param serviceChannel
	 * @param payload
	 * @return
	 * @throws MqttException
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public byte[] publishRequest(final String serviceChannel, final byte[] payload)
		throws MqttException, IOException, InterruptedException
	{
		return publishRequest(serviceChannel, payload, 0);
	}
	
	/**
	 * Publishes a request to a service and returns the response
	 *
	 * @param serviceChannel
	 * @param payload
	 * @param qos
	 * @return the response received from the service
	 * @throws MqttException
	 * @throws IOException
	 * @throws InterruptedException if there was a timeout waiting for the response
	 */
	public byte[] publishRequest(final String serviceChannel, final byte[] payload, final int qos)
		throws MqttException, IOException, InterruptedException
	{
		final String messageId = generateNewID();
		final String replyToTopic = "/message/" + messageId + "/response";
		
		try
		{
			//create a blocking queue to listen for the responding message
			BlockingQueue<byte[]> messageQueue = new LinkedBlockingQueue<byte[]>();
			
			//subscribe to the replyToTopic listening for the response
			subscribe(replyToTopic, (topicName, mqttMessage, dxlMessage) ->
				messageQueue.offer(dxlMessage.getPayload()));
			
			//create a new DXL message
			DXLMessage dxlMessage = new DXLMessage();
			dxlMessage.setVersion(DXLMessage.MESSAGE_VERSION);
			dxlMessage.setMessageType(DXLMessage.MESSAGE_TYPE_REQUEST);
			dxlMessage.setMessageId(messageId);
			dxlMessage.setReplyToTopic(replyToTopic);
			dxlMessage.setPayload(payload);
			
			//publish the message
			publish(serviceChannel, dxlMessage, qos);
			
			//wait for the response to come back with a maximum time to wait. in the event of a timeout,
			return messageQueue.poll(DEFAULT_REQUEST_TIMEOUT_MINUTES, TimeUnit.MINUTES);
		}
		finally
		{
			unsubscribe(replyToTopic);
		}
	}
	
	/**
	 * Publishes a DXLMessage message to a topic
	 *
	 * @param topicName
	 * @param dxlMessage
	 * @param qos
	 * @throws MqttException
	 */
	protected void publish(final String topicName, final DXLMessage dxlMessage, final int qos)
		throws MqttException, IOException
	{
		//pack the dxl message to be used as the mqtt payload
		MessageBufferPacker messageBufferPacker = MessagePack.newDefaultBufferPacker();
		dxlMessage.pack(messageBufferPacker);
		final byte[] mqttPayload = messageBufferPacker.toByteArray();
		
		//create a new mqtt message to send
		MqttMessage mqttMessage = new MqttMessage(mqttPayload);
		mqttMessage.setQos(qos);
		
		logger.debug("Publishing message to topic: {}", topicName);
		logger.debug("Message Type: {}", dxlMessage.getMessageType());
		logger.debug("DXL Payload: {}", dxlMessage.getPayload());
		logger.debug("MQTT Payload: {}", new String(mqttPayload));
		client.publish(topicName, mqttMessage);
	}
	
	/**
	 * Subscribes to a topic and posts all notifications to the provided listener object
	 *
	 * @param topicName
	 * @param listener
	 * @throws MqttException
	 */
	public void subscribe(final String topicName, final DXLMessageListener listener) throws MqttException
	{
		//create the new imqtt message listener
		IMqttMessageListener iMqttMessageListener = (topic, mqttMessage) -> {
			try
			{
				//create a new message unpacker
				MessageUnpacker messageUnpacker = MessagePack.newDefaultUnpacker(mqttMessage.getPayload());
				
				//unpack the message
				DXLMessage dxlMessage = new DXLMessage();
				dxlMessage.unpack(messageUnpacker);
				
				//notify the listener
				//:TODO: remove the VerboseDXLMessageListener when we are done with testing
				DXLMessageListener verboseDxlMessageListener = new VerboseDXLMessageListener(listener);
				verboseDxlMessageListener.onMessageReceived(topic, mqttMessage, dxlMessage);
			}
			catch (Exception e)
			{
				logger.error(e.getMessage(), e);
			}
		};
		
		logger.debug("Subscribing to topic {}", topicName);
		client.subscribe(topicName, iMqttMessageListener);
		
		//add this listener to the map
		subscriptionMap.put(topicName, iMqttMessageListener);
	}
	
	/**
	 * Unsubscribes from a topic
	 *
	 * @param topicName
	 * @throws MqttException
	 */
	public void unsubscribe(final String topicName) throws MqttException
	{
		client.unsubscribe(topicName);
		subscriptionMap.remove(topicName);
	}
	
	public boolean isSubscribed(final String topicName)
	{
		return subscriptionMap.containsKey(topicName);
	}
	
	public String getClientID()
	{
		return clientID;
	}
	
	@Override
	public void close() throws IOException
	{
		unsubscribeFromTopics();
		
		try
		{
			logger.debug("Disconnecting MQTT Connection...");
			client.disconnect();
			
			logger.debug("Closing MQTT Connection...");
			client.close();
		}
		catch (MqttException e)
		{
			throw new IOException(e);
		}
	}
	
	@Override
	public void connectionLost(final Throwable throwable)
	{
		logger.warn("Connection Lost", throwable);
	}
	
	@Override
	public void messageArrived(final String s, final MqttMessage mqttMessage) throws Exception
	{
		logger.debug("Message Arrived");
	}
	
	@Override
	public void deliveryComplete(final IMqttDeliveryToken iMqttDeliveryToken)
	{
		logger.debug("Delivery Complete");
	}
	
	protected String generateNewID()
	{
		return "{" + UUID.randomUUID().toString() + "}";
	}
	
	protected synchronized void unsubscribeFromTopics()
	{
		if (!subscriptionMap.isEmpty())
		{
			try
			{
				//attempt to unsubscribe from the active subscriptions
				logger.debug("Unsubscribing from topics...");
				client.unsubscribe(subscriptionMap.keySet().toArray(new String[] {}));
				subscriptionMap.clear();
			}
			catch (MqttException e)
			{
				logger.warn(e.getMessage(), e);
			}
		}
	}
}
