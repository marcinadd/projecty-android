package com.marcinadd.projecty.project.model;

import java.io.Serializable;
import java.util.List;

public class ManageProject implements Serializable {
    private Project project;
    private List<ProjectRole> projectRoles;

    public Project getProject() {
        return project;
    }

    public List<ProjectRole> getProjectRoles() {
        return projectRoles;
    }
}
