package com.threatconnect.app.addons.util.config.install;

/**
 * @author Greg Marut
 */
public class Deprecation
{
	private  String indicatorType;
	private Integer intervalDays;
	private Integer confidenceAmount;
	private boolean percentage;
	
	public String getIndicatorType()
	{
		return indicatorType;
	}
	
	public void setIndicatorType(final String indicatorType)
	{
		this.indicatorType = indicatorType;
	}
	
	public Integer getIntervalDays()
	{
		return intervalDays;
	}
	
	public void setIntervalDays(final Integer intervalDays)
	{
		this.intervalDays = intervalDays;
	}
	
	public Integer getConfidenceAmount()
	{
		return confidenceAmount;
	}
	
	public void setConfidenceAmount(final Integer confidenceAmount)
	{
		this.confidenceAmount = confidenceAmount;
	}
	
	public boolean isPercentage()
	{
		return percentage;
	}
	
	public void setPercentage(final boolean percentage)
	{
		this.percentage = percentage;
	}
}
