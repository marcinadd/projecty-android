package com.marcinadd.projecty.project.model;

import java.io.Serializable;

public class User implements Serializable {
    private long id;
    private String username;

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                '}';
    }
}
