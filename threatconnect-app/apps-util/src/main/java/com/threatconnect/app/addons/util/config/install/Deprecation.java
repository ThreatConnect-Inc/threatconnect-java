package com.threatconnect.app.addons.util.config.install;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Greg Marut
 */
public class Deprecation
{
	private Integer intervalDays;
	private Integer confidenceAmount;
	private Boolean percentage;
	
	private final List<String> indicatorTypes;
	
	public Deprecation()
	{
		this.indicatorTypes = new ArrayList<String>();
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
	
	public Boolean getPercentage()
	{
		return percentage;
	}
	
	public void setPercentage(final Boolean percentage)
	{
		this.percentage = percentage;
	}
	
	public List<String> getIndicatorTypes()
	{
		return indicatorTypes;
	}
}
