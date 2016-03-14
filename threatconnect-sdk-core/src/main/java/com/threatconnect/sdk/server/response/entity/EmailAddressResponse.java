/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.server.response.entity;

import com.threatconnect.sdk.server.entity.EmailAddress;
import com.threatconnect.sdk.server.response.entity.data.EmailAddressResponseData;
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
@XmlRootElement(name = "emailAddressResponse")
@XmlSeeAlso(EmailAddress.class)
public class EmailAddressResponse extends ApiEntitySingleResponse<EmailAddress, EmailAddressResponseData>
{
    public void setData(EmailAddressResponseData data) {
        super.setData(data); 
    }
}
