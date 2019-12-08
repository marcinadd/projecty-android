package com.marcinadd.projecty.team.model;

import com.marcinadd.projecty.project.model.Project;

import java.util.List;

public class TeamProjectListResponseModel {
    boolean isCurrentUserTeamManager;
    private String teamName;
    private List<Project> projects;

    public String getTeamName() {
        return teamName;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public boolean isCurrentUserTeamManager() {
        return isCurrentUserTeamManager;
    }
}
