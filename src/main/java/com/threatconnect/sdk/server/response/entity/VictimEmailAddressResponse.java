/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.server.response.entity;

import com.threatconnect.sdk.server.entity.VictimEmailAddress;
import com.threatconnect.sdk.server.response.entity.data.VictimEmailAddressResponseData;
import com.threatconnect.sdk.server.response.service.ApiServiceResponse;
import com.threatconnect.sdk.server.response.entity.data.VictimEmailAddressResponseData;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 *
 * @author James
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "victimEmailAddressResponse")
@XmlSeeAlso(VictimEmailAddress.class)
public class VictimEmailAddressResponse extends ApiEntitySingleResponse<VictimEmailAddress, VictimEmailAddressResponseData>
{
    public void setData(VictimEmailAddressResponseData data) {
        super.setData(data); 
    }
}
