package com.threatconnect.sdk.server.response.entity;


import com.threatconnect.sdk.server.response.entity.data.BatchResponseData;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * Created by cblades on 6/10/2015.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "batchResponse")
@XmlSeeAlso(Long.class)
public class BatchResponse extends ApiEntitySingleResponse<Long, BatchResponseData>
{
    public void setData(BatchResponseData data) {
        super.setData(data);
    }
}
