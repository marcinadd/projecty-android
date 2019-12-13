package com.marcinadd.projecty.team.model;

import com.marcinadd.projecty.project.model.Project;

import java.io.Serializable;
import java.util.List;

public class Team implements Serializable {
    private Long id;
    private String name;
    private List<Project> projects;
    private List<String> usernames;
    private TeamSummary teamSummary;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public void setUsernames(List<String> usernames) {
        this.usernames = usernames;
    }

    public TeamSummary getTeamSummary() {
        return teamSummary;
    }
}
