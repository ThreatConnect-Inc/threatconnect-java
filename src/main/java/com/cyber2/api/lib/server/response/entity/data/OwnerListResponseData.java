/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyber2.api.lib.server.response.entity.data;

import com.cyber2.api.lib.server.entity.Owner;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 *
 * @author James
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class OwnerListResponseData extends ApiEntityListResponseData<Owner>
{
    @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    @XmlElement(name = "Owner", required = false)
    private List<Owner> owner;
    
    public List<Owner> getOwner()
    {
        return owner;
    }

    public void setOwner(List<Owner> owner)
    {
        this.owner = owner;
    }

    @Override
    @JsonIgnore
    public List<Owner> getData()
    {
        return getOwner();
    }

    @Override
    public void setData(List<Owner> data)
    {
        setOwner(data);
    }
}
