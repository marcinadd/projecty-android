package com.marcinadd.projecty.project;

import android.content.Context;

import com.marcinadd.projecty.client.AuthorizedNetworkClient;
import com.marcinadd.projecty.listener.ManageProjectResponseListener;
import com.marcinadd.projecty.listener.ProjectListResponseListener;
import com.marcinadd.projecty.project.model.ManageProject;
import com.marcinadd.projecty.project.model.UserProject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
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

    public void getProjects(final ProjectListResponseListener listener) {
        apiProject.getProjects().enqueue(new Callback<UserProject>() {
            @Override
            public void onResponse(Call<UserProject> call, Response<UserProject> response) {
                if (response.isSuccessful()) {
                    listener.onProjectListResponse(response.body());
                }
            }

            @Override
            public void onFailure(Call<UserProject> call, Throwable t) {

            }
        });
    }

    public void manageProject(final long projectId, final ManageProjectResponseListener listener) {
        apiProject.manageProject(projectId).enqueue(new Callback<ManageProject>() {
            @Override
            public void onResponse(Call<ManageProject> call, Response<ManageProject> response) {
                if (response.isSuccessful()) {
                    listener.onManageProjectResponse(response.body());
                }
            }

            @Override
            public void onFailure(Call<ManageProject> call, Throwable t) {

            }
        });
    }

}
