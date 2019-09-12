package com.marcinadd.projecty.project;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ProjectClient {
    @GET("project/myProjects")
    Call<UserProject> getProjects();

    @GET("project/manageProject")
    Call<Map<String, Object>> manageProject(@Query("projectId") long projectId);
}