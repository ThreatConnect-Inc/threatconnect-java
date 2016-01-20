/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.server.response.entity;

import com.threatconnect.sdk.server.entity.OwnerMetric;
import com.threatconnect.sdk.server.response.entity.data.OwnerMetricListResponseData;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 *
 * @author James
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "ownerMetricsResponse")
@XmlSeeAlso(OwnerMetric.class)
public class OwnerMetricListResponse extends ApiEntityListResponse<OwnerMetric, OwnerMetricListResponseData>
{
    public void setData(OwnerMetricListResponseData data) {
        super.setData(data);
    }
}
