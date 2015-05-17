/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.server.response.entity.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.threatconnect.sdk.server.entity.Email;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author James
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class EmailListResponseData extends ApiEntityListResponseData<Email>
{
    @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    @XmlElement(name = "Email", required = false)
    private List<Email> email;
    
    public List<Email> getEmail()
    {
        return email;
    }

    public void setEmail(List<Email> email)
    {
        this.email = email;
    }

    @Override
    @JsonIgnore
    public List<Email> getData()
    {
        return getEmail();
    }

    @Override
    public void setData(List<Email> data)
    {
        setEmail(data);
    }
}
