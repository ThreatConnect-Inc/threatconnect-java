package com.threatconnect.app.services.message;

public class UpdateTriggerValue extends AbstractCommandConfig
{
	private String inputName;
	private String inputValue;
	
	public UpdateTriggerValue()
	{
		super(CommandType.UpdateTriggerValue);
	}
	
	public String getInputName()
	{
		return inputName;
	}
	
	public void setInputName(final String inputName)
	{
		this.inputName = inputName;
	}
	
	public String getInputValue()
	{
		return inputValue;
	}
	
	public void setInputValue(final String inputValue)
	{
		this.inputValue = inputValue;
	}
}
