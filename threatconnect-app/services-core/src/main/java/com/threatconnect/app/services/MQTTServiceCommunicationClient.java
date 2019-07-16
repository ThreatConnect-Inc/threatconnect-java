package com.threatconnect.app.services;

import com.google.gson.Gson;
import com.threatconnect.app.services.message.CommandMessage;
import com.threatconnect.app.services.message.CommandType;
import com.threatconnect.app.services.mqtt.MQTTRunnable;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttClientPersistence;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class MQTTServiceCommunicationClient extends ServiceCommunicationClient
{
	private static final Logger logger = LoggerFactory.getLogger(MQTTServiceCommunicationClient.class);
	
	private final String uri;
	private final MqttClientPersistence persistence;
	private final MqttConnectOptions mqttConnectOptions;
	protected final Gson gson;
	
	private MqttClient mqttClient;
	
	public MQTTServiceCommunicationClient(final String uri)
	{
		this(uri, new MqttConnectOptions());
	}
	
	public MQTTServiceCommunicationClient(final String uri, final MqttConnectOptions mqttConnectOptions)
	{
		this.uri = uri;
		this.persistence = new MemoryPersistence();
		this.mqttConnectOptions = mqttConnectOptions;
		this.gson = new Gson();
	}
	
	@Override
	public void start()
	{
		synchronized (this)
		{
			//make sure the client is not already connected
			if (!isConnected())
			{
				runE(() -> {
					this.mqttClient = new MqttClient(uri, MqttClient.generateClientId(), persistence);
					this.mqttClient.setCallback(new MqttHandler());
					this.mqttClient.connect(mqttConnectOptions);
				});
			}
		}
	}
	
	@Override
	public void destroy()
	{
		synchronized (this)
		{
			//make sure the client is not null
			if (null != this.mqttClient)
			{
				runW(() -> this.mqttClient.unsubscribe(getSubscribeTopic()));
				runW(() -> this.mqttClient.disconnect());
				runW(() -> this.mqttClient.close(true));
			}
		}
	}
	
	public boolean isConnected()
	{
		return (null != this.mqttClient && this.mqttClient.isConnected());
	}
	
	/**
	 * Publishes a message on the publish topic
	 *
	 * @param message
	 */
	@Override
	public void sendMessage(final CommandMessage message)
	{
		//make sure the client is not null
		if (null != this.mqttClient)
		{
			logger.trace("Sending message: {}", message.getCommand().toString());
			final MqttMessage mqttMessage = new MqttMessage(gson.toJson(message).getBytes());
			runW(() -> mqttClient.publish(getPublishTopic(), mqttMessage));
		}
	}
	
	protected void onConnected(final boolean reconnect)
	{
	
	}
	
	/**
	 * Runs an MQTT operation quietly and catches an MqttException. Any exception is logged at the warn level
	 *
	 * @param mqttRunnable
	 */
	private void runW(final MQTTRunnable mqttRunnable)
	{
		try
		{
			mqttRunnable.run();
		}
		catch (MqttException e)
		{
			logger.warn(e.getMessage());
		}
	}
	
	/**
	 * Runs an MQTT operation quietly and catches an MqttException. Any exception is logged at the error level with a stack trace
	 *
	 * @param mqttRunnable
	 */
	private void runE(final MQTTRunnable mqttRunnable)
	{
		try
		{
			mqttRunnable.run();
		}
		catch (MqttException e)
		{
			logger.error(e.getMessage(), e);
		}
	}
	
	private class MqttHandler implements MqttCallbackExtended
	{
		@Override
		public void connectComplete(final boolean reconnect, final String serverURI)
		{
			//subscribe to the subscribe channel
			runE(() -> mqttClient.subscribe(getSubscribeTopic()));
			
			//notify that the client has connected
			onConnected(reconnect);
		}
		
		@Override
		public void connectionLost(final Throwable cause)
		{
		
		}
		
		@Override
		public void messageArrived(final String topic, final MqttMessage message)
		{
			logger.trace("** messageArrived: topic={}, msg={}", topic, message);
			final String payload = new String(message.getPayload());
			final CommandType command = CommandMessage.getCommandFromMessage(payload);
			
			onMessageReceived(command, payload);
		}
		
		@Override
		public void deliveryComplete(final IMqttDeliveryToken token)
		{
		
		}
	}
}
