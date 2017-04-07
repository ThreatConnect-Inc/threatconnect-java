/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.server.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.threatconnect.sdk.server.entity.format.DateSerializer;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 *
 * @author James
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@XmlRootElement(name = "Campaign")
@XmlAccessorType(XmlAccessType.FIELD)
public class Campaign extends Group
{
    @JsonSerialize(using = DateSerializer.class)
    @XmlElement(name = "FirstSeem", required = false)
    private Date firstSeen;
    
    public Campaign()
    {
        super();
    }

    public Campaign(Integer id, String name, String type, Owner owner, String ownerName, Date dateAdded, String webLink, Date firstSeen)
    {
        super(id, name, type, owner, ownerName, dateAdded, webLink);
        this.firstSeen = firstSeen;
    }
    
    public Date getFirstSeen()
    {
        return firstSeen;
    }
    
    public void setFirstSeen(final Date firstSeen)
    {
        this.firstSeen = firstSeen;
    }
}
    
