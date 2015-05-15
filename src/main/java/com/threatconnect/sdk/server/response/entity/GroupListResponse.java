/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.server.response.entity;

import com.threatconnect.sdk.server.entity.Group;
import com.threatconnect.sdk.server.response.entity.data.GroupListResponseData;

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
