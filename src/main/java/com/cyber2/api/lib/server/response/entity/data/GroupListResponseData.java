/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyber2.api.lib.server.response.entity.data;

import com.cyber2.api.lib.server.entity.Group;
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
public class GroupListResponseData extends ApiEntityListResponseData<Group>
{
    @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    @XmlElement(name = "Group", required = false)
    private List<Group> group;
    
    public List<Group> getGroup()
    {
        return group;
    }

    public void setGroup(List<Group> group)
    {
        this.group = group;
    }

    @Override
    @JsonIgnore
    public List<Group> getData()
    {
        return getGroup();
    }

    @Override
    public void setData(List<Group> data)
    {
        setGroup(data);
    }
}
