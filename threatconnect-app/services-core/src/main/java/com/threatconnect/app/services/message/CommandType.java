package com.threatconnect.app.services.message;

public enum CommandType
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
	Acknowledge,
	Ready,
	AppUpdated
}