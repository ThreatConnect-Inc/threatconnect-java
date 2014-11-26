/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyber2.api.lib.server.response.entity;

import com.cyber2.api.lib.server.entity.Indicator;
import com.cyber2.api.lib.server.response.entity.data.IndicatorListResponseData;
import com.cyber2.api.lib.server.response.service.ApiServiceResponse;
import java.util.List;
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
