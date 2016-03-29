package com.threatconnect.sdk.server.response.entity;

import com.threatconnect.sdk.server.entity.User;
import com.threatconnect.sdk.server.response.entity.data.UserResponseData;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * Created by moweis-ad on 3/18/16.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "userResponse")
@XmlSeeAlso(User.class)
public class UserResponse extends ApiEntitySingleResponse<User, UserResponseData> {

    public void setData(UserResponseData data) { super.setData(data); }
}
