/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.server.response.entity.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.threatconnect.sdk.server.entity.Tag;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author James
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class TagListResponseData extends ApiEntityListResponseData<Tag>
{
    @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    @XmlElement(name = "Tag", required = false)
    private List<Tag> tag;
    
    public List<Tag> getTag()
    {
        return tag;
    }

    public void setTag(List<Tag> tag)
    {
        this.tag = tag;
    }

    @Override
    @JsonIgnore
    public List<Tag> getData()
    {
        return getTag();
    }

    @Override
    public void setData(List<Tag> data)
    {
        setTag(data);
    }
}
