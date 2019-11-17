package com.marcinadd.projecty.project;

import android.content.Context;

import com.marcinadd.projecty.callback.RetrofitCallback;
import com.marcinadd.projecty.client.AuthorizedNetworkClient;
import com.marcinadd.projecty.listener.RetrofitListener;
import com.marcinadd.projecty.project.model.ManageProject;
import com.marcinadd.projecty.project.model.Project;
import com.marcinadd.projecty.project.model.UserProject;

import retrofit2.Retrofit;

public class ProjectService {
    private static ProjectService projectService;
    private ApiProject apiProject;

    private ProjectService(Context context) {
        Retrofit retrofit = AuthorizedNetworkClient.getRetrofitClient(context);
        apiProject = retrofit.create(ApiProject.class);
    }

    public static ProjectService getInstance(Context context) {
        if (projectService == null) {
            projectService = new ProjectService(context);
        }
        return projectService;
    }

    public void getProjects(final RetrofitListener<UserProject> listener) {
        apiProject.getProjects().enqueue(new RetrofitCallback<>(listener));
    }

    public void manageProject(final long projectId, final RetrofitListener<ManageProject> listener) {
        apiProject.manageProject(projectId).enqueue(new RetrofitCallback<>(listener));
    }

    public void addProject(final Project project, final RetrofitListener<Void> listener) {
        apiProject.addProject(project).enqueue(new RetrofitCallback<>(listener));
    }
}
