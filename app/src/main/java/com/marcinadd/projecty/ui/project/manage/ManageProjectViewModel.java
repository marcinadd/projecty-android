package com.marcinadd.projecty.ui.project.manage;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.marcinadd.projecty.project.model.Project;
import com.marcinadd.projecty.project.model.ProjectRole;

import java.util.List;

public class ManageProjectViewModel extends ViewModel {
    private MutableLiveData<Project> mProject;
    private MutableLiveData<List<ProjectRole>> mProjectRoles;

    public ManageProjectViewModel() {
        mProject = new MutableLiveData<>();
        mProjectRoles = new MutableLiveData<>();
    }

    public LiveData<Project> getProject() {
        return mProject;
    }

    public void setProject(Project project) {
        mProject.setValue(project);
    }

    public LiveData<List<ProjectRole>> getProjectRoles() {
        return mProjectRoles;
    }

    public void setProjectRoles(List<ProjectRole> projectRoles) {
        mProjectRoles.setValue(projectRoles);
    }
}
