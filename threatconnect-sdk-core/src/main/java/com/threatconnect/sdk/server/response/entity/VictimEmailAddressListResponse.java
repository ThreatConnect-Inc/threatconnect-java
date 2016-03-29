/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.server.response.entity;

import com.threatconnect.sdk.server.entity.VictimEmailAddress;
import com.threatconnect.sdk.server.response.entity.data.VictimEmailAddressListResponseData;
import com.threatconnect.sdk.server.response.service.ApiServiceResponse;
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
@XmlRootElement(name = "victimEmailAddressesResponse")
@XmlSeeAlso(VictimEmailAddress.class)
public class VictimEmailAddressListResponse extends ApiEntityListResponse<VictimEmailAddress, VictimEmailAddressListResponseData>
{
    public void setData(VictimEmailAddressListResponseData data) {
        super.setData(data); 
    }
}
