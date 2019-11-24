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

    public void setId(long id) {
        this.id = id;
    }

    public void setName(Roles name) {
        this.name = name;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name=" + name +
                ", user=" + user +
                '}';
    }
}
