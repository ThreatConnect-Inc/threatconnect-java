package com.threatconnect.sdk.server.response.entity.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.threatconnect.sdk.server.entity.User;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

/**
 * Created by moweis-ad on 3/18/16.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class UserListResponseData extends ApiEntityListResponseData<User> {

    @JsonSerialize(include= JsonSerialize.Inclusion.NON_NULL)
    @XmlElement(name="User", required=false)
    private List<User> user;

    public List<User> getUser() { return user;  }

    public void setUser(List<User> user) { this.user = user; }

    @JsonIgnore
    @Override
    public List<User> getData() { return getUser(); }

    @Override
    public void setData(List<User> data) { setUser(data); }
}
