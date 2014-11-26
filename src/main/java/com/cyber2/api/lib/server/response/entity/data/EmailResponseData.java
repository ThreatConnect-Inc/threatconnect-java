/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyber2.api.lib.server.response.entity.data;

import com.cyber2.api.lib.server.entity.Email;
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
public class EmailResponseData extends ApiEntitySingleResponseData<Email>
{
    @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    @XmlElement(name = "Email", required = false)
    private Email email;
    
    public Email getEmail()
    {
        return email;
    }

    public void setEmail(Email email)
    {
        this.email = email;
    }

    @Override
    @JsonIgnore
    public Email getData()
    {
        return getEmail();
    }

    @Override
    public void setData(Email data)
    {
        setEmail(data);
    }
}
