/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.server.response.entity;

import com.threatconnect.sdk.server.entity.Email;
import com.threatconnect.sdk.server.response.entity.data.EmailListResponseData;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 *
 * @author James
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "emailsResponse")
@XmlSeeAlso(Email.class)
public class EmailListResponse extends ApiEntityListResponse<Email, EmailListResponseData>
{
    public void setData(EmailListResponseData data) {
        super.setData(data); 
    }
}
