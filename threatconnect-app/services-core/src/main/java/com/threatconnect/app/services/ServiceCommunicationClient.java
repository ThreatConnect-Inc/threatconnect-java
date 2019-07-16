package com.threatconnect.app.services;

import com.threatconnect.app.services.message.CommandMessage;
import com.threatconnect.app.services.message.CommandType;

public abstract class ServiceCommunicationClient
{
	public abstract void start();
	
	public abstract void destroy();
	
	public abstract void sendMessage(final CommandMessage message);
	
	protected abstract void onMessageReceived(final CommandType command, final String message);
	
	protected abstract String getPublishTopic();
	
	protected abstract String getSubscribeTopic();
	
}
