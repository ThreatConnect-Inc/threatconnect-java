package com.threatconnect.sdk.server.response.entity.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.threatconnect.sdk.server.entity.User;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * Created by moweis-ad on 3/18/16.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class UserResponseData extends ApiEntitySingleResponseData<User> {

    @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    @XmlElement(name="User", required=false)
    private User user;

    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }

    @JsonIgnore
    @Override
    public User getData() { return getUser(); }

    @Override
    public void setData(User data) { setUser(data);}
}
