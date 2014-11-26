/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyber2.api.lib.server.response.entity;

import com.cyber2.api.lib.server.entity.VictimEmailAddress;
import com.cyber2.api.lib.server.response.entity.data.VictimEmailAddressResponseData;
import com.cyber2.api.lib.server.response.service.ApiServiceResponse;
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
