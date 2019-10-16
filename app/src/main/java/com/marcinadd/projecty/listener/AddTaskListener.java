package com.marcinadd.projecty.listener;

import com.marcinadd.projecty.task.model.Task;

public interface AddTaskListener {
    void onTaskAdded(Task newTask);
    void onAddFailed();
}
