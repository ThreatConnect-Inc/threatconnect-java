package com.threatconnect.sdk.parser.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class Indicator extends Item
{
	private final IndicatorType indicatorType;
	private final List<Group> associatedItems;
	
	private Double rating;
	private Double confidence;
	private Double threatAssessRating;
	private Double threatAssessConfidence;
	private String webLink;
	private String source;
	private String description;
	private String summary;
	private Date dateAdded;
	
	public Indicator(final IndicatorType indicatorType)
	{
		super(ItemType.INDICATOR);
		this.indicatorType = indicatorType;
		this.associatedItems = new ArrayList<Group>();
	}
	
	public final IndicatorType getIndicatorType()
	{
		return indicatorType;
	}
	
	public Double getRating()
	{
		return rating;
	}
	
	public void setRating(Double rating)
	{
		this.rating = rating;
	}
	
	public Double getConfidence()
	{
		return confidence;
	}
	
	public void setConfidence(Double confidence)
	{
		this.confidence = confidence;
	}
	
	public Double getThreatAssessRating()
	{
		return threatAssessRating;
	}
	
	public void setThreatAssessRating(Double threatAssessRating)
	{
		this.threatAssessRating = threatAssessRating;
	}
	
	public Double getThreatAssessConfidence()
	{
		return threatAssessConfidence;
	}
	
	public void setThreatAssessConfidence(Double threatAssessConfidence)
	{
		this.threatAssessConfidence = threatAssessConfidence;
	}
	
	public String getWebLink()
	{
		return webLink;
	}
	
	public void setWebLink(String webLink)
	{
		this.webLink = webLink;
	}
	
	public String getSource()
	{
		return source;
	}
	
	public void setSource(String source)
	{
		this.source = source;
	}
	
	public String getDescription()
	{
		return description;
	}
	
	public void setDescription(String description)
	{
		this.description = description;
	}
	
	public String getSummary()
	{
		return summary;
	}
	
	public void setSummary(String summary)
	{
		this.summary = summary;
	}
	
	public Date getDateAdded()
	{
		return dateAdded;
	}
	
	public void setDateAdded(Date dateAdded)
	{
		this.dateAdded = dateAdded;
	}
	
	@Override
	public List<Group> getAssociatedItems()
	{
		return associatedItems;
	}
}
