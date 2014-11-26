/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyber2.api.lib.server.response.entity.data;

import com.cyber2.api.lib.server.entity.EmailAddress;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 *
 * @author James
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class EmailAddressResponseData extends ApiEntitySingleResponseData<EmailAddress>
{
    @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    @XmlElement(name = "EmailAddress", required = false)
    private EmailAddress emailAddress;
    
    public EmailAddress getEmailAddress()
    {
        return emailAddress;
    }

    public void setEmailAddress(EmailAddress emailAddress)
    {
        this.emailAddress = emailAddress;
    }

    @Override
    @JsonIgnore
    public EmailAddress getData()
    {
        return getEmailAddress();
    }

    @Override
    public void setData(EmailAddress data)
    {
        setEmailAddress(data);
    }
}
