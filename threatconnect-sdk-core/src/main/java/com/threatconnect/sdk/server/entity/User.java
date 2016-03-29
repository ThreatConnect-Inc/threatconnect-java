package com.threatconnect.sdk.server.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by moweis-ad on 3/15/16.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "User")
public class User {

    @XmlElement(name = "UserName", required = true)
    private String userName;
    @XmlElement(name = "FirstName", required = false)
    private String firstName;
    @XmlElement(name = "LastName", required = false)
    private String lastName;
    @XmlElement(name = "Role", required = false)
    private String role;

    public User() {
    }

    public User(String userName, String firstName, String lastName, String role) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("User{")
                .append("userName='" + userName)
                .append("', ")
                .append("firstName='" + firstName)
                .append("', ")
                .append("lastName='" + lastName)
                .append("', ")
                .append("role='" + role)
                .append("'}")
                .toString();

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLastName() { return lastName; }

    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getFirstName() { return firstName; }

    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
