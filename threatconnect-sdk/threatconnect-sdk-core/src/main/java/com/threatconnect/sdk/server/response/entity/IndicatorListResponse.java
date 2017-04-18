/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.server.response.entity;

import com.threatconnect.sdk.server.entity.Indicator;
import com.threatconnect.sdk.server.response.entity.data.IndicatorListResponseData;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 *
 * @author James
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "indicatorsResponse")
@XmlSeeAlso(Indicator.class)
public class IndicatorListResponse extends ApiEntityListResponse<Indicator, IndicatorListResponseData>
{
    public void setData(IndicatorListResponseData data) {
        super.setData(data); 
    }
}
