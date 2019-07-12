package com.threatconnect.app.services.mqtt;

import org.eclipse.paho.client.mqttv3.MqttException;

@FunctionalInterface
public interface MQTTRunnable
{
	void run() throws MqttException;
}
