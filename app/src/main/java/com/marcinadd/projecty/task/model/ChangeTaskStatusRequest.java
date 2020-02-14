package com.marcinadd.projecty.task.model;

import com.marcinadd.projecty.task.TaskStatus;

public class ChangeTaskStatusRequest {
    private TaskStatus status;
    public ChangeTaskStatusRequest(TaskStatus status) {
        this.status = status;
    }
}
