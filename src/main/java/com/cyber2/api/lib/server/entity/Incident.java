/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyber2.api.lib.server.entity;

import com.cyber2.api.lib.server.entity.format.DateSerializer;
import com.cyber2.api.lib.server.entity.init.ApiEntityInit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import org.codehaus.jackson.map.annotate.JsonSerialize;

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
