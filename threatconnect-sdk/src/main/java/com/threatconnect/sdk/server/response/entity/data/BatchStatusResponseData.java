package com.threatconnect.sdk.server.response.entity.data;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.threatconnect.sdk.server.entity.BatchStatus;
import com.threatconnect.sdk.server.response.entity.data.ApiEntitySingleResponseData;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * Created by cblades on 6/10/2015.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class BatchStatusResponseData extends ApiEntitySingleResponseData<BatchStatus>
{
    @JsonSerialize(include= JsonSerialize.Inclusion.NON_NULL)
    @XmlElement(name = "batchStatus", required = false)
    private BatchStatus batchStatus;

    public BatchStatusResponseData() {}

    public BatchStatusResponseData(BatchStatus batchStatus)
    {
        super(batchStatus);
        this.batchStatus = batchStatus;
    }

    public BatchStatus getBatchStatus()
    {
        return batchStatus;
    }

    public void setBatchStatus(BatchStatus batchStatus)
    {
        this.batchStatus = batchStatus;
    }

    @Override
    public BatchStatus getData()
    {
        return getBatchStatus();
    }

    @Override
    public void setData(BatchStatus data)
    {
        setBatchStatus(data);
    }
}
