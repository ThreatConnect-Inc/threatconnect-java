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
 * @author James
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Attribute")
public class Attribute
{
    @XmlElement(name = "Id", required = true)
    private Integer id;
    @XmlElement(name = "Type", required = true)
    private String type;
    @XmlElement(name = "Value", required = true)
    private String Value;
    @XmlElement(name = "Source", required = false)
    private String source;
    @JsonSerialize(using = DateSerializer.class)
    @XmlElement(name = "DateAdded", required = false)
    private Date dateAdded;
    @JsonSerialize(using = DateSerializer.class)
    @XmlElement(name = "LastModified", required = false)
    private Date lastModified;
    @XmlElement(name = "Displayed", required = false)
    private Boolean displayed;

    public Attribute() {
    }

    public Attribute(Integer id, String type, String value, String source, Date dateAdded, Date lastModified, Boolean displayed)
    {
        this.id = id;
        this.type = type;
        Value = value;
        this.source = source;
        this.dateAdded = dateAdded;
        this.lastModified = lastModified;
        this.displayed = displayed;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getType()
    {
        return type == null ? getClass().getSimpleName().toLowerCase() : type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getValue()
    {
        return Value;
    }

    public void setValue(String value)
    {
        this.Value = value;
    }

    public String getSource()
    {
        return source;
    }

    public void setSource(String source)
    {
        this.source = source;
    }

    public Date getDateAdded()
    {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded)
    {
        this.dateAdded = dateAdded;
    }

    public Date getLastModified()
    {
        return lastModified;
    }

    public void setLastModified(Date lastModified)
    {
        this.lastModified = lastModified;
    }

    public Boolean isDisplayed()
    {
        return displayed;
    }

    public void setDisplayed(Boolean displayed)
    {
        this.displayed = displayed;
    }

    @Override
    public String toString()
    {
        return "Attribute{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", Value='" + Value + '\'' +
                ", source='" + source + '\'' +
                ", dateAdded=" + dateAdded +
                ", lastModified=" + lastModified +
                ", displayed=" + displayed +
                '}';
    }
}
