package com.marcinadd.projecty.ui.project;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.marcinadd.projecty.project.model.UserProject;

public class ProjectViewModel extends ViewModel {
    private MutableLiveData<UserProject> mUserProject;

    public ProjectViewModel(UserProject response) {
        mUserProject = new MutableLiveData<>();
        mUserProject.setValue(response);
    }

    public MutableLiveData<UserProject> getmUserProject() {
        return mUserProject;
    }
}
