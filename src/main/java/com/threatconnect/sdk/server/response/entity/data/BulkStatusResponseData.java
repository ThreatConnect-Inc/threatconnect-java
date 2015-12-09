package com.threatconnect.sdk.server.response.entity.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.threatconnect.sdk.server.entity.BulkStatus;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * Created by cblades on 4/21/2015.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class BulkStatusResponseData extends ApiEntitySingleResponseData<BulkStatus>
{
    @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    @XmlElement(name = "BulkStatus", required = false)
    private BulkStatus bulkStatus;

    public BulkStatus getBulkStatus()
    {
        return bulkStatus;
    }

    public void setBulkStatus(BulkStatus bulkStatus)
    {
        this.bulkStatus = bulkStatus;
    }

    @Override
    @JsonIgnore
    public BulkStatus getData()
    {
        return getBulkStatus();
    }

    @Override
    public void setData(BulkStatus data)
    {
        setBulkStatus(data);
    }
}
