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
	WebhookMarshallEvent,
	WebhookEventResponse,
	MailEvent,
	Acknowledged,
	Ready,
	AppUpdated,
	LoggingChange,
	ServiceExitEvent,
	ServiceStoppedEvent
}