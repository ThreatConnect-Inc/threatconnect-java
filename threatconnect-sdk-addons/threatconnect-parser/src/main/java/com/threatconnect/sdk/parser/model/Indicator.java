package com.threatconnect.sdk.parser.model;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

public abstract class Indicator extends Item
{
	private final String indicatorType;
	private final Set<Group> associatedItems;
	
	private Double rating;
	private Double confidence;
	private Double threatAssessRating;
	private Double threatAssessConfidence;
	private String webLink;
	private String source;
	private String description;
	private String summary;
	private Date dateAdded;
	
	public Indicator(final String indicatorType)
	{
		super(ItemType.INDICATOR);
		this.indicatorType = indicatorType;
		this.associatedItems = new LinkedHashSet<Group>();
	}
	
	public final String getIndicatorType()
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
	public Set<Group> getAssociatedItems()
	{
		return associatedItems;
	}
	
	@Override
	public String toString()
	{
		return getIdentifier();
	}
	
	@Override
	public int hashCode()
	{
		final String id = getIdentifier();
		
		// make sure the id is not null
		if (null != id)
		{
			return id.hashCode();
		}
		else
		{
			return super.hashCode();
		}
	}
	
	@Override
	public boolean equals(Object obj)
	{
		final String id = getIdentifier();
		
		// make sure the id is not null
		if (null != id)
		{
			// make sure the other object is an indicator
			if (obj instanceof Indicator)
			{
				final Indicator other = (Indicator) obj;
				return id.equals(other.getIdentifier());
			}
			else
			{
				return super.equals(obj);
			}
		}
		else
		{
			return super.equals(obj);
		}
	}
	
	/**
	 * Returns the unique identifier for this indicator
	 * 
	 * @return
	 */
	public abstract String getIdentifier();
}
