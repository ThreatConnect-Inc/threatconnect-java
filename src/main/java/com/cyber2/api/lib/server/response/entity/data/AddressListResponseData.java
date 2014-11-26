/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyber2.api.lib.server.response.entity.data;

import com.cyber2.api.lib.server.entity.Address;
import java.util.List;
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
public class AddressListResponseData extends ApiEntityListResponseData<Address>
{
    @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    @XmlElement(name = "Address", required = false)
    private List<Address> address;
    
    public List<Address> getAddress()
    {
        return address;
    }

    public void setAddress(List<Address> address)
    {
        this.address = address;
    }

    @Override
    @JsonIgnore
    public List<Address> getData()
    {
        return getAddress();
    }

    @Override
    public void setData(List<Address> data)
    {
        setAddress(data);
    }
}
