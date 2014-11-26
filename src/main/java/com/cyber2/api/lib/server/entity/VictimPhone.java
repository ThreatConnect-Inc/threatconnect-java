/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyber2.api.lib.server.entity;

import com.cyber2.api.lib.server.entity.init.ApiEntityInit;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 *
 * @author James
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "VictimPhone")
public class VictimPhone extends VictimAsset
{
    @XmlElement(name = "PhoneType", required = true)
    private String phoneType;
    
    public VictimPhone()
    {
    }
    
    public String getPhoneType()
    {
        return phoneType;
    }

    public void setPhoneType(String phoneType)
    {
        this.phoneType = phoneType;
    }
    
}
