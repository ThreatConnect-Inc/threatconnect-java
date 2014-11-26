/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyber2.api.lib.server.response.entity.data;

import com.cyber2.api.lib.server.entity.Tag;
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
