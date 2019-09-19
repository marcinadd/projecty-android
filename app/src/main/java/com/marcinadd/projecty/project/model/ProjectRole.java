package com.marcinadd.projecty.project.model;

import java.io.Serializable;

public class ProjectRole implements Serializable {
    private long id;
    private String name;
    private Project project;
    private User user;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Project getProject() {
        return project;
    }

    public User getUser() {
        return user;
    }
}
