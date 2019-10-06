package com.marcinadd.projecty.task.model;

import java.util.List;

public class ManageTaskResponseModel {

    private Task task;

    private long projectId;

    private List<String> notAssignedUsernames;

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public List<String> getNotAssignedUsernames() {
        return notAssignedUsernames;
    }

    public void setNotAssignedUsernames(List<String> notAssignedUsernames) {
        this.notAssignedUsernames = notAssignedUsernames;
    }
}
