package com.threatconnect.sdk.playbooks.content.entity;

import java.util.Date;

public class TCEntity
{
	private Integer id;
	private String type;
	private String value;
	private String ownerName;
	private Integer confidence;
	private Short rating;
	private Integer threatAssessConfidence;
	private Short threatAssessRating;
	private Date dateAdded;
	private Date dateLastModified;
	
	private String webLink;
	
	public TCEntity()
	{
		this(null, null);
	}
	
	public TCEntity(Object type, Object value)
	{
		this.type = type == null ? null : type.toString();
		this.value = value == null ? null : value.toString();
	}
	
	public TCEntity(Object type, Object value, String owner)
	{
		this(type, value);
		ownerName = owner;
	}
	
	public Integer getId()
	{
		return id;
	}
	
	public void setId(final Integer id)
	{
		this.id = id;
	}
	
	public String getType()
	{
		return type;
	}
	
	public void setType(final String type)
	{
		this.type = type;
	}
	
	public String getValue()
	{
		return value;
	}
	
	public void setValue(final String value)
	{
		this.value = value;
	}
	
	public String getOwnerName()
	{
		return ownerName;
	}
	
	public void setOwnerName(final String ownerName)
	{
		this.ownerName = ownerName;
	}
	
	public Integer getConfidence()
	{
		return confidence;
	}
	
	public void setConfidence(final Integer confidence)
	{
		this.confidence = confidence;
	}
	
	public Short getRating()
	{
		return rating;
	}
	
	public void setRating(final Short rating)
	{
		this.rating = rating;
	}
	
	public Integer getThreatAssessConfidence()
	{
		return threatAssessConfidence;
	}
	
	public void setThreatAssessConfidence(final Integer threatAssessConfidence)
	{
		this.threatAssessConfidence = threatAssessConfidence;
	}
	
	public Short getThreatAssessRating()
	{
		return threatAssessRating;
	}
	
	public void setThreatAssessRating(final Short threatAssessRating)
	{
		this.threatAssessRating = threatAssessRating;
	}
	
	public Date getDateAdded()
	{
		return dateAdded;
	}
	
	public void setDateAdded(final Date dateAdded)
	{
		this.dateAdded = dateAdded;
	}
	
	public Date getDateLastModified()
	{
		return dateLastModified;
	}
	
	public void setDateLastModified(final Date dateLastModified)
	{
		this.dateLastModified = dateLastModified;
	}
	
	public String getWebLink()
	{
		return webLink;
	}
	
	public void setWebLink(final String webLink)
	{
		this.webLink = webLink;
	}
}
