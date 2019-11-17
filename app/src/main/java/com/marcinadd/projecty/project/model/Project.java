package com.marcinadd.projecty.project.model;

import java.io.Serializable;
import java.util.List;

public class Project implements Serializable {
    private List<String> usernames;

    public Project() {
    }

    private long id;
    private String name;

    public Project(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getUsernames() {
        return usernames;
    }

    public void setUsernames(List<String> usernames) {
        this.usernames = usernames;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
