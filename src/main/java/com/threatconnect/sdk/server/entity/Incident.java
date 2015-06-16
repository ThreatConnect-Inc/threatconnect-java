/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.server.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.threatconnect.sdk.server.entity.format.DateSerializer;

import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


/**
 *
 * @author eric
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Incident")
public class Incident extends Group
{
    @JsonSerialize(using = DateSerializer.class)
    @XmlElement(name = "EventDate", required = false)
    private Date eventDate;
    
    public Incident()
    {
        super();
    }

    public Incident(Integer id, String name, String type, Owner owner, String ownerName, Date dateAdded, String webLink, Date eventDate)
    {
        super(id, name, type, owner, ownerName, dateAdded, webLink);
        this.eventDate = eventDate;
    }

    /**
     * @return the eventDate
     */
    public Date getEventDate()
    {
        return eventDate;
    }

    /**
     * @param eventDate the eventDate to set
     */
    public void setEventDate(Date eventDate)
    {
        this.eventDate = eventDate;
    }
    
}
