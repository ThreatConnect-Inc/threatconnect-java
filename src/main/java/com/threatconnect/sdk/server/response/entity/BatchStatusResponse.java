package com.threatconnect.sdk.server.response.entity;


import com.threatconnect.sdk.server.entity.BatchStatus;
import com.threatconnect.sdk.server.response.entity.data.BatchResponseData;
import com.threatconnect.sdk.server.response.entity.data.BatchStatusResponseData;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * Created by cblades on 6/10/2015.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "batchStatusResponse")
@XmlSeeAlso(BatchStatus.class)
public class BatchStatusResponse extends ApiEntitySingleResponse<BatchStatus, BatchStatusResponseData>
{
    public void setData(BatchStatusResponseData data) {
        super.setData(data);
    }
}
