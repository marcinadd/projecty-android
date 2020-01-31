package com.marcinadd.projecty.project.model;

import java.io.Serializable;

public class User implements Serializable {
    private long id;
    private String username;

    public User(long id, String username) {
        this.id = id;
        this.username = username;
    }

    public User() {
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "ChatUser{" +
                "id=" + id +
                ", username='" + username + '\'' +
                '}';
    }
}
