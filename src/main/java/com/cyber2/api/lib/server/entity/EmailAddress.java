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
 * @author eric
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@XmlRootElement(name = "EmailAddress")
@XmlAccessorType(XmlAccessType.FIELD)
public class EmailAddress extends Indicator
{
    @XmlElement(name = "Address", required = true)
    private String address;
    
    public EmailAddress()
    {
        super();
    }
    
    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }
    
}
