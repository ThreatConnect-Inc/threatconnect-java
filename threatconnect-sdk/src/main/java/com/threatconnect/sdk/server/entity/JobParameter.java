package com.threatconnect.sdk.server.entity;

/**
 * Created by dtineo on 9/7/15.
 */

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@XmlRootElement(name = "JobParameter")
@XmlAccessorType(XmlAccessType.FIELD)
public class JobParameter
{
    @XmlElement(name = "name", required = true)
    private String name;
    @XmlElement(name = "value", required = false)
    private String value;
    @XmlElement(name = "encrypted", required = true)
    private boolean encrypted;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getValue()
    {
        return value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }

    public boolean isEncrypted()
    {
        return encrypted;
    }

    public void setEncrypted(boolean encrypted)
    {
        this.encrypted = encrypted;
    }

}
