/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.server.response.entity;

import com.threatconnect.sdk.server.entity.DnsResolution;
import com.threatconnect.sdk.server.response.entity.data.DnsResolutionListResponseData;
import com.threatconnect.sdk.server.response.service.ApiServiceResponse;
import com.threatconnect.sdk.server.entity.DnsResolution;
import com.threatconnect.sdk.server.response.entity.data.DnsResolutionListResponseData;

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
@XmlRootElement(name = "dnsResolutionsResponse")
@XmlSeeAlso(DnsResolution.class)
public class DnsResolutionListResponse extends ApiEntityListResponse<DnsResolution, DnsResolutionListResponseData>
{
    public void setData(DnsResolutionListResponseData data) {
        super.setData(data); 
    }
}
