/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.server.response.entity;

import com.threatconnect.sdk.server.entity.EmailAddress;
import com.threatconnect.sdk.server.response.entity.data.EmailAddressListResponseData;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 *
 * @author James
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "emailAddressesResponse")
@XmlSeeAlso(EmailAddress.class)
public class EmailAddressListResponse extends ApiEntityListResponse<EmailAddress, EmailAddressListResponseData>
{
    public void setData(EmailAddressListResponseData data) {
        super.setData(data); 
    }
}
