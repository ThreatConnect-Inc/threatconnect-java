package com.threatconnect.sdk.server.entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.threatconnect.sdk.server.response.entity.data.BatchResponseData;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * Created by cblades on 6/10/2015.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "batch")
public class Batch
{
    @JsonSerialize(include= JsonSerialize.Inclusion.NON_NULL)
    @JsonProperty("batchId")
    private int batchId;

    public int getBatchId()
    {
        return batchId;
    }

    public void setBatchId(int batchId)
    {
        this.batchId = batchId;
    }
}
