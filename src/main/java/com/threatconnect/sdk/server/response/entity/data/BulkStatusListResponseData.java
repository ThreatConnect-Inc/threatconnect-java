package com.threatconnect.sdk.server.response.entity.data;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.threatconnect.sdk.server.entity.BulkStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

/**
 * Created by cblades on 4/21/2015.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class BulkStatusListResponseData extends ApiEntityListResponseData<BulkStatus>
{
    @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    @XmlElement(name = "BulkStatus", required = false)
    private List<BulkStatus> bulkStatuses;

    public List<BulkStatus> getBulkStatuses()
    {
        return bulkStatuses;
    }

    public void setBulkStatuses(List<BulkStatus> bulkStatuses)
    {
        this.bulkStatuses = bulkStatuses;
    }


    @Override
    @JsonIgnore
    public List<BulkStatus> getData()
    {
        return getBulkStatuses();
    }

    @Override
    public void setData(List<BulkStatus> data)
    {
        setBulkStatuses(data);
    }
}
