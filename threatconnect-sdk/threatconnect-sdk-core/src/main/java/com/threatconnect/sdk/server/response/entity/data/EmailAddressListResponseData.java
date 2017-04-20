/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.server.response.entity.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.threatconnect.sdk.server.entity.EmailAddress;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author James
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class EmailAddressListResponseData extends ApiEntityListResponseData<EmailAddress>
{
    @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    @XmlElement(name = "EmailAddress", required = false)
    private List<EmailAddress> emailAddress;
    
    public List<EmailAddress> getEmailAddress()
    {
        return emailAddress;
    }

    public void setEmailAddress(List<EmailAddress> emailAddress)
    {
        this.emailAddress = emailAddress;
    }

    @Override
    @JsonIgnore
    public List<EmailAddress> getData()
    {
        return getEmailAddress();
    }

    @Override
    public void setData(List<EmailAddress> data)
    {
        setEmailAddress(data);
    }
}
