package com.threatconnect.sdk.app.model;

public class Url extends Indicator
{
	private String text;
	
	public Url()
	{
		super(IndicatorType.URL);
	}
	
	public String getText()
	{
		return text;
	}
	
	public void setText(String text)
	{
		this.text = text;
	}
}
