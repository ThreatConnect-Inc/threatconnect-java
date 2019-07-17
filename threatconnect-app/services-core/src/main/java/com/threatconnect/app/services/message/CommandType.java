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
	WebhookEvent,
	MailEvent,
	Acknowledge,
	Ready,
	AppUpdated
}