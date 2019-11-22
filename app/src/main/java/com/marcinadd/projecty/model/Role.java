package com.marcinadd.projecty.model;

import com.marcinadd.projecty.project.model.User;

public class Role {
    private long id;
    private Roles name;
    private User user;

    public long getId() {
        return id;
    }

    public Roles getName() {
        return name;
    }

    public User getUser() {
        return user;
    }
}
