package com.threatconnect.app.services;

import com.google.gson.Gson;
import com.threatconnect.app.services.message.CommandMessage;
import com.threatconnect.app.services.message.CommandType;
import com.threatconnect.app.services.mqtt.MQTTRunnable;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttClientPersistence;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ServiceCommunicationClient
{
	private static final Logger logger = LoggerFactory.getLogger(ServiceCommunicationClient.class);
	
	private final String uri;
	private final MqttClientPersistence persistence;
	private final Gson gson;
	
	private MqttClient mqttClient;
	
	public ServiceCommunicationClient(final String uri)
	{
		this.uri = uri;
		this.persistence = new MemoryPersistence();
		this.gson = new Gson();
	}
	
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
					this.mqttClient.connect();
				});
			}
		}
	}
	
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
	protected void sendMessage(final CommandMessage message)
	{
		//make sure the client is not null
		if (null != this.mqttClient)
		{
			final MqttMessage mqttMessage = new MqttMessage(gson.toJson(message).getBytes());
			runW(() -> mqttClient.publish(getPublishTopic(), mqttMessage));
		}
	}
	
	protected abstract void onMessageReceived(final CommandType command, final String message);
	
	protected abstract String getPublishTopic();
	
	protected abstract String getSubscribeTopic();
	
	protected abstract void onConnected(final boolean reconnect);
	
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
