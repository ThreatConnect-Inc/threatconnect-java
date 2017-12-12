package com.threatconnect.opendxl.client.message;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A simple wrapper class that just enchances verbose logging. Useful for debugging purposes
 *
 * @author Greg Marut
 */
public class VerboseDXLMessageListener implements DXLMessageListener
{
	private static final Logger logger = LoggerFactory.getLogger(VerboseDXLMessageListener.class);
	
	private final DXLMessageListener dxlMessageListener;
	
	public VerboseDXLMessageListener()
	{
		this(null);
	}
	
	public VerboseDXLMessageListener(final DXLMessageListener dxlMessageListener)
	{
		this.dxlMessageListener = dxlMessageListener;
	}
	
	@Override
	public void onMessageReceived(final String topicName, final MqttMessage mqttMessage, final DXLMessage dxlMessage)
	{
		logger.debug("Message Received on topic {}", topicName);
		logger.debug("MQTT Payload: {}", new String(mqttMessage.getPayload()));
		logger.debug("MessageID: {}", dxlMessage.getMessageId());
		logger.debug("SourceClientID: {}", dxlMessage.getSourceClientId());
		logger.debug("SourceBrokerID: {}", dxlMessage.getSourceBrokerId());
		logger.debug("ClientIds: {}", dxlMessage.getClientIds());
		logger.debug("BrokerIds: {}", dxlMessage.getBrokerIds());
		logger.debug("DXL Payload: {}", dxlMessage.getPayload());
		logger.debug("ReplyToToic: {}", dxlMessage.getReplyToTopic());
		
		//forward the message received event as long as the listener is not nul
		if (null != dxlMessageListener)
		{
			dxlMessageListener.onMessageReceived(topicName, mqttMessage, dxlMessage);
		}
	}
}
