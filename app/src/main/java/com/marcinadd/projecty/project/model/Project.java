package com.marcinadd.projecty.project.model;

import com.marcinadd.projecty.task.TaskStatus;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class Project implements Serializable {
    private List<String> usernames;
    private long id;
    private String name;
    private Map<TaskStatus, Long> taskSummary;

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

    public Map<TaskStatus, Long> getTaskSummary() {
        return taskSummary;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
