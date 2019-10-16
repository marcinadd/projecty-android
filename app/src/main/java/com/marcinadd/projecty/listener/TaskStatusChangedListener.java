package com.marcinadd.projecty.listener;

import com.marcinadd.projecty.task.TaskStatus;
import com.marcinadd.projecty.task.model.Task;

public interface TaskStatusChangedListener {
    void onTaskStatusChanged(Task task, TaskStatus newTaskStatus);
}
