package com.threatconnect.app.addons.util.config.install;

/**
 * @author Greg Marut
 */
public class Deprecation
{
	private String indicatorType;
	private Integer intervalDays;
	private Integer confidenceAmount;
	private boolean deleteAtMinimum;
	private String actionAtMinimum;
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
	
	@Deprecated
	public boolean isDeleteAtMinimum()
	{
		return deleteAtMinimum;
	}
	
	@Deprecated
	public void setDeleteAtMinimum(final boolean deleteAtMinimum)
	{
		this.deleteAtMinimum = deleteAtMinimum;
	}
	
	public String getActionAtMinimum()
	{
		return actionAtMinimum;
	}
	
	public void setActionAtMinimum(final String actionAtMinimum)
	{
		this.actionAtMinimum = actionAtMinimum;
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
