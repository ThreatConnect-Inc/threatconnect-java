/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.server.response.entity;

import com.threatconnect.sdk.server.entity.Address;
import com.threatconnect.sdk.server.entity.BulkStatus;
import com.threatconnect.sdk.server.response.entity.data.AddressListResponseData;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 *
 * @author James
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "addressesResponse")
@XmlSeeAlso(BulkStatus.class)
public class AddressListResponse extends ApiEntityListResponse<Address, AddressListResponseData>
{
    public void setData(AddressListResponseData data) {
        super.setData(data); 
    }
}
