package com.marcinadd.projecty.project;

import com.marcinadd.projecty.project.model.ManageProject;
import com.marcinadd.projecty.project.model.UserProject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ProjectClient {
    @GET("project/myProjects")
    Call<UserProject> getProjects();

    @GET("project/manageProject")
    Call<ManageProject> manageProject(@Query("projectId") long projectId);

    @POST("project/changeName")
    Call<Void> changeName(@Query("id") long projectId, @Query("name") String newName);

    @POST("project/changeRole")
    Call<Void> changeRole(@Query("projectId") long projectId, @Query("roleId") long roleId, @Query("newRoleName") String newRoleName);

    @POST("project/deleteUser")
    Call<Void> deleteUser(@Query("projectId") long projectId, @Query("userId") long userId);

    @POST("project/addUsers")
    Call<Void> addUsers(@Query("projectId") long projectId, @Query("usernames") List<String> usernames);
}