package com.threatconnect.sdk.client.fluent;

import com.threatconnect.sdk.server.entity.User;

/**
 * Created by moweis-ad on 3/21/16.
 */
public class UserBuilder {

    private String userName;
    private String firstName;
    private String lastName;
    private String role;

    public UserBuilder withUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public UserBuilder withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public UserBuilder withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public UserBuilder withRole(String role) {
        this.role = role;
        return this;
    }

    public User createUser() {
        return new User(userName, firstName, lastName, role);
    }
}
