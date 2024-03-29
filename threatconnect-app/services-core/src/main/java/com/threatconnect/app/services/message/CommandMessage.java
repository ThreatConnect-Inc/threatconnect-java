package com.threatconnect.app.services.message;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandMessage
{
	private static final Logger logger = LoggerFactory.getLogger(CommandMessage.class);
	
	private static final String FIELD_COMMAND = "command";
	
	private String appXid;
	private CommandType command;
	
	public CommandMessage()
	{
	
	}
	
	public CommandMessage(CommandType command)
	{
		this.command = command;
	}
	
	public String getAppXid()
	{
		return appXid;
	}
	
	public void setAppXid(final String appXid)
	{
		this.appXid = appXid;
	}
	
	public CommandType getCommand()
	{
		return command;
	}
	
	public void setCommand(CommandType command)
	{
		this.command = command;
	}
	
	public static CommandType getCommandFromMessage(final String message)
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
				return CommandType.valueOf(commandValue);
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
