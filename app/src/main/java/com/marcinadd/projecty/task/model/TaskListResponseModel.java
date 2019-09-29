package com.marcinadd.projecty.task.model;

import com.marcinadd.projecty.project.model.Project;

import java.io.Serializable;
import java.util.List;

public class TaskListResponseModel implements Serializable {
    private List<Task> toDoTasks;
    private List<Task> inProgressTasks;
    private List<Task> doneTasks;
    private Project project;
    private boolean hasPermissionToEdit;

    public List<Task> getToDoTasks() {
        return toDoTasks;
    }

    public List<Task> getInProgressTasks() {
        return inProgressTasks;
    }

    public List<Task> getDoneTasks() {
        return doneTasks;
    }

    public Project getProject() {
        return project;
    }

    public boolean isHasPermissionToEdit() {
        return hasPermissionToEdit;
    }
}
