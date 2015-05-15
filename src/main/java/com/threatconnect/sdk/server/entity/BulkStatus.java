/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.server.entity;

import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.threatconnect.sdk.server.entity.format.DateSerializer;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 *
 * @author cblades
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "BulkStatus")
public class BulkStatus
{
    @XmlElement(name = "Name", required = true)
    private String name;
    @XmlElement(name = "CsvEnabled", required = true)
    private boolean csvEnabled;
    @XmlElement(name = "JsonEnabled", required = true)
    private boolean jsonEnabled;
    @JsonSerialize(using = DateSerializer.class)
    @XmlElement(name = "NextRun", required = true)
    private Date nextRun;
    @JsonSerialize(using = DateSerializer.class)
    @XmlElement(name = "LastRun", required = true)
    private Date lastRun;
    @XmlElement(name = "Status", required = true)
    private String status;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public boolean isCsvEnabled()
    {
        return csvEnabled;
    }

    public void setCsvEnabled(boolean csvEnabled)
    {
        this.csvEnabled = csvEnabled;
    }

    public boolean isJsonEnabled()
    {
        return jsonEnabled;
    }

    public void setJsonEnabled(boolean jsonEnabled)
    {
        this.jsonEnabled = jsonEnabled;
    }

    public Date getNextRun()
    {
        return nextRun;
    }

    public void setNextRun(Date nextRun)
    {
        this.nextRun = nextRun;
    }

    public Date getLastRun()
    {
        return lastRun;
    }

    public void setLastRun(Date lastRun)
    {
        this.lastRun = lastRun;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

}
