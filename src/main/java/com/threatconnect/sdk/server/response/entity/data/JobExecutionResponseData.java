package com.threatconnect.sdk.server.response.entity.data;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.threatconnect.sdk.server.entity.JobExecution;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * Created by dtineo
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class JobExecutionResponseData extends ApiEntitySingleResponseData<JobExecution>
{
    @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    @XmlElement(name = "jobExecution", required = false)
    private JobExecution jobExecution;

    @Override
    public JobExecution getData()
    {
        return getJobExecution();
    }

    @Override
    public void setData(JobExecution data)
    {
        setJobExecution(data);
    }

    public JobExecution getJobExecution()
    {
        return jobExecution;
    }

    public void setJobExecution(JobExecution jobExecution)
    {
        this.jobExecution = jobExecution;
    }
}
