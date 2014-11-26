/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyber2.api.lib.server.response.entity.data;

import com.cyber2.api.lib.server.entity.Attribute;
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
public class AttributeListResponseData extends ApiEntityListResponseData<Attribute>
{
    @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    @XmlElement(name = "Attribute", required = false)
    private List<Attribute> attribute;
    
    public List<Attribute> getAttribute()
    {
        return attribute;
    }

    public void setAttribute(List<Attribute> attribute)
    {
        this.attribute = attribute;
    }

    @Override
    @JsonIgnore
    public List<Attribute> getData()
    {
        return getAttribute();
    }

    @Override
    public void setData(List<Attribute> data)
    {
        setAttribute(data);
    }
}
