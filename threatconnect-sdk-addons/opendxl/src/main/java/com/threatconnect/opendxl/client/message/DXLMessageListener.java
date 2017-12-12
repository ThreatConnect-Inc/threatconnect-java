package com.threatconnect.opendxl.client.message;

import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * @author Greg Marut
 */
public interface DXLMessageListener
{
	void onMessageReceived(String topicName, MqttMessage mqttMessage, DXLMessage dxlMessage);
}
