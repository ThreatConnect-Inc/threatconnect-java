package com.threatconnect.sdk.parser.model;

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
	
	@Override
	public String toString()
	{
		return getText();
	}
}
