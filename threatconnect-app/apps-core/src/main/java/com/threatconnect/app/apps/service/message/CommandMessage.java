package com.threatconnect.app.apps.service.message;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandMessage
{
	private static final Logger logger = LoggerFactory.getLogger(CommandMessage.class);
	
	private static final String FIELD_COMMAND = "command";
	
	public enum Command
	{
		CreateConfig,
		DeleteConfig,
		UpdateConfig,
		Shutdown,
		ListServices,
		RunService,
		Heartbeat,
		Launch,
		StartSession,
		FireEvent,
		WebHookEvent,
		MailEvent,
		Acknowledge
	}
	
	private Command command;
	
	public CommandMessage()
	{
	
	}
	
	public CommandMessage(Command command)
	{
		this.command = command;
	}
	
	public Command getCommand()
	{
		return command;
	}
	
	public void setCommand(Command command)
	{
		this.command = command;
	}
	
	public static Command getCommandFromMessage(final String message)
	{
		//parse the message as a json object
		logger.trace(message);
		JsonElement jsonMessage = new JsonParser().parse(message);
		
		// make sure the element is not null
		if (null != jsonMessage && !jsonMessage.isJsonNull())
		{
			final String commandValue = jsonMessage.getAsJsonObject().get(FIELD_COMMAND).getAsString();
			
			try
			{
				return Command.valueOf(commandValue);
			}
			catch (IllegalArgumentException e)
			{
				logger.warn("Unrecognized command: " + commandValue);
				return null;
			}
		}
		else
		{
			return null;
		}
	}
}
