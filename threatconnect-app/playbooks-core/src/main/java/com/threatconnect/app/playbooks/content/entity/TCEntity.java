package com.threatconnect.app.playbooks.content.entity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.Date;
import java.util.Objects;

public class TCEntity
{
	private Long id;
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

	public TCEntity(Long id, String type, String value, String ownerName)
	{
		this.id = id;
		this.type = type;
		this.value = value;
		this.ownerName = ownerName;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(final Long id)
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

    @Override
    public String toString()
    {
        //just output in json format
        Gson gson = new Gson();
        java.lang.reflect.Type type = new TypeToken<TCEntity>(){}.getType();
		return gson.toJson(this, type);
    }

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		TCEntity tcEntity = (TCEntity) o;
		return Objects.equals(getId(), tcEntity.getId()) &&
				Objects.equals(getType(), tcEntity.getType()) &&
				Objects.equals(getValue(), tcEntity.getValue()) &&
				Objects.equals(getOwnerName(), tcEntity.getOwnerName()) &&
				Objects.equals(getDateAdded(), tcEntity.getDateAdded()) &&
				Objects.equals(getWebLink(), tcEntity.getWebLink());
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(getId(), getType(), getValue(), getOwnerName(), getDateAdded(), getWebLink());
	}
}
