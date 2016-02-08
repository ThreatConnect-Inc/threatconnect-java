package com.threatconnect.sdk.server.entity;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by cblades on 6/10/2015.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@XmlRootElement(name = "BatchStatus")
@XmlAccessorType(XmlAccessType.FIELD)
public class BatchStatus
{
    @XmlElement(name = "status", required = false)
    private Status status;

    @XmlElement(name = "errorCount", required = false)
    private Integer errorCount;

    @XmlElement(name = "successCount", required = false)
    private Integer successCount;

    @XmlElement(name = "unprocessCount", required = false)
    private Integer unprocessCount;

    public enum Status {
        Created,
        Queued,
        Running,
        Completed
    }

    public BatchStatus()
    {
    }

    public BatchStatus(Status status, Integer errorCount, Integer successCount, Integer unprocessCount)
    {
        this.status = status;
        this.errorCount = errorCount;
        this.successCount = successCount;
        this.unprocessCount = unprocessCount;
    }

    public Status getStatus()
    {
        return status;
    }

    public void setStatus(Status status)
    {
        this.status = status;
    }

    public Integer getErrorCount()
    {
        return errorCount;
    }

    public void setErrorCount(Integer errorCount)
    {
        this.errorCount = errorCount;
    }

    public Integer getSuccessCount()
    {
        return successCount;
    }

    public void setSuccessCount(Integer successCount)
    {
        this.successCount = successCount;
    }

    public Integer getUnprocessCount()
    {
        return unprocessCount;
    }

    public void setUnprocessCount(Integer unprocessCount)
    {
        this.unprocessCount = unprocessCount;
    }
}
