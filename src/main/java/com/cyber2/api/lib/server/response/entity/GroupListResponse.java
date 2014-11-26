/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyber2.api.lib.server.response.entity;

import com.cyber2.api.lib.server.entity.Group;
import com.cyber2.api.lib.server.response.entity.data.ApiEntityListResponseData;
import com.cyber2.api.lib.server.response.entity.data.GroupListResponseData;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 *
 * @author James
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "groupsResponse")
@XmlSeeAlso(Group.class)
public class GroupListResponse extends ApiEntityListResponse<Group, GroupListResponseData>
{
    public void setData(GroupListResponseData data) {
        super.setData(data);
    }

}
