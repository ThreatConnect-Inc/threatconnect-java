/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyber2.api.lib.server.response.entity;

import com.cyber2.api.lib.server.entity.VictimNetworkAccount;
import com.cyber2.api.lib.server.response.entity.data.VictimNetworkAccountResponseData;
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
@XmlSeeAlso(VictimNetworkAccount.class)
public class VictimNetworkAccountResponse extends ApiEntitySingleResponse<VictimNetworkAccount, VictimNetworkAccountResponseData>
{
    public void setData(VictimNetworkAccountResponseData data) {
        super.setData(data); 
    }
}
