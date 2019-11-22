package com.marcinadd.projecty.project.model;

import com.marcinadd.projecty.model.Role;

import java.io.Serializable;

public class ProjectRole extends Role implements Serializable {
    private Project project;

    public Project getProject() {
        return project;
    }
}
