/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyber2.api.lib.server.response.entity;

import com.cyber2.api.lib.server.entity.EmailAddress;
import com.cyber2.api.lib.server.response.entity.data.EmailAddressResponseData;
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
@XmlRootElement(name = "emailAddressResponse")
@XmlSeeAlso(EmailAddress.class)
public class EmailAddressResponse extends ApiEntitySingleResponse<EmailAddress, EmailAddressResponseData>
{
    public void setData(EmailAddressResponseData data) {
        super.setData(data); 
    }
}
