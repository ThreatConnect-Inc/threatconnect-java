package com.threatconnect.sdk.server.response.entity;

import com.threatconnect.sdk.server.entity.User;
import com.threatconnect.sdk.server.response.entity.data.UserListResponseData;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * Created by moweis-ad on 3/18/16.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "usersResponse")
@XmlSeeAlso(User.class)
public class UserListResponse extends ApiEntityListResponse<User, UserListResponseData> {

    public void setData(UserListResponseData data) { super.setData(data); }
}
