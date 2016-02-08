/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.server.response.entity;

import com.threatconnect.sdk.server.entity.VictimNetworkAccount;
import com.threatconnect.sdk.server.response.entity.data.VictimNetworkAccountResponseData;
import com.threatconnect.sdk.server.response.service.ApiServiceResponse;
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
@XmlSeeAlso(VictimNetworkAccount.class)
public class VictimNetworkAccountResponse extends ApiEntitySingleResponse<VictimNetworkAccount, VictimNetworkAccountResponseData>
{
    public void setData(VictimNetworkAccountResponseData data) {
        super.setData(data); 
    }
}
