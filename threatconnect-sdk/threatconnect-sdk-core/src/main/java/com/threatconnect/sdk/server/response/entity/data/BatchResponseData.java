package com.threatconnect.sdk.server.response.entity.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.threatconnect.sdk.server.entity.Attribute;
import com.threatconnect.sdk.server.entity.Batch;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * Created by cblades on 6/10/2015.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class BatchResponseData extends ApiEntitySingleResponseData<Long>
{
    @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    @XmlElement(name = "batchId", required = false)
    private Long batchId;

    public void setBatchId(Long batchId)
    {
        this.batchId = batchId;
    }

    public Long getBatchId()
    {
        return this.batchId;
    }

    @Override
    @JsonIgnore
    public Long getData()
    {
        return getBatchId();
    }

    @Override
    public void setData(Long data)
    {
        setBatchId(data);
    }

}
