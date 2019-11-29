package com.marcinadd.projecty.request;

import com.marcinadd.projecty.task.TaskStatus;

public class ChangeTaskStatusRequest {
    private TaskStatus status;
    public ChangeTaskStatusRequest(TaskStatus status) {
        this.status = status;
    }
}
