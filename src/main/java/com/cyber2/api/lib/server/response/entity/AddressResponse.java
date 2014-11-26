/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyber2.api.lib.server.response.entity;

import com.cyber2.api.lib.server.entity.Address;
import com.cyber2.api.lib.server.response.entity.data.AddressResponseData;
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
@XmlRootElement(name = "addressResponse")
@XmlSeeAlso(Address.class)
public class AddressResponse extends ApiEntitySingleResponse<Address, AddressResponseData>
{
    public void setData(AddressResponseData data) {
        super.setData(data); 
    }
}
