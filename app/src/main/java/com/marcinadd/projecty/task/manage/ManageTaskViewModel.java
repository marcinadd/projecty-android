package com.marcinadd.projecty.task.manage;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.marcinadd.projecty.task.model.Task;

public class ManageTaskViewModel extends ViewModel {

    private MutableLiveData<Task> task;

    public MutableLiveData<Task> getTask() {
        if (task == null) {
            task = new MutableLiveData<Task>();
        }
        return task;
    }
}
