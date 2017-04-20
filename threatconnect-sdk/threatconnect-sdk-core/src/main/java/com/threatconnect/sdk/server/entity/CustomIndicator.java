/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.server.entity;

import java.util.Date;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.threatconnect.sdk.util.CustomIndicatorSerializer;
import com.threatconnect.sdk.util.CustomIndicatorDeserializer;

@JsonDeserialize(using = CustomIndicatorDeserializer.class)
@JsonSerialize(using = CustomIndicatorSerializer.class)
@JsonInclude(Include.NON_NULL)
@XmlRootElement(name = "CustomIndicator")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomIndicator extends Indicator
{
    @JsonIgnore
    @XmlTransient
    private String indicatorType;
    
	@JsonIgnore
    @XmlTransient
    private Map<String,String> map;
     
	@JsonIgnore
    @XmlTransient
	private CustomIndicatorIdFinder finder;
	
	public void setUniqueIdFinder(CustomIndicatorIdFinder finder) {
		this.finder = finder;
	}

	public CustomIndicator()
    {
        super();
    }

    public CustomIndicator(Indicator indicator)
    {
        super();
        this.setId(indicator.getId());
        this.setDateAdded(indicator.getDateAdded());
        this.setLastModified(indicator.getLastModified());
        this.setRating(indicator.getRating());
        this.setConfidence(indicator.getConfidence());
        this.setWebLink(indicator.getWebLink());
        this.setDescription(indicator.getDescription());
        this.setObservationCount(indicator.getObservationCount());
        this.setOwner(indicator.getOwner());
        this.setFalsePositiveLastReported(indicator.getFalsePositiveLastReported());
        this.setFalsePositiveCount(indicator.getFalsePositiveCount());
        this.setOwnerName(indicator.getOwnerName());
    }
    

    
    public CustomIndicator(Integer id, Owner owner, String ownerName, String type, Date dateAdded, Date lastModified, Double rating, Double confidence, Double threatAssessRating, Double threatAssessConfidence, String webLink, String source, String description, String summary, Map<String,String> keyValuePairs)
    {
        super(id, owner, ownerName, type, dateAdded, lastModified, rating, confidence, threatAssessRating, threatAssessConfidence, webLink, source, description, summary);
        map = keyValuePairs;
    }
    
    public Map<String,String> getMap()
    {
        return map;
    }
    
    public void setMap(Map<String,String> map)
    {
        this.map = map;
    }
    
    public String getIndicatorType() {
		return indicatorType;
	}

	public void setIndicatorType(String indicatorType) {
		this.indicatorType = indicatorType;
	}   
	
	 public Indicator castToIndicator()
	    {
		 	Indicator result = new Indicator();
		 	result.setId(this.getId());
		 	result.setDateAdded(this.getDateAdded());
		 	result.setLastModified(this.getLastModified());
		 	result.setRating(this.getRating());
		 	result.setConfidence(this.getConfidence());
		 	result.setWebLink(this.getWebLink());
		 	result.setDescription(this.getDescription());
		 	result.setObservationCount(this.getObservationCount());
		 	result.setOwner(this.getOwner());
		 	result.setFalsePositiveLastReported(this.getFalsePositiveLastReported());
		 	result.setFalsePositiveCount(this.getFalsePositiveCount());
		 	result.setOwnerName(this.getOwnerName());
		 	return result;
	    }

	public String getUniqueId() {
		if(finder == null)
			throw new RuntimeException("please provider an implementation to find unique id");
		
		return finder.getUniqueId(this);
	}

}
