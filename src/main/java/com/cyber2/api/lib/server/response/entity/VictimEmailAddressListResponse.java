/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyber2.api.lib.server.response.entity;

import com.cyber2.api.lib.server.entity.VictimEmailAddress;
import com.cyber2.api.lib.server.response.entity.data.VictimEmailAddressListResponseData;
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
@XmlRootElement(name = "victimEmailAddressesResponse")
@XmlSeeAlso(VictimEmailAddress.class)
public class VictimEmailAddressListResponse extends ApiEntityListResponse<VictimEmailAddress, VictimEmailAddressListResponseData>
{
    public void setData(VictimEmailAddressListResponseData data) {
        super.setData(data); 
    }
}
