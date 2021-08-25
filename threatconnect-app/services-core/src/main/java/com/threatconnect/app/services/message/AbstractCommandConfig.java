package com.threatconnect.app.services.message;

import java.util.Optional;

public abstract class AbstractCommandConfig extends CommandMessage
{
	private Long triggerId;
	private String requestKey;
	
	public AbstractCommandConfig(final CommandType command)
	{
		super(command);
	}
	
	public Long getTriggerId()
	{
		return triggerId;
	}
	
	public void setTriggerId(final Long triggerId)
	{
		this.triggerId = triggerId;
	}
	
	public String getRequestKey()
	{
		return Optional.ofNullable(requestKey)
			.orElseGet(this::generateRequestKey);
	}
	
	public void setRequestKey(final String requestKey)
	{
		this.requestKey = requestKey;
	}
	
	/**
	 * In the event that a request key is not defined, we can define a temporary unique key that can help track round
	 * trip requests. While collisions are possible, the risk should be fairly low given the use cases of this
	 * @return
	 */
	protected String generateRequestKey()
	{
		return triggerId + getCommand().toString();
	}
}
