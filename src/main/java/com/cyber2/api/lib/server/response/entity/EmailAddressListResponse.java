/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyber2.api.lib.server.response.entity;

import com.cyber2.api.lib.server.entity.EmailAddress;
import com.cyber2.api.lib.server.response.entity.data.EmailAddressListResponseData;
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
@XmlRootElement(name = "emailAddressesResponse")
@XmlSeeAlso(EmailAddress.class)
public class EmailAddressListResponse extends ApiEntityListResponse<EmailAddress, EmailAddressListResponseData>
{
    public void setData(EmailAddressListResponseData data) {
        super.setData(data); 
    }
}
