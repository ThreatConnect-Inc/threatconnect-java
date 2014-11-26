/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyber2.api.lib.server.response.entity;

import com.cyber2.api.lib.server.entity.Email;
import com.cyber2.api.lib.server.response.entity.data.EmailResponseData;
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
@XmlRootElement(name = "emailResponse")
@XmlSeeAlso(Email.class)
public class EmailResponse extends ApiEntitySingleResponse<Email, EmailResponseData>
{
    public void setData(EmailResponseData data) {
        super.setData(data); 
    }
}
