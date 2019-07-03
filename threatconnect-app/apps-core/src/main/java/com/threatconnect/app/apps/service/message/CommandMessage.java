package com.threatconnect.app.apps.service.message;

public class CommandMessage
{
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
		MailEvent
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
}
