package com.threatconnect.sdk.parser.model;

public class Url extends Indicator
{
	public static final String INDICATOR_TYPE = "URL";
	
	private String text;
	
	public Url()
	{
		super(INDICATOR_TYPE);
	}
	
	public String getText()
	{
		return text;
	}
	
	public void setText(String text)
	{
		this.text = text;
	}
	
	@Override
	public String getIdentifier()
	{
		return getText();
	}
}
