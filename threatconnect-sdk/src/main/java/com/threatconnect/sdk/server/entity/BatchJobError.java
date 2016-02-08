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
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "BatchJobError")
public class BatchJobError
{
    @XmlElement(name = "errorReason", required = true)
    private String errorReason;

    @XmlElement(name = "errorSource", required = false)
    private Indicator errorSource;

    @XmlElement(name = "errorSource", required = false)
    private String errorMessage;

    public BatchJobError(String errorReason, Indicator errorSource)
    {
        this.errorReason = errorReason;
        this.errorSource = errorSource;
    }

    public BatchJobError(String errorReason, String errorMessage)
    {
        this.errorReason = errorReason;
        this.errorMessage = errorMessage;
    }

    public BatchJobError()
    {
    }

    public String getErrorReason()
    {
        return errorReason;
    }

    public void setErrorReason(String errorReason)
    {
        this.errorReason = errorReason;
    }

    public Indicator getErrorSource()
    {
        return errorSource;
    }

    public void setErrorSource(Indicator errorSource)
    {
        this.errorSource = errorSource;
    }

    public String getErrorMessage()
    {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage)
    {
        this.errorMessage = errorMessage;
    }
}
