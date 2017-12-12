package com.threatconnect.opendxl.client;

import com.threatconnect.opendxl.client.message.DXLMessage;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * @author Greg Marut
 */
public interface ServiceHandler
{
	/**
	 * Called when a service has been invoked on a specified channel. The returned byte array will be sent to the caller
	 * as the message payload
	 *
	 * @param channel
	 * @param mqttMessage
	 * @param dxlMessage
	 * @return
	 */
	byte[] invoke(final String channel, final MqttMessage mqttMessage, final DXLMessage dxlMessage);
}
