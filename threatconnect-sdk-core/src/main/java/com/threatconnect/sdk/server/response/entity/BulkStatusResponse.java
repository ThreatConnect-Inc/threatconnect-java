package com.threatconnect.sdk.server.response.entity;

import com.threatconnect.sdk.server.entity.Address;
import com.threatconnect.sdk.server.entity.BulkStatus;
import com.threatconnect.sdk.server.response.entity.data.AddressResponseData;
import com.threatconnect.sdk.server.response.entity.data.BulkStatusResponseData;
import com.threatconnect.sdk.server.response.entity.data.BulkStatusResponseData;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * Created by cblades on 4/21/2015.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "BulkStatus")
@XmlSeeAlso(BulkStatus.class)
public class BulkStatusResponse extends ApiEntitySingleResponse<BulkStatus, BulkStatusResponseData>
{
    public void setData(BulkStatusResponseData data)
    {
        super.setData(data);
    }
}
