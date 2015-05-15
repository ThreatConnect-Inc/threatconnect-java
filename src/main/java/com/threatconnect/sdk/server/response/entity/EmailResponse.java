/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.server.response.entity;

import com.threatconnect.sdk.server.entity.Email;
import com.threatconnect.sdk.server.response.entity.data.EmailResponseData;
import com.threatconnect.sdk.server.response.service.ApiServiceResponse;
import com.threatconnect.sdk.server.entity.Email;
import com.threatconnect.sdk.server.response.entity.data.EmailResponseData;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 *
 * @author James
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "emailResponse")
@XmlSeeAlso(Email.class)
public class EmailResponse extends ApiEntitySingleResponse<Email, EmailResponseData>
{
    public void setData(EmailResponseData data) {
        super.setData(data); 
    }
}
