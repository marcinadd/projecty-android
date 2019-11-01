package com.marcinadd.projecty.project;

import com.marcinadd.projecty.project.model.ManageProject;
import com.marcinadd.projecty.project.model.Project;
import com.marcinadd.projecty.project.model.UserProject;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiProject {
    @GET("projects")
    Call<UserProject> getProjects();

    @GET("projects/{projectId}?roles=true")
    Call<ManageProject> manageProject(@Path("projectId") long projectId);

    @PATCH("projects/{projectId}")
    Call<Void> updateProject(@Path("projectId") long projectId, @Body Map<String, String> fields);

    @PATCH("projectRoles/{projectRoleId}")
    Call<Void> changeRole(@Path("projectRoleId") long projectRoleId, @Body Map<String, String> fields);

    @DELETE("projectRoles/{projectRoleId}")
    Call<Void> deleteUser(@Path("projectRoleId") long projectRoleId);

    @POST("projects/{projectId}/roles")
    Call<Void> addUsers(@Path("projectId") long projectId, @Body List<String> usernames);

    @DELETE("projects/{projectId}")
    Call<Void> deleteProject(@Path("projectId") long projectId);

    @POST("projects")
    Call<Void> addProject(@Body Project project);
}