package com.threatconnect.sdk.server.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by dtineo
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@XmlRootElement(name = "JobExecution")
@XmlAccessorType(XmlAccessType.FIELD)
public class JobExecution
{
    @XmlElement(name = "id", required = true)
    private Long id;

    @XmlElement(name = "jobId", required = true)
    private Integer jobId;

    @XmlElement(name = "status", required = false)
    private Status status;

    public static enum Status {
        Scheduled, Queued, Running, Completed, Failed, Detached, Partial_Failure
    }

    public JobExecution()
    {
    }

    public Status getStatus()
    {
        return status;
    }

    public void setStatus(Status status)
    {
        this.status = status;
    }

    public Integer getJobId()
    {
        return jobId;
    }

    public void setJobId(Integer jobId)
    {
        this.jobId = jobId;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }
}
